package com.example.noteapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PasswordAdapter extends ArrayAdapter<UserCredential> {

    User user;
    Context c;
    UserCredential userPassword;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PasswordAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserCredential> objects) {
        super(context, resource, objects);
        c=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;

        if(view==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_password,parent,false);
        }

        TextView tvWebsite;
        ImageView ivShowPass;
        tvWebsite=view.findViewById(R.id.tvWebsite);

        ivShowPass=view.findViewById(R.id.ivShowPass);

         userPassword=getItem(position);



      if(userPassword != null) {
          tvWebsite.setText(userPassword.getWebsite());
          ivShowPass.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                      Intent intent = new Intent(v.getContext(), ShowPassword.class);
                      intent.putExtra("UserPassword", userPassword);
                      intent.putExtra("isUpdate", 1);
                      intent.putExtra("User", user);
                      c.startActivity(intent);
              }
          });

      }
        return view;
    }
}
