package com.example.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class S2 extends AppCompatActivity {

    private Spinner spinner, spinner1, spinner2, spinner3, spinner4, spinner5, spinner6;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);

        db = FirebaseFirestore.getInstance();

        initializeSpinners();
        setupSpinnerAdapters();

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            if (areAllSpinnersSelected()) {
                String height = spinner.getSelectedItem().toString();
                String religion = spinner1.getSelectedItem().toString();
                String community = spinner2.getSelectedItem().toString();
                String smoke = spinner3.getSelectedItem().toString();
                String status = spinner4.getSelectedItem().toString();
                String drinking = spinner5.getSelectedItem().toString();
                String language = spinner6.getSelectedItem().toString();

                saveToFirestore(height, religion, community, smoke, status, drinking, language);
            } else {
                Toast.makeText(S2.this, "Please select all fields", Toast.LENGTH_SHORT).show();
            }
        });

        Button skipButton = findViewById(R.id.skip_button);
        skipButton.setOnClickListener(v -> moveToNextActivity());
    }

    private void initializeSpinners() {
        spinner = findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);
    }

    private void setupSpinnerAdapters() {
        String[] height = {"- -", "4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.7", "4.8", "4.9", "4.10", "4.11", "5.0", "5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10", "5.11", "6.0", "6.1", "6.2", "6.3", "6.4", "6.5"};
        String[] Religion = {"- -", "Hindu", "Muslim", "Christian", "Spiritual", "Atheist", "Buddhist", "Jain", "Shinto", "Islam", "Sikh", "Judaism", "Others"};
        String[] Community = {"- -", "Brahmin", "Thakur", "Rajput", "Kappu Naidu", "Kamma", "Vaishya", "Reddy", "Chowdary", "Komati", "Nair", "Others"};
        String[] Smoke = {"- -", "Yes", "No", "Planning to Quit"};
        String[] Status = {"- -", "Single", "Married", "Unmarried", "Committed", "Divorced", "Divorced with kids", "Widowed", "Others"};
        String[] Drinking = {"- -", "Regular", "Socially", "Planning to Quit", "Occasionally", "Teetotaler"};
        String[] Language = {"- -", "English", "Telugu", "Hindi", "Tamil", "Kannada", "Malayalam", "Urdu", "Spanish"};

        setupSpinnerAdapter(spinner, height);
        setupSpinnerAdapter(spinner1, Religion);
        setupSpinnerAdapter(spinner2, Community);
        setupSpinnerAdapter(spinner3, Smoke);
        setupSpinnerAdapter(spinner4, Status);
        setupSpinnerAdapter(spinner5, Drinking);
        setupSpinnerAdapter(spinner6, Language);
    }

    private void setupSpinnerAdapter(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private boolean areAllSpinnersSelected() {
        return !spinner.getSelectedItem().toString().equals("- -")
                && !spinner1.getSelectedItem().toString().equals("- -")
                && !spinner2.getSelectedItem().toString().equals("- -")
                && !spinner3.getSelectedItem().toString().equals("- -")
                && !spinner4.getSelectedItem().toString().equals("- -")
                && !spinner5.getSelectedItem().toString().equals("- -")
                && !spinner6.getSelectedItem().toString().equals("- -");
    }

    private void saveToFirestore(String height, String religion, String community, String smoke, String status, String drinking, String language) {
        // Get the current user's UID

        // Create a map with user details
        Map<String, Object> data = new HashMap<>();
        data.put("Height", height);
        data.put("Religion", religion);
        data.put("Community", community);
        data.put("Smoke", smoke);
        data.put("Status", status);
        data.put("Drinking", drinking);
        data.put("Language", language);

        String number = getIntent().getStringExtra("mobile");

        db.collection("users").document(number)
                .set(data, SetOptions.merge()) // Set data for the document
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            moveToNextActivity(); // Move to the next activity after data is saved
                        } else {
                            Toast.makeText(S2.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                            Log.e("Firestore", "Error saving data to Firestore", task.getException());
                        }
                    }
                });
    }

    // Method to move to the next activity
    private void moveToNextActivity() {
        Intent intent = new Intent(S2.this, MainActivity.class); // Change this to the correct activity you want to navigate to
        startActivity(intent);
    }
}
