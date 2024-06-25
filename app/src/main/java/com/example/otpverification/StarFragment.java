

package com.example.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.otpverification.Adapter.ImagePagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.razorpay.PaymentResultListener;

import java.util.ArrayList;
import java.util.List;

public class StarFragment extends Fragment implements PaymentResultListener {

    private ViewPager2 viewPager2;
    private List<String> imageUrls;
    private ImagePagerAdapter adapter;
    private FirebaseFirestore db;
    private static final int SCROLL_THRESHOLD = 2; // Number of scrolls required
    private int verticalScrollCount = 0;
    private int previousPosition = 0;
    private UserAdapter userAdapter;
    private List<User> userList;
    private String CurrentUserNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_star, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        imageUrls = new ArrayList<>();

        String url1 = "https://firebasestorage.googleapis.com/v0/b/otpverification-19c3b.appspot.com/o/images%2F1718286391754.jpg?alt=media&token=1f7a4d8b-bf8d-45bd-92c2-b45cd29f1e3d";
        String url2 = "https://firebasestorage.googleapis.com/v0/b/otpverification-19c3b.appspot.com/o/images%2F1718289845140.jpg?alt=media&token=5fdbcab8-d0ba-478a-b2cc-680f5e63d992";
        String numb = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        if (numb != null) {
            CurrentUserNumber = numb.replace("+91", "");

            viewPager2 = view.findViewById(R.id.viewPager2);

            imageUrls.add(url1);
            imageUrls.add(url2);
            imageUrls.add(url1);

            adapter = new ImagePagerAdapter(getContext(), imageUrls);
            viewPager2.setAdapter(adapter);

            viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

            setupScrollListener();
        } else {
            // Handle the case where the phone number is null
            Toast.makeText(getContext(), "Phone number is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupScrollListener() {
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position > previousPosition) {
                    verticalScrollCount++;
                    Log.d("TAG", "Scroll count: " + verticalScrollCount);
                    checkScrollCount();
                }
                previousPosition = position;
            }
        });
    }

    private void checkScrollCount() {
        if (verticalScrollCount >= SCROLL_THRESHOLD && !isPremiumUser()) {
            openMonthlyChargesActivity();
        }
    }

    private boolean isPremiumUser() {
        // Replace with your actual logic to check if the user is a premium customer
        return false;
    }

    private void openMonthlyChargesActivity() {
        Intent intent = new Intent(getActivity(), MonthlyCharges.class);
        startActivity(intent);
        // Reset the scroll count if needed
        verticalScrollCount = 0;
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

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("TAG", "onPaymentSuccess: " + s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("TAG", "onPaymentError: ");
    }
}
