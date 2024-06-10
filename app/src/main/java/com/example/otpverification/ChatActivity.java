package com.example.otpverification;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessageList;
    private ImageButton emojiButton;
    private ImageButton sendButton;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize chat message list with example data
        chatMessageList = new ArrayList<>();
        chatMessageList.add(new ChatMessage("Hey, how's it going, man? Long time!", R.drawable.jack, "10:00 AM", true));
        chatMessageList.add(new ChatMessage("Hey Rahul! Yeah, it's been ages. I've been good, just caught up with work mostly. You?", R.drawable.kp, "10:01 AM", false));
        chatMessageList.add(new ChatMessage("Hey, how's it going, man? Long time!", R.drawable.jack, "10:00 AM", true));
        chatMessageList.add(new ChatMessage("Hey Rahul! Yeah, it's been ages. I've been good, just caught up with work mostly. You?", R.drawable.kp, "10:01 AM", false)); // Add more chat messages as needed
        chatMessageList.add(new ChatMessage("Hey, how's it going, man? Long time!", R.drawable.jack, "10:00 AM", true));
        chatMessageList.add(new ChatMessage("Hey Rahul! Yeah, it's been ages. I've been good, just caught up with work mostly. You?", R.drawable.kp, "10:01 AM", false));
        chatMessageList.add(new ChatMessage("Hey, how's it going, man? Long time!", R.drawable.jack, "10:00 AM", true));
        chatMessageList.add(new ChatMessage("Hey Rahul! Yeah, it's been ages. I've been good, just caught up with work mostly. You?", R.drawable.kp, "10:01 AM", false)); // Add more chat messages as needed
        chatMessageList.add(new ChatMessage("Hey, how's it going, man? Long time!", R.drawable.jack, "10:00 AM", true));
        chatMessageList.add(new ChatMessage("Hey Rahul! Yeah, it's been ages. I've been good, just caught up with work mostly. You?", R.drawable.kp, "10:01 AM", false));
        chatMessageList.add(new ChatMessage("Hey, how's it going, man? Long time!", R.drawable.jack, "10:00 AM", true));
        chatMessageList.add(new ChatMessage("Hey Rahul! Yeah, it's been ages. I've been good, just caught up with work mostly. You?", R.drawable.kp, "10:01 AM", false)); // Add more chat messages as needed
        chatMessageList.add(new ChatMessage("Hey, how's it going, man? Long time!", R.drawable.jack, "10:00 AM", true));
        chatMessageList.add(new ChatMessage("Hey Rahul! Yeah, it's been ages. I've been good, just caught up with work mostly. You?", R.drawable.kp, "10:01 AM", false));
        chatMessageList.add(new ChatMessage("Hey, how's it going, man? Long time!", R.drawable.jack, "10:00 AM", true));
        chatMessageList.add(new ChatMessage("Hey Rahul! Yeah, it's been ages. I've been good, just caught up with work mostly. You?", R.drawable.kp, "10:01 AM", false)); // Add more chat messages as needed

        chatAdapter = new ChatAdapter(chatMessageList);
        recyclerView.setAdapter(chatAdapter);

        emojiButton = findViewById(R.id.emoji_button);
        sendButton = findViewById(R.id.send_button);
        editText = findViewById(R.id.edit_text);



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle send button click
                String message = editText.getText().toString();
                if (!message.isEmpty()) {
                    chatMessageList.add(new ChatMessage(message, R.drawable.ic_launcher_background, "Now", true));
                    chatAdapter.notifyItemInserted(chatMessageList.size() - 1);
                    recyclerView.scrollToPosition(chatMessageList.size() - 1);
                    editText.setText("");
                }
            }
        });
    }
}