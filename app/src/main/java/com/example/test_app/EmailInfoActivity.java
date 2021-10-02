package com.example.test_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.test_app.model.DeleteBody;
import com.example.test_app.model.Email;
import com.example.test_app.model.Selected;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

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
    ImageView noresult,del;
    TextView txtNoRes;
    TextInputLayout layTop;
    boolean toggle=false;
    boolean sortToggle=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_info);

        retrofit= new Retrofit.Builder()
                .baseUrl("https://android-dev.homingos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(ApiInterface.class);

       // dummyData();

        //Intializing UI
        init();
        permCopy.addAll(emails);

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
                if(!toggle)
                filter(search.getText().toString());
            }

        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sortToggle)
                sortAlpha();
                else{
                    sortDate();
                }
                sortToggle=!sortToggle;
            }
        });

        layTop.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(del.getVisibility()==View.VISIBLE){
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().selectedDao().deleteAll();
                    del.setVisibility(View.INVISIBLE);
                    layTop.setStartIconDrawable(R.drawable.ic_menu);
                    search.setText("");
                    update();
                    toggle=false;
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(del.getVisibility()==View.VISIBLE){
                    DeleteBody deleteBody=new DeleteBody();
                    ArrayList<String> ids=new ArrayList<>();
                    List<Selected> id=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().selectedDao().getAll();
                    for(Selected i:id){
                        ids.add(i.getId());
                    }
                    deleteBody.setIds(ids);
                    Call<JsonObject> call= api.deleteMultiple(token,deleteBody);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void dummyData() {
        emails.add(new Email("Hello","2332","dsd g","fdfd","011","fewfwef"));
        emails.add(new Email("Hello","dfd","dsggr g","fdfd","02","fewfwef"));
        emails.add(new Email("Hello","gffgf2332","rgrgr g","fdfd","05","fewfwef"));
        emails.add(new Email("Hello","fgfgfgfg","vfb g","fdfd","00","fewfwef"));
        emails.add(new Email("Hello","hghghgh2332","Prvaes g","fdfd","010","fewfwef"));
        emails.add(new Email("Hello","23etre32","Prvaes g","fdfd","fdf","fewfwef"));
        emails.add(new Email("Hello","fgfgfd","Prvaes g","fdfd","fdf","fewfwef"));
        emails.add(new Email("Hello","fgdgdgdg","Prvaes g","fdfd","fdf","fewfwef"));
        emails.add(new Email("Hello","hjhjh","Prvaes g","fdfd","fdf","fewfwef"));
        emails.add(new Email("Hello","rgsdsd","Prvaes g","fdfd","fdf","fewfwef"));
        emails.add(new Email("Hello","rwqgsdh","Prvaes g","fdfd","fdf","fewfwef"));
        emails.add(new Email("Hello","gdfhtrjuj","Prvaes g","fdfd","fdf","fewfwef"));
        emails.add(new Email("Hello","sfgstrggd","Prvaes g","fdfd","fdf","fewfwef"));
        emails.add(new Email("Hello","arawrfwr","Prvaes g","fdfd","fdf","fewfwef"));
    }

    private void sortDate() {
        Collections.sort(emails, new Comparator<Email>() {
            @Override
            public int compare(Email s1, Email s2) {
                return s1.getTime().compareToIgnoreCase(s2.getTime());
            }
        });
        update();
        Toast.makeText(getApplicationContext(),"Sorted according to Time",Toast.LENGTH_SHORT).show();
    }

    private void setDefaultListEmail() {
        List<Email> defaultList= DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().emailDao().getAll();
        permCopy.clear();
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
        Toast.makeText(getApplicationContext(),"Sorted alphabetically",Toast.LENGTH_SHORT).show();
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
        layTop=findViewById(R.id.layTop);
        del=findViewById(R.id.btnDelEmail);

    }
    private void update(){
        recyler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                Context context=getApplicationContext();
                List<Selected> ids=DatabaseClient.getInstance(context).getAppDatabase().selectedDao().getAll();
                Integer x=ids.size();
                Email email=emails.get(position);

                if(DatabaseClient.getInstance(context).getAppDatabase().selectedDao().get(email.getId()).size()==0){
                    sharedPreferences.edit().putInt("selectCount",x+1).commit();
                    view.findViewById(R.id.layoutEmail).setBackgroundColor(Color.parseColor("#E2F0FA"));
                    view.findViewById(R.id.imgTick).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.iconLetter).setVisibility(View.INVISIBLE);
                    email.setSelected(true);
                    DatabaseClient.getInstance(context).getAppDatabase().selectedDao().insert(new Selected(email.getId()));
                }else{
                    sharedPreferences.edit().putInt("selectCount",x-1).commit();
                    view.findViewById(R.id.layoutEmail).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    view.findViewById(R.id.imgTick).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.iconLetter).setVisibility(View.VISIBLE);
                    email.setSelected(false);
                    DatabaseClient.getInstance(context).getAppDatabase().selectedDao().delete(new Selected(email.getId()));
                }

                if(ids.size()!=0){
                    toggle=true;
                    search.setText(ids.size()+"");
                    del.setVisibility(View.VISIBLE);
                    layTop.setStartIconDrawable(R.drawable.ic_back_1);
                }else {toggle=false;
                    del.setVisibility(View.INVISIBLE);
                    layTop.setStartIconDrawable(R.drawable.ic_menu);
                }


            }
        }));
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