package com.example.growbuddy;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ResponseGenerator extends AppCompatActivity {
    TextView tv;
    String value;
    ProgressBar bar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_response_generator);
        tv=findViewById(R.id.textView);
        bar=findViewById(R.id.sendPromptProgressBar);

        Intent intent = getIntent();
        if (intent != null) {
            value = intent.getStringExtra("msg"); // Assign value to the class-level variable
        }

        GeminiPro model=new GeminiPro();
        String query="give info about the "+value+" and solution also for that";
        bar.setVisibility(View.VISIBLE);
        tv.setText("");
        model.getResponse(query, new ResponseCallback(){
            @Override
            public void onResponse(String response) {
                tv.setText(response);
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(ResponseGenerator.this, "Error " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.GONE);
            }
        });










    }
}