package com.example.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
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
        love.animate().translationY(-1600).setDuration(1000).setStartDelay(0);
        lottie.animate().translationX(0).setDuration(2000).setStartDelay(0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LoveAnime.this, AS.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}