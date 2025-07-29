package com.example.growbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SeedsFragment extends Fragment {

    private ArrayList<CommanModel> seed_list;
    private RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;

    FloatingActionButton btn_upload_seed;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_seeds, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = view.findViewById(R.id.recyclerview_seeds);


        seed_list = new ArrayList<>();
        SeedsAdapter seedsAdapter = new SeedsAdapter(seed_list,getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(seedsAdapter);
        btn_upload_seed=view.findViewById(R.id.upload_btn_seed);


        firebaseDatabase.getReference().child("AppUser/data/seeds")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            CommanModel CommanModel = dataSnapshot.getValue(CommanModel.class);
                            seed_list.add(CommanModel);
                        }
                        seedsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






        btn_upload_seed.setOnClickListener(v->{
            Intent i1=new Intent(getContext(),Seeds_Upload_Activity.class);
            startActivity(i1);
        });




        return view;
    }
}