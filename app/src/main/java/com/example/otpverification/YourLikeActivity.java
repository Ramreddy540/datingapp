package com.example.otpverification;import android.os.Bundle;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class YourLikeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private YourLikeAdapter yourLikeAdapter;
    private List<YourLikeItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_like);

        recyclerView = findViewById(R.id.your_like_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        // Add items to the list (example)
        itemList.add(new YourLikeItem(R.drawable.jack, "Gandhi"));
        itemList.add(new YourLikeItem(R.drawable.kp, "Madhan"));
        itemList.add(new YourLikeItem(R.drawable.jack, "Jr PK"));
        itemList.add(new YourLikeItem(R.drawable.kp, "Nagi"));
        itemList.add(new YourLikeItem(R.drawable.jack, "Naresh"));
        itemList.add(new YourLikeItem(R.drawable.kp, "Akhil"));
        itemList.add(new YourLikeItem(R.drawable.jack, "vishnu"));
        itemList.add(new YourLikeItem(R.drawable.jack, "Gandhi"));
        itemList.add(new YourLikeItem(R.drawable.kp, "Madhan"));
        itemList.add(new YourLikeItem(R.drawable.jack, "Jr PK"));
        itemList.add(new YourLikeItem(R.drawable.kp, "Nagi"));
        itemList.add(new YourLikeItem(R.drawable.jack, "Naresh"));
        itemList.add(new YourLikeItem(R.drawable.kp, "Akhil"));
        itemList.add(new YourLikeItem(R.drawable.jack, "vishnu"));

        yourLikeAdapter = new YourLikeAdapter(this, itemList);
        recyclerView.setAdapter(yourLikeAdapter);
    }
}
git