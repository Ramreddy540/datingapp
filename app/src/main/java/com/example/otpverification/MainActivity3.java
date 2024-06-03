package com.example.otpverification;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MemberAdapter memberAdapter;
    private List<Member> memberList;
    private ImageButton emojiButton;
    private ImageButton sendButton;
    private EditText editText;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recycler_view1);
        emojiButton = findViewById(R.id.emoji_button);
        sendButton = findViewById(R.id.send_button);
        editText = findViewById(R.id.edit_text);

        // Initialize member list with drawable resource IDs and example data
        memberList = new ArrayList<>();
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("M S Dhoni", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        // Add more members as needed

        // Set up RecyclerView
        memberAdapter = new MemberAdapter(memberList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(memberAdapter);

        // Set up search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when query is submitted
                memberAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search when text is changed
                memberAdapter.filter(newText);
                return false;
            }
        });




    }
}
