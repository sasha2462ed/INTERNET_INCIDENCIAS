package com.example.siteapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazEscalableAdmBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class interfaz_escalable_adm extends AppCompatActivity {


    ActivityInterfazEscalableAdmBinding layout;
    RecyclerView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_escalable_adm);

        layout=ActivityInterfazEscalableAdmBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());

        String ip = getString(R.string.ip);

        RecyclerView lista =layout.lista;
        ArrayList<Incidencias> itemRec;

        itemRec=new ArrayList();

        String URL = ip+"/conexion_php/buscar_incidencias_adm.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.isEmpty()) {
                    try {
                        JSONArray object= null;

                        object = new JSONArray(response);

                        for(int i=0;i<object.length();i++) {
                            JSONObject indicencia = object.getJSONObject(i);

                            itemRec.add(new Incidencias(
                                            indicencia.getString("idIncidencias"),
                                            indicencia.getString("tipo").toString(),
                                            indicencia.getString("comentario").toString(),
                                            indicencia.getString("hora").toString(),
                                            indicencia.getString("estado").toString(),
                                            indicencia.getString("id").toString(),
                                            indicencia.getString("cedula").toString(),
                                            indicencia.getString("departamento").toString() ,
                                            indicencia.getString("lp").toString()

                                    )
                            );
                        }

                        lista.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        RecyclerView.Adapter<myAdapter.ContenetViews> adapter= new myAdapter(itemRec);
                        adapter.notifyDataSetChanged();
                        lista.setAdapter(adapter);
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Sin incidencias que mostrar", Toast.LENGTH_SHORT).show();
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
                parametros.put("scale", "SC");

                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);



    }
}