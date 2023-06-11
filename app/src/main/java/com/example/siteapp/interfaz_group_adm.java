package com.example.siteapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.siteapp.databinding.ActivityInterfazGroupAdmBinding;

public class interfaz_group_adm extends AppCompatActivity {

    ActivityInterfazGroupAdmBinding layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_group_adm);
        layout=ActivityInterfazGroupAdmBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.menu_adm,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId())
        {

            case R.id.id_adm1:

                Intent intent = new Intent( getApplicationContext(),interfaz_escalable_adm.class);
                startActivity(intent);


                break;
            case R.id.id_adm2:

                intent = new Intent(getApplicationContext(), interfaz_group_tec.class);
                startActivity(intent);


                break;

            case R.id.id_adm3:

                intent = new Intent(getApplicationContext(), interfaz_mostrar_graficas.class);
                startActivity(intent);


                break;


            case R.id.id_adm4:

                intent = new Intent(getApplicationContext(), interfaz_dependiente.class);
                startActivity(intent);


                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent( getApplicationContext(),interfaz_dependiente.class);
        startActivity(intent);
    }


}