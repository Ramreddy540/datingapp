package com.example.otpverification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.otpverification.Adapter.ImagePagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StarFragment extends Fragment {

    private static final String TAG = "Star";

    private ViewPager2 viewPager2;

    private List<String> imageUrls;
    private ImagePagerAdapter adapter;
    private FirebaseFirestore db;

    private UserAdapter userAdapter;
    private List<User> userList;

    private String CurrentUserNumber;

    public StarFragment() {
        // Required empty public constructor
        db = FirebaseFirestore.getInstance();
        imageUrls = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_star, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String url1= "https://firebasestorage.googleapis.com/v0/b/otpverification-19c3b.appspot.com/o/images%2F1718286391754.jpg?alt=media&token=1f7a4d8b-bf8d-45bd-92c2-b45cd29f1e3d";
        String url2= "https://firebasestorage.googleapis.com/v0/b/otpverification-19c3b.appspot.com/o/images%2F1718289845140.jpg?alt=media&token=5fdbcab8-d0ba-478a-b2cc-680f5e63d992";
        String numb = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        CurrentUserNumber = numb.replace("+91", "");

        viewPager2 = view.findViewById(R.id.viewPager2);

        imageUrls.add(url1);
        imageUrls.add(url2);
        imageUrls.add(url1);

        adapter = new ImagePagerAdapter(getContext(), imageUrls);
        viewPager2.setAdapter(adapter);

        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

//        fetchData();

        //fetchAllUserCollections();

    }

    public void fetchImageUrls(final OnCompleteListener<List<String>> listener) {
        db.collectionGroup("step1").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.exists() && document.contains("image1")) {
                                    String imageUrl = document.getString("image1");
                                    imageUrls.add(imageUrl);
                                }
                            }

                        } else {

                        }
                    }


                });
    }

    private void fetchAllUserCollections() {
        CollectionReference usersCollection = db.collection("users");
        usersCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String userId = document.getId(); // This is the phone number
                    fetchImageUrlFromUserCollection(userId);
                }
            } else {
                Log.w(TAG, "Error getting user collections.", task.getException());
            }
        });
    }

    private void fetchImageUrlFromUserCollection(String userId) {
        db.collection("users").document(userId).collection("steps").document("step1").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String imageUrl = document.getString("image1");
                            if (imageUrl != null) {
                                imageUrls.add(imageUrl);
                                updateViewPager();
                            }
                        }
                    } else {
                        Log.w(TAG, "Error getting document for user: " + userId, task.getException());
                    }
                });
    }

    private void updateViewPager() {
        if (adapter == null) {
            adapter = new ImagePagerAdapter(getContext(), imageUrls);
            viewPager2.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void fetchData() {
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String height = document.getString("height");
                            String religion = document.getString("religion");
                            String community = document.getString("community");
                            String smoke = document.getString("smoke");
                            String status = document.getString("status");
                            String drinking = document.getString("drinking");
                            String language = document.getString("language");
                            List<String> imageUrls = (List<String>) document.get("imageUrls");

                            // Ensure imageUrls is not null
                            if (imageUrls == null) {
                                imageUrls = new ArrayList<>();
                            }

                            User user = new User(height, religion, community, smoke, status, drinking, language, imageUrls);
                            userList.add(user);
                        }
                        userAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Error getting documents.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
