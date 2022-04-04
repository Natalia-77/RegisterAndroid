package com.example.register.network.account;

import com.example.register.constants.Url;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountService {

    private static AccountService instance;
    private final Retrofit retrofit;

    private AccountService()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS)
       .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static AccountService getInstance()
    {
        if(instance==null)
        {
            instance = new AccountService();
        }
        return instance;
    }

    public AccountApi json()
    {
        return retrofit.create(AccountApi.class);
    }
}
