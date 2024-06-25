package com.example.otpverification;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

public class HelpCenter extends AppCompatActivity {

    BottomSheetDialog dialog;
    ImageButton backArrow;

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
        backArrow = findViewById(R.id.idArrow);

        dialog = new BottomSheetDialog(this);
        //inflate view
        onCreateDialog();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                inflatedView.findViewById(R.id.aro1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
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
                dialog.show();
            }
        });

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void onCreateDialog() {

        View view = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.bottomsheet_dialog, null,false);

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(getApplicationContext(),OTPSendActivity.class);
                startActivity(i);
                Toast.makeText(HelpCenter.this, "Logout Succesfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
        });

        dialog.setContentView(view);
    }

}
