package com.example.test_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_app.adapter.EmailAdapter;
import com.example.test_app.api.ApiInterface;
import com.example.test_app.database.DatabaseClient;
import com.example.test_app.model.AllMailsResponse;
import com.example.test_app.model.Email;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmailInfoActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiInterface api;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ArrayList<Email> emails=new ArrayList<>();
    RecyclerView recyler;
    LinearLayoutManager layout;
    EmailAdapter adapter;
    EditText search;
    ArrayList<Email> permCopy=new ArrayList<>();
    ImageView sort;
    ImageView noresult;
    TextView txtNoRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_info);

        retrofit= new Retrofit.Builder()
                .baseUrl("https://android-dev.homingos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(ApiInterface.class);

        //Intializing UI
        init();

        setDefaultListEmail();

        String token=sharedPreferences.getString("token","");
        Call<AllMailsResponse> call=api.getAllMails(token);
        call.enqueue(new Callback<AllMailsResponse>() {
            @Override
            public void onResponse(Call<AllMailsResponse> call, Response<AllMailsResponse> response) {
                if(response.body()!=null){
                    ArrayList n= response.body().getMails();
                    emails.addAll(n);
                    permCopy.clear();
                    permCopy.addAll(n);
                    update();
                    Log.e("emails",emails.size()+"");
                    for(Email e:permCopy){
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().emailDao().insert(e);
                    Log.d("emailData",DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().emailDao().getAll().size()+"");
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Not Available",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllMailsResponse> call, Throwable t) {
                setDefaultListEmail();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(search.getText().toString());
            }

        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortAlpha();
            }
        });

    }

    private void setDefaultListEmail() {
        List<Email> defaultList= DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().emailDao().getAll();
        permCopy.clear();
        emails.clear();
        permCopy.addAll(defaultList);
        emails.addAll(defaultList);
        update();
        Log.e("emails",emails.size()+"");

    }

    private void filter(String text) {
        text=text.toLowerCase();
        ArrayList<Email> filtered=new ArrayList<>();
        for(Email e:permCopy){
            if(e.getBody().toLowerCase(Locale.ROOT).contains(text) || e.getSender().toLowerCase(Locale.ROOT).startsWith(text) || e.getSubject().toLowerCase().contains(text) ){
                filtered.add(e);
            }
        }
        emails=filtered;
        update();


    }

    private void sortAlpha(){
        Collections.sort(emails, new Comparator<Email>() {
            @Override
            public int compare(Email s1, Email s2) {
                return s1.getSender().compareToIgnoreCase(s2.getSender());
            }
        });
        update();
    }

    private void init() {
        sharedPreferences=getSharedPreferences("MyShared",MODE_PRIVATE);
        edit=sharedPreferences.edit();
        recyler=findViewById(R.id.recyclerEmail);
        layout= new LinearLayoutManager(getApplicationContext());
        search=findViewById(R.id.txtSearch);
        sort=findViewById(R.id.btnSort);
        noresult=findViewById(R.id.nores);
        txtNoRes=findViewById(R.id.txtNoRes);

    }
    private void update(){
        adapter= new EmailAdapter(emails,getApplication());
        recyler.setHasFixedSize(true);
        recyler.setLayoutManager(layout);
        recyler.setAdapter(adapter);
        if(emails.size()==0){
            noresult.setVisibility(View.VISIBLE);
            txtNoRes.setVisibility(View.VISIBLE);
            txtNoRes.setText("No Results found for "+search.getText());
            if(search.getText().equals(""))
                txtNoRes.setText("No results found.");
        }else {noresult.setVisibility(View.INVISIBLE);
                txtNoRes.setVisibility(View.INVISIBLE);
        }
    }
}