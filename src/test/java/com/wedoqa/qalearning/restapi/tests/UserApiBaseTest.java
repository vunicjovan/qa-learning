package com.wedoqa.qalearning.restapi.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedoqa.qalearning.restapi.dto.UserDTO;
import com.wedoqa.qalearning.restapi.dto.UsersDTO;
import com.wedoqa.qalearning.restapi.enums.MethodType;
import com.wedoqa.qalearning.restapi.enums.SchemaType;
import com.wedoqa.qalearning.restapi.generics.ApiBaseTest;
import com.wedoqa.qalearning.restapi.utils.RequestObjectUtils;
import com.wedoqa.qalearning.restapi.utils.UserAssertionProvider;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNull;

@Execution(ExecutionMode.SAME_THREAD)
public class UserApiBaseTest extends ApiBaseTest {

    private final RequestObjectUtils requestObjectUtils = new RequestObjectUtils();
    private final ObjectMapper mapper = new ObjectMapper();
    private final UserAssertionProvider assertionProvider = new UserAssertionProvider();

    @ParameterizedTest(name = "ID: {0}")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void testUserMatchingWithGivenUserId(int id) {
        UserDTO userDTO = executeMethod(
                MethodType.GET,
                200,
                "users/{id}",
                null,
                UserDTO.class,
                requestSpecification.pathParam("id", id),
                SchemaType.USER_SCHEMA,
                ContentType.JSON
        );

        // make sure that given user actually has corresponding username
        assertThat(userDTO.getUsername(), equalTo(USERNAME_LIST.get(id - 1)));
    }

    @Test
    @DisplayName("Check if new user with demanded properties is created")
    public void testNewUserCreation() {
        UserDTO userDTO = requestObjectUtils.postRequestUser();

        UserDTO createdUserDTO = executeMethod(
                MethodType.POST,
                201,
                "/users",
                userDTO,
                UserDTO.class,
                requestSpecification,
                SchemaType.USER_SCHEMA,
                ContentType.JSON
        );

        // make sure that created user has corresponding field values
        assertionProvider.assertObjectsAreEqualExcludingNullables(createdUserDTO, userDTO);
    }

    @Test
    @DisplayName("Check if user with demanded ID is updated")
    public void testExistingUserUpdate() {
        UserDTO userDTO = requestObjectUtils.updateRequestUser();

        UserDTO updatedUserDTO = executeMethod(
                MethodType.PUT,
                200,
                "/users/1",
                userDTO,
                UserDTO.class,
                requestSpecification,
                SchemaType.UNDEFINED_SCHEMA,
                ContentType.JSON
        );

        // make sure that user is updated
        assertionProvider.assertObjectsAreEqualExcludingNullables(updatedUserDTO, userDTO);
    }

    @Test
    @DisplayName("Check if patch operation for username has succeeded")
    public void testExistingUserPartialUpdate() throws JSONException {
        JSONObject patchUser = new JSONObject();
        patchUser.put("username", "James");

        UserDTO userDTO = executeMethod(
                MethodType.PATCH,
                200,
                "/users/1",
                patchUser.toString(),
                UserDTO.class,
                requestSpecification,
                SchemaType.USER_SCHEMA,
                ContentType.JSON
        );

        // make sure that PATCH operation has succeeded
        assertThat(userDTO.getUsername(), equalTo("James"));
    }

    @Test
    @DisplayName("Check if deletion of user with provided ID was successful")
    public void testDeleteExistingUser() {
        UserDTO userDTO = executeMethod(
                MethodType.DELETE,
                200,
                "/users/{id}",
                null,
                UserDTO.class,
                requestSpecification.pathParam("id", 1),
                SchemaType.UNDEFINED_SCHEMA,
                ContentType.JSON
        );

        // make sure that user with given ID does not exist anymore
        assertNull(userDTO.getId());
    }

    @Test
    @DisplayName("Parse users to UsersDTO object")
    public void testUsersDtoObjectParsing() {
        List<UserDTO> users = mapper.convertValue(
                executeMethod(
                        MethodType.GET,
                        200,
                        "/users",
                        null,
                        List.class,
                        requestSpecification,
                        SchemaType.USERS_SCHEMA,
                        ContentType.JSON
                ),
                new TypeReference<>() {}
        );

        UsersDTO usersDTO = new UsersDTO(users);

        // check size of returned data and pretty-print it
        assertThat(usersDTO.getUsers(), hasSize(10));
        usersDTO.getUsers().forEach(System.out::println);
    }

}
