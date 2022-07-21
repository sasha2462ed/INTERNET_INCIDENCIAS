package com.example.siteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityDepartamentoAdministrativoBinding;
import com.example.siteapp.databinding.ActivityDepartamentoTecnicoBinding;
import com.example.siteapp.databinding.ActivityInterfazEstadoBinding;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class interfaz_estado extends General {
    private ActivityInterfazEstadoBinding v30;
    //int valor3=0;
    private Spinner sping;
    String idCliente;
    String idIncidencia;
    String cedula;
    String departamento;
    String tipo;
    int state;
    String stt;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_estado);
        v30 = ActivityInterfazEstadoBinding.inflate(getLayoutInflater());
        View view = v30.getRoot();
        setContentView(view);

        RecyclerView lisst=v30.listg;
        ArrayList<Gestion> itemRec5;
        itemRec5=new ArrayList();


        idCliente = getIntent().getStringExtra("idClient");
        idIncidencia = getIntent().getStringExtra("idIncidencia");
        cedula = getIntent().getStringExtra("cedula");
        departamento = getIntent().getStringExtra("departamento");
        tipo = getIntent().getStringExtra("tipo");
        Log.i("result", "Datagestion: " + idCliente);
        Log.i("result", "Datagestion: " + idIncidencia);

///////////////////////////*************************////////////////////////

        Log.i("result","Data: "+departamento);
        //parametros.put("cierre", v30.resoluciong.getText().toString());


        ///////////**********************//////////////////
        v30.icono05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
/******************/////////////


        ////////////********************///////////////////////////////////////////

        String URL="http://192.168.101.5/conexion_php/item_estados.php";

        //parametros.put("id".toString().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray nodos = new JSONArray(response);

                    JSONArray id = new JSONArray(nodos.get(0).toString());
                    JSONArray name = new JSONArray(nodos.get(1).toString());

                    sping = v30.sping;
                    String[] opciones = new String[name.length()];

                    JSONObject nods = new JSONObject();


                    for (int i = 0; i < name.length(); i++) {
                        opciones[i] = name.get(i).toString();
                        nods.put(name.get(i).toString(), id.get(i).toString());
                    }

                    ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_estado, opciones);

                    sping.setAdapter(adapter7);


                    sping.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            stt = sping.getItemAtPosition(position).toString();


                            try {
                                state = Integer.parseInt(String.valueOf(nods.getString(stt)));
                                v30.btnstate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        if (v30.resoluciong.getText().toString().isEmpty()) {
                                            Toast.makeText(getApplicationContext(), "Campo resolucion vacio", Toast.LENGTH_SHORT).show();

                                        }
                                        else {

                                            String URL1 = "http://192.168.101.5/conexion_php/modificar_estado.php";

                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.i("oliver", response);
                                                    if (response.equals("1")) {
                                                        //Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();


                                                    } else {
                                                        //Toast.makeText(getApplicationContext(), "OPERACION FALLIDA ", Toast.LENGTH_SHORT).show();

                                                    }

                                                }

                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                                }

                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> parametros = new HashMap<String, String>();
                                                    //parametros.put("id".toString().toString());
                                                    parametros.put("estado", String.valueOf(state));
                                                    parametros.put("idIncidencia", idIncidencia);

                                                    return parametros;
                                                }
                                            };
                                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                                            requestQueue.add(stringRequest);


                                            //////************////////
                                            String URL11 = "http://192.168.101.5/conexion_php/insertar_cierre.php";

                                            StringRequest stringRequest11 = new StringRequest(Request.Method.POST, URL11, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.i("oliver", response);
                                                    if (response.equals("1")) {
                                                        Toast.makeText(getApplicationContext(), "Resolucion agregada", Toast.LENGTH_SHORT).show();
                                                        v30.resoluciong.getText().clear();


                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Resolucion no agregada ", Toast.LENGTH_SHORT).show();

                                                    }

                                                }

                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                                }

                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> parametros = new HashMap<String, String>();
                                                    //parametros.put("id".toString().toString());
                                                    parametros.put("id_user", idCliente );
                                                    parametros.put("id_inc", idIncidencia);
                                                    parametros.put("cierre", v30.resoluciong.getText().toString().trim());





                                                    return parametros;
                                                }
                                            };
                                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                                            requestQueue.add(stringRequest11);
                                            ///////*********////////




                                        }




                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        /****************************************/
        /***/

        /****APS/
         /****************************************/


        String URL3 = "http://192.168.101.5/conexion_php/detalle_gestionn.php";

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()) {
                    try {
                        JSONArray object= null;

                        object = new JSONArray(response);
                        Log.i("result","Data: "+response);
                        for(int i=0;i<object.length();i++) {
                            JSONObject gestion = object.getJSONObject(i);

                            itemRec5.add(new Gestion(
                                            gestion.getString("cierre").toString(),
                                            gestion.getString("tipo").toString(),
                                            gestion.getString("fecha").toString(),
                                    gestion.getString("idd").toString()



                                    )
                            );
                        }


                        lisst.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        RecyclerView.Adapter<myGestion.Contenet> adapter54= new myGestion(itemRec5);
                        lisst.setAdapter(adapter54);

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
                parametros.put("tipo", departamento);
                // ojo con ese tipo y departamento es para el php de gestion de cierre
                parametros.put("id_usuarios", idCliente);
                parametros.put("idIncidencia", idIncidencia);
                //parametros.put("tipo", tipo);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest1);



        //return false;


        /****************************************/
        /***/

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

        switch (item.getItemId())
        {

            case R.id.salir:


                finishAffinity();
                System.exit(0);


                break;

            case R.id.regresar:

                if(tip_usuario.equals("C")){

                    Intent intent = new Intent( getApplicationContext(),interfaz_mostrar_incidencias_usuario.class);
                    startActivity(intent);

                }

                else if (tip_usuario.equals("T")){

                    Intent intent = new Intent( getApplicationContext(),interfaz_mostrar_incidencias_nivel_tecnico.class);
                    startActivity(intent);


                }else {

                    Intent intent = new Intent( getApplicationContext(),interfaz_mostrar_incidencias_nivel_tecnico.class);
                    startActivity(intent);
                }

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
