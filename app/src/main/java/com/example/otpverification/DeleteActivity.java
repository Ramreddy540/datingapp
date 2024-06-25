package com.example.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class DeleteActivity extends AppCompatActivity {

    private Button deleteButton;
    private RadioGroup radioGroup;
    private TextInputEditText editTextFeedback;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        db = FirebaseFirestore.getInstance();

        deleteButton = findViewById(R.id.buttonDeletePermanently);
        radioGroup = findViewById(R.id.radioGroup);
        editTextFeedback = findViewById(R.id.editTextFeedback);

        deleteButton.setEnabled(false);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            deleteButton.setEnabled(checkedId != -1);
        });

        deleteButton.setOnClickListener(v -> {
            if (isRadioButtonSelected()) {
                deleteUser();
            }
        });
    }

    private boolean isRadioButtonSelected() {
        return radioGroup.getCheckedRadioButtonId() != -1;
    }

    private String getSelectedReason() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        return selectedRadioButton.getText().toString();
    }

    private void deleteUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userPhoneNumber = user.getPhoneNumber();
            if (userPhoneNumber != null) {
                logDeletedUser(userPhoneNumber, getSelectedReason(), getFeedback());
            }
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("DeleteActivity", "User account deleted.");
                            Toast.makeText(DeleteActivity.this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();
                            navigateToOTPSendActivity();
                        } else {
                            Log.e("DeleteActivity", "Failed to delete user account.", task.getException());
                            Toast.makeText(DeleteActivity.this, "Failed to delete account.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(DeleteActivity.this, "No user is signed in.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFeedback() {
        return editTextFeedback.getText().toString();
    }

    private void logDeletedUser(String phoneNumber, String reason, String feedback) {
        Map<String, Object> deletedUser = new HashMap<>();
        deletedUser.put("phoneNumber", phoneNumber);
        deletedUser.put("reason", reason);
        deletedUser.put("feedback", feedback);
        deletedUser.put("timestamp", System.currentTimeMillis());

        db.collection("deletedaccounts")
                .document(phoneNumber)
                .set(deletedUser)
                .addOnSuccessListener(aVoid -> Log.d("DeleteActivity", "Deleted user logged with phone number: " + phoneNumber))
                .addOnFailureListener(e -> Log.e("DeleteActivity", "Error logging deleted user", e));
    }

    private void navigateToOTPSendActivity() {
        Intent intent = new Intent(DeleteActivity.this, OTPSendActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
