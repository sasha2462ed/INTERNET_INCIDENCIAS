package com.example.siteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityInterfazDepartamentoAdministrativoBinding;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class interfaz_departamento_administrativo extends AppCompatActivity {
    String trampa;
    private ActivityInterfazDepartamentoAdministrativoBinding v4;
    RequestQueue requestQueue;
    DatePickerDialog.OnDateSetListener setListener1;
    int val;
    String valor;
    private ListView lv2;
    Context ct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_departamento_administrativo);
        v4 = ActivityInterfazDepartamentoAdministrativoBinding.inflate(getLayoutInflater());
        View view = v4.getRoot();
        setContentView(view);
        trampa = getIntent().getStringExtra("trampa");
///***********************************************************////

        ct=view.getContext();
        String ip = getString(R.string.ip);
        String URL=ip+"/conexion_php/listadm.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray nodos = new JSONArray(response);

                    JSONArray id = new JSONArray(nodos.get(0).toString());
                    JSONArray name = new JSONArray(nodos.get(1).toString());

                    lv2 = (ListView) findViewById(R.id.lv2);
                    String[] problemas1 = new String[name.length()];

                    JSONObject nods = new JSONObject();


                    for (int i = 0; i < name.length(); i++) {
                        problemas1[i] = name.get(i).toString();
                        nods.put(name.get(i).toString(), id.get(i).toString());
                    }


                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_items, problemas1);
                    lv2.setAdapter(adapter1);



                    lv2.setOnItemClickListener (new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            valor=lv2.getItemAtPosition(position).toString();
                           // stateap = spinnergrfap.getSelectedItem().toString();


                            final String[] valor1 = {""};
                            valor1[0] = problemas1[position];
                            Toast.makeText(getApplicationContext(),"Su incidencia es " + valor1[0], Toast.LENGTH_SHORT).show();


                            Log.i("resultap",valor1[0]);


                            //////////*////////////////

                            try{
                                val = Integer.parseInt(String.valueOf(nods.getString(valor)));
                                Log.i("resultap", String.valueOf(val));

                                v4.btn4adm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String ip = getString(R.string.ip);
                                        String URL= ip+"/conexion_php/insertar_incidencias.php";

                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.i("oliver",response);
                                                if(response.equals("1")){
                                                    Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                                                    v4.txp4adm.getText().clear();


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
                                                parametros.put("tipoo", String.valueOf(val));
                                                Log.i("olivertipo", String.valueOf(val));
                                                parametros.put("departamento", String.valueOf(2));
                                                parametros.put("comentario",v4.txp4adm.getText().toString());
                                                SharedPreferences admin=ct.getSharedPreferences("x",MODE_PRIVATE);
                                                String id=admin.getString("id","");
                                                String ap=admin.getString("ap","");
                                                parametros.put("id_usuarios", id);
                                                Log.i("oliverid",id);
                                                parametros.put("ap", ap);
                                                Log.i("oliverap",ap);
                                                return parametros;
                                            }
                                        };
                                        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


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

/////*******************************************************//////

        v4.btn6adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences admin=getBaseContext().getSharedPreferences("x", Context.MODE_PRIVATE);
                String tip_usuario=admin.getString("tip_usuario","");

                if (tip_usuario.equals("C")){

                    Intent intent = new Intent( getApplicationContext(),interfaz_usuario.class);
                    startActivity(intent);

                }

                else if (tip_usuario.equals("D")){

                    Intent intent = new Intent( getApplicationContext(),interfaz_dependiente.class);
                    startActivity(intent);


                }
            }
        });

        v4.btn7adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

    }
}
