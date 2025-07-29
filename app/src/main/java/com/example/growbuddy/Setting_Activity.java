package com.example.growbuddy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Setting_Activity extends AppCompatActivity {
    ScrollView scrollView;
    CardView cardAbout,cardSecurity,cardContact,cardlogout,cardLanguage;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        scrollView = findViewById(R.id.scrollView);
        scrollView.setVerticalScrollBarEnabled(false);

        cardContact = findViewById(R.id.card_contact);
        cardSecurity = findViewById(R.id.card_security);
        cardAbout = findViewById(R.id.card_about_us);
        cardlogout = findViewById(R.id.cd_4_1);
        cardLanguage=findViewById(R.id.card_language);

        cardContact.setOnClickListener(view -> {
            Intent intent = new Intent(Setting_Activity.this, ContactUsActivity.class);
            startActivity(intent);
        });

        cardAbout.setOnClickListener(view -> {
            Intent intent = new Intent(Setting_Activity.this, About_Us_Activity.class);
            startActivity(intent);
        });

        cardSecurity.setOnClickListener(v -> {
            Intent intent = new Intent(Setting_Activity.this, Security_Privacy_Activity.class);
            startActivity(intent);
        });

        cardlogout.setOnClickListener(view -> {
            AlertDialog.Builder ad = new AlertDialog.Builder(Setting_Activity.this);
            ad.setTitle("Logout");
            ad.setMessage("Are you sure you want to Logout ?");
            ad.setPositiveButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
            ad.setNegativeButton("Logout", (dialogInterface, i) -> {
                Intent intent = new Intent(Setting_Activity.this, LoginActivity.class);
                editor.putBoolean("islogin", false).commit();
                startActivity(intent);
            }).create().show();
        });

        cardLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6=new Intent(getApplicationContext(),LanguageChange.class);
                startActivity(i6);
            }
        });


    }
}
