package com.example.otpverification;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.otpverification.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.framelayout);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (itemId) {

                    case R.id.navStar:
                        fragmentTransaction.replace(R.id.framelayout, new StarFragment());
                        break;
                    case R.id.navlike:
                        fragmentTransaction.replace(R.id.framelayout, new HeartFragment());
                        break;
                    case R.id.navmessage:
                        fragmentTransaction.replace(R.id.framelayout, new MessageFragment());
                        break;
                    case R.id.navprofile:
                        fragmentTransaction.replace(R.id.framelayout, new ProfileFragment());
                        break;

                }

                fragmentTransaction.commit();
                return true;
            }
        });
    }
}
