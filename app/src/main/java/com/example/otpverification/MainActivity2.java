package com.example.otpverification;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3;
    private Uri uri1, uri2, uri3;
    private List<Uri> imageUris = new ArrayList<>();
    private final int REQUEST_CODE_PHOTO_1 = 1;
    private final int REQUEST_CODE_PHOTO_2 = 2;
    private final int REQUEST_CODE_PHOTO_3 = 3;
    private FirebaseFirestore db;

    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ivPhoto1 = findViewById(R.id.profileimage1);
        ivPhoto2 = findViewById(R.id.profileimage2);
        ivPhoto3 = findViewById(R.id.profileimage3);

        phoneNum = getIntent().getStringExtra("mobile");

        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();

        // Set OnClickListener for profile images
        setProfileImageOnClickListener(ivPhoto1, REQUEST_CODE_PHOTO_1);
        setProfileImageOnClickListener(ivPhoto2, REQUEST_CODE_PHOTO_2);
        setProfileImageOnClickListener(ivPhoto3, REQUEST_CODE_PHOTO_3);

        // Set OnClickListener for the button to move to the next activity
        Button moveToNextActivityButton = findViewById(R.id.moveToNextActivityButton);
        moveToNextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveToNextActivity();
            }
        });

        // Set OnClickListener for the skip TextView to move to the next activity
        TextView skipTextView = findViewById(R.id.skip1);
        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity2.this,
                        "Image uploaded", Toast.LENGTH_LONG).show();
                moveToNextActivity();
            }
        });
    }

    private void setProfileImageOnClickListener(final ImageView imageView, final int requestCode) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery(requestCode);
            }
        });
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            // Set the selected image to the appropriate ImageView
            if (requestCode == REQUEST_CODE_PHOTO_1) {
                ivPhoto1.setImageURI(uri);
                uri1 = uri;
            } else if (requestCode == REQUEST_CODE_PHOTO_2) {
                ivPhoto2.setImageURI(uri);
                uri2 = uri;
            } else if (requestCode == REQUEST_CODE_PHOTO_3) {
                ivPhoto3.setImageURI(uri);
                uri3 = uri;
            }
            // Upload the selected image to Firebase Storage
            uploadImageToFirebase(uri, phoneNum);
        }
    }

    private void uploadImageToFirebase(Uri uri, String phoneNum) {

        Map<String, Object> user = new HashMap<>();
        user.put("image1", uri1);
        user.put("image2", uri2);
        user.put("image3", uri3);

        db.collection(phoneNum).document("step1").set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {


                saveImageUrlToDatabase(uri.toString());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("TAG", "onFailure: "+e.getLocalizedMessage());

                    }
                });


    }

    private void saveImageUrlToDatabase(String imageUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("images");
        String imageId = databaseReference.push().getKey();
        databaseReference.child(imageId).setValue(imageUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity2.this, "Image URL saved to database", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity2.this, "Failed to save image URL to database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseDatabase", "Failed to save image URL to database", e);
                    }
                });
    }

    // Method to move to the next activity
    private void moveToNextActivity() {
        Intent intent = new Intent(MainActivity2.this, S1.class);
        startActivity(intent);
    }
}
