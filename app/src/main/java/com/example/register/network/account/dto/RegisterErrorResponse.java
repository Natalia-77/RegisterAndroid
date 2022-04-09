package com.example.register.network.account.dto;

import lombok.Data;

@Data
public class RegisterErrorResponse {
    private String[] email;
    private String[] password;
    private String[] confirmPassword;

}
