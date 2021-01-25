package com.wedoqa.qalearning.restapi.dto;

import java.util.ArrayList;
import java.util.List;

public class UsersDTO {

    private List<UserDTO> users = new ArrayList<>();

    public UsersDTO() {
        super();
    }

    public UsersDTO(List<UserDTO> users) {
        this.users = users;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public UsersDTO setUsers(List<UserDTO> users) {
        this.users = users;
        return this;
    }
}
