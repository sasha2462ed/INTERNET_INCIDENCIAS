package com.example.siteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.siteapp.databinding.ActivityInterfazOnboardingBinding;

public class interfaz_onboarding extends AppCompatActivity {

    ActivityInterfazOnboardingBinding views;
    int screens=0;
    SharedPreferences app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views=ActivityInterfazOnboardingBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());

        views.btnNex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId()==views.btnNex.getId()){

                    if(screens==5){

                    }else{

                        if(screens==0){
                            frStup2 fragment=new frStup2();
                            FragmentTransaction admin=getSupportFragmentManager().beginTransaction();
                            admin.setCustomAnimations(R.anim.entrada,0,0,R.anim.entrada);
                            admin.replace(views.contFr.getId(),fragment);
                            admin.commit();
                            Log.i("result", String.valueOf(screens));

                        }else if(screens==1){
                            frStup3 fragment=new frStup3();
                            FragmentTransaction admin=getSupportFragmentManager().beginTransaction();
                            admin.setCustomAnimations(R.anim.entrada,0,0,R.anim.entrada);
                            admin.replace(views.contFr.getId(),fragment);
                            admin.commit();

                        }else if(screens==2){
                            frStup4 fragment=new frStup4();
                            FragmentTransaction admin=getSupportFragmentManager().beginTransaction();
                            admin.setCustomAnimations(R.anim.entrada,0,0,R.anim.entrada);
                            admin.replace(views.contFr.getId(),fragment);
                            admin.commit();

                        }else if (screens==3){

//                            SharedPreferences.Editor data=app.edit();
//                            data.remove("status");
//                            data.putString("status","1");
//                            data.apply();

                            Intent intent = new Intent( getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }

                        Log.i("result","Click Aqui");
                        screens++;

                    }
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}