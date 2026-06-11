# TeamCity Auto Project

API test automation framework for [JetBrains TeamCity](https://www.jetbrains.com/teamcity/), built with Java, REST Assured and TestNG. It exercises the TeamCity REST API to verify project creation, build configuration management, and role-based access control.

## Tech stack

| Purpose | Tool |
| --- | --- |
| Language | Java 20 |
| Build | Maven (with bundled `mvnw` wrapper) |
| HTTP / API testing | [REST Assured](https://rest-assured.io/) 5.1.1 |
| Test runner | TestNG 7.7.1 |
| Assertions | AssertJ (`SoftAssertions`) |
| Reporting | [Allure](https://allurereport.org/) 2.24.0 |
| Boilerplate | Lombok |
| Serialization | Gson, Jackson annotations |

## Project layout

```
src/
├── main/java/com/example/teamcity/api/
│   ├── config/        # Config — loads config.properties
│   ├── enums/         # Role enum (SYSTEM_ADMIN, PROJECT_ADMIN, ...)
│   ├── generators/    # Random + structured test data generation and cleanup
│   ├── models/        # POJOs: User, Project, BuildType, Role, Roles, ...
│   ├── requests/      # Request layer
│   │   ├── checked/   # Requests that assert 2xx and return deserialized models
│   │   └── unchecked/ # Requests that return the raw Response for negative tests
│   └── spec/          # Specifications — REST Assured request specs (auth/superuser/unauth)
├── main/resources/
│   └── config.properties   # host + superUserToken
└── test/java/com/example/teamcity/api/
    ├── BaseTest.java          # SoftAssertions lifecycle
    ├── BaseApiTest.java       # superuser requests + test data cleanup
    ├── CreateProjectTest.java
    ├── BuildConfigurationTest.java
    └── RolesTest.java         # role-based access control scenarios
```

### How it's organized

- **Specifications** builds REST Assured request specs for three auth modes: unauthenticated, basic-auth as a generated user, and superuser (token-based).
- **Checked vs. unchecked requests** — `checked/*` requests assert a successful status code and return the deserialized model; `unchecked/*` requests return the raw `Response` so negative cases can assert on error codes and bodies. `CheckedRequests` / `UncheckedRequests` bundle the per-entity requests (user, project, build config) behind one spec.
- **Test data** — `TestDataGenerator` builds randomized `User` / `Project` / `BuildType` objects, and `TestDataStorage` tracks created entities so `BaseApiTest` can clean them up after each test.

## Prerequisites

- JDK 20+
- A reachable TeamCity server
- Maven (optional — the repo includes the `mvnw` wrapper)

## Configuration

Edit `src/main/resources/config.properties` to point at your TeamCity instance:

```properties
host=<your-teamcity-host>:8111
superUserToken=<your-superuser-token>
```

The superuser token is printed to the TeamCity server log on startup (search for "Super user authentication token"). Requests are sent over `http://` to `host`.

> **Note:** `config.properties` currently holds a host and token. Replace them with your own and avoid committing real credentials.

## Running the tests

```bash
# all tests
./mvnw test

# a single test class
./mvnw test -Dtest=RolesTest

# a single method
./mvnw test -Dtest=RolesTest#systemAdminTestShouldHaveRightsToCreateProject
```

`testFailureIgnore` is enabled in the Surefire config, so the build completes even when tests fail — check the report for results.

## Allure reports

Results are written to `allure-results/`. To view the HTML report:

```bash
./mvnw allure:serve
```

(Requires the [Allure CLI](https://allurereport.org/docs/install/) for the standalone `allure serve allure-results` command.)

## Test coverage

- **CreateProject / BuildConfiguration** — creating projects and build configurations as an authenticated user.
- **Roles** — access control checks, e.g.:
  - unauthorized users cannot create projects
  - a system admin can create a project
  - a project admin can add a build config to **their** project but not to another project

## API request examples

The `request-examples/` directory contains `.http` files (`getRequests.http`, `buildConfigurationTest.http`) with sample TeamCity REST calls usable from IntelliJ's HTTP client.
