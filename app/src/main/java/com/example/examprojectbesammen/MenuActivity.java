package com.example.examprojectbesammen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button tipsAndTricksMenuBtn = findViewById(R.id.tipsAndTricksMenuBtn);
        tipsAndTricksMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, TipsAndTricksActivity.class);
            startActivity(intent);
        });
        Button yourConversations = findViewById(R.id.chatMenuBtn);
        yourConversations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toChat = new Intent(MenuActivity.this, ChatActivity.class);
                startActivity(toChat);
            }
        });

    }

    public void onTipsAndTricksMenuButtonClick(View view) {
        Intent intent = new Intent(MenuActivity.this, TipsAndTricksActivity.class);
        startActivity(intent);
    }
}