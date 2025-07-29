package com.example.growbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.Range;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private ArcGauge tempGauge, humidityGauge, moistureGauge;
    private CardView tempCard, humidityCard, moistCard;
    private Button btn_temperature, btn_humidity, btn_soil_moisture;
    private Boolean doubletap = false;
    Double temp = 0.0;//Todo: Temperature Value from Firebase
    Double hum = 20.0;//Todo: Humidity Value from Firebase
    Double moist = 200.0;//Todo: Soil Moisture from Firebase

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tempCard = view.findViewById(R.id.tempCard);
        moistCard = view.findViewById(R.id.moistureCard);
        humidityCard = view.findViewById(R.id.humidityCard);
        //btn_temperature = view.findViewById(R.id.btn_temp);

        // Initialize your gauges, buttons, and other UI elements here



        tempCard.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), TemperatureActivity.class);
            startActivity(i);
        });

        // Add onClickListeners for other buttons and cards




        // Add your methods for soilmoisture(), humidity(), temperature(), updateGauge(), and onBackPressed()

        // Remember to replace getActivity() with requireActivity() for fragment-specific context

        // You may need to handle the lifecycle methods like onResume(), onPause(), etc., based on your requirements
//        btn_humidity.setOnClickListener(v -> {
//            Intent i = new Intent(getActivity(), HumidityActivity.class);
//            startActivity(i);
//        });

        humidityCard.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(),HumidityActivity.class);
            startActivity(i);
        });

//        btn_soil_moisture.setOnClickListener(v -> {
//            Intent i = new Intent(getActivity(), SoilMoistureActivity.class);
//            startActivity(i);
//        });

        moistCard.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SoilMoistureActivity.class);
            startActivity(i);
        });

        return view;

    }

    private void soilmoisture() {

        //TODO: Sets the range of the ArcGauge for Soil Moisture from 200 to 2000 with different Colors.
        Range mrange = new Range();
        mrange.setColor(Color.parseColor("#0000FF"));
        mrange.setFrom(200.0);
        mrange.setTo(800.0);

        Range mrange2 = new Range();
        mrange2.setColor(Color.parseColor("#E3E500"));
        mrange2.setFrom(800.0);
        mrange2.setTo(1400.0);

        Range mrange3 = new Range();
        mrange3.setColor(Color.parseColor("#ce0000"));
        mrange3.setFrom(1400.0);
        mrange3.setTo(2000.0);

// Add color ranges to gauge
        moistureGauge.addRange(mrange);
        moistureGauge.addRange(mrange2);
        moistureGauge.addRange(mrange3);

// Set min, max, and current value
        moistureGauge.setMinValue(200.0);
        moistureGauge.setMaxValue(2000.0);
        moistureGauge.setValue(moist);
//TODO:_____________________________________________________________________________________________
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
    private void temperature() {
        //TODO: Sets the range of the ArcGauge for Temperature from 0°C to 50°C with different Colors.
        Range trange = new Range();
        trange.setColor(Color.parseColor("#0000FF"));
        trange.setFrom(0.0);
        trange.setTo(16.66);

        Range trange2 = new Range();
        trange2.setColor(Color.parseColor("#E3E500"));
        trange2.setFrom(16.66);
        trange2.setTo(32.33);

        Range trange3 = new Range();
        trange3.setColor(Color.parseColor("#ce0000"));
        trange3.setFrom(32.33);
        trange3.setTo(50.0);

// Add color ranges to gauge
        tempGauge.addRange(trange);
        tempGauge.addRange(trange2);
        tempGauge.addRange(trange3);

// Set min, max, and current value
        tempGauge.setMinValue(0.0);
        tempGauge.setMaxValue(50.0);
        tempGauge.setValue(temp);
//TODO:_____________________________________________________________________________________________
    }

    //TODO: Updates the ArcGauge slowly
    private void updateGauge() {
        double newValue = 0.0;
        String previous = String.valueOf(tempGauge.getValue());
        Double previousValue = Double.parseDouble(previous);
//        newValue = Double.parseDouble(et.getText().toString());

        if (newValue > previousValue)
        {
            double difference = newValue - previousValue;
            int duration = 3000; // Duration for the update in milliseconds
            int steps = 20; // Number of steps for the update

            double stepValue = difference / steps;
            int delayBetweenSteps = duration / steps;

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                double currentValue = previousValue;

                @Override
                public void run() {
                    currentValue += stepValue;
                    tempGauge.setValue(currentValue);

                    // Stop the timer when reaching the new value
                    if (currentValue >= newValue) {
                        tempGauge.setValue(newValue);
                        timer.cancel();
                    }
                }
            }, 0, delayBetweenSteps);
        }

        else if (newValue < previousValue)
        {
            double difference = previousValue - newValue; // Calculating the difference to decrease

            int duration = 3000; // Duration for the update in milliseconds
            int steps = 20; // Number of steps for the update

            double stepValue = difference / steps;
            int delayBetweenSteps = duration / steps;

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                double currentValue = previousValue;

                @Override
                public void run() {
                    currentValue -= stepValue; // Decrease the value
                    tempGauge.setValue(currentValue);

                    // Stop the timer when reaching the new value
                    if (currentValue <= newValue) {
                        tempGauge.setValue(newValue);
                        timer.cancel();
                    }
                }
            }, 0, delayBetweenSteps);
        }
    }




}

