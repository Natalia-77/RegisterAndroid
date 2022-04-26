package com.example.register.network.account.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto  {

    private int id;
    private String name;
    private String email;
    private String photo;
}
