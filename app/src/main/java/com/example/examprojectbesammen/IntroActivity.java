package com.example.examprojectbesammen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
//Oskar, Gustav
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide(); // hides action bar

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toLogin = new Intent(IntroActivity.this, LogOnActivity.class);
                startActivity(toLogin);
                finish(); //removes activity from stack
            }
        }, 3000);
    }
}
