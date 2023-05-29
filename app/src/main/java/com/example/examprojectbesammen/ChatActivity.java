package com.example.examprojectbesammen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private CollectionReference messagesRef;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private EditText messageEditText;
    private Button sendButton;

    private List<String> messages;

    //Oskar, Lasse, Gustav, David
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setTitle("gruppechat: x");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        firestore = FirebaseFirestore.getInstance();
        messagesRef = firestore.collection("Messages");

        recyclerView = findViewById(R.id.recyclerView);
        messageAdapter = new MessageAdapter(messages, FirebaseAuth.getInstance().getCurrentUser().getEmail());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);


        messages = new ArrayList<>();
        fetchMessages();

        sendButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_700));

//Lasse, David
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString().trim();
                if (!message.isEmpty()) {
                    sendMessage(message);
                } else {
                    Toast.makeText(ChatActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Oskar, Lasse, David
    private void sendMessage(String message) {
        String author = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Date timestamp = new Date();

        Map<String, Object> data = new HashMap<>();
        data.put("text", message);
        data.put("author", author);
        data.put("timestamp", timestamp);

        messagesRef.add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Message sent successfully
                        messageEditText.setText(""); // Clear the input field

                        messages.add(message);
                        messageAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle message sending failure
                        Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Oskar, Lasse, Gustav, David
    private void fetchMessages() {
        messagesRef.orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(ChatActivity.this, "Error fetching messages", Toast.LENGTH_SHORT).show();
                }
                if (value != null) {
                    List<String> messages = new ArrayList<>();
                    String loggedInUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        if (documentSnapshot.contains("text")) {
                            String text = documentSnapshot.getString("text");
                            String author = documentSnapshot.getString("author");

                            String message = author + ": " + text;
                            messages.add(message);

                        }
                    }
                    messageAdapter.setMessages(messages);
                    scrollToLastMessage();
                }
            }
        });
    }

    //Oskar, Lasse
    private void scrollToLastMessage(){
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
            }
        });
    }

    //methods to back button on action bar

    //Oskar
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent toMenu = new Intent(ChatActivity.this, MenuActivity.class);
        startActivity(toMenu);
    }
}