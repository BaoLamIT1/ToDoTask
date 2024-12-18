package com.example.todotask.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.todotask.CreateDatabase;
import com.example.todotask.Fragment.AddFragment;
import com.example.todotask.Fragment.CalendarFragment;
import com.example.todotask.Fragment.MenuFragment;
import com.example.todotask.Fragment.TagFragment;
import com.example.todotask.R;
import com.example.todotask.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CalendarFragment calendarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            // Chỉ thay fragment nếu không có trạng thái trước đó
            replaceFragment(new CalendarFragment());
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.bottom_navigation_calendar) {
                replaceFragment(new CalendarFragment());
            } else if (id == R.id.bottom_navigation_menu) {
                replaceFragment(new MenuFragment());
            } else if (id == R.id.bottom_navigation_tag) {
                replaceFragment(new TagFragment());
            } else if (id == R.id.bottom_navigation_add) {
                replaceFragment(new AddFragment());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}