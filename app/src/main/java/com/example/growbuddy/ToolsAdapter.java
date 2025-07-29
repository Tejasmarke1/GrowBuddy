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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolderTools> {

    static ArrayList<CommanModel> tools_list;
    Context tools_context;
    FirebaseDatabase database;
    Firebase firebase;
    private Context context;
    public ToolsAdapter(ArrayList<CommanModel> tools_list,Context tools_context){
        this.tools_list = tools_list;
        this.tools_context = tools_context;
        this.context = tools_context;
    }

    @NonNull
    @Override
    public ViewHolderTools onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(tools_context).inflate(R.layout.tools_look,parent,false);
        return new ViewHolderTools(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTools holder, @SuppressLint("RecyclerView") int position) {
        CommanModel model = tools_list.get(position);
        holder.sh_tool_name.setText(model.getName());
        holder.sh_tool_price.setText(model.getPrice());
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_tool_image);
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_tool_image_bg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(tools_list.get(position));
            }
        });

        holder.btn_deleteT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                String headlineToRemove =model.getName();
                Query query = database.getReference().child("AppUser/data/tools").orderByChild("name").equalTo(headlineToRemove);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(tools_context, "Plant removed successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(tools_context, "Error removing item", Toast.LENGTH_SHORT).show();
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
        return tools_list.size();
    }

    public class ViewHolderTools extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sh_tool_name,sh_tool_price;
        ImageView sh_tool_image,sh_tool_image_bg;

        ImageButton btn_deleteT;
        public ViewHolderTools(@NonNull View itemView) {
            super(itemView);

            sh_tool_name = itemView.findViewById(R.id.sh_tool_name);
            sh_tool_price = itemView.findViewById(R.id.sh_tool_price);
            sh_tool_image = itemView.findViewById(R.id.sh_tool_image);
            sh_tool_image_bg = itemView.findViewById(R.id.sh_tool_image_bg);
            btn_deleteT = itemView.findViewById(R.id.toolDeleteDataT);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                showCustomDialog(tools_list.get(position));
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
        Button deletebtnTools=dialogView.findViewById(R.id.deleteData);
        Button editbtnTools=dialogView.findViewById(R.id.editData);

        // Populate dialog with data from clicked item
        Picasso.get().load(data.getImage()).into(imageView);
        nameTextView.setText(data.getName());
        priceTextView.setText(data.getPrice()+"Rs.");
        descriptionTextView.setText(data.getDescription());


        deletebtnTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                String headlineToRemove =data.getName();
                Query query = database.getReference().child("AppUser/data/tools").orderByChild("name").equalTo(headlineToRemove);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(tools_context, "Item removed successfully", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(tools_context, "Error removing item", Toast.LENGTH_SHORT).show();
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

        editbtnTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(R.layout.edit_popup_dialog);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                EditText plant_edt_name = alertDialog.findViewById(R.id.plant_edt_name);
                EditText plant_edt_description = alertDialog.findViewById(R.id.plant_edt_description);
                EditText plant_edt_price = alertDialog.findViewById(R.id.plant_edt_price);


                Button tools_edt_upload = alertDialog.findViewById(R.id.itm_edt_upload);

                // Set initial values
                plant_edt_name.setText(data.getName());
                plant_edt_description.setText(data.getDescription());
                plant_edt_price.setText(data.getPrice());


                tools_edt_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String headlineToUpdate = data.getName();
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", plant_edt_name.getText().toString());
                        map.put("description", plant_edt_description.getText().toString());
                        map.put("price", plant_edt_price.getText().toString());

                        database = FirebaseDatabase.getInstance();
                        Query query = database.getReference().child("AppUser/data/tools")
                                .orderByChild("name").equalTo(headlineToUpdate);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().updateChildren(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(tools_context, "Tools Data updated successfully", Toast.LENGTH_SHORT).show();
                                                    alertDialog.dismiss(); // Close the dialog after successful update
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(tools_context, "Error updating item", Toast.LENGTH_SHORT).show();
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
