package com.example.growbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PlantFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private RecyclerView plant_recyclerView;
    private RecyclerView flower_recyclerView;
    private ArrayList<CommanModel> recycleList;
    FloatingActionButton upload_btn;

    private ArrayList<CommanModel> plant_list;
    private RecyclerView recyclerView;

    private DatabaseReference databaseReference;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView=view.findViewById(R.id.recyclerview_plant);


        plant_list = new ArrayList<>();
        PlantsAdapter plantsAdapter = new PlantsAdapter(plant_list,getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(plantsAdapter);


        upload_btn = view.findViewById(R.id.upload_btn);
        firebaseDatabase.getReference().child("AppUser/data/plants")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            CommanModel plantModel = dataSnapshot.getValue(CommanModel.class);
                            plant_list.add(plantModel);
                        }
                        plantsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PlantsUploadActivity.class);
                startActivity(i);
            }
        });


        // Dialog box

        databaseReference = FirebaseDatabase.getInstance().getReference().child("AppUser/data/plants");


        return  view;
    }
}
