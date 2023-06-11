package com.example.siteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.siteapp.databinding.ActivityInterfazMostrarIncidenciasUsuarioBinding;

public class interfaz_mostrar_incidencias_usuario extends AppCompatActivity {

    ActivityInterfazMostrarIncidenciasUsuarioBinding layout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            layout=ActivityInterfazMostrarIncidenciasUsuarioBinding.inflate(getLayoutInflater());
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



            switch (item.getItemId()) {

                case R.id.admin:


                    frlnAdu fr3 = new frlnAdu();
                    FragmentTransaction mFragmentAdmin = getSupportFragmentManager().beginTransaction();
                    mFragmentAdmin.replace(layout.containerF.getId(), fr3);
                    mFragmentAdmin.commit();


                    break;
                case R.id.tecnico:

                    frlnTecu fr4 = new frlnTecu();
                    FragmentTransaction mFragmentTec = getSupportFragmentManager().beginTransaction();
                    mFragmentTec.replace(layout.containerF.getId(), fr4);
                    mFragmentTec.commit();


                    break;


                case R.id.regre:


                        Intent intent = new Intent(getApplicationContext(), interfaz_usuario.class);
                        startActivity(intent);


                    break;

            }

            return super.onOptionsItemSelected(item);
        }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), interfaz_usuario.class);
        startActivity(intent);
    }
}