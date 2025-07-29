package com.example.growbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


import com.example.growbuddy.databinding.ActivityMainHomeBinding;

public class MainHome extends AppCompatActivity {

    ActivityMainHomeBinding binding;

    private Boolean doubletap = false;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.floatBtn.setOnClickListener(view -> {

//            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
//            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            Intent intent=new Intent(this, AI_Photo.class);
            startActivity(intent);


        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.alerts) {
                replaceFragment(new AlertsFragment());

            } else if (itemId == R.id.add_plants) {
                replaceFragment(new AddPlantsFragment());
            } else if (itemId == R.id.settings) {
                replaceFragment(new CustomerFragment());
            }


            return true;

        });



    }

    public void onBackPressed() {
        if (doubletap) {
            super.onBackPressed();
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);

        }

        else
        {
            Toast.makeText(getApplicationContext(), "Press again to exit the app", Toast.LENGTH_SHORT).show();
            doubletap = true;
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubletap = false;
                }
            }, 2000);
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}