package com.dashcrypto.login.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Login {

    @NotNull
    private String email;

    @NotNull
    private String password;

}
