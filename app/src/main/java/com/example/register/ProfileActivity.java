package com.example.register;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.register.app.HomeApplication;
import com.example.register.constants.Url;
import com.example.register.network.account.AccountService;
import com.example.register.network.account.dto.UpdateResponse;
import com.example.register.network.account.dto.UpdateUserModel;
import com.example.register.network.account.dto.UserDto;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextInputEditText name, email;
    ImageView image;
    Button update, selectImage;
    String selectPhoto = "";
    Integer user_id = 0;

    ActivityResultLauncher<Intent> startResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();
                        image.setImageURI(uri);
                        Bitmap bitmap = null;

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        selectPhoto = Base64.encodeToString(bytes, Base64.DEFAULT);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
            user_id = dto.getId();
            String email_user = dto.getEmail();
            String username = dto.getName();
            String photo_avatar = dto.getPhoto();
            String url = Url.urls + photo_avatar;
            name.setText(username);
            email.setText(email_user);
            //отримую тут фото обраного користувача.При бажанні можна завантажувати нове фото...
            Glide.with(HomeApplication.getAppContext())
                    .load(url)
                    //вставляю фото у відповідний елемент
                    .into(image);

        }

    }

    //обробка кліку по кнопці Select для вибору нового фото...
    public void handleSelectImageClick(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //У об"екта ActivityResultLauncher викликається метод launch():
        startResult.launch(Intent.createChooser(intent, "Select Image"));

    }

    public void handleClickupdate(View view) {

        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setName(name.getText().toString());
        updateUserModel.setEmail(email.getText().toString());
        updateUserModel.setPhoto(selectPhoto);
        System.out.println(updateUserModel.getEmail());
        //-----Отримала екземпляр класу------
        DialogProgressBar progressBar = new DialogProgressBar(this);
        //-----Як тільки натиснула кнопку Реєстрація,відкривається Прогрес бар...
        progressBar.show();

        AccountService.getInstance()
                .json()
                .updateUser(user_id, updateUserModel)
                .enqueue(new Callback<UpdateResponse>() {
                    @Override
                    public void onResponse(Call<UpdateResponse> call,
                                           Response<UpdateResponse> response) {

                        if (response.isSuccessful()) {
                            //------Якщо все ОК,то закриваю...
                            progressBar.dismiss();

                            UpdateResponse data = response.body();
                            System.out.println("------++++++++++++++++-----" + user_id);
                            //За умови 200 - перехід на іншу activity ...
                            startActivity(new Intent(ProfileActivity.this,
                                                                   UserActivity.class));
                        } else {
                            try {
                                System.out.println("*****ERRORS********");
                                progressBar.dismiss();
                                String serverError = response.errorBody().string();
                                System.out.println(serverError);
                            } catch (Exception e) {
                                System.out.println("Error parse data" + e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateResponse> call, Throwable t) {
                        String str = t.toString();

                    }
                });
    }
}