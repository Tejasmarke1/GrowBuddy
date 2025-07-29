package com.example.growbuddy;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlertsFragment extends Fragment {

    TextView textTemp, textRadar,textSmoke;

    Float temp,smoke,distance;

    ImageView red;

    private NotificationAdapter notificationAdapter;
    private List<NotificationModel> notificationList;

    String data,dataD,datas,datasD;

    public AlertsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alerts, container, false);

        textTemp=view.findViewById(R.id.data_temp_card);
        textRadar=view.findViewById(R.id.radar_distance);
        textSmoke=view.findViewById(R.id.gas_reading);
        red=view.findViewById(R.id.smoke_detected);
        





        readData();
        return view;
    }

    private void readData() {
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {

        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mData.getReference("Demo");
        DatabaseReference mRef1 = mData.getReference("Demo");
        DatabaseReference mRef2= mData.getReference("Demo");
        mRef = mData.getReference("uno/temp");
        mRef1 = mData.getReference("uno/radar");
        mRef2= mData.getReference("uno/smoke");
//        mRef=mData.getReference().child("Temperature");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    temp = (Float) Float.parseFloat(snapshot.getValue().toString());
                    textTemp.setText(String.valueOf(temp));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });

        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    distance = (Float) Float.parseFloat(snapshot.getValue().toString());
                    textRadar.setText(String.valueOf(distance));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });

        mRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    smoke = (Float) Float.parseFloat(snapshot.getValue().toString());
                    textSmoke.setText(String.valueOf(smoke));
                    if (smoke > 250) {
                        red.setImageResource(R.drawable.red);
                    } if  (smoke < 250){
                        red.setImageResource(R.drawable.green);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });
    }




}