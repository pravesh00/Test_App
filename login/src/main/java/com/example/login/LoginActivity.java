package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.api.ApiInterface;
import com.example.login.model.LoginBody;
import com.example.login.model.Token;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView signup;
    Retrofit retrofit;
    EditText email,password;
    ApiInterface api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit= new Retrofit.Builder()
                .baseUrl("https://android-dev.homingos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(ApiInterface.class);

        //Intialising UI components
        init();

        //signup button logic
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        //login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String txtEmail=email.getText().toString();
        String txtPass=password.getText().toString();
        LoginBody log=new LoginBody(txtEmail,txtPass);
        Call<Token> call=api.login(log);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.body() !=null)
                Toast.makeText(getApplicationContext(),response.body().getToken(),Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getApplicationContext(),String.valueOf(R.string.error),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void signup() {
        Intent i= new Intent(this,SignUpActivity.class);
        startActivity(i);
        finish();
    }

    private void init() {
        login=(Button) findViewById(R.id.btnLogin);
        signup=(TextView) findViewById(R.id.txtSignUp);
        email=(EditText) findViewById(R.id.txtEmail);
        password=(EditText) findViewById(R.id.txtPassword);
    }
}