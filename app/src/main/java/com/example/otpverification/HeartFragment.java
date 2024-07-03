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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        yourLikeAdapter = new YourLikeAdapter(getContext(), itemList);
        recyclerView.setAdapter(yourLikeAdapter);
    }

    private void fetchAllLikes() {

        db.collection("likes")
                .document(number)
                .collection("liked_by_you")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                    String name = document.getString("likedName");
                                    String image = document.getString("likedImage");
                                    itemList.add(new YourLikeItem(image, name));

                            }

                            yourLikeAdapter.notifyDataSetChanged();

                        } else {
                            Log.d("Firestore", "Error getting liked users: ", task.getException());
                        }
                    }
                });


    }
}
