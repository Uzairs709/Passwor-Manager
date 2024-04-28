package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signup extends AppCompatActivity {

    EditText etPassSignup,etEmailSignup;
    Button btnCreateAccount;
    CredentialDatabase credentialDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
       init();
       createAccountListener();

    }
    private void init(){
        etEmailSignup=findViewById(R.id.etEmailSignup);
        etPassSignup=findViewById(R.id.etPassSignup);

        btnCreateAccount=findViewById(R.id.btnCreateAccount);
        credentialDatabase =new CredentialDatabase(this);
    }

    private void createAccountListener(){
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etEmailSignup.getText().toString().isEmpty()&&!etPassSignup.getText().toString().isEmpty()) {
                    credentialDatabase.open();
                    credentialDatabase.insertNewUser(etEmailSignup.getText().toString(), etPassSignup.getText().toString());
                    credentialDatabase.close();
                    Intent intent=new Intent(v.getContext(),LoginScreen.class);
                    Toast.makeText(v.getContext(),"Account created Successfully",Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(v.getContext(),"All Fields Required",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}