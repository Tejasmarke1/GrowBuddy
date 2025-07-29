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

public class Uplaod_Tools_Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;


    TextView tv_tool_name, tv_tool_description,tv_tool_price;
    ImageView tool_uploadbtn, toolImage;
    Button tools_upl_submit;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private ProgressDialog dialog;

    RelativeLayout relativeLayout;

    Uri itm_ImageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplaod_tools);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("please Wait");
        dialog.setCancelable(false);
        dialog.setTitle("Uploading");
        dialog.setCanceledOnTouchOutside(false);

        tv_tool_name = findViewById(R.id.tool_name);
        tv_tool_description = findViewById(R.id.tool_description);
        tv_tool_price = findViewById(R.id.tool_price);
        tool_uploadbtn = findViewById(R.id.tool_uploadbtn);
        toolImage = findViewById(R.id.toolImage);
        tools_upl_submit = findViewById(R.id.itm_upl_submit);
        relativeLayout = findViewById(R.id.itm_relative);


        tool_uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
                relativeLayout.setVisibility(View.VISIBLE);
                tool_uploadbtn.setVisibility(View.GONE);

            }
        });

        tools_upl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                final StorageReference reference = firebaseStorage.getReference().child("AppUser/data/tools")
                        .child(System.currentTimeMillis() + "");
                reference.putFile(itm_ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                                toolModel model = new toolModel();
                                CommanModel model = new CommanModel(uri.toString(),  tv_tool_price.getText().toString(),tv_tool_description.getText().toString(), tv_tool_name.getText().toString());
                                model.setImage(uri.toString());
                                model.setName(tv_tool_name.getText().toString());
                                model.setDescription(tv_tool_description.getText().toString());
                                model.setPrice(tv_tool_price.getText().toString());

                                database.getReference().child("AppUser/data/tools").push().setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                tv_tool_name.setText("");
                                                tv_tool_description.setText("");
                                                tv_tool_price.setText("");
                                                toolImage.setImageURI(null);
                                                tool_uploadbtn.setVisibility(View.VISIBLE);

                                                Toast.makeText(getApplicationContext(), "Upload !!!!!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

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

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            itm_ImageUri = data.getData();
            toolImage.setImageURI(itm_ImageUri);
        }
    }
}