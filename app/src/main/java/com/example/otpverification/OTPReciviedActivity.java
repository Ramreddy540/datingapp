package com.example.otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.concurrent.TimeUnit;

public class OTPReciviedActivity extends AppCompatActivity {

    private EditText intputcode1,intputcode2,intputcode3,intputcode4,intputcode5,intputcode6;

    private String verificatonID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otprecivied);
       TextView textMobile=findViewById(R.id.textmoblie);
        textMobile.setText(String.format("+91-%s",getIntent().getStringExtra("moblie")));
        intputcode1=findViewById(R.id.inputcode1);
        intputcode2=findViewById(R.id.inputcode2);
        intputcode3=findViewById(R.id.inputcode3);
        intputcode4=findViewById(R.id.inputcode4);
        intputcode5=findViewById(R.id.inputcode5);
        intputcode6=findViewById(R.id.inputcode6);

        setupOTPInputs();
        final ProgressBar progressBar=findViewById(R.id.progessBarr);
        final Button buttonVerify=findViewById(R.id.buttonverify);

        verificatonID=getIntent().getStringExtra("verficationId");

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (intputcode1.getText().toString().trim().isEmpty()
                        || intputcode2.getText().toString().trim().isEmpty()
                        || intputcode3.getText().toString().trim().isEmpty()
                        || intputcode4.getText().toString().trim().isEmpty()
                        || intputcode5.getText().toString().trim().isEmpty()
                        || intputcode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OTPReciviedActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code=
                        intputcode1.getText().toString()+
                        intputcode2.getText().toString()+
                        intputcode3.getText().toString()+
                        intputcode4.getText().toString()+
                        intputcode5.getText().toString()+
                        intputcode6.getText().toString();

           if (verificatonID != null) {
           progressBar.setVisibility(View.VISIBLE);
           buttonVerify.setVisibility(View.INVISIBLE);
               PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(verificatonID,code);
               FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                              progressBar.setVisibility(View.GONE);
                              buttonVerify.setVisibility(View.VISIBLE);
                              if (task.isSuccessful()){
                                  Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                  startActivity(intent);
                              }else {
                                  Toast.makeText(OTPReciviedActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                              }
                           }
                       });

                }
            }
        });


    findViewById(R.id.textResendOTP).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" +getIntent().getStringExtra("moblie"),
                    60,
                    TimeUnit.SECONDS,
                    OTPReciviedActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }
                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(OTPReciviedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                          verificatonID =newVerificationId;
                            Toast.makeText(OTPReciviedActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

        }
    });
    }
    private void setupOTPInputs(){
        intputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             if (s.toString().trim().isEmpty()){
              intputcode2.requestFocus();
             }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        intputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             if (s.toString().trim().isEmpty()){
              intputcode3.requestFocus();
             }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        intputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()){
                    intputcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        intputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()){
                    intputcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        intputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()){
                    intputcode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}