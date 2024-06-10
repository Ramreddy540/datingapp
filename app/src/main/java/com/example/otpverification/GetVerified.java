package com.example.otpverification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class GetVerified extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1; // Define the request code
    private static final int CAMERA_PERMISSION_CODE = 101;

    private FirebaseFirestore db;
    private ImageView imageView,imageView1; // ImageView to display the captured image
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_verified);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference().child("verified_profile");

        TextView verifiedTextView = findViewById(R.id.verified);
        verifiedTextView.setText(getString(R.string.Get_Verified));

        TextView selfieTextView = findViewById(R.id.selfie);
        selfieTextView.setText(getString(R.string.click_the_selfie_same_as_above_imagee));

        imageView = findViewById(R.id.plus);
        imageView1=findViewById(R.id.back);// Initialize the ImageView

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void openCamera(View view) {
        // Check if the camera permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission if it's not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            // Permission is already granted, open camera intent
            startCamera();
        }
    }

    private void startCamera() {
        // Open camera intent here
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, start camera
                startCamera();
            } else {
                // Camera permission denied, show a message
                Toast.makeText(this, "Camera permission is required to open the camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the image captured by the camera and set it to the ImageView
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap); // Set the captured image to the ImageView
        }
    }

    public void onSaveClicked(View view) {
        // Upload the image to Firebase
        uploadImageToFirebase();

        // Move to the next activity
        Intent intent = new Intent(this, MainActivity.class); // Replace NextActivity with the desired activity
        startActivity(intent);
        finish(); // Finish the current activity if you don't want to go back to it
    }

    public void uploadImageToFirebase() {
        // Get the bitmap from the ImageView
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap imageBitmap = imageView.getDrawingCache();

        // Convert the bitmap to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] imageData = baos.toByteArray();

        // Upload the byte array to Firebase Storage
        StorageReference imageRef = storageReference.child("Verification Images");
        UploadTask uploadTask = imageRef.putBytes(imageData);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image uploaded successfully
                // Handle success if needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the failure
                Log.e("FirebaseUpload", "Failed to upload image to Firebase: " + e.getMessage());
                Toast.makeText(GetVerified.this, "Failed to upload image to Firebase", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
