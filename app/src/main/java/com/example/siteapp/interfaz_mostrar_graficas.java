package com.example.siteapp;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.siteapp.databinding.ActivityInterfazMostrarGraficasBinding;

public class interfaz_mostrar_graficas extends General {

    ActivityInterfazMostrarGraficasBinding layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout=ActivityInterfazMostrarGraficasBinding.inflate(getLayoutInflater());
        View view = layout.getRoot();
        setContentView(view);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.graficos,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        SharedPreferences admin=getBaseContext().getSharedPreferences("x", Context.MODE_PRIVATE);
        String tip_usuario=admin.getString("tip_usuario","");

        switch (item.getItemId())
        {

            case R.id.nod:

                Intent intent = new Intent( getApplicationContext(),interfaz_graficoN.class);
                startActivity(intent);

                break;


            case R.id.ap:

                intent = new Intent(getApplicationContext(), interfaz_graficos.class);
                startActivity(intent);

                break;


            case R.id.salir:

                if(tip_usuario.equals("C")){

                    intent = new Intent(getApplicationContext(), interfaz_usuario.class);
                    startActivity(intent);

                }

                else if (tip_usuario.equals("T")) {

                    intent = new Intent(getApplicationContext(), interfaz_tecnico.class);
                    startActivity(intent);


                } else {

                    intent = new Intent(getApplicationContext(), interfaz_dependiente.class);
                    startActivity(intent);


                }

                break;


        }

        return super.onOptionsItemSelected(item);
    }
}