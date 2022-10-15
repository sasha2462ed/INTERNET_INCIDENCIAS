package com.example.siteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.siteapp.databinding.ActivityInterfazMostrarIncidenciasAdministrativasBinding;

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