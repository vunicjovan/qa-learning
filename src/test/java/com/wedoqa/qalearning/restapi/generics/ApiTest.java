package com.wedoqa.qalearning.restapi.generics;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiTest {

    protected final static String USER_URL = "https://jsonplaceholder.typicode.com";
    protected static List<String> USERNAME_LIST = new ArrayList<>();

    @BeforeAll
    public static void initialSetup() {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = USER_URL;
        RestAssured.port = 443;
        USERNAME_LIST = fetchData("/users").jsonPath().getList("username");
    }

    @AfterAll
    public static void terminate() {
        RestAssured.reset();
    }

    public static Response fetchData(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                        when().get(endpoint).
                        then().contentType(ContentType.JSON).extract().response();
    }

}
