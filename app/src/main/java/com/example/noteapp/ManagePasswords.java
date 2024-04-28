package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ManagePasswords extends AppCompatActivity {

    User user;
    ListView lvManagePassword;
    ArrayList<UserCredential> savedPasswords;
    CredentialDatabase credentialDatabase;
    PasswordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_passwords);
        init();
        credentialDatabase.open();
        savedPasswords = credentialDatabase.readSavedUsersCredential(user.getId());
        credentialDatabase.close();

        adapter=new PasswordAdapter(ManagePasswords.this,0,savedPasswords);
        adapter.setUser(user);
        lvManagePassword.setAdapter(adapter);
    }
    private void init(){
        Intent intent=getIntent();
        user= (User) intent.getSerializableExtra("User");

        lvManagePassword=findViewById(R.id.lvManagePassword);
        savedPasswords=new ArrayList<>();
        credentialDatabase=new CredentialDatabase(this);
    }
}