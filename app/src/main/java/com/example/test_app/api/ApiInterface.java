package com.example.test_app.api;

import android.content.SharedPreferences;

import com.example.test_app.model.AllMailsResponse;
import com.example.test_app.model.AllUserResponse;
import com.example.test_app.model.Email;
import com.example.test_app.model.LoginBody;
import com.example.test_app.model.SignUpBody;
import com.example.test_app.model.Token;
import com.example.test_app.model.UserResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/verifyToken")
    Call<AllUserResponse> verify(@Header("x-access-token") String token);

    @POST("/login")
    Call<Token> login(@Body LoginBody loginBody);

    @POST("/signup")
    Call<Token> signup(@Body SignUpBody signUpBody);

    @GET("/getAllMails")
    Call<AllMailsResponse> getAllMails(@Header("x-access-token") String token);

    @DELETE("/deleteMailById?id={id}")
    Call<JsonObject> delete(@Header("x-access-token") String token, @Path("id") String id);




}
