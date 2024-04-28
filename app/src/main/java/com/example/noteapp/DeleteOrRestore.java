package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DeleteOrRestore extends AppCompatActivity {

    UserCredential userCredential;

    TextView tvWebsite,tvEmail,tvPass;
    Button btnRestore,btnDelete;

    CredentialDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_or_restore);
        init();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.deleteCredential(userCredential.getId());
                db.close();
                finish();
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.updateSavedData(userCredential.getId(),tvWebsite.getText().toString(),tvEmail.getText().toString(),tvPass.getText().toString(),0);
                db.close();
                finish();
            }
        });
    }

    private void init(){
        tvWebsite=findViewById(R.id.tvWebsite);
        tvEmail=findViewById(R.id.tvEmail);
        tvPass=findViewById(R.id.tvPassword);

        btnDelete=findViewById(R.id.btnPermDelete);
        btnRestore=findViewById(R.id.btnRestore);

        db=new CredentialDatabase(DeleteOrRestore.this);
        Intent intent=getIntent();
        userCredential= (UserCredential) intent.getSerializableExtra("DeletedCredential");

        tvWebsite.setText(userCredential.getWebsite());
        tvEmail.setText(userCredential.getEmail());
        tvPass.setText(userCredential.getPassword());
    }
}