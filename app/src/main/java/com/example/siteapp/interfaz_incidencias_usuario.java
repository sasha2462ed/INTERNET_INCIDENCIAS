package com.example.siteapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazIncidenciasUsuarioBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class interfaz_incidencias_usuario extends AppCompatActivity {
    private ActivityInterfazIncidenciasUsuarioBinding v030;

    String idCliente;
    String idIncidencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v030 = ActivityInterfazIncidenciasUsuarioBinding.inflate(getLayoutInflater());
        View view = v030.getRoot();
        setContentView(view);
        idCliente = getIntent().getStringExtra("idClient");
        idIncidencia = getIntent().getStringExtra("idIncidencia");



        RecyclerView list=v030.list;
        ArrayList<Gestion> itemRec05;
        itemRec05=new ArrayList();

        String ip = getString(R.string.ip);
        String URL = ip+"/conexion_php/detalle_gestionn.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()) {
                    try {
                        JSONArray object= null;

                        object = new JSONArray(response);
                        Log.i("result","Data: "+response);
                        for(int i=0;i<object.length();i++) {
                            JSONObject gestion = object.getJSONObject(i);

                            itemRec05.add(new Gestion(
                                            gestion.getString("cierre").toString(),
                                            gestion.getString("tipo").toString(),
                                            gestion.getString("fecha").toString(),
                                            gestion.getString("idd").toString(),
                                    gestion.getString("idtec").toString(),
                                    gestion.getString("referencia").toString()



                                    )
                            );
                        }


                        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        RecyclerView.Adapter<myGestion.Contenet> adapter504= new myGestion(itemRec05);
                        list.setAdapter(adapter504);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Sin resoluciones que mostrar", Toast.LENGTH_SHORT).show();
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
                parametros.put("id_inc", idIncidencia);
                return parametros;
                ///
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);



    }

}