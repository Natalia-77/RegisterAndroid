package com.example.register.app;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

public class HomeApplication extends Application {
    private static HomeApplication instance;
    private static Context appContext;

    public static HomeApplication getInstance() {
        return instance;
    }
    public static Context getAppContext() {
        return appContext;
    }
    public void setAppContext(Context appContext) {
        this.appContext=appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
