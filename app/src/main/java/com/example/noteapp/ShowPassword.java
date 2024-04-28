package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ShowPassword extends AppCompatActivity {

    EditText etWebsite, etSavedEmail, etSavedPassword;
    Button btnUpdate, btnSave, btnDelete;

    CredentialDatabase db;
    UserCredential userCredential;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_password);
        init();

        Intent intent = getIntent();
        userCredential = (UserCredential) intent.getSerializableExtra("UserPassword");
        System.out.println(userCredential);
        user = (User) intent.getSerializableExtra("User");
        int i = intent.getIntExtra("isUpdate", 10);
        if (i == 1) {
            btnSave.setVisibility(Button.GONE);
        } else if (i == 0) {
            btnUpdate.setVisibility(Button.GONE);
            btnDelete.setVisibility(Button.GONE);
        }

        if (userCredential != null) {
            etWebsite.setText(userCredential.getWebsite());
            etSavedPassword.setText(userCredential.getPassword());
            etSavedEmail.setText(userCredential.getEmail());
        }



        update();
        delete();
        insert();
    }

    private void init() {
        etWebsite = findViewById(R.id.etWebsite);
        etSavedPassword = findViewById(R.id.etSavedPassword);
        etSavedEmail = findViewById(R.id.etSavedEmail);

        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = new CredentialDatabase(this);
    }

    private void update() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.updateSavedData(userCredential.getId(), etWebsite.getText().toString(), etSavedEmail.getText().toString(), etSavedPassword.getText().toString());
                db.close();
                finish();
            }
        });
    }

    private void delete() {

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.updateSavedData(userCredential.getId(), etWebsite.getText().toString(), etSavedEmail.getText().toString(), etSavedPassword.getText().toString(), 1);
                db.close();
            }
        });
    }

    private void insert() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.insertNewSavedPassword(user.getId(), etWebsite.getText().toString(), etSavedEmail.getText().toString(), etSavedPassword.getText().toString());
                db.close();
                finish();
            }
        });
    }
}