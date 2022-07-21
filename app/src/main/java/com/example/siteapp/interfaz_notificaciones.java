package com.example.siteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityGraficosBinding;
import com.example.siteapp.databinding.ActivityInterfazNotificacionesBinding;
import com.example.siteapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class interfaz_notificaciones extends General {

    private ActivityInterfazNotificacionesBinding layout;
    Context ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_notificaciones);
        layout= ActivityInterfazNotificacionesBinding.inflate(getLayoutInflater());
        View view = layout.getRoot();
        setContentView(view);


        RecyclerView list=layout.lista;
        ArrayList<list_notificacion> itemRec;

        itemRec=new ArrayList();

        String URL = "http://192.168.101.5/conexion_php/buscar_notificaciones_tip.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.isEmpty()) {
                    try {
                        JSONArray object= null;

                        object = new JSONArray(response);
                        Log.i("result","Data: "+response);

                        for(int i=0;i<object.length();i++) {
                            JSONObject notificacion = object.getJSONObject(i);

                            itemRec.add(new list_notificacion(

                                    notificacion.getString("asunto").toString(),
                                    notificacion.getString("fecha").toString(),
                                    notificacion.getString("estado").toString(),
                                    notificacion.getString("comentario").toString(),
                                    notificacion.getString("idNoti").toString(),
                                    notificacion.getString("origen").toString()

                                    )
                            );
                        }

                        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        RecyclerView.Adapter<adapter_notify.adapter_notificacion> adapter= new adapter_notify(itemRec);
                        list.setAdapter(adapter);
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Sin nuevas notificaciones", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("result",error.toString());

            }

        }){
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("id_tip_noti", String.valueOf(1));
                parametros.put("estado", "NV");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


        //////////////////////

        RecyclerView lisst=layout.lista1;
        ArrayList<list_notificacion1> itemRec1;

        itemRec1=new ArrayList();

        URL = "http://192.168.101.5/conexion_php/buscar_notificaciones_tip.php";

        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()) {
                    try {
                        JSONArray object = null;

                        object = new JSONArray(response);
                        Log.i("result", "Data: " + response);

                        for (int i = 0; i < object.length(); i++) {
                            JSONObject notificacion = object.getJSONObject(i);

                            itemRec1.add(new list_notificacion1(

                                            notificacion.getString("asunto").toString(),
                                            notificacion.getString("fecha").toString(),
                                            notificacion.getString("estado").toString(),
                                            notificacion.getString("comentario").toString(),
                                            notificacion.getString("idNoti").toString(),
                                            notificacion.getString("origen").toString()

                                    )
                            );
                        }

                        lisst.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        RecyclerView.Adapter<adapter_notify1.adapter_notificacion> adapter = new adapter_notify1(itemRec1);
                        lisst.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Sin notificaciones que mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("result", error.toString());

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_tip_noti", String.valueOf(1));
                parametros.put("estado", "V");
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.regresar,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences admin=getBaseContext().getSharedPreferences("x", Context.MODE_PRIVATE);
        String tip_usuario=admin.getString("tip_usuario","");
        Log.i("result","Data: "+tip_usuario);

        switch (item.getItemId())
        {

            case R.id.salir:

                finishAffinity();
                System.exit(0);


                break;

            case R.id.regresar:

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