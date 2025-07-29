package com.example.growbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class LanguageChange extends AppCompatActivity {

    Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_change);
        btn=findViewById(R.id.btn_change);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i1);
            }
        });


    }

    public void changeLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // Update the text of textViewHello based on the new locale
        // Restart the activity to apply the new language
        recreate();
    }

    public void changeToMarathi(View view) {
        changeLanguage("mr");
        Toast.makeText(this, "Language changed to Marathi", Toast.LENGTH_SHORT).show();

        String languageCodeMR = "mr"; // Example: Change language to Marathi
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("languageCode", languageCodeMR);
        editor.apply();



        // startActivity(intent);
    }

    public void changeToHindi(View view) {
        changeLanguage("hi");
        Toast.makeText(this, "Language changed to Hindi", Toast.LENGTH_SHORT).show();

        String languageCode = "hi";
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("languageCode", languageCode);
        editor.apply();

    }


    public void changeToEnglish(View view) {
        changeLanguage("en");
        Toast.makeText(this, "Language changed to English", Toast.LENGTH_SHORT).show();

        String languageCode = "en";
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("languageCode", languageCode);
        editor.apply();
    }


}