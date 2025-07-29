package com.example.growbuddy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolderCart> {

    static ArrayList<CartModel> cart_list;
    Context cart_context;
    FirebaseDatabase database;
    private Context mContext;


    public CartAdapter(ArrayList<CartModel> cart_list,Context cart_context){
        this.cart_list = cart_list;
        this.cart_context = cart_context;
        mContext = cart_context;
    }

    public static double calculateTotalPrice() {
        double totalPrice = 0.0;
        if (cart_list != null) { // Null check for cart_list
            for (CartModel item : cart_list) {
                String priceString = item.getPrice();
                if (priceString != null && priceString.matches("^-?\\d+(\\.\\d+)?$")) {
                    // Null check for priceString and regex check for valid number
                    totalPrice += Double.parseDouble(priceString);
                } else {
                    // Handle the scenario where the priceString is null or not a valid number
                    if (priceString == null) {
                        Log.e("CartAdapter", "Price is null for item: " + item.getName());
                    } else {
                        Log.e("CartAdapter", "Invalid price format for item: " + item.getName());
                    }
                }
            }
        } else {
            // Handle the scenario where cart_list is null
            Log.e("CartAdapter", "Cart list is null");
        }
        return totalPrice;
    }



    @NonNull
    @Override
    public CartAdapter.ViewHolderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cart_context).inflate(R.layout.cart_look,parent,false);
        return new ViewHolderCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolderCart holder, int position) {
        CartModel model = cart_list.get(position);
        holder.sh_buy_price.setText(model.getPrice());
        holder.sh_buy_name.setText(model.getName());
        Picasso.get().load(model.getImage()).placeholder(R.drawable.plant_bg).into(holder.sh_buy_image);

        String email =retrieveFromSharedPreferences();
        String email1 = email.replace("@", "");
        String email2 = email1.replace(".", "");


//        holder.delete_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                database = FirebaseDatabase.getInstance();
//                String headlineToRemove = model.getName();
//                Query query = database.getReference().child("AppUser").child("Order").child(email2).child("cart")
//                        .orderByChild("name").equalTo(headlineToRemove);
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Toast.makeText(cart_context, "Item removed successfully", Toast.LENGTH_SHORT).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(cart_context, "Error removing item", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//            }
//        });

//        holder.delete_buy_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                database = FirebaseDatabase.getInstance();
//                String email = retrieveFromSharedPreferences();
//                String emailWithoutSpecialChars = email.replace(".", "").replace("@", "");
//
//                String headlineToRemove = model.getName();
//                Query query = database.getReference()
//                        .child("AppUser")
//                        .child("orders")
//                        .child(emailWithoutSpecialChars)
//                        .orderByChild("name")
//                        .equalTo(headlineToRemove);
//
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            snapshot.getRef().removeValue()
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(cart_context, "Plant removed successfully", Toast.LENGTH_SHORT).show();
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(cart_context, "Error removing item", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Log.e("CartAdapter", "Database Error: " + databaseError.getMessage());
//                    }
//                });
//            }
//        });

    }
    private String retrieveFromSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("email", "");
    }

    @Override
    public int getItemCount() {
        return cart_list.size();
    }

    public class ViewHolderCart extends RecyclerView.ViewHolder {
        TextView sh_buy_name,sh_buy_price;
        ImageView sh_buy_image;
        ImageView delete_buy_cart;
        public ViewHolderCart(@NonNull View itemView) {
            super(itemView);

            sh_buy_price = itemView.findViewById(R.id.sh_buy_price);
            sh_buy_name = itemView.findViewById(R.id.sh_buy_name);
            sh_buy_image = itemView.findViewById(R.id.sh_buy_image);

        }
    }

}
