package com.example.noteapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeletedItem extends AppCompatActivity {

    User user;
    CredentialDatabase db;
    DeletePasswordAdapter adapter;
    ListView lvDeletedPassManager;
    ArrayList<UserCredential> deletedPasswords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deleted_item);

        user= (User) getIntent().getSerializableExtra("User");
        db=new CredentialDatabase(this);

        db.open();
        deletedPasswords=db.readDeletedUsersCredential(user.getId());
        db.close();
        lvDeletedPassManager=findViewById(R.id.lvDeletedItem);
        adapter=new DeletePasswordAdapter(DeletedItem.this,0,deletedPasswords);
        adapter.setUser(user);
        lvDeletedPassManager.setAdapter(adapter);

    }
}