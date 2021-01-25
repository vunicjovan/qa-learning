package com.wedoqa.qalearning.restapi.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedoqa.qalearning.restapi.dto.UserDTO;
import com.wedoqa.qalearning.restapi.dto.UsersDTO;
import com.wedoqa.qalearning.restapi.generics.ApiTest;
import com.wedoqa.qalearning.restapi.utils.RequestObjectUtils;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserApiTest extends ApiTest {

    private final RequestObjectUtils requestObjectUtils = new RequestObjectUtils();
    private final ObjectMapper mapper = new ObjectMapper();

    @ParameterizedTest(name = "ID: {0}")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void testUserMatchingWithGivenUserId(int id) {
        UserDTO userDTO = executeMethod(
                "get",
                200,
                "users/{id}",
                null,
                UserDTO.class,
                requestSpecification.pathParam("id", id),
                "schemas/user-schema.json",
                ContentType.JSON
        );

        assertThat(userDTO.getUsername(), equalTo(USERNAME_LIST.get(id - 1)));
    }

    @Test
    @DisplayName("Check if new user with demanded properties is created")
    public void testNewUserCreation() {
        UserDTO userDTO = requestObjectUtils.postRequestUser();

        UserDTO createdUserDTO = executeMethod(
                "post",
                201,
                "/users",
                userDTO,
                UserDTO.class,
                requestSpecification,
                "schemas/user-schema.json",
                ContentType.JSON
        );

        assertThat(createdUserDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(userDTO);
    }

    @Test
    @DisplayName("Check if user with demanded ID is updated")
    public void testExistingUserUpdate() {
        UserDTO userDTO = requestObjectUtils.updateRequestUser();

        UserDTO updatedUserDTO = executeMethod(
                "put",
                200,
                "/users/1",
                userDTO,
                UserDTO.class,
                requestSpecification,
                null,
                ContentType.JSON
        );

        // make sure user is updated
        assertThat(updatedUserDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(userDTO);
    }

    @Test
    @DisplayName("Check if patch operation for username has succeeded")
    public void testExistingUserPartialUpdate() throws JSONException {
        JSONObject patchUser = new JSONObject();
        patchUser.put("username", "James");

        UserDTO userDTO = executeMethod(
                "patch",
                200,
                "/users/1",
                patchUser.toString(),
                UserDTO.class,
                requestSpecification,
                "schemas/user-schema.json",
                ContentType.JSON
        );

        assertThat(userDTO.getUsername(), equalTo("James"));
    }

    @Test
    @DisplayName("Check if deletion of user with provided ID was successful")
    public void testDeleteExistingUser() {
        UserDTO userDTO = executeMethod(
                "delete",
                200,
                "/users/{id}",
                null,
                UserDTO.class,
                requestSpecification.pathParam("id", 1),
                null,
                ContentType.JSON
        );

        assertNull(userDTO.getId());
    }

    @Test
    @DisplayName("Parse users to UsersDTO object")
    public void testUsersDtoObjectParsing() {
        List<UserDTO> users = mapper.convertValue(
                executeMethod(
                        "get",
                        200,
                        "/users",
                        null,
                        List.class,
                        requestSpecification,
                        "schemas/users-schema.json",
                        ContentType.JSON
                ),
                new TypeReference<>() {}
        );

        UsersDTO usersDTO = new UsersDTO(users);

        assertThat(usersDTO.getUsers(), hasSize(10));
        usersDTO.getUsers().forEach(System.out::println);
    }

}
