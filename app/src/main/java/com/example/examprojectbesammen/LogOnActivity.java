package com.example.examprojectbesammen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogOnActivity extends AppCompatActivity {
//Oskar
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        FirebaseApp.initializeApp(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        Button logInUserBtn = findViewById(R.id.button);
        Button createUserBtn = findViewById(R.id.button2);
        logInUserBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_700));
        createUserBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_500));

        logInUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailInput = findViewById(R.id.editTextTextEmailAddress);
                EditText passwordInput = findViewById(R.id.editTextTextPassword);

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent toMenu = new Intent(LogOnActivity.this, MenuActivity.class);
                            startActivity(toMenu);
                        } else {
                            Toast.makeText(LogOnActivity.this, "login failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreateUser = new Intent(LogOnActivity.this, CreateUserActivity.class);
                startActivity(toCreateUser);
            }
        });
    }
}
