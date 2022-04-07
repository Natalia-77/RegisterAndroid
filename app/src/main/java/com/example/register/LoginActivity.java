package com.example.register;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.register.decodejwt.DecodedTokenJwt;
import com.example.register.network.account.AccountService;
import com.example.register.network.account.dto.LoginResponce;
import com.example.register.network.account.dto.LoginUserDto;
import com.example.register.network.account.dto.PayloadDataDto;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail,inputPassword;
    private Button btnLogin;
    static final String USER_KEY = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);

    }

    public void handleLoginClick(View view){
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(inputEmail.getText().toString());
        loginUserDto.setPassword(inputPassword.getText().toString());

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
                                   System.out.println("Error!");
                                   System.out.println(response.code());
                                   System.out.println(response.errorBody().string());
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