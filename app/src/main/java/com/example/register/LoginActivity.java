package com.example.register;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.register.decodejwt.DecodedTokenJwt;
import com.example.register.network.account.AccountService;
import com.example.register.network.account.dto.LoginErrorResponse;
import com.example.register.network.account.dto.LoginResponce;
import com.example.register.network.account.dto.LoginUserDto;
import com.example.register.network.account.dto.PayloadDataDto;
import com.example.register.servererror.ServerLoginError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$");
    private TextInputLayout inputFieldEmail,inputFieldPass;
    private TextInputEditText inputEmail,inputPassword,errorServerLogin;
    //private EditText inputEmail,inputPassword;
    private Button btnLogin;
    static final String USER_KEY = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputFieldEmail = findViewById(R.id.emailTextInputLayout);
        inputFieldPass = findViewById(R.id.passTextInputLayout);
        btnLogin = findViewById(R.id.btnLogin);
        errorServerLogin = findViewById(R.id.errorMessage);

        //перевірка поля на валідність здійснюється в процесі вводу даних в поле.Як тільки буде отримано
        //валідне значення в полі - повідомлення про помилку зникне.
        //Не треба натискати на кнопку ЛОГІН,щоб отримати повідомлення помилки.
        inputFieldEmail.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                String email = inputEmail.getText().toString();
                if (!PASSWORD_PATTERN.matcher(email).matches()) {
                    //якщо результат по реджексу false-виводиться повідомлення про помилку.
                    inputFieldEmail.setError("Enter valid email! ");
                    inputFieldEmail.setErrorEnabled(true);
                } else {
                    //в іншому ж випадку помидки немає.
                    inputFieldEmail.setError("");
                    inputFieldEmail.setErrorEnabled(false);
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

    @Override
    int getContentActivityViewId() {

        return R.layout.activity_login;
    }

    @Override
    int getNavigationMenuItemId() {

        return R.id.menuLogin;
    }


    public void handleLoginClick(View view){
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(inputEmail.getText().toString());
        loginUserDto.setPassword(inputPassword.getText().toString());

        //якщо попередня спроба авторизуватись була невдалою,користувач пробує знову вводити
        //дані для логіна,то як тільки починає вводити дані в перше поле,то поле з помилкою зі
        //сторони сервера очищаю.
        if(loginUserDto.email!=null){
            errorServerLogin.setText("");
        }

      Call <LoginResponce> call = AccountService.getInstance().json().loginUser(loginUserDto);
      call.enqueue(new Callback<LoginResponce>(){
                       @SneakyThrows
                       //@RequiresApi(api = Build.VERSION_CODES.O)
                       @Override
                       public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                           if(response.isSuccessful()) {
                               LoginResponce loginResponce = response.body();
                               System.out.println("Success!");
                               String res= loginResponce.getToken();
                               //Розпарсила дані з пейлоада токена.
                               //Модель в папці з dto
                               //Клас DecodedTokenJwt в package decodejwt...
                               PayloadDataDto payloadDataDto = DecodedTokenJwt.decodToken(res);
                               System.out.println(payloadDataDto.getName());
                               Intent intent = new Intent(LoginActivity.this, SuccessActivity.class);
                               String nameUser = payloadDataDto.getName();
                               //отримали ім"я зареєстрованого користувача і передали на іншу activity
                               intent.putExtra(USER_KEY, nameUser);
                               startActivity(intent);
                           }
                           else{
                               try {
                                   //Помилки,отримані зі сторони сервера...
                                   //System.out.println("Error!");
                                   //System.out.println("Code"+"-->"+response.code());
                                   //System.out.println("Error"+"-->"+response.errorBody().string());
                                   String errorLogin=response.errorBody().string();
                                   //System.out.println(errorLogin);
                                   LoginErrorResponse errorResponse = ServerLoginError.loginErrorResponse(errorLogin);
                                  // System.out.println("GetMess"+"-->"+ errorResponse.getMessage());
                                   errorServerLogin.setText(errorResponse.getMessage());
                                   inputEmail.setText("");
                                   inputPassword.setText("");

                               } catch (Exception e) {
                                   System.out.println("Error parse data!");
                               }
                           }
                       }

                       @Override
                       public void onFailure(Call<LoginResponce> call, Throwable t) {
                           String str = t.toString();
                           System.out.println(str);
                       }
                   }
      );

    }
}