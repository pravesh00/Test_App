package com.example.test_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_app.api.ApiInterface;
import com.example.test_app.database.DatabaseClient;
import com.example.test_app.model.AllUserResponse;
import com.example.test_app.model.UserResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity {
    Retrofit retrofit;
    ApiInterface api;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        retrofit= new Retrofit.Builder()
                .baseUrl("https://android-dev.homingos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(ApiInterface.class);

        //Intializing UI
        init();


        String token= sharedPreferences.getString("token","");
        Call<AllUserResponse> call=api.verify(token);
        call.enqueue(new Callback<AllUserResponse>() {
            @Override
            public void onResponse(Call<AllUserResponse> call, Response<AllUserResponse> response) {
                if(response.body()!=null){
                    try{DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().deleteALL();
                        for(UserResponse u:response.body().getUsers()){

                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().insert(u);

                    }
                    int x=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().getAll().size();
                    Log.d("sizeof",x+"");}
                    finally {


                    Toast.makeText(getApplicationContext(),"Loging In",Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(getApplicationContext(),EmailInfoActivity.class);
                    startActivity(i);
                    finish();}
                }
                else
                {
                    Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<AllUserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    private void init() {
        sharedPreferences=getSharedPreferences("MyShared",MODE_PRIVATE);
        edit=sharedPreferences.edit();
    }
}