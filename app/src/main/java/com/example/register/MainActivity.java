package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends BaseActivity  {

    private TextInputEditText helloStartText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        helloStartText=findViewById(R.id.hello);

    }

    @Override
    int getContentActivityViewId() {
      return  R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.menuHome;
    }



}