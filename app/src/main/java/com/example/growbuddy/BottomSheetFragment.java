package com.example.growbuddy;

//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//
//
//public class BottomSheetFragment extends BottomSheetDialogFragment {
//
//
//
//
//
//    public BottomSheetFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
//
//        result = view.findViewById(R.id.result);
//        confidence = view.findViewById(R.id.confidence);
//        imageView = view.findViewById(R.id.imageView);
//        picture = view.findViewById(R.id.button);
//
//
//
//
//        return view;
//    }
//}

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.growbuddy.ml.ModelUnquant;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int IMAGE_SIZE = 224;

    private TextView resultTextView, confidenceTextView;
    private ImageView imageView;
    private Button pictureButton;

    LottieAnimationView view1;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resultTextView = view.findViewById(R.id.result);
        imageView = view.findViewById(R.id.imageView);
        pictureButton = view.findViewById(R.id.button);
        view1=view.findViewById(R.id.animation_scan);

        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    // Request camera permission if we don't have it
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            view1.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, IMAGE_SIZE, IMAGE_SIZE, false);
            classifyImage(image);
        }
    }

    private void classifyImage(Bitmap image) {
        try {
            ModelUnquant model = ModelUnquant.newInstance(requireContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            int[] intValues = new int[IMAGE_SIZE * IMAGE_SIZE];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i = 0; i < IMAGE_SIZE; i++) {
                for (int j = 0; j < IMAGE_SIZE; j++) {
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.F / 255.F));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.F / 255.F));
                    byteBuffer.putFloat((val & 0xFF) * (1.F / 255.F));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

//            String[] classes = {"guava_wilt", "guava_algal_leaf", "fruit_rot_guava", "anthrancose_custard_apple", "leaf_spot_custard",
//                    "leaf_spot_sapota", "sooty_mold", "citrusscab", "citrus_tristeza", "scooty_mould_lemon", "anthrancose_lemon", "Citrus canker lemon",
//                    "Powdery mildew mango", "Anthrancose mango"};

            String[] classes={"mango_healty","mango_sooty_mould","mango_anthrancose","mango_bacterial_canker","mango_powdery_mildew","mango_die_black",
            "citrus_canker","citrus_melanose","citrus_black_spot","citus_melanose","citrus_healty","custrd_black_canker","leaf_spot_custard","guava_rust","guava_mumification","guava_dot","guava_canker","guava_healthy"
            };

            resultTextView.setText(classes[maxPos]);
            resultTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // To search the disease on the internet
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/search?q=" + resultTextView.getText())));
                }
            });

            model.close();
        } catch (IOException e) {
            // Handle the exception
        }
    }
}
