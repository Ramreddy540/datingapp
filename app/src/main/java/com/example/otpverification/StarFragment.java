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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.otpverification.Adapter.ImagePagerAdapter;
import com.example.otpverification.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.razorpay.PaymentResultListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StarFragment extends Fragment implements PaymentResultListener, OnImageClickListener
{

    private ViewPager2 viewPager2;
    private ImagePagerAdapter adapter;
    private FirebaseFirestore db;
    private static final int SCROLL_THRESHOLD = 2;
    private int verticalScrollCount = 0;
    private int previousPosition = 0;
    private UserAdapter userAdapter;
    private List<User> userList;
    private String CurrentUserNumber;
    private List<Users> viewPagerItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_star, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(),
                R.color.pink));

        db = FirebaseFirestore.getInstance();

        String numb = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        if (numb != null) {
            CurrentUserNumber = numb.replace("+91", "");

            viewPager2 = view.findViewById(R.id.viewPager2);
            viewPagerItems = new ArrayList<>();
            adapter = new ImagePagerAdapter(getContext(), viewPagerItems, this);
            viewPager2.setAdapter(adapter);

            viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

            // setupScrollListener();

            fetchAllUsers();

        }
        else {
            // Handle the case where the phone number is null
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }

    }

    private void fetchAllUsers() {

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult()) {

                                String userId = document.getId();
                                if (!userId.equals(CurrentUserNumber)) {

                                    String firstName = document.getString("firstName");
                                    String dob = document.getString("dob");
                                    String height = document.getString("Height");
                                    String religion = document.getString("Religion");
                                    String status = document.getString("Status");
                                    String language = document.getString("Language");
                                    String smoke = document.getString("Smoke");
                                    String community = document.getString("Community");
                                    String drinking = document.getString("Drinking");
                                    String imageResId = document.getString("image1");
                                    String phone_Num = document.getString("phoneNum");

                                    // Calculate age from DOB
                                    int age = calculateAge(dob);

                                    viewPagerItems.add(new Users(firstName, String.valueOf(age), height, religion,
                                            status, language, smoke, community, drinking, imageResId, phone_Num));

                                }

                            }

                            adapter.notifyDataSetChanged();

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public int calculateAge(String dobString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dob = sdf.parse(dobString);
            Calendar dobCalendar = Calendar.getInstance();
            dobCalendar.setTime(dob);

            Calendar today = Calendar.getInstance();

            int age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0; // Default age if parsing fails
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

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("TAG", "onPaymentSuccess:" + s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("TAG", "onPaymentError:");
    }

    @Override
    public void onImageClick(String likedUserName, String likedUserImage, String likedUserPhone) {

        storeLike(CurrentUserNumber, likedUserName, likedUserImage, likedUserPhone);

    }

    private void storeLike(String likerId, String likedName, String likedUserImage, String likedUserPhone) {

        Map<String, Object> likeData = new HashMap<>();
        likeData.put("likedName",  likedName);
        likeData.put("likedImage", likedUserImage);
        likeData.put("timestamp",  FieldValue.serverTimestamp());

        db.collection("likes")
                .document(likerId)
                .collection("liked_by_you")
                .document(likedUserPhone)
                .set(likeData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Profile Liked", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error adding like", e);
                    }
                });

    }

}
