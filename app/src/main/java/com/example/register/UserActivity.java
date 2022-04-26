package com.example.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.app.HomeApplication;
import com.example.register.card.UserAdapter;
import com.example.register.network.account.AccountService;
import com.example.register.network.account.dto.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends BaseActivity {
    private UserAdapter adapter;
    private RecyclerView rcvUsers;
    private Button btnDelete, btnEdit;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user);
        textView = findViewById(R.id.titleTextView);
        btnDelete = findViewById(R.id.button_delete);
        btnEdit = findViewById(R.id.button_edit);

        rcvUsers = findViewById(R.id.recyclerUsers);
        rcvUsers.setHasFixedSize(true);
        rcvUsers.setLayoutManager(new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL, false));

        AccountService.getInstance()
                .json()
                .getUsers()
                .enqueue(new Callback<List<UserDto>>() {
                    @Override
                    public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                        if (response.isSuccessful()) {
                            adapter = new UserAdapter(response.body());
                            rcvUsers.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserDto>> call, Throwable t) {
                        String str = t.toString();
                    }
                });
    }

    @Override
    int getContentActivityViewId() {
        return R.layout.activity_user;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.menuUsers;
    }


}