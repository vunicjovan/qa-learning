package com.wedoqa.qalearning.restapi.utils;

import com.wedoqa.qalearning.restapi.dto.UserDTO;

public interface RequestObjectServices {

    UserDTO postRequestUser();
    UserDTO updateRequestUser();

}
