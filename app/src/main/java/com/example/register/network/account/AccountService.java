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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(130, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Url.urls)
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
