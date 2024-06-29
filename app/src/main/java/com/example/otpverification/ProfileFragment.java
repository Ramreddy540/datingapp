package com.example.otpverification;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private CircleImageView ivPhoto;

    private TextView text1;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        ivPhoto = rootView.findViewById(R.id.profileimage);
        text1=rootView.findViewById(R.id.firstNameTextView);
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();


        String userId = String.format("%s",mAuth.getCurrentUser().getPhoneNumber().substring("+91".length()));
        Log.d("TAG", "Phone:"+userId);

        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {


                            String Fname = document.getString("firstName");
                            String Lname = document.getString("lastName");
                            String image = document.getString("image1");

                            text1.setText(Fname+" "+Lname);

                            Glide.with(getActivity().getApplicationContext()).load(image).into(ivPhoto);



                        }
                    }});



                            ImageView vector = rootView.findViewById(R.id.vector);
        vector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });

        // Get the "Get Verified" TextView
        TextView getVerifiedTextView = rootView.findViewById(R.id.getverified);
        // Set OnClickListener to navigate to GetVerifiedActivity
        getVerifiedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GetVerified.class);
                startActivity(intent);
            }
        });

        // Get the "Edit Profile" TextView
        TextView editProfileTextView = rootView.findViewById(R.id.editprofile);
        // Set OnClickListener to navigate to S2Activity
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity3.class);
                startActivity(intent);
            }
        });

        // Get the "Instagram" TextView
        TextView instagramTextView = rootView.findViewById(R.id.instagra);
        // Set OnClickListener to navigate to InstagramActivity
        instagramTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InstagramActivity.class);
                startActivity(intent);
            }
        });

        // Get the "Help Center" TextView
        TextView helpCenterTextView = rootView.findViewById(R.id.helpCenterTextView);
        // Set OnClickListener to navigate to HelpCenterActivity
        helpCenterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpCenter.class);
                startActivity(intent);
            }
        });

        if (getArguments() != null) {
            String firstName = getArguments().getString("firstName", "");
            TextView firstNameTextView = rootView.findViewById(R.id.firstNameTextView);
            firstNameTextView.setText(firstName);
        }
        Button myButton = rootView.findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MonthlyCharges.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            Uri uri = data.getData();
            ivPhoto.setImageURI(uri);
        }
    }
}
