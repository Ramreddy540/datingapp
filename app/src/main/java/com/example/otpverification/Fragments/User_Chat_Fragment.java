package com.example.otpverification.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otpverification.Adapter.MessageAdapter;
import com.example.otpverification.Models.Message;
import com.example.otpverification.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User_Chat_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText messageEditText;
    private ImageButton sendButton;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private DatabaseReference chatReference;

    // Replace these with dynamic values based on user selection or authentication
    private String chatId = "chatId1";
    private String userId = "user2";
    private String receiverId = "user1";

    // List of disallowed words
    private static final List<String> disallowedWords = Arrays.asList(
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "zero"
    );

    public User_Chat_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user__chat_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        messageEditText = view.findViewById(R.id.messageEditText);
        sendButton = view.findViewById(R.id.sendButton);


        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, userId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(messageAdapter);

        chatReference = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        receiveMessages();
    }

    private void sendMessage() {
        String messageText = messageEditText.getText().toString();
        if (!TextUtils.isEmpty(messageText) && !containsDisallowedContent(messageText)) {
            String messageId = chatReference.push().getKey();
            Message message = new Message(userId, receiverId, messageText, System.currentTimeMillis());
            chatReference.child(messageId).setValue(message);
            messageEditText.setText("");
        } else {
            Toast.makeText(getActivity(), "Message contains numbers or disallowed words and cannot be sent", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean containsDisallowedContent(String message) {
        // Check for digits
        for (int i = 0; i < message.length(); i++) {
            if (Character.isDigit(message.charAt(i))) {
                return true;
            }
        }

        // Check for disallowed words
        String[] words = message.toLowerCase().split("\\s+");
        for (String word : words) {
            if (disallowedWords.contains(word)) {
                return true;
            }
        }

        return false;
    }

    private void receiveMessages() {
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    messageList.add(message);
                }
                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}
