package com.example.test_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_app.api.ApiInterface;
import com.example.test_app.model.LoginBody;
import com.example.test_app.model.Token;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();
        retrofit= new Retrofit.Builder()
                .baseUrl("https://android-dev.homingos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
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
        ProgressDialog p= new ProgressDialog(LoginActivity.this);
        p.setMessage("Loading... ");
        p.setTitle("Login");
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setIndeterminate(true);
        p.show();
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                p.hide();
                if(response.body() !=null){
                    edit.putString("token",response.body().getToken());
                    edit.commit();
                Toast.makeText(getApplicationContext(),"Login Succesfull",Toast.LENGTH_SHORT).show();

                Intent i= new Intent(getApplicationContext(),EmailInfoActivity.class);
                startActivity(i);
                finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                p.hide();
            }
        });

    }

    private void signup() {
        Intent i= new Intent(this, SignUpActivity.class);
        startActivity(i);
        finish();
    }

    private void init() {
        login=(Button) findViewById(R.id.btnLogin);
        signup=(TextView) findViewById(R.id.txtSignUp);
        email=(EditText) findViewById(R.id.txtEmail);
        password=(EditText) findViewById(R.id.txtPassword);
        sharedPreferences=getSharedPreferences("MyShared",MODE_PRIVATE);
        edit=sharedPreferences.edit();
    }
}