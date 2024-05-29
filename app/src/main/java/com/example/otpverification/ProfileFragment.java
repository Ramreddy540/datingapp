package com.example.otpverification;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private CircleImageView ivPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        ivPhoto = rootView.findViewById(R.id.profileimage);

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
        TextView GetVerifiedTextView = rootView.findViewById(R.id.getverified);
        // Set OnClickListener to navigate to GetVerifiedActivity
        GetVerifiedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the HelpCenterActivity
                Intent intent = new Intent(getActivity(), GetVerified.class);
                startActivity(intent);
            }
        });

        // Get the "S2" TextView
        TextView EdirProfileTextView = rootView.findViewById(R.id.editprofile);
        // Set OnClickListener to navigate to S2Activity
        EdirProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the HelpCenterActivity
                Intent intent = new Intent(getActivity(), S2.class);
                startActivity(intent);
            }
        });
        // Get the "Help Center" TextView
        TextView helpCenterTextView = rootView.findViewById(R.id.helpCenterTextView);
        // Set OnClickListener to navigate to HelpCenterActivity
        helpCenterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the HelpCenterActivity
                Intent intent = new Intent(getActivity(), HelpCenter.class);
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
