package com.example.growbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    ImageView img_splash;
    TextView tv_splash;
    Animation fadeInAnim;
    Button getStarted;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img_splash =findViewById(R.id.plant);
        tv_splash = findViewById(R.id.tv_main_title);
        getStarted = findViewById(R.id.getStarted);

        fadeInAnim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fadein);
        img_splash.setAnimation(fadeInAnim);
        tv_splash.setAnimation(fadeInAnim);

        startBackgroundService();

        getStarted.setOnClickListener(view -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
    private void startBackgroundService() {
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);
    }
}