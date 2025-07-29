package com.example.growbuddy;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundService extends Service {
    Double temp = 0.0;
    Double hum = 5.0;
    Double moist = 0.0;
    Double gas = 0.0;
    Integer motor = 0;
    Double ctemp = 0.0;
    Integer radar = 0;
    double tempLimit = 45.0;
    double humLimit = 40.0;
    int moistLowerLimit = 820;
    int moistUpperLimit = 308;
    double gasLimit = 300.0;
    double ctempLimit = 50.0;
    int radarLimit = 100;
    double moistPercentage;
    //    int motorON = 1;
//    int motorOFF = 0;
    boolean wasBelowTempLimit = false;
    boolean wasBelowHumLimit = false;
    boolean wasBelowMoistLimit = false;
    boolean wasBelowGasLimit = false;
    boolean wasBelowCtempLimit = false;
    boolean wasBelowRadarLimit = false;
    //    boolean wasBelowMotorLimit = false;
    DecimalFormat decimalFormat = new DecimalFormat("#");
    FirebaseDatabase mData = FirebaseDatabase.getInstance();
    DatabaseReference mTempRef = mData.getReference("Reading/Temperature");
    DatabaseReference mHumRef = mData.getReference("Reading/Humidity");
    DatabaseReference mSoilRef = mData.getReference("Reading/Soil_Moisture");
    DatabaseReference mGasRef = mData.getReference("uno/smoke");
    DatabaseReference mCtempRef = mData.getReference("uno/temp");
    DatabaseReference mRadarRef = mData.getReference("uno/radar");
    DatabaseReference mMotorRef = mData.getReference().child("motor");
    DatabaseReference topLevelNodeRef;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Your logic to perform tasks in the background

        topLevelNodeRef = FirebaseDatabase.getInstance().getReference().child("motor");

        // For example, you can schedule the ReminderBroadcast
        createNotificationChannel();//TODO: Compulsory
        fetchDataFromFirebase();
        valueToPercentage();
        notificationSend();
        return START_STICKY;
    }


    private void scheduleReminderBroadcast(String title, String content, int notificationId) {
        // Schedule the ReminderBroadcast with unique data
        Intent intent = new Intent(BackgroundService.this, ReminderBrodcast.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("notificationId", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                BackgroundService.this, notificationId, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long currentTime = System.currentTimeMillis();
        long callTime = 1000;

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                currentTime + callTime,
                pendingIntent);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "GrowBuddyNotificationChannel";
            String description = "Channel for GrowBuddy Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyGrowBuddy", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void notificationSend() {

//TODO:--------------------- Temperature -------------------------

        if (temp > tempLimit && !wasBelowTempLimit) {
            scheduleReminderBroadcast("🌡️ High Temperature", "High Atmosperic Temperature : " + temp + "°C", 1);
            wasBelowTempLimit = true;
        }
        if (temp <= tempLimit) {
            wasBelowTempLimit = false; // Reset the flag
        }

//TODO:--------------------- Humidity -------------------------

        if (hum > humLimit && !wasBelowHumLimit) {
            scheduleReminderBroadcast("☁️ High Humidity", "High Atmospheric Humidity : " + Double.parseDouble(decimalFormat.format(hum)) + "% RH", 2);
            wasBelowHumLimit = true;
        }
        if (hum <= humLimit) {
            wasBelowHumLimit = false; // Reset the flag
        }

//TODO:--------------------- Soil Moisture -------------------------

        if (moist > moistLowerLimit && !wasBelowMoistLimit) {
            scheduleReminderBroadcast("💧 Low Soil Moisture", "Soil Moisture : " + Double.parseDouble(decimalFormat.format(moistPercentage)) + "%", 3);
            wasBelowMoistLimit = true;
            scheduleReminderBroadcast("🌊 Water Pump", "Water Supply is ON", 5);
            mMotorRef.setValue(1);


            if (moist < moistUpperLimit) {
                scheduleReminderBroadcast("🌊 Water Pump", "Water Supply is OFF", 6);
                mMotorRef.setValue(0);
            }

        }



//TODO:--------------------- Gas Sensor -------------------------

        if (gas > gasLimit && !wasBelowGasLimit) {
            scheduleReminderBroadcast("⚠️ Alert !", "Fire Detected Near the Controller", 4);
            wasBelowGasLimit = true;
        }
        if (gas <= gasLimit) {
            wasBelowGasLimit = false; // Reset the flag
        }

//TODO:--------------------- Controller Temperature -------------------------

        if (ctemp == ctempLimit && !wasBelowCtempLimit) {
            scheduleReminderBroadcast("⚠️ Alert !","🌡️ Controller Temperature Very High : 50°C",7);
            wasBelowCtempLimit = true;
        }
        if (ctemp <= ctempLimit) {
            wasBelowCtempLimit = false;
        }


//TODO:--------------------- Radar -------------------------

        if (radar < radarLimit && !wasBelowRadarLimit) {
            scheduleReminderBroadcast("⚠️ Alert !","🏃🏻 Activity Detected near Controller",8);
            wasBelowRadarLimit = true;
        }
        if (radar <= radarLimit) {
            wasBelowRadarLimit = false;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void fetchDataFromFirebase() {

//        TODO: Fetching Temperature Data
        mTempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    temp = Double.parseDouble(snapshot.getValue().toString());
                    waterPumpOperation();
                    notificationSend();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });

//        TODO: Fetching Humidity Data
        mHumRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    hum = Double.parseDouble(snapshot.getValue().toString());
                    notificationSend();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });

//        TODO: Fetching Soil Moisture Data
        mSoilRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    moist = Double.parseDouble(snapshot.getValue().toString());
                    valueToPercentage();
                    notificationSend();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });

//        TODO: Fetching Gas Data
        mGasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    gas = Double.parseDouble(snapshot.getValue().toString());
                    notificationSend();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });

//        TODO: Fetching Motor Data
        mMotorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    motor = Integer.parseInt(snapshot.getValue().toString());
                    notificationSend();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });

//        TODO: Fetching Controller Temperature Data
        mCtempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ctemp = Double.parseDouble(snapshot.getValue().toString());
                    notificationSend();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });

//        TODO: Fetching Radar Data
//        mRadarRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    radar = Integer.parseInt(snapshot.getValue().toString());
//                    notificationSend();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle cancelled event
//            }
//        });
        mRadarRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String radarValue = snapshot.getValue().toString();
                    radar = (int) Double.parseDouble(radarValue);
                    notificationSend();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });
    }

    private void valueToPercentage() {
        double per = 1024 - moist;
        moistPercentage = ((per / 1024) * 100);
    }

    private void waterPumpOperation() {

        if (moist >= 820  )
        {
            Integer motorValue = 1;
            topLevelNodeRef.setValue(motorValue);
        }

        if (moist < 370)
        {
            Integer motorValue = 0;
            topLevelNodeRef.setValue(motorValue);
        }

    }

}