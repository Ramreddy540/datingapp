package com.example.otpverification;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HelpCenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        // Find all LinearLayouts
        LinearLayout ll1 = findViewById(R.id.ll1);
        LinearLayout ll2 = findViewById(R.id.ll2);
        LinearLayout ll3 = findViewById(R.id.ll3);
        LinearLayout ll4 = findViewById(R.id.ll4);
        LinearLayout ll5 = findViewById(R.id.ll5);
        LinearLayout ll6 = findViewById(R.id.ll6);
        LinearLayout ll7 = findViewById(R.id.ll7);

        // Set click listeners for each LinearLayout
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to FAQActivity
                Intent intent = new Intent(HelpCenter.this, DeleteActivity.class);
                startActivity(intent);
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SupportActivity
                Intent intent = new Intent(HelpCenter.this, SupportActivity.class);
                startActivity(intent);
            }
        });

        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the desired layout resource
                View inflatedView = LayoutInflater.from(HelpCenter.this).inflate(R.layout.safetytips, null);

                // Add any additional logic here, such as modifying views within the inflated layout

                // Display the inflated layout
                setContentView(inflatedView);
            }
        });

        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the desired layout resource
                View inflatedView = LayoutInflater.from(HelpCenter.this).inflate(R.layout.tandc, null);

                // Add any additional logic here, such as modifying views within the inflated layout

                // Display the inflated layout
                setContentView(inflatedView);
            }
        });

        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the desired layout resource
                View inflatedView = LayoutInflater.from(HelpCenter.this).inflate(R.layout.privacy, null);

                // Add any additional logic here, such as modifying views within the inflated layout

                // Display the inflated layout
                setContentView(inflatedView);
            }
        });


        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DeleteActivity
                Intent intent = new Intent(HelpCenter.this, DeleteActivity.class);
                startActivity(intent);
            }
        });

        ll7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LogoutActivity
                Intent intent = new Intent(HelpCenter.this, LoveAnime.class);
                startActivity(intent);
            }
        });
    }
}
