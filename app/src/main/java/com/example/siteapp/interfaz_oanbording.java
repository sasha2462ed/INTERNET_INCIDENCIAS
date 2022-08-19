package com.example.siteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityInterfazOanbordingBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class interfaz_oanbording extends AppCompatActivity  {

    ActivityInterfazOanbordingBinding views;
    int screens=0;

    SharedPreferences app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views=ActivityInterfazOanbordingBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());
        //views.btnNext.setOnClickListener(this);

        views.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId()==views.btnNext.getId()){

                    if(screens==5){

                    }else{

                        if(screens==0){
                            frStep2 fragment=new frStep2();
                            FragmentTransaction admin=getSupportFragmentManager().beginTransaction();
                            admin.setCustomAnimations(R.anim.entrada,0,0,R.anim.entrada);
                            admin.replace(views.contFrag.getId(),fragment);
                            admin.commit();
                            Log.i("result", String.valueOf(screens));

                        }else if(screens==1){
                            frStep3 fragment=new frStep3();
                            FragmentTransaction admin=getSupportFragmentManager().beginTransaction();
                            admin.setCustomAnimations(R.anim.entrada,0,0,R.anim.entrada);
                            admin.replace(views.contFrag.getId(),fragment);
                            admin.commit();

                        }else if(screens==2){
                            frStep4 fragment=new frStep4();
                            FragmentTransaction admin=getSupportFragmentManager().beginTransaction();
                            admin.setCustomAnimations(R.anim.entrada,0,0,R.anim.entrada);
                            admin.replace(views.contFrag.getId(),fragment);
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


}