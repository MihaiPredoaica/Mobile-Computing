package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ui.notifications.NotificationsFragment;
import com.example.myapplication.ui.profile.ProfileFragment;
import com.example.myapplication.ui.profile.ProfileViewModel;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login;

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPassword);
        db = new Database(this);

        login = findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString();
                String sPassword = password.getText().toString();

                Boolean chkEmailPassword = db.checkEmailPassword(sEmail, sPassword);
                if(chkEmailPassword){
                    Intent intent = new Intent(MainActivity.this, BottomNavigation.class);
                    startActivity(intent);
                    DataContent userDataContent = com.example.myapplication.DataContent.getInstance();
                    userDataContent.setEmail(sEmail);
                    String sUsername = db.getUsername(sEmail);
                    int id = db.getUserId(sEmail);
                    userDataContent.setUsername(sUsername);
                    userDataContent.setPassword(sPassword);
                    userDataContent.setUserId(id);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView registerHere = findViewById(R.id.registerHere);
        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

    }
}
