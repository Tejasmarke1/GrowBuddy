package com.example.growbuddy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeedsAdapter extends RecyclerView.Adapter<SeedsAdapter.ViewHolderSeeds> {

    static ArrayList<CommanModel> seed_list;
    Context seed_context;
    FirebaseDatabase database;
    Firebase firebase;
    private Context context;

    public SeedsAdapter(ArrayList<CommanModel> seed_list,Context seed_context){
        this.seed_list = seed_list;
        this.seed_context = seed_context;
        this.context = seed_context;
    }

    @NonNull
    @Override
    public ViewHolderSeeds onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(seed_context).inflate(R.layout.seeds_look,parent,false);
        return new ViewHolderSeeds(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSeeds holder, @SuppressLint("RecyclerView") int position) {
        CommanModel model = seed_list.get(position);
        holder.sh_seed_name.setText(model.getName());
        holder.sh_seed_price.setText(model.getPrice());
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_seed_image);
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_seed_image_bg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(seed_list.get(position));
            }
        });

        holder.btn_deleteS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                String headlineToRemove =model.getName();
                Query query = database.getReference().child("AppUser/data/seeds").orderByChild("name").equalTo(headlineToRemove);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(seed_context, "Plant removed successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(seed_context, "Error removing item", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }


        });

    }

    @Override
    public int getItemCount() {
        return seed_list.size();
    }

    public class ViewHolderSeeds extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sh_seed_name,sh_seed_price;
        ImageView sh_seed_image,sh_seed_image_bg;

        ImageButton btn_deleteS;
        public ViewHolderSeeds(@NonNull View itemView) {
            super(itemView);
            sh_seed_name = itemView.findViewById(R.id.sh_seed_name);
            sh_seed_price = itemView.findViewById(R.id.sh_seed_price);
            sh_seed_image = itemView.findViewById(R.id.sh_seed_image);
            sh_seed_image_bg = itemView.findViewById(R.id.sh_seed_image_bg);
            btn_deleteS=itemView.findViewById(R.id.seedDeleteDataS);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                showCustomDialog(seed_list.get(position));
            }
        }
    }

    private void showCustomDialog(CommanModel data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Details");

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.card_plants, null);
        builder.setView(dialogView);

        ImageView imageView = dialogView.findViewById(R.id.imageViewCardPlants);
        TextView nameTextView = dialogView.findViewById(R.id.nameTextView);
        TextView priceTextView = dialogView.findViewById(R.id.priceTextView);
        TextView descriptionTextView = dialogView.findViewById(R.id.descriptionTextView);
        Button deletebtnPlant=dialogView.findViewById(R.id.deleteData);
        Button editbtnPlant=dialogView.findViewById(R.id.editData);

        // Populate dialog with data from clicked item
        Picasso.get().load(data.getImage()).into(imageView);
        nameTextView.setText(data.getName());
        priceTextView.setText(data.getPrice()+"Rs.");
        descriptionTextView.setText(data.getDescription());


        deletebtnPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                String headlineToRemove =data.getName();
                Query query = database.getReference().child("AppUser/data/seeds").orderByChild("name").equalTo(headlineToRemove);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(seed_context, "Item removed successfully", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(seed_context, "Error removing item", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        editbtnPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(R.layout.edit_popup_dialog);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                EditText plant_edt_name = alertDialog.findViewById(R.id.plant_edt_name);
                EditText plant_edt_description = alertDialog.findViewById(R.id.plant_edt_description);
                EditText plant_edt_price = alertDialog.findViewById(R.id.plant_edt_price);


                Button plant_edt_upload = alertDialog.findViewById(R.id.itm_edt_upload);

                // Set initial values
                plant_edt_name.setText(data.getName());
                plant_edt_description.setText(data.getDescription());
                plant_edt_price.setText(data.getPrice());


                plant_edt_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String headlineToUpdate = data.getName();
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", plant_edt_name.getText().toString());
                        map.put("description", plant_edt_description.getText().toString());
                        map.put("price", plant_edt_price.getText().toString());

                        database = FirebaseDatabase.getInstance();
                        Query query = database.getReference().child("AppUser/data/seeds")
                                .orderByChild("name").equalTo(headlineToUpdate);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().updateChildren(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(seed_context, "Item updated successfully", Toast.LENGTH_SHORT).show();
                                                    alertDialog.dismiss(); // Close the dialog after successful update
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(seed_context, "Error updating item", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }


                });



            }
        });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
