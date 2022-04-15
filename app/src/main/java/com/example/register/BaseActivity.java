package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public abstract class BaseActivity extends AppCompatActivity implements

        NavigationBarView.OnItemSelectedListener{

    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentActivityViewId());
        bottomNavigationView =(BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuRegister) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        } else if (itemId == R.id.menuLogin) {
            System.out.println("---Login---");
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else if (itemId == R.id.menuHome) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        overridePendingTransition(0, 0);
        finish();
        return true;
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = bottomNavigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getContentActivityViewId();

    abstract int getNavigationMenuItemId();
}