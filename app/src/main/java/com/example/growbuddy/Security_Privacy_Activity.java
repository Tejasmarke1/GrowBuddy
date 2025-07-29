package com.example.growbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.growbuddy.databinding.ActivitySecurityPrivacyBinding;

public class Security_Privacy_Activity extends AppCompatActivity {

    ActivitySecurityPrivacyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySecurityPrivacyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.help1.setOnClickListener(v ->
                Toast.makeText(this, "Wait.. Help is Coming", Toast.LENGTH_SHORT).show());


    }
}