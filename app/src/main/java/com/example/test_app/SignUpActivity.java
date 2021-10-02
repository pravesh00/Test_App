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
import com.example.test_app.model.SignUpBody;
import com.example.test_app.model.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    TextView login;
    Button signup;
    EditText email,password,address;
    Retrofit retrofit;
    ApiInterface api;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        retrofit= new Retrofit.Builder()
                .baseUrl("https://android-dev.homingos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(ApiInterface.class);

        //Intializing UI
        init();

        //login text logic
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        //signup button logic
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup() {
        ProgressDialog p= new ProgressDialog(SignUpActivity.this);
        p.setMessage("Loading... ");
        p.setTitle("Login");
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setIndeterminate(true);
        p.show();
        String txtEmail=email.getText().toString();
        String txtPass=password.getText().toString();
        String txtAddress=address.getText().toString();
        SignUpBody log=new SignUpBody(txtEmail,txtPass,txtAddress);
        Call<Token> call=api.signup(log);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                p.hide();
                if(response.body() !=null)
                {Toast.makeText(getApplicationContext(),"Login Succesfull",Toast.LENGTH_SHORT).show();
                    edit.putString("token",response.body().getToken());
                    edit.commit();
                    Intent i= new Intent(getApplicationContext(),EmailInfoActivity.class);
                    startActivity(i);
                    finish();

                }
                else
                    Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                p.hide();
                Toast.makeText(getApplicationContext(),String.valueOf(R.string.error),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void login() {
        Intent i= new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void init() {
        login=(TextView) findViewById(R.id.txtLogin);
        signup=(Button) findViewById(R.id.btnSignUp);
        email=(EditText) findViewById(R.id.txtEmailNew);
        password=(EditText) findViewById(R.id.txtPasswordNew);
        address=(EditText) findViewById(R.id.txtAddressNew);
        sharedPreferences=getSharedPreferences("MyShared",MODE_PRIVATE);
        edit=sharedPreferences.edit();
    }
}