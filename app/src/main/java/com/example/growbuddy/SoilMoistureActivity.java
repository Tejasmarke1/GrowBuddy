package com.example.growbuddy;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import  java.text.DecimalFormat;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class SoilMoistureActivity extends AppCompatActivity {
    ArcGauge moistGauge,moistGauge2;
    ImageView back;
    TextView currentText,moistText,currentText2,moistText2;
    Float moist ;
    Float moist2 = 200.0f;//Todo: Soil Moisture from Firebase
//    Boolean doubletap = false;
    Double moistPercentage,moistPercentage2;
    Integer motor;
    DatabaseReference topLevelNodeRef;
    SwitchCompat switchCompat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_moisture);
        back = findViewById(R.id.back_arrow);
        // soil mositure 2
        moistGauge = findViewById(R.id.moistGauge);
        currentText=findViewById(R.id.mosit_data);
        moistText=findViewById(R.id.tM);

        // soil mositure 2
        moistGauge2 = findViewById(R.id.moistGauge2);
        currentText2=findViewById(R.id.mosit_data2);
        moistText2=findViewById(R.id.tM2);
        switchCompat=findViewById(R.id.switch_motar);
        topLevelNodeRef = FirebaseDatabase.getInstance().getReference().child("motor");
        soilmoisture();
        fetchDataFromFirebase();

        // Soil Moisture
        soilmoisture2();
        fetchDataFromFirebase2();



        back.setOnClickListener(view -> {
            Intent intent = new Intent(SoilMoistureActivity.this, MainHome.class);
            startActivity(intent);
        });
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Convert boolean value to integer (1 for true, 0 for false)
                int intValue = isChecked ? 1 : 0;

                // Update the top-level node in Firebase with the integer value
                topLevelNodeRef.setValue(intValue);
            }
        });
    }

    private void fetchDataFromFirebase() {

        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mData.getReference("Demo");
        mRef = mData.getReference("Reading/Soil_Moisture");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    moist = Float.parseFloat(snapshot.getValue().toString());
                    vauleToPercentage();
//                    waterPumpOperation();
                    updateGauge();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });
    }
     private void soilmoisture(){
        Range mrange = new Range();
        mrange.setColor(Color.parseColor("#ce0000")); // Red color for low moisture
        mrange.setFrom(0.0);
        mrange.setTo(33.0); // Represents 0% to 33% moisture

        Range mrange2 = new Range();
        mrange2.setColor(Color.parseColor("#E3E500")); // Yellow color for medium moisture
        mrange2.setFrom(33.0);
        mrange2.setTo(66.0); // Represents 33% to 66% moisture

        Range mrange3 = new Range();
        mrange3.setColor(Color.parseColor("#00FF00")); // Green color for high moisture
        mrange3.setFrom(66.0);
        mrange3.setTo(100.0); // Represents 66% to 100% moisture

        // Add color ranges to gauge
        moistGauge.addRange(mrange);
        moistGauge.addRange(mrange2);
        moistGauge.addRange(mrange3);

        // Set min, max, and current value
        moistGauge.setMinValue(0.0);
        moistGauge.setMaxValue(100.0);
//        moistGauge.setValue(100.0 - (moist / 1024.0) * 100.0);
    }
    private void updateGauge() {
    double newValue = 0.0;
    String previous = valueOf(moistGauge.getValue());
    Double previousValue = Double.parseDouble(previous);
    newValue = moistPercentage;

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
                        moistGauge.setValue(Double.parseDouble(decimalFormat.format(currentValue)));
                        moistText.setText(valueOf(currentValue));
                        currentText.setText(valueOf(currentValue));
                    }
                });

                // Stop the timer when reaching the new value
                if (currentValue >= finalNewValue) {
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            moistGauge.setValue(Double.parseDouble(decimalFormat.format(finalNewValue)));
                            moistText.setText(valueOf(finalNewValue));
                            currentText.setText(valueOf(finalNewValue));
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
                        moistGauge.setValue(Double.parseDouble(decimalFormat.format(currentValue)));
                        moistText.setText(valueOf(currentValue));
                        currentText.setText(valueOf(currentValue));
                    }
                });

                // Stop the timer when reaching the new value
                if (currentValue <= finalNewValue1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            moistGauge.setValue(Double.parseDouble(decimalFormat.format(finalNewValue1)));
                            moistText.setText(valueOf(finalNewValue1));
                            currentText.setText(valueOf(finalNewValue1));
                            timer.cancel();
                        }
                    });
                }
            }
        }, 0, delayBetweenSteps);
    }
}
    private void vauleToPercentage() {

        double per = 1024 - moist;
        moistPercentage = ((per/1024)*100);

    }
