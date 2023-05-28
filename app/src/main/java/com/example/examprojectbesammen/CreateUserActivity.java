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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CreateUserActivity extends AppCompatActivity {
    //Oskar
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Button createUserBtn = findViewById(R.id.button3);
        createUserBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_700));



        createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailInput = findViewById(R.id.editTextTextEmailAddress2);
                EditText passwordInput = findViewById(R.id.editTextTextPassword2);
                EditText validatePasswordInput = findViewById(R.id.editTextTextPassword3);

                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String validatePassword = validatePasswordInput.getText().toString();

                boolean check1 = validateEmail(email);
                boolean check2 = validatePassword(password);
                boolean check3 = doesPasswordsMatch(password, validatePassword);

                if (check1 && check2 && check3) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                CollectionReference usersRef = db.collection("Users");
                                String UserId = task.getResult().getUser().getUid();

                                Map<String, Object> user = new HashMap<>();
                                user.put("email", email);
                                user.put("username", email);
                                usersRef.document(email).set(user);

                                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> loginTask) {
                                        if (loginTask.isSuccessful()) {
                                            Log.d("USER CREATION", "onComplete: USER CREATED AND LOGGED IN");
                                            Intent toMenu = new Intent(CreateUserActivity.this, MenuActivity.class);
                                            startActivity(toMenu);
                                        } else {
                                            Toast.makeText(CreateUserActivity.this, "Failed to log in User", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(CreateUserActivity.this, "Failed to create User", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

            }
        });
    }

    private static final Pattern EmailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); // email regex pattern found online
    private static final Pattern PasswordPattern = Pattern.compile("^(?=.*\\d).{8,}$"); //password pattern chatgpt gave me,-
    // -asked for at least 8 characters and at least 1 number

    private boolean validateEmail(String email) {
        if (!EmailPattern.matcher(email).matches()) {
            Toast.makeText(CreateUserActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
        }
        return EmailPattern.matcher(email).matches(); // returns true if email matches pattern
    }

    private boolean validatePassword(String password) {
        if (!PasswordPattern.matcher(password).matches()) {
            Toast.makeText(CreateUserActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
        }
        return PasswordPattern.matcher(password).matches();
    }

    private boolean doesPasswordsMatch(String password, String password2) {
        return password.equals(password2);
    }

}
