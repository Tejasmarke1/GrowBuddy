package com.example.growbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUsActivity extends AppCompatActivity {
    Button send;
    EditText et_contact_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        send = findViewById(R.id.send);
        et_contact_message =findViewById(R.id.question);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view) {
                String subject= "Asking Query";
                String message=et_contact_message.getText().toString().trim();
                String email="ladhemohit8888@gmail.com";
                if (message.isEmpty()) {
                    Toast.makeText(ContactUsActivity.this, "Please add some Message", Toast.LENGTH_SHORT).show();
                } else{
                    String mail ="mailto:"+email+
                            "?&subject="+ Uri.encode(subject)+
                            "&body="+Uri.encode(message);
                    Intent i =new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse(mail));
                    try {
                        startActivity(Intent.createChooser(i,"Send Email..."));
                    }
                    catch (Exception e){
                        Toast.makeText(ContactUsActivity.this, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(ContactUsActivity.this, "Please add some Message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}