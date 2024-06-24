package com.example.otpverification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class SupportActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageVi;
    private EditText emailField, firstNameField, mobileNumberField, helpField, descriptionField;
    private Button submitButton;
    private Button addFileButton;
    private TextView attachmentTextView;
    private Uri imageUri;

    // Realtime Database reference
    private DatabaseReference db;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        // Initialize Realtime Database reference
        db = FirebaseDatabase.getInstance().getReference("support");
        storageReference = FirebaseStorage.getInstance().getReference();

        // Find views by ID
        imageVi = findViewById(R.id.ArrowBackward);
        emailField = findViewById(R.id.emailField);
        firstNameField = findViewById(R.id.firstNameField);
        mobileNumberField = findViewById(R.id.mobileNumberField);
        helpField = findViewById(R.id.helpField);
        descriptionField = findViewById(R.id.descriptionField);
        submitButton = findViewById(R.id.submitButton);
        addFileButton = findViewById(R.id.addFileButton);
        attachmentTextView = findViewById(R.id.attachmentTextView);

        // Set click listener for back arrow
        imageVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Set click listener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRequest();
            }
        });

        // Set click listener for add file button
        addFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            attachmentTextView.setText("File Selected: " + imageUri.getLastPathSegment());
        }
    }

    private void submitRequest() {
        String email = emailField.getText().toString().trim();
        String firstName = firstNameField.getText().toString().trim();
        String mobileNumber = mobileNumberField.getText().toString().trim();
        String help = helpField.getText().toString().trim();
        String description = descriptionField.getText().toString().trim();

        if (!isValidEmail(email)) {
            Toast.makeText(SupportActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidName(firstName)) {
            Toast.makeText(SupportActivity.this, "First name should start with a capital letter.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidPhoneNumber(mobileNumber)) {
            Toast.makeText(SupportActivity.this, "Please enter a valid 10-digit phone number.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (help.isEmpty() || description.isEmpty()) {
            Toast.makeText(SupportActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri != null) {
            uploadImageToRealtimeDatabase(email, firstName, mobileNumber, help, description);
        } else {
            saveRequestToRealtimeDatabase(email, firstName, mobileNumber, help, description, null);
        }
    }

    private void uploadImageToRealtimeDatabase(String email, String firstName, String mobileNumber, String help, String description) {
        final StorageReference fileReference = storageReference.child("uploads/" + UUID.randomUUID().toString());

        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    saveRequestToRealtimeDatabase(email, firstName, mobileNumber, help, description, imageUrl);
                }))
                .addOnFailureListener(e -> Toast.makeText(SupportActivity.this, "Failed to upload image.", Toast.LENGTH_SHORT).show());
    }

    private void saveRequestToRealtimeDatabase(String email, String firstName, String mobileNumber, String help, String description, @Nullable String imageUrl) {
        SupportRequest request = new SupportRequest(email, firstName, mobileNumber, help, description, imageUrl);

        db.child(mobileNumber)
                .setValue(request)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(SupportActivity.this, "Request submitted successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(SupportActivity.this, "Failed to submit request.", Toast.LENGTH_SHORT).show());
    }

    // Validate email format
    private boolean isValidEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Validate name format (first letter capitalized)
    private boolean isValidName(String name) {
        return !name.isEmpty() && Character.isUpperCase(name.charAt(0));
    }

    // Validate phone number format (10 digits)
    private boolean isValidPhoneNumber(String phoneNumber) {
        return !phoneNumber.isEmpty() && phoneNumber.length() == 10 && phoneNumber.matches("\\d{10}");
    }

    // Define a SupportRequest class to hold the data
    public static class SupportRequest {
        public String email;
        public String firstName;
        public String mobileNumber;
        public String help;
        public String description;
        public String imageUrl;

        public SupportRequest() {
            // Default constructor required for calls to DataSnapshot.getValue(SupportRequest.class)
        }

        public SupportRequest(String email, String firstName, String mobileNumber, String help, String description, String imageUrl) {
            this.email = email;
            this.firstName = firstName;
            this.mobileNumber = mobileNumber;
            this.help = help;
            this.description = description;
            this.imageUrl = imageUrl;
        }
    }
}
