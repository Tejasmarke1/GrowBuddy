package com.example.growbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.growbuddy.databinding.ActivityAboutUsBinding;

public class About_Us_Activity extends AppCompatActivity {
        ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ////////////////////////////////////////////////////////////////////////////////////////////////////

        binding.himanshu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(binding.himanshu1.getVisibility()== View.VISIBLE){
                        binding.himanshu1.setVisibility(View.INVISIBLE);
                        binding.himanshu2.setVisibility(View.VISIBLE);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.tejas1.getLayoutParams();
                        params.addRule(RelativeLayout.BELOW, binding.himanshu2.getId());
                        binding.tejas1.setLayoutParams(params);
                    }
            }
        });

        binding.himanshu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.himanshu2.getVisibility()== View.VISIBLE){
                    binding.himanshu2.setVisibility(View.INVISIBLE);
                    binding.himanshu1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.tejas1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.himanshu1.getId());
                    binding.tejas1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////

        binding.tejas1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.tejas1.getVisibility()== View.VISIBLE){
                    binding.tejas1.setVisibility(View.INVISIBLE);
                    binding.tejas2.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.kalpak1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.tejas2.getId());
                    binding.kalpak1.setLayoutParams(params);
                }
            }
        });

        binding.tejas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.tejas2.getVisibility()== View.VISIBLE){
                    binding.tejas2.setVisibility(View.INVISIBLE);
                    binding.tejas1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.kalpak1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.tejas1.getId());
                    binding.kalpak1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        binding.kalpak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.kalpak1.getVisibility()== View.VISIBLE){
                    binding.kalpak1.setVisibility(View.INVISIBLE);
                    binding.kalpak2.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.om1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.kalpak2.getId());
                    binding.om1.setLayoutParams(params);
                }
            }
        });

        binding.kalpak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.kalpak2.getVisibility()== View.VISIBLE){
                    binding.kalpak2.setVisibility(View.INVISIBLE);
                    binding.kalpak1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.om1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.kalpak1.getId());
                    binding.om1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////

        binding.om1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.om1.getVisibility()== View.VISIBLE){
                    binding.om1.setVisibility(View.INVISIBLE);
                    binding.om2.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.mohit1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.om2.getId());
                    binding.mohit1.setLayoutParams(params);
                }
            }
        });

        binding.om2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.om2.getVisibility()== View.VISIBLE){
                    binding.om2.setVisibility(View.INVISIBLE);
                    binding.om1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.mohit1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.om1.getId());
                    binding.mohit1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////////////////////////

        binding.mohit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.mohit1.getVisibility()== View.VISIBLE){
                    binding.mohit1.setVisibility(View.INVISIBLE);
                    binding.mohit2.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.yogesh1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.mohit2.getId());
                    binding.yogesh1.setLayoutParams(params);
                }
            }
        });

        binding.mohit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.mohit2.getVisibility()== View.VISIBLE){
                    binding.mohit2.setVisibility(View.INVISIBLE);
                    binding.mohit1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.yogesh1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.mohit1.getId());
                    binding.yogesh1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////

        binding.yogesh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.yogesh1.getVisibility()== View.VISIBLE){
                    binding.yogesh1.setVisibility(View.INVISIBLE);
                    binding.yogesh2.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.prasann1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.yogesh2.getId());
                    binding.prasann1.setLayoutParams(params);
                }
            }
        });

        binding.yogesh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.yogesh2.getVisibility()== View.VISIBLE){
                    binding.yogesh2.setVisibility(View.INVISIBLE);
                    binding.yogesh1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.prasann1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.yogesh1.getId());
                    binding.prasann1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////

        binding.prasann1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.prasann1.getVisibility()== View.VISIBLE){
                    binding.prasann1.setVisibility(View.INVISIBLE);
                    binding.prasann2.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.ganesh1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.prasann2.getId());
                    binding.ganesh1.setLayoutParams(params);
                }
            }
        });

        binding.prasann2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.prasann2.getVisibility()== View.VISIBLE){
                    binding.prasann2.setVisibility(View.INVISIBLE);
                    binding.prasann1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.ganesh1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.prasann1.getId());
                    binding.ganesh1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////////////////////////////////////

        binding.ganesh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.ganesh1.getVisibility()== View.VISIBLE){
                    binding.ganesh1.setVisibility(View.INVISIBLE);
                    binding.ganesh2.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.saurabh1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.ganesh2.getId());
                    binding.saurabh1.setLayoutParams(params);
                }
            }
        });

        binding.ganesh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.ganesh2.getVisibility()== View.VISIBLE){
                    binding.ganesh2.setVisibility(View.INVISIBLE);
                    binding.ganesh1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.saurabh1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.ganesh1.getId());
                    binding.saurabh1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////////////////////////////////////

        binding.saurabh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.saurabh1.getVisibility()== View.VISIBLE){
                    binding.saurabh1.setVisibility(View.INVISIBLE);
                    binding.saurabh2.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.mayur1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.saurabh2.getId());
                    binding.mayur1.setLayoutParams(params);
                }
            }
        });

        binding.saurabh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.saurabh2.getVisibility()== View.VISIBLE){
                    binding.saurabh2.setVisibility(View.INVISIBLE);
                    binding.saurabh1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.mayur1.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, binding.saurabh1.getId());
                    binding.mayur1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////////////////////////////////////

        binding.mayur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.mayur1.getVisibility()== View.VISIBLE){
                    binding.mayur1.setVisibility(View.INVISIBLE);
                    binding.mayur2.setVisibility(View.VISIBLE);
//                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.mayur1.getLayoutParams();
//                    params.addRule(RelativeLayout.BELOW, binding.mayur2.getId());
//                    binding.mayur1.setLayoutParams(params);
                }
            }
        });

        binding.mayur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.mayur2.getVisibility()== View.VISIBLE){
                    binding.mayur2.setVisibility(View.INVISIBLE);
                    binding.mayur1.setVisibility(View.VISIBLE);
//                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.mayur1.getLayoutParams();
//                    params.addRule(RelativeLayout.BELOW, binding.mayur1.getId());
//                    binding.mayur1.setLayoutParams(params);
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////









    }
}