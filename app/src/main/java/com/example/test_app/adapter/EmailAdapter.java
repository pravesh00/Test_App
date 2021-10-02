package com.example.test_app.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_app.EmailActivity;
import com.example.test_app.ProfileActivity;
import com.example.test_app.R;
import com.example.test_app.database.DatabaseClient;
import com.example.test_app.model.Email;
import com.example.test_app.model.Selected;
import com.example.test_app.model.UserResponse;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailViewHolder> {

    ArrayList<Email> emailList;
    Context context;
    Dialog myDialog;
    SharedPreferences sharedPreferences;

    public EmailAdapter(ArrayList<Email> emailList, Context context) {
        this.emailList = emailList;
        this.context=context;
        sharedPreferences=context.getSharedPreferences("MyShared",MODE_PRIVATE);

    }

    @NonNull
    @Override
    public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.email_template,parent,false);
        return new EmailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailViewHolder holder, int position) {
        Email email=emailList.get(position);
        holder.subject.setText(email.getSubject());
        holder.sender.setText(email.getSender());
        holder.body.setText(email.getBody());
        holder.icon.setLetter(email.getSender());
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog= new Dialog(view.getContext());
                myDialog.setContentView(R.layout.profile_template);
                List<UserResponse> emailList=DatabaseClient.getInstance(context).getAppDatabase().userDao().getUserById(email.getSender());
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView sender=myDialog.findViewById(R.id.txtName);
                if(emailList.size()!=0){
                sender.setText(emailList.get(0).getName());
                TextView add=myDialog.findViewById(R.id.txtAddProfile);
                add.setText(emailList.get(0).getAddress());}
                TextView mail=myDialog.findViewById(R.id.txtEmailProfile);
                mail.setText(email.getSender());
                myDialog.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), EmailActivity.class);
                i.putExtra("sender",email.getSender());
                i.putExtra("body",email.getBody());
                i.putExtra("subject",email.getSubject());
                i.putExtra("id",email.getId());

                view.getContext().startActivity(i);}
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    public class EmailViewHolder extends RecyclerView.ViewHolder{

        MaterialLetterIcon icon;
        TextView sender,body,subject;
        ConstraintLayout layout;
        CircleImageView img;
        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.iconLetter);
            sender=itemView.findViewById(R.id.txtSender);
            body=itemView.findViewById(R.id.txtBody);
            subject=itemView.findViewById(R.id.txtSubject);
            layout=itemView.findViewById(R.id.layoutEmail);
            img=itemView.findViewById(R.id.imgTick);


        }
    }

}
