package com.example.register.network.account;

import com.example.register.network.account.dto.RegisterResponse;
import com.example.register.network.account.dto.RegisterUserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    @POST("/api/account/register")
    public Call<RegisterResponse> register(@Body RegisterUserDto registerUserDto);
}

