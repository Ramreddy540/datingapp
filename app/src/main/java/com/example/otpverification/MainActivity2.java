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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3;

    private final int REQUEST_CODE_PHOTO_1 = 1;
    private final int REQUEST_CODE_PHOTO_2 = 2;
    private final int REQUEST_CODE_PHOTO_3 = 3;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ivPhoto1 = findViewById(R.id.profileimage1);
        ivPhoto2 = findViewById(R.id.profileimage2);
        ivPhoto3 = findViewById(R.id.profileimage3);

        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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
            } else if (requestCode == REQUEST_CODE_PHOTO_2) {
                ivPhoto2.setImageURI(uri);
            } else if (requestCode == REQUEST_CODE_PHOTO_3) {
                ivPhoto3.setImageURI(uri);
            }
            // Upload the selected image to Firebase Storage
            uploadImageToFirebase(uri);
        }
    }

    private void uploadImageToFirebase(Uri uri) {
        String imageId = UUID.randomUUID().toString();
        StorageReference imageRef = storageReference.child("images/" + imageId);
        imageRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // If the image upload is successful, save its URL to the Firebase Realtime Database
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                saveImageUrlToDatabase(imageUrl);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to upload image
                        Toast.makeText(MainActivity2.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseStorage", "Failed to upload image", e);
                    }
                });
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");
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
