package com.example.noteapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DeletePasswordAdapter extends ArrayAdapter<UserCredential> {

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DeletePasswordAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserCredential> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=convertView;

        if(view==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_password,parent,false);
        }

        TextView tvWebsite;
        ImageView ivShow;

        tvWebsite=view.findViewById(R.id.tvWebsite);
        ivShow=view.findViewById(R.id.ivShowPass);

        UserCredential userCredential=getItem(position);
       if(userCredential!=null) {
           tvWebsite.setText(userCredential.getWebsite());
       }
        ivShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),DeleteOrRestore.class);
                intent.putExtra("DeletedCredential",userCredential);
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
