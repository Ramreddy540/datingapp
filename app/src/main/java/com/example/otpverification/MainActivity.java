package com.example.otpverification;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private static final int NAV_STAR = R.id.navStar;
    private static final int NAV_LIKE = R.id.navlike;
    private static final int NAV_MESSAGE = R.id.navmessage;
    private static final int NAV_PROFILE = R.id.navprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavView);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            int itemId = item.getItemId();

            if (itemId == NAV_STAR) {
                selectedFragment = new StarFragment();
            } else if (itemId == NAV_LIKE) {
                selectedFragment = new HeartFragment();
            } else if (itemId == NAV_MESSAGE) {
                selectedFragment = new MessageFragment();
            } else if (itemId == NAV_PROFILE) {
                selectedFragment = new ProfileFragment();
            }


            if (selectedFragment != null) {
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.framelayout, selectedFragment);
                fragmentTransaction1.commit();
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
