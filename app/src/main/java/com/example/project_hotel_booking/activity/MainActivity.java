package com.example.project_hotel_booking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.project_hotel_booking.Adapter.ViewPagerAdapter;
import com.example.project_hotel_booking.Fragments.Fragment1;
import com.example.project_hotel_booking.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.example.project_hotel_booking.Fragments.Fragment1;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private boolean isSelectRoomFragmentVisible = true; // Flag to track the visibility of SelectRoomFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPager.setAdapter(new ViewPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Tab " + (position + 1))
        ).attach();
    }

    @Override
    public void onBackPressed() {
        if (!isSelectRoomFragmentVisible) {
            // If the SelectRoomFragment is not visible, make it visible and hide others
            isSelectRoomFragmentVisible = true;
            viewPager.setCurrentItem(0); // Switch to the first tab (Fragment1)
        } else {
            super.onBackPressed();
        }
    }

    // Method to navigate back to Fragment1 (SelectRoomFragment)
    public void navigateToSelectRoomFragment() {
        isSelectRoomFragmentVisible = true;
        viewPager.setCurrentItem(0); // Switch to the first tab (Fragment1)
    }

    // Method to navigate to other fragments (for example, ConfirmationMessageFragment)
    public void navigateToOtherFragment(Fragment fragment) {
        isSelectRoomFragmentVisible = false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFrameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
