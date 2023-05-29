package com.example.examprojectbesammen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        Button tipsAndTricksMenuBtn = findViewById(R.id.tipsAndTricksMenuBtn);
        tipsAndTricksMenuBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_500));

        Button profileBtn = findViewById(R.id.profileMenuBtn);
        profileBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_500));

        Button yourConversations = findViewById(R.id.chatMenuBtn);
        yourConversations.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_700));


        tipsAndTricksMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, TipsAndTricksActivity.class);
            startActivity(intent);
        });
        yourConversations = findViewById(R.id.chatMenuBtn);

        yourConversations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toChat = new Intent(MenuActivity.this, ChatActivity.class);
                startActivity(toChat);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfile = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(toProfile);
            }
        });

    }

    public void onTipsAndTricksMenuButtonClick(View view) {
        Intent intent = new Intent(MenuActivity.this, TipsAndTricksActivity.class);
        startActivity(intent);
    }
}