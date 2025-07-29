package com.example.growbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Request_Data extends AppCompatActivity {

    TextView nameU, emailU, total_price;
    ImageView imageU;
    RecyclerView rc1;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_data);

        nameU = findViewById(R.id.nameUser);
        emailU = findViewById(R.id.emailUser);
        imageU = findViewById(R.id.userImage12);
        total_price = findViewById(R.id.totalPrice);
        rc1 = findViewById(R.id.recycleList);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String image = getIntent().getStringExtra("image");



        nameU.setText(name);
        emailU.setText(email);
        // Load image using Picasso or any other image loading library
        Picasso.get().load(image).placeholder(R.drawable.ic_launcher_background).into(imageU);

        // Retrieve total price from SharedPreferences and display it
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String totalPrice = preferences.getString("totalPrice", "");
        total_price.setText("Total Price: " + totalPrice);

        // Initialize RecyclerView and Adapter
        ArrayList<CartModel> buy_list = new ArrayList<>();
        cartAdapter = new CartAdapter(buy_list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc1.setLayoutManager(linearLayoutManager);
        rc1.setAdapter(cartAdapter);

        // Retrieve data from Firebase
        String emailKey = email.replace("@", "").replace(".", "");
        FirebaseDatabase.getInstance().getReference().child("AppUser").child(emailKey).child("cart")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            CartModel commanModel = dataSnapshot.getValue(CartModel.class);
                            buy_list.add(commanModel);
                            displayTotalPrice();

                        }
                        cartAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                    }
                });
    }
    private void displayTotalPrice() {
        double totalPrice = ((CartAdapter) rc1.getAdapter()).calculateTotalPrice();
        // Display total price in your UI, for example:
        total_price.setText("Total Price: " + totalPrice);
    }

}
