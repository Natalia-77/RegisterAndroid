package com.example.register;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.register.network.account.AccountService;
import com.example.register.network.account.dto.RegisterResponse;
import com.example.register.network.account.dto.RegisterServerValidErrors;
import com.example.register.network.account.dto.RegisterUserDto;
import com.example.register.servererror.ServerRegisterError;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$");
    private TextInputLayout inputRegisterName, inputRegisterEmail, inputRegisterPass, inputRegisterConfirm, errorRegisterLayout;
    private TextInputEditText name, email, pass, passconfirm, errorServerRegister;
    private ShapeableImageView image;
    private RelativeLayout layout;
    Button select, register;
    //TextView login;
    int SELECT_PICTURE = 200;
    static final String USER_KEY = "USER";
    String selectPhoto = "";
    private ProgressBar spinner;
    boolean flag = false;

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
                        Bitmap bitmap=null;

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
        //setContentView(R.layout.activity_register);
        image = findViewById(R.id.image);
        select = findViewById(R.id.select);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        passconfirm = findViewById(R.id.confirm);
        register = findViewById(R.id.register);
        //login = findViewById(R.id.tvLogin);
        errorServerRegister = findViewById(R.id.errorsServer);
        inputRegisterName = findViewById(R.id.nameTextInputLayout);
        inputRegisterEmail = findViewById(R.id.emailTextInputLayout);
        inputRegisterPass = findViewById(R.id.passwordTextInputLayout);
        inputRegisterConfirm = findViewById(R.id.confirmTextInputLayout);
        errorRegisterLayout = findViewById(R.id.errorsTextInputLayout);

        flag = isConnected(getApplicationContext());
        if(flag){
            String res = isConnectedFast(getApplicationContext());
            Toast.makeText(this, res, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Network connection is fail", Toast.LENGTH_LONG).show();
        }

        inputRegisterName.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                String nameUser = name.getText().toString();
                if (nameUser.isEmpty()) {
                    inputRegisterName.setError("Required field!");
                    inputRegisterName.setErrorEnabled(true);
                } else {
                    inputRegisterName.setError("");
                    inputRegisterName.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        inputRegisterEmail.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                String emailUser = email.getText().toString();
                if (!PASSWORD_PATTERN.matcher(emailUser).matches()) {
                    inputRegisterEmail.setError("Enter valid email!");
                    inputRegisterEmail.setErrorEnabled(true);
                } else {
                    inputRegisterEmail.setError("");
                    inputRegisterEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        inputRegisterPass.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                String passUser = pass.getText().toString();
                if (passUser.isEmpty()) {
                    inputRegisterPass.setError("Required field!");
                    inputRegisterPass.setErrorEnabled(true);
                } else {
                    inputRegisterPass.setError("");
                    inputRegisterPass.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        inputRegisterConfirm.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                String confirm = passconfirm.getText().toString();
                if (confirm.isEmpty()) {
                    inputRegisterConfirm.setError("Required field!");
                    inputRegisterConfirm.setErrorEnabled(true);
                } else {
                    inputRegisterConfirm.setError("");
                    inputRegisterConfirm.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public boolean isConnected(Context context) {
        //Отримала екземпляр класу ConnectivityManager
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
           // System.out.println("Not connected");
            return false;
        }
        //Отримую інфо про всі наявні мережі
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        //якщо вони виявлені...
        if (info != null) {
            // iтератор проходжу по них...
            for (int i = 0; i < info.length; i++) {
                //перевіряю стан підключення мережі...
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                   // System.out.println("++++");
                    return true;
                }
            }
        }
        return false;

    }

    public static String isConnectedFast(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if ((info != null && info.isConnected())) {
            return isConnectionFast(info.getType(),
                    info.getSubtype());
        } else
            return "No NetWork Access";

    }
    public static String isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            System.out.println("CONNECTED VIA WIFI");
            return "CONNECTED FROM WIFI";
        } else if (type == ConnectivityManager.TYPE_MOBILE) {

            System.out.println("CONNECTED VIA MOBILE");
            return "CONNECTED FROM MOBILE";
            }
        return "";

    }

    @Override
    int getContentActivityViewId() {
        return R.layout.activity_register;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.menuRegister;
    }


    public void handleSelectImageClick(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //У об"екта ActivityResultLauncher викликається метод launch():
        startResult.launch(Intent.createChooser(intent, "Select Image"));

    }


    public void handleClick(View view) {

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setName(name.getText().toString());
        registerUserDto.setEmail(email.getText().toString());
        registerUserDto.setPassword(pass.getText().toString());
        registerUserDto.setConfirmPassword(passconfirm.getText().toString());
        registerUserDto.setPhoto(selectPhoto);
        //-----Отримала екземпляр класу------
        DialogProgressBar progressBar = new DialogProgressBar(this);
        //-----Як тільки натиснула кнопку Реєстрація,відкривається Прогрес бар...
        progressBar.show();

        AccountService.getInstance()
                .json()
                .registers(registerUserDto)
                .enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                        if (response.isSuccessful()) {
                            //------Якщо все ОК,то закриваю...
                            progressBar.dismiss();

                            RegisterResponse data = response.body();
                            System.out.println("------++++++++++++++++-----");
                            //За умови 200 - перехід на іншу activity ...
                            Intent intent = new Intent(RegisterActivity.this, SuccessActivity.class);
                            String nameUser = name.getText().toString();
                            //отримали ім"я зареєстрованого користувача і передали на іншу activity
                            intent.putExtra(USER_KEY, nameUser);
                            startActivity(intent);


                        } else {
                            try {
                                System.out.println("******************");
                                progressBar.dismiss();
                                String serverError = response.errorBody().string();
                                //конвертація з JSON в Gson
                                RegisterServerValidErrors serverValidErrors = ServerRegisterError.errors(serverError);
                                System.out.println("Error server" + "-->> " + serverValidErrors.getErrors().getConfirmPassword().toString());
                                //отримала записані в строчку помилки сервера
                                String res = ServerRegisterError.listErrors(serverValidErrors);
                                //всі помилки записую у відповідне поле для помилок в стовпчик.
                                errorServerRegister.setText(res);
                            } catch (Exception e) {
                                System.out.println("Error parse data"+e.getMessage());
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