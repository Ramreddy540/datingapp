package com.example.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class LoveAnime extends AppCompatActivity {

    TextView love;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_anime);
        love = findViewById(R.id.love);
        lottie = findViewById(R.id.lottie);
        love.animate().translationY(-1400).setDuration(2700).setStartDelay(0);
        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LoveAnime.this, AS.class); // Replace NextActivity with the desired activity
                startActivity(i);
                finish(); // Optional, depending on whether you want to keep this activity in the back stack
            }
        }, 3000);
    }
}