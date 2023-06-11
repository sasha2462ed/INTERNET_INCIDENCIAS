package com.example.siteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazIncAdmBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class interfaz_inc_adm extends AppCompatActivity {

    ActivityInterfazIncAdmBinding layout;
    private Spinner spin_adm;
    String statenod;
    int ga;
    String busca;
    String C0;
    String C1;
    String C2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_inc_adm);
        layout = ActivityInterfazIncAdmBinding.inflate(getLayoutInflater());
        View view = layout.getRoot();
        setContentView(view);

        layout.v05.setVisibility(View.INVISIBLE);
        layout.spinAdm.setVisibility(View.INVISIBLE);
        layout.txp4adm.setVisibility(View.INVISIBLE);
        layout.btn4adm.setVisibility(View.INVISIBLE);

        layout.icono89.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busca=layout.v06.getText().toString();
                buscar(busca);
            }
        });

        layout.btn6adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), interfaz_dependiente.class);
                startActivity(intent);
            }
        });


    }

    private void buscar(String busca){
        String ip = getString(R.string.ip);
        String URL = ip+"/conexion_php/buscar_usuario.php?cedula="+ busca;

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    Log.i("responseimagen", "Array: " + response.toString());

                    try {

                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            jsonObject = response.getJSONObject(i);
                            C0=jsonObject.getString("id");
                            C1=jsonObject.getString("idap");
                            C2=jsonObject.getString("cedula");


                            layout.v05.setVisibility(View.VISIBLE);
                            layout.spinAdm.setVisibility(View.VISIBLE);
                            layout.txp4adm.setVisibility(View.VISIBLE);
                            layout.btn4adm.setVisibility(View.VISIBLE);

                            insertar_list(C0,C1,C2);



                        }



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Sin incidencias que mostrar", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(getApplicationContext(), "Usuario no registrado",Toast.LENGTH_SHORT).show();



                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Error de conexion",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "USUARIO NO EXISTE",Toast.LENGTH_SHORT).show();

            }
        });
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonArrayRequest);

    }

    private void insertar_list(String C0, String C1, String C2){
        String ip = getString(R.string.ip);
        String URL=ip+"/conexion_php/list_inc.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray nodos=new JSONArray(response);

                    JSONArray id=new JSONArray(nodos.get(0).toString());
                    JSONArray name=new JSONArray(nodos.get(1).toString());

                    spin_adm = layout.spinAdm;
                    String[] opciones = new String[name.length()];

                    JSONObject nods=new JSONObject();


                    for (int i=0;i<name.length();i++){
                        opciones[i]=name.get(i).toString();
                        nods.put(name.get(i).toString(), id.get(i).toString());
                    }

                    ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_estado, opciones);

                    spin_adm.setAdapter(adapter7);


                    spin_adm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            statenod = spin_adm.getItemAtPosition(position).toString();
                            Log.i("result2",statenod);


                            try {
                                Log.i("result1",nods.getString(statenod));

                                ga = Integer.parseInt(String.valueOf(nods.getString(statenod)));
                                Log.i("result5", String.valueOf(ga));

                                String ip = getString(R.string.ip);
                                String URL = ip+"/conexion_php/buscar_item_list.php?list="+ ga;

                                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("oliver",response);
                                        if(!response.isEmpty()){


                                            layout.btn4adm.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    insert_inc(response.toString(),C0,C1,C2);
                                                }
                                            });


                                        }else{
                                            Toast.makeText(getBaseContext(), "OPERACION FALLIDA ", Toast.LENGTH_SHORT).show();


                                        }

                                    }

                                }, new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }){
                                    @Override
                                    protected Map<String, String> getParams () throws AuthFailureError {
                                        Map<String,String> parametros = new HashMap<String, String>();
                                        //parametros.put("id".toString().toString());

                                        return parametros;
                                    }
                                };
                                VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);


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

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void insert_inc(String response, String C0, String C1, String C2){
        String ip = getString(R.string.ip);
        String URL= ip+"/conexion_php/insertar_incidencias.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String respons) {
                Log.i("oliver",respons);
                if(respons.equals("1")){
                    Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                    layout.txp4adm.getText().clear();
                    layout.v05.setVisibility(View.INVISIBLE);
                    layout.spinAdm.setVisibility(View.INVISIBLE);
                    layout.txp4adm.setVisibility(View.INVISIBLE);
                    layout.btn4adm.setVisibility(View.INVISIBLE);

                }else{
                    Toast.makeText(getBaseContext(), "OPERACION FALLIDA ", Toast.LENGTH_SHORT).show();



                }

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                //parametros.put("id".toString().toString());


                parametros.put("tipoo", String.valueOf(ga));
                parametros.put("departamento", String.valueOf(response.toString()));
                parametros.put("comentario",layout.txp4adm.getText().toString());
                parametros.put("id_usuarios", C0);
                parametros.put("ap", C1);
                parametros.put("cedula", C2);
                String ip = getString(R.string.ip);
                parametros.put("ip",ip);
                parametros.put("referencia", "");
                parametros.put("group_inc","");
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent( getApplicationContext(),interfaz_dependiente.class);
        startActivity(intent);
    }
}