package com.example.otpverification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeartFragment extends Fragment {

    private RecyclerView recyclerView;
    private YourLikeAdapter yourLikeAdapter;
    private List<YourLikeItem> itemList;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String number;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        String user_num = auth.getCurrentUser().getPhoneNumber();
        number = user_num.replace("+91", "");

        itemList = new ArrayList<>();

        fetchAllLikes();

        recyclerView = view.findViewById(R.id.your_like_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        yourLikeAdapter = new YourLikeAdapter(getContext(), itemList);
        recyclerView.setAdapter(yourLikeAdapter);
    }

    private void fetchAllLikes() {

        // Fetch the user details from the 'users' collection
        db.collection("likes").document(number).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userDocument = task.getResult();
                    if (userDocument.exists()) {
                        String name = userDocument.getString("likedName");
                        String image = userDocument.getString("likedImage");
                        itemList.add(new YourLikeItem(image, name));

                    }

                    yourLikeAdapter.notifyDataSetChanged();

                } else {
                    Log.d("Firestore", "Error fetching user details: ", task.getException());
                }
            }
        });
    }
}
