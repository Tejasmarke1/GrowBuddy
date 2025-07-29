package com.example.growbuddy;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TemperatureActivity extends AppCompatActivity {
    ArcGauge tempGauge;
    ImageView back;


    TextView tempCurrent,tempMainText;
    float temp = 0.0F;//Todo: Temperature Value from Firebase

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        tempCurrent=findViewById(R.id.temp_data);
        tempGauge = findViewById(R.id.tempGauge);
        back = findViewById(R.id.back_arrow);
        tempMainText=findViewById(R.id.tT);

        temperature();

        fetchDataFromFirebase();



        back.setOnClickListener(view -> {
            Intent intent = new Intent(TemperatureActivity.this, MainHome.class);
            startActivity(intent);
        });

    }

    private void fetchDataFromFirebase() {

        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mData.getReference("Demo");
        mRef = mData.getReference("Reading/Temperature");
//        mRef=mData.getReference().child("Temperature");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    temp = Float.parseFloat(snapshot.getValue().toString());
                    tempCurrent.setText(String.valueOf(temp));
                    tempMainText.setText(String.valueOf(temp));
                    updateGauge();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });
    }

    private void temperature() {
        //TODO: Sets the range of the ArcGauge for Temperature from 0°C to 50°C with different Colors.
        Range trange = new Range();
        trange.setColor(Color.parseColor("#0000FF"));
        trange.setFrom(0.0f);
        trange.setTo(16.6f);

        Range trange2 = new Range();
        trange2.setColor(Color.parseColor("#E3E500"));
        trange2.setFrom(16.6f);
        trange2.setTo(32.33f);

        Range trange3 = new Range();
        trange3.setColor(Color.parseColor("#ce0000"));
        trange3.setFrom(32.3f);
        trange3.setTo(50.0f);

        // Add color ranges to gauge
        tempGauge.addRange(trange);
        tempGauge.addRange(trange2);
        tempGauge.addRange(trange3);

        // Set min, max, and current value
        tempGauge.setMinValue(0.0f);
        tempGauge.setMaxValue(50.0f);
        //tempGauge.setValue(temp);
        updateGauge();
    }




    private void updateGauge() {
        double newValue = 0.0;
        String previous = valueOf(tempGauge.getValue());
        Double previousValue = Double.parseDouble(previous);
        newValue = temp;

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
                            tempGauge.setValue(Double.parseDouble(decimalFormat.format(currentValue)));
                            tempMainText.setText(valueOf(currentValue));
                            tempCurrent.setText(valueOf(currentValue));
                        }
                    });

                    // Stop the timer when reaching the new value
                    if (currentValue >= finalNewValue) {
                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                tempGauge.setValue(Double.parseDouble(decimalFormat.format(finalNewValue)));
                                tempMainText.setText(valueOf(finalNewValue));
                                tempCurrent.setText(valueOf(finalNewValue));
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
//                    tempGauge.setValue(Double.parseDouble(decimalFormat.format(currentValue)));
//                    tv_temp.setText(String.valueOf(currentValue));

                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            tempGauge.setValue(Double.parseDouble(decimalFormat.format(currentValue)));
                            tempMainText.setText(valueOf(currentValue));
                            tempCurrent.setText(valueOf(currentValue));
                        }
                    });

                    // Stop the timer when reaching the new value
                    if (currentValue <= finalNewValue1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tempGauge.setValue(Double.parseDouble(decimalFormat.format(finalNewValue1)));
                                tempMainText.setText(valueOf(finalNewValue1));
                                tempCurrent.setText(valueOf(finalNewValue1));
                                timer.cancel();
                            }
                        });
                    }
                }
            }, 0, delayBetweenSteps);
        }
    }


}