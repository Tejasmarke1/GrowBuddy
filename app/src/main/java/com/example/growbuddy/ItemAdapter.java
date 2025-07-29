package com.example.growbuddy;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolderItem> {


    private ArrayList<ItemModel> item_list;
    private Context itm_context;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private ProgressDialog dialog;



    public ItemAdapter(ArrayList<ItemModel> item_list, Context itm_context) {
        this.item_list = item_list;
        this.itm_context = itm_context;
        this.database = FirebaseDatabase.getInstance(); // Initialize FirebaseDatabase
        this.firebaseStorage = FirebaseStorage.getInstance(); // Initialize FirebaseStorage
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_look, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        ItemModel model = item_list.get(position);

        // Load images using Picasso library
        Picasso.get().load(model.getItm_productImage()).placeholder(R.drawable.ic_launcher_background).into(holder.itm_productImage);
        Picasso.get().load(model.getItm_productImage()).placeholder(R.drawable.ic_launcher_background).into(holder.itm_productImagebg);
        holder.itm_headline.setText(model.getItm_headline());
        holder.itm_price.setText(model.getItm_price());


        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show progress dialog while processing
                dialog = new ProgressDialog(itm_context);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
                dialog.setTitle("Processing");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                // Create a new ItemModel with details from the current model
                ItemModel newModel = new ItemModel();
                newModel.setItm_headline(model.getItm_headline());
                newModel.setItm_price(model.getItm_price());
                newModel.setItm_description(model.getItm_description());

                // Push the new item to the "buy" node in Firebase Database
                database.getReference().child("buy").push().setValue(newModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(itm_context, "Item added to cart successfully!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(itm_context, "Error occurred!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView itm_headline, itm_price;
        ImageView itm_productImage,itm_productImagebg;
        Button addtocart;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            itm_headline = itemView.findViewById(R.id.tv_HeadLine_item);
            itm_price = itemView.findViewById(R.id.item_price);
            itm_productImage = itemView.findViewById(R.id.item_image);
            itm_productImagebg = itemView.findViewById(R.id.itemImage);
            addtocart = itemView.findViewById(R.id.addToCart);
        }
    }
}
