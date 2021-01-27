package com.wedoqa.qalearning.restapi.generics;

import com.wedoqa.qalearning.restapi.enums.MethodType;
import com.wedoqa.qalearning.restapi.enums.SchemaType;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.ws.rs.HttpMethod;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiBaseTest {

    protected final static String USER_URL = "https://jsonplaceholder.typicode.com";
    protected static List<String> USERNAME_LIST = new ArrayList<>();
    protected static RequestSpecification requestSpecification;

    @BeforeAll
    public static void initialSetup() {
        // base RestAssured configuration
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = USER_URL;
        RestAssured.port = 443;
        USERNAME_LIST = fetchData("/users").jsonPath().getList("username");

        // base reusable request specification
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
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

    public <T> T executeMethod(MethodType method, int statusCode, String path, Object payload, Class<T> responseClass,
                               RequestSpecification specification, SchemaType schema, ContentType contentType) {
        ValidatableResponse response = prepareRequestConfig(method, path, payload, specification);
        assert response != null;
        response = assertStatusCodeAndContentType(response, statusCode, contentType);
        if (!schema.equals(SchemaType.UNDEFINED_SCHEMA)) response.assertThat().body(matchesJsonSchemaInClasspath(schema.getPath()));

        return extractResource(response, responseClass);
    }

    private ValidatableResponse prepareRequestConfig(MethodType method, String path, Object payload, RequestSpecification specification) {
        switch (method) {
            case POST:
                return given()
                        .spec(specification)
                        .body(payload)
                        .when()
                        .post(path)
                        .then();
            case PUT:
                return given()
                        .spec(specification)
                        .body(payload)
                        .when()
                        .put(path)
                        .then();
            case GET:
                return given()
                        .spec(specification)
                        .when()
                        .get(path)
                        .then();
            case DELETE:
                return given()
                        .spec(specification)
                        .when()
                        .delete(path)
                        .then();
            case PATCH:
                return given()
                        .spec(specification)
                        .body(payload)
                        .when()
                        .patch(path)
                        .then();
            default:
                return null;
        }
    }

    private ValidatableResponse assertStatusCodeAndContentType(ValidatableResponse response, int statusCode, ContentType contentType) {
        return response
                .assertThat()
                .statusCode(statusCode)
                .and()
                .assertThat()
                .contentType(contentType);
    }

    private <T> T extractResource(ValidatableResponse response, Class<T> responseClass) {
        return response
                .extract()
                .as(responseClass);
    }

}
