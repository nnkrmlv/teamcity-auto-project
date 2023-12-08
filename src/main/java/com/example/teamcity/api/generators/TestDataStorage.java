package com.example.teamcity.api.generators;

import java.util.ArrayList;
import java.util.List;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private List<TestData> testDataList;
    private TestDataStorage() {
        this.testDataList = new ArrayList<>();
    }

    public static TestDataStorage getTestDataStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    public static TestData addTestData() {
        var testData = TestDataGenerator.generate();
        addTestData(testData);
        return testData;
    }
    public static TestData addTestData(TestData testData) {
        getTestDataStorage().testDataList.add(testData);
        return testData;
    }

    public void delete() {
        testDataList.forEach(TestData::delete);
    }
}
