package com.example.siteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.siteapp.databinding.ActivityInterfazMenuOnboardingBinding;


public class interfaz_menuOnboarding extends AppCompatActivity {

    ActivityInterfazMenuOnboardingBinding views;
    int screens=0;
    SharedPreferences app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_menu_onboarding);
        views=ActivityInterfazMenuOnboardingBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());


        app=getApplicationContext().getSharedPreferences("myApp",MODE_PRIVATE);

        if(app.getString("status","").equals("1")){

            Intent intent = new Intent( getApplicationContext(),MainActivity.class);
            startActivity(intent);

        }else{

            SharedPreferences.Editor data=app.edit();
            data.putString("status","0");
            data.apply();

        }


        views.ontec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor data=app.edit();
                data.remove("status");
                data.putString("status","1");
                data.apply();

                Intent intent = new Intent( getApplicationContext(), interfaz_oanbording.class);
                startActivity(intent);

            }
        });

        views.onUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor data=app.edit();
                data.remove("status");
                data.putString("status","1");
                data.apply();

                Intent intent = new Intent( getApplicationContext(), interfaz_onboarding.class);
                startActivity(intent);

            }
        });


    }
}