//    private void waterPumpOperation() {
//
//        if (moist >= 820  )
//        {
//            Integer motorValue = 1;
//            topLevelNodeRef.setValue(motorValue);
//        }
//
//        if (moist < 370)
//        {
//            Integer motorValue = 0;
//            topLevelNodeRef.setValue(motorValue);
//        }
//
//    }


    // Soil Moisture 2
    private void soilmoisture2(){
        Range mrange = new Range();
        mrange.setColor(Color.parseColor("#ce0000")); // Red color for low moisture
        mrange.setFrom(0.0);
        mrange.setTo(33.0); // Represents 0% to 33% moisture

        Range mrange2 = new Range();
        mrange2.setColor(Color.parseColor("#E3E500")); // Yellow color for medium moisture
        mrange2.setFrom(33.0);
        mrange2.setTo(66.0); // Represents 33% to 66% moisture

        Range mrange3 = new Range();
        mrange3.setColor(Color.parseColor("#00FF00")); // Green color for high moisture
        mrange3.setFrom(66.0);
        mrange3.setTo(100.0); // Represents 66% to 100% moisture

        // Add color ranges to gauge
        moistGauge2.addRange(mrange);
        moistGauge2.addRange(mrange2);
        moistGauge2.addRange(mrange3);

        // Set min, max, and current value
        moistGauge2.setMinValue(0.0);
        moistGauge2.setMaxValue(100.0);
//        moistGauge.setValue(100.0 - (moist / 1024.0) * 100.0);
    }
    private void fetchDataFromFirebase2() {

        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mData.getReference("Demo");
        mRef = mData.getReference("uno/soil");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    moist2 = Float.parseFloat(snapshot.getValue().toString());
                    vauleToPercentage2();
                    updateGauge2();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });
    }
    private void fetchDataFromFirebase3() {

        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mData.getReference("Demo");
        mRef = mData.getReference().child("motor");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    motor = Integer.parseInt(snapshot.getValue().toString());
                    switchOperation();
//                    vauleToPercentage2();
//                    updateGauge2();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });
    }
    private void updateGauge2() {
        double newValue = 0.0;
        String previous = valueOf(moistGauge2.getValue());
        Double previousValue = Double.parseDouble(previous);
        newValue = moistPercentage2;

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
                            moistGauge2.setValue(Double.parseDouble(decimalFormat.format(currentValue)));
                            moistText2.setText(valueOf(currentValue));
                            currentText2.setText(valueOf(currentValue));
                        }
                    });

                    // Stop the timer when reaching the new value
                    if (currentValue >= finalNewValue) {
                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                moistGauge2.setValue(Double.parseDouble(decimalFormat.format(finalNewValue)));
                                moistText2.setText(valueOf(finalNewValue));
                                currentText2.setText(valueOf(finalNewValue));
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
                            moistGauge2.setValue(Double.parseDouble(decimalFormat.format(currentValue)));
                            moistText2.setText(valueOf(currentValue));
                            currentText2.setText(valueOf(currentValue));
                        }
                    });

                    // Stop the timer when reaching the new value
                    if (currentValue <= finalNewValue1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                moistGauge2.setValue(Double.parseDouble(decimalFormat.format(finalNewValue1)));
                                moistText2.setText(valueOf(finalNewValue1));
                                currentText2.setText(valueOf(finalNewValue1));
                                timer.cancel();
                            }
                        });
                    }
                }
            }, 0, delayBetweenSteps);
        }
    }
    private void vauleToPercentage2() {

        double per = 1024 - moist2;
        moistPercentage2 = ((per/1024)*100);

    }
    private  void switchOperation() {
        if (motor == 0) {
            switchCompat.setChecked(false);
        } else if (motor == 1) {
            switchCompat.setChecked(true);
        }
    }
}