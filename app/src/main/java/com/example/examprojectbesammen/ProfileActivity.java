package com.example.examprojectbesammen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        getSupportActionBar().setTitle(user + "'s profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button resetPassBtn = findViewById(R.id.button4);
        resetPassBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_700));

        Button logOutBtn = findViewById(R.id.button5);
        logOutBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_700));

    }
    //methods to back button on action bar
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent toMenu = new Intent(ProfileActivity.this, MenuActivity.class);
        startActivity(toMenu);
    }
}

