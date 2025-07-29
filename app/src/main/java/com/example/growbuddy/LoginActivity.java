package com.example.growbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.growbuddy.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        checkBox = findViewById(R.id.checkbox);

        preferences =
                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preferences.edit();
        if (preferences.getBoolean("islogin",false )) {
            Intent i = new Intent(LoginActivity.this,MainHome.class);
            startActivity(i);
            finish();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User/Username");

        binding.button.setOnClickListener(view -> {
            String user=binding.etLoginUsername.getText().toString();
            String pass=binding.etLoginPassword.getText().toString();
            if (user.isEmpty()) {
                binding.etLoginUsername.setError("Username is Empty");
            } else if (!user.equals("admin")) {
                binding.etLoginUsername.setError("Enter Valid Username");
            } else if (pass.isEmpty()) {
                binding.etLoginPassword.setError("Password is Empty");
            } else if (!pass.equals("admin")) {
                binding.etLoginPassword.setError("Enter Valid Password");
            } else {
                Toast.makeText(LoginActivity.this, "Login is Successfully done", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, MainHome.class);
                i.putExtra("username" ,binding.etLoginUsername.getText().toString());
                i.putExtra("password" , binding.etLoginPassword.getText().toString());

                editor.putBoolean("islogin",true).commit();
                startActivity(i);
                finish();
            }
        });

    }

    }


