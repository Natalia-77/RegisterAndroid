package com.example.register.network.account;

import com.example.register.network.account.dto.LoginResponce;
import com.example.register.network.account.dto.LoginUserDto;
import com.example.register.network.account.dto.RegisterResponse;
import com.example.register.network.account.dto.RegisterUserDto;
import com.example.register.network.account.dto.UpdateResponse;
import com.example.register.network.account.dto.UpdateUserModel;
import com.example.register.network.account.dto.UserDto;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountApi {

    @POST("/api/account/register")
    Call<RegisterResponse> registers(@Body RegisterUserDto registerUserDto);

    @POST("/api/account/login")
    Call<LoginResponce> loginUser(@Body LoginUserDto loginUserDto);

    @GET("/api/account/users")
    Call<List<UserDto>> getUsers();

    @PUT("api/account/{id}")
    Call<UpdateResponse> updateUser(@Path("id") int id, @Body UpdateUserModel body);

}

