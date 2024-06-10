package com.example.otpverification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HeartFragment extends Fragment {

    private RecyclerView recyclerView;
    private YourLikeAdapter yourLikeAdapter;
    private List<YourLikeItem> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.your_like_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

        yourLikeAdapter = new YourLikeAdapter(getContext(), itemList);
        recyclerView.setAdapter(yourLikeAdapter);
    }
}
