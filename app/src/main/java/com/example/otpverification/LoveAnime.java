package com.example.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoveAnime extends AppCompatActivity {

    TextView love;
    LottieAnimationView lottie;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_anime);
        love = findViewById(R.id.love);
        lottie = findViewById(R.id.lottie);
        love.animate().translationY(-1600).setDuration(1000).setStartDelay(0);
        lottie.animate().translationX(0).setDuration(2000).setStartDelay(0);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user= auth.getCurrentUser();

        db= FirebaseFirestore.getInstance();

        String number = user.getPhoneNumber().replace("+91", "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                db.collection("users").document(number).get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {


                                    String name = document.getString("firstName");
                                    String dob = document.getString("dob");

                                    if (name != null && !name.isEmpty() && dob != null && !dob.isEmpty()) {
                                        // User exists and has required data, navigate to dashboard
                                        Intent intent = new Intent(LoveAnime.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // User exists but does not have required data, navigate to registration
                                        Intent intent = new Intent(LoveAnime.this, MainActivity2.class);
                                        intent.putExtra("mobile", getIntent().getStringExtra("mobile"));
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    // User does not exist, create a new user entry

                                    Intent intent = new Intent(getApplicationContext(), AS.class);
                                    intent.putExtra("mobile", getIntent().getStringExtra("mobile"));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(LoveAnime.this, "Error checking user", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }, 3000);

    }
}