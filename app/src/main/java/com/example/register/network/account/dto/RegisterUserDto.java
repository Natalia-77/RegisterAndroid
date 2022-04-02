package com.example.register.network.account.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    public String name;
    public String email;
    public String password;
    public String confirmPassword;
    public String photo;
}
