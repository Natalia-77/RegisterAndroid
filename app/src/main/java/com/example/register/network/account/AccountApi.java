package com.example.register.network.account;

import com.example.register.network.account.dto.RegisterResponse;
import com.example.register.network.account.dto.RegisterUserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccountApi {

    @POST("/api/account/register")
    Call<RegisterResponse> registers(@Body RegisterUserDto registerUserDto);
}

