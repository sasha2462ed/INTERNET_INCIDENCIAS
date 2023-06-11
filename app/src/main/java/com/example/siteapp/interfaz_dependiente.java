package com.example.siteapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazDependienteBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class interfaz_dependiente extends AppCompatActivity {

    com.nex3z.notificationbadge.NotificationBadge NotificationBadge;
    private ActivityInterfazDependienteBinding v7;



    Context ct;
    MenuItem menuItem;
    TextView notification;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private PendingIntent pendingIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_dependiente);
        v7 = ActivityInterfazDependienteBinding.inflate(getLayoutInflater());
        View view = v7.getRoot();
        setContentView(view);
        ct=view.getContext();


        validarUsuario();



        v7.v02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),interfaz_tec_adm.class);
                startActivity(intent);
            }
        });

        v7.v03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),interfaz_inc_adm.class);
                startActivity(intent);
            }
        });

        v7.v04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),interfaz_group_adm.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.cerrar,menu);
        menuItem = menu.findItem(R.id.notify);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.cerrar:

                SharedPreferences admin=ct.getSharedPreferences("x",ct.MODE_PRIVATE);
                SharedPreferences.Editor data=admin.edit();
                data.remove("estado");
                data.remove("nombre");
                data.remove("cedula");
                data.remove("tip_usuario");
                data.remove("id");
                data.remove("ap");
                data.apply();

                Intent intent = new Intent( getApplicationContext(),MainActivity.class);
                startActivity(intent);

                break;

            case R.id.notify:

                intent = new Intent(getApplicationContext(), interfaz_notificaciones.class);
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
        finishAffinity();
        System.exit(0);
    }


    @Override
    protected void onStop() {
        super.onStop();
        finishAffinity(); // se comporta bien
        //Toast.makeText(getApplicationContext(),"aqui stop", Toast.LENGTH_SHORT).show();
        //finish();  // se comporta mas o menos el detalle me reingresa en otra actividd

    }



}

