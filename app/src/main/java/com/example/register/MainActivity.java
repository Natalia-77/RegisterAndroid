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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.register.network.account.AccountService;
import com.example.register.network.account.dto.RegisterResponse;
import com.example.register.network.account.dto.RegisterUserDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    Button select, register;
    EditText name,email,pass,passconfirm;
    TextView tvInfo;
    Bitmap bitmap=null;
    static final String USER_KEY = "USER";
    String selectPhoto="";

    //функція,що обробляє результат повертає об"єкт ActivityResultLauncher,який застосовується для запуску іншої activity.
    //ActivityResultCallback представляє інтерфейс з методом onActivityResult,якийуде обробляти результат.
    //На вхід у мене приймає об"єкт Intent,повертає ActivityResult
    ActivityResultLauncher<Intent> startResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                            Uri uri = intent.getData();
                            image.setImageURI(uri);
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            ByteArrayOutputStream stream=new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                            byte[] bytes=stream.toByteArray();
                            selectPhoto= Base64.encodeToString(bytes,Base64.DEFAULT);
                        }
                }
           });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tvInfo);
        image = findViewById(R.id.image);
        select = findViewById(R.id.select);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        passconfirm=findViewById(R.id.confirm);
        register=findViewById(R.id.register);
    }

    public void handleSelectImageClick(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //У об"екта ActivityResultLauncher викликається метод launch():
        startResult.launch(Intent.createChooser(intent, "Select Image"));

    }


    public void handleClick(View view){

        RegisterUserDto registerUserDto=new RegisterUserDto();
        registerUserDto.setName(name.getText().toString());
        registerUserDto.setEmail(email.getText().toString());
        registerUserDto.setPassword(pass.getText().toString());
        registerUserDto.setConfirmPassword(passconfirm.getText().toString());
        registerUserDto.setPhoto(selectPhoto);

        AccountService.getInstance()
                .json()
                .registers(registerUserDto)
                .enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            RegisterResponse data = response.body();
                            System.out.println("------++++++++++++++++-----");
                            //За умови 200 - перехід на іншу activity ...
                            Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                            String nameUser = name.getText().toString();
                            //отримали ім"я зареєстрованого користувача і передали на іншу activity
                            intent.putExtra(USER_KEY, nameUser);
                            startActivity(intent);


                        } else {
                            try {
                                System.out.println("******************");
                                System.out.println(response.code());
                                System.out.println(response.errorBody().string());
                            } catch (Exception e) {
                                System.out.println("---Error response parse body---");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        String str = t.toString();

                    }
                });

    }
}