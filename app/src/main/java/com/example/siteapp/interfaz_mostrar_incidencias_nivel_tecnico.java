package com.example.siteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityInterfazMostrarIncidenciasAdministrativasBinding;
import com.example.siteapp.databinding.ActivityMainBinding;
import com.example.siteapp.databinding.FragmentFrInTecBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class interfaz_mostrar_incidencias_nivel_tecnico extends AppCompatActivity {


    ActivityInterfazMostrarIncidenciasAdministrativasBinding layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout=ActivityInterfazMostrarIncidenciasAdministrativasBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.incidencias_dependientes,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences admin=getBaseContext().getSharedPreferences("x", Context.MODE_PRIVATE);
        String tip_usuario=admin.getString("tip_usuario","");

            switch (item.getItemId())
            {

                case R.id.admin:
                        frInAd fr2=new frInAd();
                        FragmentTransaction mFragmentAdmin = getSupportFragmentManager().beginTransaction();
                        mFragmentAdmin.replace(layout.containerFrag.getId(),fr2);
                        mFragmentAdmin.commit();

                    break;
                case R.id.tecnico:
                        frInTec fr1 = new frInTec();
                        FragmentTransaction mFragmentTec = getSupportFragmentManager().beginTransaction();
                        mFragmentTec.replace(layout.containerFrag.getId(),fr1);
                        mFragmentTec.commit();

                    break;

                case R.id.regre:

                    if(tip_usuario.equals("C")){
                        Intent intent = new Intent( getApplicationContext(),interfaz_usuario.class);
                        startActivity(intent);

                    }
                    else if (tip_usuario.equals("T")) {
                        Intent intent = new Intent( getApplicationContext(),interfaz_tecnico.class);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent( getApplicationContext(),interfaz_dependiente.class);
                        startActivity(intent);
                    }

                    break;
            }
        return super.onOptionsItemSelected(item);
    }

}