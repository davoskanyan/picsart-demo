package com.picsart.demo.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationDto {
    public final String name;
    public final String email;
    public final String password;

    @JsonCreator
    public RegistrationDto(@JsonProperty("name") String name,
                           @JsonProperty("email") String email,
                           @JsonProperty("password") String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}