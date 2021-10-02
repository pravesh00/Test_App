package com.example.test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_app.api.ApiInterface;
import com.example.test_app.model.Email;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmailActivity extends AppCompatActivity {
    Email email;
    TextView sender,body,subject;
    MaterialLetterIcon icon;
    ImageView delete;
    Retrofit retrofit;
    ApiInterface api;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        email= new Email();
        Bundle bundle= getIntent().getExtras();
        email.setBody(bundle.getString("body"));
        email.setId(bundle.getString("id"));
        email.setSubject(bundle.getString("subject"));
        email.setSender(bundle.getString("sender"));


        retrofit= new Retrofit.Builder()
                .baseUrl("https://android-dev.homingos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(ApiInterface.class);

        init();

        setContent();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void delete() {
        ProgressDialog p= new ProgressDialog(EmailActivity.this);
        p.setMessage("Deleting... ");
        p.setTitle("Delete");
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setIndeterminate(true);
        p.show();
        Call<JsonObject> call=api.delete(sharedPreferences.getString("token",""),email.getId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                p.hide();
                Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                p.hide();
            }
        });
    }

    private void setContent() {
        sender.setText(email.getSender());
        subject.setText(email.getSubject());
        body.setText(email.getBody());
        icon.setLetter(email.getSender());
    }

    private void init() {
        sender=findViewById(R.id.txtSenderEmail);
        subject=findViewById(R.id.btnSubject);
        body=findViewById(R.id.txtBodyEmail);
        icon=findViewById(R.id.iconLetterEmail);
        delete=findViewById(R.id.btnDelete);
        sharedPreferences=getSharedPreferences("MyShared",MODE_PRIVATE);
        edit=sharedPreferences.edit();
        back=findViewById(R.id.btnBack);
    }
}