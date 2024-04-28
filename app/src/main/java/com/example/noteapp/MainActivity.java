package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    User user;
    ImageButton ibSavedPasswords,ibDeletedPasswords;
    FloatingActionButton fabAddNewCredential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

        ibDeletedPasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), DeletedItem.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        ibSavedPasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), ManagePasswords.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        fabAddNewCredential.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ShowPassword.class);
                intent.putExtra("isUpdate",0);
                intent.putExtra("User",user);
                startActivity(intent);

            }
        });

    }
    private void init(){
        ibDeletedPasswords=findViewById(R.id.ibDeleted);
        ibSavedPasswords=findViewById(R.id.ibPassword);
        fabAddNewCredential=findViewById(R.id.fabAddNewCredential);
        Intent intent=getIntent();
        user= (User) intent.getSerializableExtra("User");
    }
}