package com.example.examprojectbesammen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapterActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<String> messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        messagesList = new ArrayList<>(); // Initialize your list of messages

        // Add some sample messages for testing
        messagesList.add("Hello");
        messagesList.add("How are you?");
        messagesList.add("I'm fine, thank you!");

        messageAdapter = new MessageAdapter(messagesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);
    }

    // Your activity code...

    private static class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

        private List<String> messages;

        public MessageAdapter(List<String> messages) {
            this.messages = messages;
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            String message = messages.get(position);
            holder.bind(message);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public static class MessageViewHolder extends RecyclerView.ViewHolder {
            private TextView messageTextView;

            public MessageViewHolder(@NonNull View itemView) {
                super(itemView);
                messageTextView = itemView.findViewById(R.id.messageTextView);
            }

            public void bind(String message) {
                messageTextView.setText(message);
            }
        }
    }