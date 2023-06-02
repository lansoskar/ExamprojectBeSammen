package com.example.examprojectbesammen;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<String> messages;
    private String loggedInUser;

    public MessageAdapter(List<String> messages, String loggedInUser) {
        this.messages = messages != null ? messages : new ArrayList<>();
        this.loggedInUser = loggedInUser;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages != null ? messages : new ArrayList<>();
        notifyDataSetChanged();
    }

    //Lasse, David
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    //Gustav, Lasse
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String message = messages.get(position);
        String[] messageParts = message.split(": ");
        String author = messageParts[0];
        String text = messageParts[1];

        holder.bind(text);
        if (loggedInUser.equals(author)) {
            holder.messageTextView.setBackgroundResource(R.drawable.authormessage);
            holder.messageTextView.setGravity(Gravity.END);
            holder.messageTextView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            holder.messageTextView.setBackgroundResource(R.drawable.othermessage);
            holder.messageTextView.setGravity(Gravity.START);
            holder.messageTextView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }

    }

    //Lasse, David
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
