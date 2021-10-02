package com.example.login.api;

import com.example.login.model.LoginBody;
import com.example.login.model.SingUpBody;
import com.example.login.model.Token;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/login")
    Call<Token> login(@Body LoginBody loginBody);

    @POST("/signup")
    Call<Token> signup(@Body SingUpBody singUpBody);
}
