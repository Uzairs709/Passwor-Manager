package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button btnLogin,btnSignUp;

    CredentialDatabase credentialDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        init();

        loginListener();
        signUpListener();
    }
    private void init(){
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);

        btnLogin=findViewById(R.id.btnLogin);
        btnSignUp=findViewById(R.id.btnSignUp);

        credentialDatabase=new CredentialDatabase(this);
    }
    private void loginListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                    credentialDatabase.open();
                    User user = credentialDatabase.returnUser(etEmail.getText().toString(), etPassword.getText().toString());
                    credentialDatabase.close();
                    if (user != null) {
                        Toast.makeText(v.getContext(), "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("User", user);
                        startActivity(intent);

                        finish();
                    } else {
                        Toast.makeText(v.getContext(), "Invalid Email or Password ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), "Enter all Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void signUpListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });
    }

}