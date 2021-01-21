package com.wedoqa.qalearning.restapi.tests;

import com.wedoqa.qalearning.restapi.dto.*;
import com.wedoqa.qalearning.restapi.generics.ApiTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
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
                .assertThat().body(matchesJsonSchemaInClasspath("schemas/users-schema.json"))
                .and().assertThat().body("id", hasSize(10))
                .log().all();
    }

    @ParameterizedTest(name = "ID: {0}")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void testUserMatchingWithGivenUserId(int id) {


        given().pathParam("id", id).when().get("/users/{id}")
                .then().assertThat().body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .and().assertThat().body("username", equalTo(USERNAME_LIST.get(id - 1)))
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
        UserDTO userDTO = new UserDTO(
                "John Doe",
                "johndoe",
                "johndoe@email.com",
                new AddressDTO(
                        "Some street",
                        "Some suite",
                        "Some city",
                        "Some zipcode",
                        new GeoDTO("32.4567", "56.7889")
                ),
                "111/11223344",
                "johndoe.com",
                new CompanyDTO("Some company", "Some phrase", "Some business information")
        );

        given().header("Content-type", "application/json").and().body(userDTO)
                .when().post("/users").then().statusCode(201)
                .and().assertThat().body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .log().all();
    }

    @Test
    @DisplayName("Check if user with demanded ID is updated")
    public void testExistingUserUpdate() {
        UserDTO userDTO = new UserDTO(
                "John Doe",
                "johndoe",
                "johndoe@email.com",
                new AddressDTO(
                        "Some street",
                        "Some suite",
                        "Some city",
                        "Some zipcode",
                        new GeoDTO("32.4567", "56.7889")
                ),
                "111/11223344",
                "johndoe.com",
                new CompanyDTO("Some company", "Some phrase", "Some business information")
        );

        given().header("Content-type", "application/json").and().body(userDTO)
                .when().put("/users/1").then()
                .statusCode(200)
                .body("username", equalTo("johndoe"))
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

    @Test
    @DisplayName("Parse users to UsersDTO object")
    public void testUsersDtoObjectParsing() {
        Response response = given().when().get("/users")
                .then().assertThat().statusCode(200)
                .and()
                .assertThat().contentType(ContentType.JSON)
                .assertThat().body(matchesJsonSchemaInClasspath("schemas/users-schema.json"))
                .and().assertThat().body("id", hasSize(10))
                .log().all()
                .extract().response();

        UsersDTO usersDTO = new UsersDTO(Arrays.asList(response.getBody().as(UserDTO[].class)));

        usersDTO.getUsers().forEach(System.out::println);
    }

}
