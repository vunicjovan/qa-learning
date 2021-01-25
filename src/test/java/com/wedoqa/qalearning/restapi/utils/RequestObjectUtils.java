package com.wedoqa.qalearning.restapi.utils;

import com.wedoqa.qalearning.restapi.dto.AddressDTO;
import com.wedoqa.qalearning.restapi.dto.CompanyDTO;
import com.wedoqa.qalearning.restapi.dto.GeoDTO;
import com.wedoqa.qalearning.restapi.dto.UserDTO;

public class RequestObjectUtils implements RequestObjectServices {

    @Override
    public UserDTO postRequestUser() {
        UserDTO userDTO = new UserDTO()
                .setName("John Doe")
                .setUsername("johndoe")
                .setEmail("johndoe@email.com")
                .setAddress(
                        new AddressDTO()
                                .setStreet("Some street")
                                .setSuite("Some suite")
                                .setCity("Some city")
                                .setZipcode("Some zipcode")
                                .setGeo(
                                        new GeoDTO()
                                                .setLat("32.4567")
                                                .setLng("56.7889")
                                )
                )
                .setPhone("111/11223344")
                .setWebsite("johndoe.com")
                .setCompany(
                        new CompanyDTO()
                                .setName("Some company")
                                .setCatchPhrase("Some phrase")
                                .setBs("Some business information")
                );

        return userDTO;
    }

    @Override
    public UserDTO updateRequestUser() {
        UserDTO userDTO = new UserDTO()
                .setName("John Doe")
                .setUsername("johndoe")
                .setEmail("johndoe@email.com")
                .setAddress(
                        new AddressDTO()
                                .setStreet("Some street")
                                .setSuite("Some suite")
                                .setCity("Some city")
                                .setZipcode("Some zipcode")
                                .setGeo(
                                        new GeoDTO()
                                                .setLat("32.4567")
                                                .setLng("56.7889")
                                )
                )
                .setPhone("111/11223344")
                .setWebsite("johndoe.com")
                .setCompany(
                        new CompanyDTO()
                                .setName("Some company")
                                .setCatchPhrase("Some phrase")
                                .setBs("Some business information")
                );

        return userDTO;
    }
}
