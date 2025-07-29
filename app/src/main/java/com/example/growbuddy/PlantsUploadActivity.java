package com.example.growbuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PlantsUploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;


    TextView tv_plant_name, tv_plant_description,tv_plant_price;
    ImageView plant_uploadbtn, plantImage;
    Button plant_upl_submit;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private ProgressDialog dialog;

    RelativeLayout relativeLayout;

    Uri itm_ImageUri;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_upload);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        dialog= new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("please Wait");
        dialog.setCancelable(false);
        dialog.setTitle("Uploading");
        dialog.setCanceledOnTouchOutside(false);

        tv_plant_name = findViewById(R.id.plant_name);
        tv_plant_description = findViewById(R.id.plant_description);
        tv_plant_price=findViewById(R.id.plant_price);
        plant_uploadbtn = findViewById(R.id.plant_uploadbtn);
        plantImage = findViewById(R.id.PlantImage);
        plant_upl_submit = findViewById(R.id.itm_upl_submit);
        relativeLayout = findViewById(R.id.itm_relative);


        plant_uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
                relativeLayout.setVisibility(View.VISIBLE);
                plant_uploadbtn.setVisibility(View.GONE);

            }
        });

        plant_upl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                final StorageReference reference = firebaseStorage.getReference().child("AppUser/data/plants")
                        .child(System.currentTimeMillis() + "");
                reference.putFile(itm_ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                                PlantModel model = new PlantModel();
                                CommanModel model = new CommanModel(tv_plant_name.getText().toString(),tv_plant_description.getText().toString(),tv_plant_price.getText().toString(),uri.toString());
                                model.setImage(uri.toString());
                                model.setName(tv_plant_name.getText().toString());
                                model.setDescription(tv_plant_description.getText().toString());
                                model.setPrice(tv_plant_price.getText().toString());

                                database.getReference().child("AppUser/data/plants").push().setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                tv_plant_name.setText("");
                                                tv_plant_description.setText("");
                                                tv_plant_price.setText("");
                                                plantImage.setImageURI(null);
                                                plant_uploadbtn.setVisibility(View.VISIBLE);

                                                Toast.makeText(PlantsUploadActivity.this, "Upload !!!!!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialog.dismiss();
                                                Toast.makeText(PlantsUploadActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }
                        });
                    }
                });

            }
        });
    }

    private void UploadImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            itm_ImageUri = data.getData();
            plantImage.setImageURI(itm_ImageUri);
        }
    }
}