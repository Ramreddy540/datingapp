package com.example.otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class OTPSendActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText inputMoblie;
    private String my_VerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpsend);

        inputMoblie = findViewById(R.id.inputMobile);
        Button buttonGetOTP=findViewById(R.id.buttonGetOTP);
        final ProgressBar progressBar=findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressBar.setVisibility(View.GONE);
                buttonGetOTP.setVisibility(View.VISIBLE);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressBar.setVisibility(View.GONE);
                buttonGetOTP.setVisibility(View.VISIBLE);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OTPSendActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(OTPSendActivity.this, "SMS quota exceeded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OTPSendActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken){

                my_VerificationId = verificationId;
                mResendToken = forceResendingToken;

                progressBar.setVisibility(View.GONE);
                buttonGetOTP.setVisibility(View.VISIBLE);
                Intent intent=new Intent(OTPSendActivity.this, OTPReceivedActivity.class);
                intent.putExtra("mobile",inputMoblie.getText().toString());
                intent.putExtra("verficationId",verificationId);
                startActivity(intent);
            }

        };

        buttonGetOTP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(inputMoblie.getText().toString().trim().isEmpty()) {
                   Toast.makeText(OTPSendActivity.this, "Enter Mobile Number",
                           Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                buttonGetOTP.setVisibility(View.INVISIBLE);

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+inputMoblie.getText().toString().trim())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(OTPSendActivity.this)
                        .setCallbacks(mCallbacks)
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);

            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            Intent intent=new Intent(OTPSendActivity.this, OTPReceivedActivity.class);
                            intent.putExtra("mobile",inputMoblie.getText().toString());
                            intent.putExtra("verficationId",my_VerificationId);
                            startActivity(intent);

                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}