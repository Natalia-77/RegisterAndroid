package com.example.register.servererror;


import com.example.register.network.account.dto.LoginErrorResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerLoginError {

    public static LoginErrorResponse loginErrorResponse (String errorMessage) {

        return new Gson().fromJson(errorMessage, LoginErrorResponse.class);
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
