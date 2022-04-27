package com.example.register;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.register.app.HomeApplication;
import com.example.register.constants.Url;
import com.example.register.network.account.AccountService;
import com.example.register.network.account.dto.DeleteResponse;
import com.example.register.network.account.dto.UserDto;
import com.google.android.material.textfield.TextInputEditText;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteActivity extends AppCompatActivity {
    TextInputEditText name, email;
    ImageView image;
    Button update, selectImage;
    Integer user_delete = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email_profile);
        image = findViewById(R.id.imageProfile);
        selectImage = findViewById(R.id.selectImage);
        update = findViewById(R.id.update);

        //отримала з актіві основного списку об"єкт класу-моделі одного instance...
        Intent intent = getIntent();
        UserDto dto = (UserDto) intent.getExtras().getSerializable("Place");

        //якщо є дані,передані зі списку...
        if (dto != null) {
            //заповнюю відповідні поля вже актівіті профіля даними...
            user_delete = dto.getId();
            String email_user = dto.getEmail();
            String username = dto.getName();
            String photo_avatar = dto.getPhoto();
            String url = Url.urls + photo_avatar;
            name.setText(username);
            email.setText(email_user);
            //отримую тут фото обраного користувача...
            Glide.with(HomeApplication.getAppContext())
                    .load(url)
                    //вставляю фото у відповідний елемент
                    .into(image);

        }

    }
    public void handleDeleteUser(View view) {
        AccountService.getInstance()
                .json()
                .deleteUser(user_delete)
                .enqueue(new Callback<DeleteResponse>() {
                    @Override
                    public void onResponse(Call<DeleteResponse> call,
                                           Response<DeleteResponse> response) {

                        if (response.isSuccessful()) {
                            DeleteResponse data = response.body();
                            System.out.println("------++++++++++++++++-----" + user_delete);
                            //якщо успішно відбувся запит,тоді
                            // перенапрявляю на список з користувачами....
                            startActivity(new Intent(DeleteActivity.this,
                                    UserActivity.class));
                        } else {
                            try {
                                System.out.println("*****ERRORS********");
                                String serverError = response.errorBody().string();
                                System.out.println(serverError);
                            } catch (Exception e) {
                                System.out.println("Error parse data" + e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteResponse> call, Throwable t) {
                        String str = t.toString();

                    }
                });
    }

    public void handleBackToList(View view){
        startActivity(new Intent(DeleteActivity.this,
                UserActivity.class));
    }
}