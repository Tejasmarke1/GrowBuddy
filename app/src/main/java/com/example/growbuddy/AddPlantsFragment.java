package com.example.growbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddPlantsFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private RecyclerView plant_recyclerView;
    private RecyclerView flower_recyclerView;
    private ArrayList<PlantsModel> recycleList;
    FloatingActionButton upload_btn;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_plants, container, false);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        plant_recyclerView = view.findViewById(R.id.recyclerview_item);
//        recycleList = new ArrayList<>();
//        PlantAdapter recycleAdapter = new PlantAdapter(recycleList, getContext());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        plant_recyclerView.setLayoutManager(linearLayoutManager);
//        plant_recyclerView.setNestedScrollingEnabled(false);
//        plant_recyclerView.setAdapter(recycleAdapter);
//
//        upload_btn = view.findViewById(R.id.upload_btn);
//        firebaseDatabase.getReference().child("item_section")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            PlantModel plantModel = dataSnapshot.getValue(PlantModel.class);
//                            recycleList.add(plantModel);
//                        }
//                        recycleAdapter.notifyDataSetChanged();
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//        upload_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), UploadActivity.class);
//                startActivity(i);
//            }
//        });
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


        return view;
    }
    private static class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // Return the Fragment associated with the specified position
            // This is where you would typically return a different Fragment for each tab
            switch (position) {
                case 0:
                    return new PlantFragment();
                case 1:
                    return new SeedsFragment();
                case 2:
                    return new ToolsFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Return the number of tabs
            return 3; // 3 tabs
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Return the title of the tab at the specified position
            switch (position) {
                case 0:
                    return "Plants";
                case 1:
                    return "Seeds";
                case 2:
                    return "Tools";
                default:
                    return null;
            }
        }
    }
}