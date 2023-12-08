package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedProject extends Request implements CrudInterface {
    public UncheckedProject(RequestSpecification spec) {
        super(spec);
    }
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";


    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj).post(PROJECT_ENDPOINT);
    }


    @Override
    public Response get(String id) {
        return given()
                .spec(spec)
                .get(PROJECT_ENDPOINT + "/id:" + id);
    }

    @Override
    public Response update(String id, Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .put(PROJECT_ENDPOINT + "/id:" + id);
    }

    @Override
    public Response delete(String id) {
        return given()
                .spec(spec)
                .delete(PROJECT_ENDPOINT + "/id:" + id);
    }
}
