package com.example.otpverification;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class S1 extends AppCompatActivity {
    private TextInputEditText editTextFirstName, editTextLastName, editTextEmail, editTextDOB;
    private RadioGroup radioGroup;
    private Button buttonNext;
    private FirebaseFirestore db;
    private Calendar calendar;
    BottomSheetDialog dialog;
    private String Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1);

        db = FirebaseFirestore.getInstance();
        calendar = Calendar.getInstance();

        editTextFirstName = findViewById(R.id.name);
        editTextLastName = findViewById(R.id.lastName);
        editTextEmail = findViewById(R.id.email);
        editTextDOB = findViewById(R.id.dob);
        radioGroup = findViewById(R.id.gender);
        buttonNext = findViewById(R.id.next);

        dialog = new BottomSheetDialog(this);

        onCreateDialog();

        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
            }
        });

    }

    private void onCreateDialog() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.s1bottomsheet_dialog, null,false);

        // Set the background to be transparent to enable the dim effect
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).setBackgroundResource(android.R.color.transparent);
        }

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateInputs()) {

                    saveDataToFirestore();

                    moveToNextActivity();
                }
                dialog.dismiss();
            }
        });


        dialog.setContentView(view);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateEditText();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateEditText() {
        String dateFormat = "dd/MM/yyyy"; // Change as needed
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        editTextDOB.setText(sdf.format(calendar.getTime()));
    }

    private void saveDataToFirestore() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String dob = editTextDOB.getText().toString().trim();
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String gender = radioButton.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("email", email);
        user.put("dob", dob);
        user.put("gender", gender);

        String numb = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Number = numb.replace("+91", "");

        // Add data to Firestore
        db.collection("users").document(Number)
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TAG", "onSuccess: Step 2 successfull");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: "+e.getLocalizedMessage());
                    }
                });

    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (TextUtils.isEmpty(editTextFirstName.getText().toString().trim())) {
            editTextFirstName.setError("First name is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(editTextLastName.getText().toString().trim())) {
            editTextLastName.setError("Last name is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(editTextEmail.getText().toString().trim()) || !isValidEmail(editTextEmail.getText().toString().trim())) {
            editTextEmail.setError("Valid email is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(editTextDOB.getText().toString().trim())) {
            editTextDOB.setError("Date of birth is required");
            isValid = false;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Gender selection is required", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void moveToNextActivity() {
        Intent intent = new Intent(S1.this, S2.class);
        intent.putExtra("mobile",Number);
        startActivity(intent);
    }


}
