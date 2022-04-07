package com.example.register;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.succsess_activity);
        display = findViewById(R.id.tvdisplay);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView userView = findViewById(R.id.tvuser);
            //String username = extras.getString(MainActivity.USER_KEY);
            String username = extras.getString(LoginActivity.USER_KEY);
            userView.setText("Hello  : " +  username);
        }
    }
}
