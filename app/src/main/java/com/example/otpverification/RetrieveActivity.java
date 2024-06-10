//package com.example.otpverification;
//
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.otpverification.UserData;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.auth.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RetrieveActivity extends AppCompatActivity {
//
//    private FirebaseFirestore db;
//    private RecyclerView recyclerView;
//    private UserDataAdapter adapter;
//    private List<UserData> userDataList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_retrieve);
//
//        // Initialize Firebase Firestore
//        db = FirebaseFirestore.getInstance();
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        userDataList = new ArrayList<>();
//        adapter = new UserDataAdapter(userDataList);
//        recyclerView.setAdapter(adapter);
//
//        // Retrieve data from Firestore
//        retrieveData();
//
//        // Retrieve image URLs from Firebase Realtime Database
//        retrieveImageUrls();
//    }
//
//    private void retrieveData() {
//        db.collection("UserData")
//                .document("User1")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            // Retrieve data from the document
//                            String height = document.getString("Height");
//                            String religion = document.getString("Religion");
//                            String community = document.getString("Community");
//                            String smoke = document.getString("Smoke");
//                            String status = document.getString("Status");
//                            String drinking = document.getString("Drinking");
//                            String language = document.getString("Language");
//
//                            // Create UserData object
//                            UserData userData = new UserData();
//                            userData.setHeight(height);
//                            userData.setReligion(religion);
//                            userData.setCommunity(community);
//                            userData.setSmoke(smoke);
//                            userData.setStatus(status);
//                            userData.setDrinking(drinking);
//                            userData.setLanguage(language);
//
//                            // Add UserData object to the list
//                            userDataList.add(userData);
//
//                            // Notify adapter about data changes
//                            adapter.notifyDataSetChanged();
//                        } else {
//                            Toast.makeText(RetrieveActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(RetrieveActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//
//    private void retrieveImageUrls() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int i = 1;
//                // Iterate through the dataSnapshot to retrieve image URLs
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String imageUrl = snapshot.getValue(String.class);
//                    // Load the image into the appropriate ImageView using Glide
//                    loadImage(imageUrl, i);
//                    i++;
//                    // Stop after loading 3 images
//                    if (i > 3) {
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle error
//                Toast.makeText(RetrieveActivity.this, "Failed to retrieve image URLs", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void loadImage(String imageUrl, int imageViewNumber) {
//        // Get the appropriate ImageView based on the number
//        ImageView imageView;
//        switch (imageViewNumber) {
//            case 1:
//                imageView = findViewById(R.id.imageView1);
//                break;
//            case 2:
//                imageView = findViewById(R.id.imageView2);
//                break;
//            case 3:
//                imageView = findViewById(R.id.imageView3);
//                break;
//            default:
//                return;
//        }
//        // Load image into the ImageView using Glide
//        Glide.with(this)
//                .load(imageUrl)
//                .apply(new RequestOptions().placeholder(R.drawable.placeholder)) // Placeholder image
//                .into(imageView);
//    }
//}
