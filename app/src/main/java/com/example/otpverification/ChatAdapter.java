package com.example.otpverification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<ChatMessage> chatMessageList;

    public ChatAdapter(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage currentMessage = chatMessageList.get(position);
        holder.profileImageView.setImageResource(currentMessage.getProfilePic());
        holder.messageTextView.setText(currentMessage.getMessage());
        holder.timeTextView.setText(currentMessage.getTime());

        // Align messages to left or right based on sender
        if (currentMessage.isSentByMe()) {
            holder.messageTextView.setBackgroundResource(R.drawable.bg_message_sent);
            holder.messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            holder.messageTextView.setBackgroundResource(R.drawable.bg_message_received);
            holder.messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView messageTextView;
        TextView timeTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
