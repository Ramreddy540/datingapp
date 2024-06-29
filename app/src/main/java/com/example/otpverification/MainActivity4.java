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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity4 extends AppCompatActivity {

    private Spinner spinner, spinner1, spinner2, spinner3, spinner4, spinner5, spinner6;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

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
                Toast.makeText(MainActivity4.this, "Please select all fields", Toast.LENGTH_SHORT).show();
            }
        });

        Button skipButton = findViewById(R.id.skip_button);
        skipButton.setOnClickListener(v -> moveToProfileFragment());
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
        // Create a map with user details
        Map<String, Object> data = new HashMap<>();
        data.put("Height", height);
        data.put("Religion", religion);
        data.put("Community", community);
        data.put("Smoke", smoke);
        data.put("Status", status);
        data.put("Drinking", drinking);
        data.put("Language", language);

        // Assuming you have the user's mobile number passed via Intent
        String number = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        // Save data to Firestore
        db.collection("users").document(number.replace("+91",""))
                .set(data, SetOptions.merge()) // Set data for the document
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            moveToProfileFragment(); // Move to the ProfileFragment after data is saved
                        } else {
                            Toast.makeText(MainActivity4.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                            Log.e("Firestore", "Error saving data to Firestore", task.getException());
                        }
                    }
                });
    }

    private void moveToProfileFragment() {
        // Replace MainActivity4 with the correct activity if necessary
        Fragment fragment = new ProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
