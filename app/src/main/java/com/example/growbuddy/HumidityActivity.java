package com.example.growbuddy;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import java.text.DecimalFormat;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class HumidityActivity extends AppCompatActivity {
    ArcGauge humidityGauge;
    ImageView back;

    TextView humCurrent,textMain;
    Float hum = 20.0f;//Todo: Humidity Value from Firebase



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        humidityGauge = findViewById(R.id.humidityGauge);
        back = findViewById(R.id.back_arrow);

        humCurrent = findViewById(R.id.hum_data);
        textMain = findViewById(R.id.tH);



        humidity();
        fetchDataFromFirebase();

        back.setOnClickListener(view -> {
            Intent intent = new Intent(HumidityActivity.this, MainHome.class);
            startActivity(intent);
        });

    }

    private void fetchDataFromFirebase() {

        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mData.getReference("Demo");
        mRef = mData.getReference("Reading/Humidity");
//        mRef=mData.getReference().child("Temperature");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    hum = Float.parseFloat(snapshot.getValue().toString());
                    humCurrent.setText(valueOf(hum));
                    textMain.setText(valueOf(hum));
                    updateGauge();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });
    }

    private void humidity() {
        // TODO: Sets the range of the ArcGauge for Humidity from 20% to 90% RH with different Colors.
        Range hrange = new Range();
        hrange.setColor(Color.parseColor("#0000FF"));
        hrange.setFrom(20.0);
        hrange.setTo(43.33);

        Range hrange2 = new Range();
        hrange2.setColor(Color.parseColor("#E3E500"));
        hrange2.setFrom(43.33);
        hrange2.setTo(66.66);

        Range hrange3 = new Range();
        hrange3.setColor(Color.parseColor("#ce0000"));
        hrange3.setFrom(66.66);
        hrange3.setTo(90.0);

// Add color ranges to gauge
        humidityGauge.addRange(hrange);
        humidityGauge.addRange(hrange2);
        humidityGauge.addRange(hrange3);

// Set min, max, and current value
        humidityGauge.setMinValue(20.0);
        humidityGauge.setMaxValue(90.0);
        humidityGauge.setValue(hum);
//TODO:_____________________________________________________________________________________________
    }



    private void updateGauge() {
        double newValue = 0.0;
        String previous = valueOf(humidityGauge.getValue());
        Double previousValue = Double.parseDouble(previous);
        newValue = hum;

        if (newValue > previousValue) {
            double difference = newValue - previousValue;
            int duration = 3000; // Duration for the update in milliseconds
            int steps = 20; // Number of steps for the update

            double stepValue = difference / steps;
            int delayBetweenSteps = duration / steps;

            Timer timer = new Timer();
            double finalNewValue = newValue;
            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            timer.scheduleAtFixedRate(new TimerTask() {
                double currentValue = previousValue;

                @Override
                public void run() {
                    currentValue += stepValue;
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            humidityGauge.setValue(Double.parseDouble(decimalFormat.format(currentValue)));
                            textMain.setText(valueOf(currentValue));
                            humCurrent.setText(valueOf(currentValue));
                        }
                    });

                    // Stop the timer when reaching the new value
                    if (currentValue >= finalNewValue) {
                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                humidityGauge.setValue(Double.parseDouble(decimalFormat.format(finalNewValue)));
                                textMain.setText(valueOf(finalNewValue));
                                humCurrent.setText(valueOf(finalNewValue));
                                timer.cancel();
                            }
                        });
                    }
                }
            }, 0, delayBetweenSteps);
        } else if (newValue < previousValue) {
            double difference = previousValue - newValue; // Calculating the difference to decrease

            int duration = 3000; // Duration for the update in milliseconds
            int steps = 20; // Number of steps for the update

            double stepValue = difference / steps;
            int delayBetweenSteps = duration / steps;

            Timer timer = new Timer();
            double finalNewValue1 = newValue;
            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            timer.scheduleAtFixedRate(new TimerTask() {
                double currentValue = previousValue;

                @Override
                public void run() {
                    currentValue -= stepValue; // Decrease the value
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            humidityGauge.setValue(Double.parseDouble(decimalFormat.format(currentValue)));
                            textMain.setText(valueOf(currentValue));
                            humCurrent.setText(valueOf(currentValue));
                        }
                    });

                    // Stop the timer when reaching the new value
                    if (currentValue <= finalNewValue1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                humidityGauge.setValue(Double.parseDouble(decimalFormat.format(finalNewValue1)));
                                textMain.setText(valueOf(finalNewValue1));
                                humCurrent.setText(valueOf(finalNewValue1));
                                timer.cancel();
                            }
                        });
                    }
                }
            }, 0, delayBetweenSteps);
        }
    }

}