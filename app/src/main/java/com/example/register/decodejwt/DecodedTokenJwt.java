package com.example.register.decodejwt;

import android.util.Base64;
import com.example.register.network.account.dto.PayloadDataDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;

public class DecodedTokenJwt {

    public static PayloadDataDto decodToken (String encodedToken) throws UnsupportedEncodingException {
        String[] parts = encodedToken.split("\\.");
        String payload = parts[1];
        byte[] data = Base64.decode(payload, Base64.DEFAULT);
        String text = new String(data, "UTF-8");

        return new Gson().fromJson(text, PayloadDataDto.class);
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
