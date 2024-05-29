package com.example.otpverification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AS extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as);

        Button getStartedButton = findViewById(R.id.getStartedButton);

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call method to handle button click
                onGetStartedButtonClick();
            }
        });
    }

    // Method to handle button click
    private void onGetStartedButtonClick() {
        // Show a toast message
        Toast.makeText(this, "Get Started button clicked!", Toast.LENGTH_SHORT).show();

        // Create an Intent to start the new activity
        Intent intent = new Intent(AS.this, OTPSendActivity.class);
        startActivity(intent);
    }
}
