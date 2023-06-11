package com.example.siteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazMostrarIncidenciasAdministrativasBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class interfaz_mostrar_incidencias_nivel_tecnico extends AppCompatActivity {


    ActivityInterfazMostrarIncidenciasAdministrativasBinding layout;
    Context ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout=ActivityInterfazMostrarIncidenciasAdministrativasBinding.inflate(getLayoutInflater());
        //setContentView(layout.getRoot());
        View view = layout.getRoot();
        setContentView(view);
        ct=view.getContext();

        //validarUsuario();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.incidencias_dependientes,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

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

                        Intent intent = new Intent( getApplicationContext(),interfaz_tecnico.class);
                        startActivity(intent);


                    break;
            }
        return super.onOptionsItemSelected(item);
    }

    private void validarUsuario(){



        String ip = getString(R.string.ip);
        String URL = ip+"/conexion_php/hash.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()) {
                    try {
                        JSONObject objUser= new JSONObject(response);
                        //Intent activity=null;
                        Log.i("result","Datares: "+response);
                        SharedPreferences admin=ct.getSharedPreferences("x",ct.MODE_PRIVATE);
                        SharedPreferences.Editor data=admin.edit();

                        data.putString("nombre",objUser.getString("nombre"));
                        data.putString("cedula",objUser.getString("cedula"));
                        data.putString("tip_usuario",objUser.getString("tip_usuario"));
                        data.putString("id",objUser.getString("id"));
                        data.putString("ap",objUser.getString("ap"));
                        data.putString("ip",ip.toString());
                        data.putString("id_groupInc",objUser.getString("group_inc"));
                        data.commit();
                        data.apply();



                    } catch (JSONException e) {
                        Log.i("Error",e.getMessage());
                    }

                }else{

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                SharedPreferences admin=ct.getSharedPreferences("x",ct.MODE_PRIVATE);
                String cedula=admin.getString("cedula","");
                String contrasena=admin.getString("contrasena","");
                Log.i("result","Datac: "+cedula);
                Log.i("result","Datacc: "+contrasena);
                parametros.put("cedula",cedula);
                parametros.put("contrasena",contrasena);
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), interfaz_tecnico.class);
        startActivity(intent);
    }
}