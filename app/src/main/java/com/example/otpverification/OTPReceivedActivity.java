package com.example.otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class OTPReceivedActivity extends AppCompatActivity {

    private EditText otpEditText1,otpEditText2,otpEditText3,otpEditText4,otpEditText5,otpEditText6;

    private String verificatonID;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpreceived);

        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        TextView textMobile=findViewById(R.id.textmoblie);
        textMobile.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));
        otpEditText1=findViewById(R.id.inputcode1);
        otpEditText2=findViewById(R.id.inputcode2);
        otpEditText3=findViewById(R.id.inputcode3);
        otpEditText4=findViewById(R.id.inputcode4);
        otpEditText5=findViewById(R.id.inputcode5);
        otpEditText6=findViewById(R.id.inputcode6);

        setupOtpEditTexts();
        final ProgressBar progressBar=findViewById(R.id.progessBarr);
        final Button buttonVerify=findViewById(R.id.buttonverify);

        verificatonID=getIntent().getStringExtra("verficationId");

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otpEditText1.getText().toString().trim().isEmpty()
                        || otpEditText2.getText().toString().trim().isEmpty()
                        || otpEditText3.getText().toString().trim().isEmpty()
                        || otpEditText4.getText().toString().trim().isEmpty()
                        || otpEditText5.getText().toString().trim().isEmpty()
                        || otpEditText6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OTPReceivedActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code=
                        otpEditText1.getText().toString()+
                                otpEditText2.getText().toString()+
                                otpEditText3.getText().toString()+
                                otpEditText4.getText().toString()+
                                otpEditText5.getText().toString()+
                                otpEditText6.getText().toString();

           if (verificatonID != null) {
           progressBar.setVisibility(View.VISIBLE);
           buttonVerify.setVisibility(View.INVISIBLE);

           PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider
                                                    .getCredential(verificatonID,code);


               FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                              progressBar.setVisibility(View.GONE);
                              buttonVerify.setVisibility(View.VISIBLE);
                              if (task.isSuccessful()){

                              checkUserInFirestore();

                              }
                              else {
                                  Toast.makeText(OTPReceivedActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                              }
                           }
                       });

                }
            }
        });
    }

    private void checkUserInFirestore() {
        String userId = String.format("%s",mAuth.getCurrentUser().getPhoneNumber().substring("+91".length()));
        Log.d("TAG", "Phone:"+userId);

        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {


                            String name = document.getString("firstName");
                            String dob = document.getString("dob");

                            if (name != null && !name.isEmpty() && dob != null && !dob.isEmpty()) {
                                // User exists and has required data, navigate to dashboard
                                Intent intent = new Intent(OTPReceivedActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // User exists but does not have required data, navigate to registration
                                Intent intent = new Intent(OTPReceivedActivity.this, MainActivity2.class);
                                intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            // User does not exist, create a new user entry

                            Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                            intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(OTPReceivedActivity.this, "Error checking user", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupOtpEditTexts() {
        otpEditText1.addTextChangedListener(new OtpTextWatcher(otpEditText1, otpEditText2));
        otpEditText2.addTextChangedListener(new OtpTextWatcher(otpEditText2, otpEditText3));
        otpEditText3.addTextChangedListener(new OtpTextWatcher(otpEditText3, otpEditText4));
        otpEditText4.addTextChangedListener(new OtpTextWatcher(otpEditText4, otpEditText5));
        otpEditText5.addTextChangedListener(new OtpTextWatcher(otpEditText5, otpEditText6));
        otpEditText6.addTextChangedListener(new OtpTextWatcher(otpEditText6, null));
    }

    private class OtpTextWatcher implements TextWatcher {
        private View currentView;
        private View nextView;

        public OtpTextWatcher(View currentView, View nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1) {
                if (nextView != null) {
                    nextView.requestFocus();
                }
            } else if (s.length() == 0) {
                if (currentView != null) {
                    currentView.requestFocus();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

}