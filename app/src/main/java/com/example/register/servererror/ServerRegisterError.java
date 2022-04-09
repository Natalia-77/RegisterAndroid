package com.example.register.servererror;

import com.example.register.network.account.dto.RegisterServerValidErrors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerRegisterError {

    public static RegisterServerValidErrors errors(String errorMessage) {

        return new Gson().fromJson(errorMessage, RegisterServerValidErrors.class);
    }

    public static String listErrors(RegisterServerValidErrors errors) {
        String strErrors = "";
        if (errors.getErrors().getEmail() != null) {
            for (String str : errors.getErrors().getEmail()) {
                strErrors += str + "\n";
            }
        }
        if (errors.getErrors().getPassword() != null) {
            for (String str : errors.getErrors().getPassword()) {
                strErrors += str + "\n";
            }
        }
        if (errors.getErrors().getConfirmPassword() != null) {
            for (String str : errors.getErrors().getConfirmPassword()) {
                strErrors += str + "\n";
            }
        }
        return strErrors;
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
