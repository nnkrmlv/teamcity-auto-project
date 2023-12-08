package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomData {
    private static int LENGHT = 10;
    public static String getString() {
        return "test_" + RandomStringUtils.randomAlphabetic(LENGHT);
    }
}
