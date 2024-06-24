package com.example.otpverification;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.otpverification.Fragments.User_Chat_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavView);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment selectedFragment = null;

            switch (itemId) {
                case R.id.navStar:
                    selectedFragment = new StarFragment();
                    break;
                case R.id.navlike:
                    selectedFragment = new HeartFragment();
                    break;
                case R.id.navmessage:
                    selectedFragment = new MessageFragment();
                    break;
                case R.id.navprofile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            if (selectedFragment != null) {
                fragmentTransaction.replace(R.id.framelayout, selectedFragment);
                fragmentTransaction.commit();
            }

            return true;
        });


            // Load default fragment
        if (savedInstanceState == null) {
            Fragment defaultFragment = new StarFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, defaultFragment)
                    .commit();
        }
    }
}
