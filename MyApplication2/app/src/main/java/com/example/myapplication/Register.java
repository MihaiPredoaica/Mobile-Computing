package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText email, username, password, confPassword;
    DataContent dataContent;
    Database db;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        db = new Database(this);
        dataContent = com.example.myapplication.DataContent.getInstance();

        email = (EditText) findViewById(R.id.registerEmail);
        username = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.registerPassword);
        confPassword = (EditText) findViewById(R.id.registerConfirmPass);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString();
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();
                String sConfPassword = confPassword.getText().toString();

                if(sEmail.equals("") || sUsername.equals("") || sPassword.equals("") || sConfPassword.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }else {
                    if(sPassword.equals(sConfPassword)){
                        Boolean chkEmail = db.chkEmail(sEmail);
                        if(chkEmail){
                            Boolean insert = db.insertUser(sEmail, sUsername, sPassword, 300);
                            if(insert){
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
