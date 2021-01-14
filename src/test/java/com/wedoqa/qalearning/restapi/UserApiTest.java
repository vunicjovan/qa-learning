package com.wedoqa.qalearning.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedoqa.qalearning.restapi.generics.ApiTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserApiTest extends ApiTest {

    @Test
    @DisplayName("Total number of users is 10?")
    public void testNumberOfUsersShouldBe10() {
        given().when().get("/users")
                .then().assertThat().statusCode(200)
                .and()
                .assertThat().contentType(ContentType.JSON)
                .assertThat().body("id", hasSize(10))
                .log().all();
    }

    @ParameterizedTest(name = "ID: {0}")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void testUserMatchingWithGivenUserId(int id) {
        given().pathParam("id", id).when().get("/users/{id}")
                .then().assertThat().body("username", equalTo(USERNAME_LIST.get(id - 1)))
                .log().all();
    }

    @Test
    @DisplayName("User with ID=6 lives Apt. 950 in Norberto Crossing, South Christy?")
    public void testAddressOfUserWithGivenId() {
        given().pathParam("id", 6).when().get("/users/{id}")
                .then().assertThat().body("address.suite", equalTo("Apt. 950"))
                .body("address.street", equalTo("Norberto Crossing"))
                .body("address.city", equalTo("South Christy"))
                .log().all();
    }

    @Test
    @DisplayName("Check if new user with demanded properties is created")
    public void testNewUserCreation() {
        Map<Object, Object> someMap = new HashMap<>() {{
            put("name", "John Doe");
            put("username", "johndoe");
            put("email", "johndoe@email.com");
            put("address", new HashMap<>() {{
                put("street", "Some street");
                put("suite", "Some suite 11");
                put("city", "Some city");
                put("zipcode", "Some zipcode");
                put("geo", new HashMap<>() {{
                    put("lat", 34.55555);
                    put("lng", 43.55555);
                }});
            }});
            put("phone", "11-22-33-44-55");
            put("website", "johndoe.com");
            put("company", new HashMap<>() {{
                put("name", "Some company");
                put("catchPhrase", "Some catchphrase");
                put("bs", "Some business information");
            }});
        }};
        JSONObject newUser = new JSONObject(someMap);

        given().header("Content-type", "application/json").and().body(newUser.toString())
                .when().post("/users").then().statusCode(201)
                .log().all();
    }

    @Test
    @DisplayName("Check if patch operation for username has succeeded")
    public void testExistingUserPartialUpdate() throws JSONException {
        JSONObject patchUser = new JSONObject();
        patchUser.put("username", "James");

        given().header("Content-type", "application/json").and().body(patchUser.toString())
                .when().patch("/users/1").then()
                .statusCode(200)
                .body("username", equalTo("James"))
                .log().all();
    }

    @Test
    @DisplayName("Check if deletion of user with provided ID was successful")
    public void testDeleteExistingUser() {
        Response response = given().header("Content-type", "application/json").and().pathParam("id", 1)
                .when().delete("/users/{id}").then()
                .statusCode(200)
                .extract()
                .response();

        assertNull(response.jsonPath().getString("name"));
    }

}
