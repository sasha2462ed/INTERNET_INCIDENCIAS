package com.example.siteapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityInterfazConsultarBinding;
import com.example.siteapp.databinding.ActivityInterfazTecnicoUsuarioBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class interfaz_consultar extends AppCompatActivity {
    private ActivityInterfazConsultarBinding v02;
    String buscar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_consultar);
        v02 = ActivityInterfazConsultarBinding.inflate(getLayoutInflater());
        View view = v02.getRoot();
        setContentView(view);

        buscar = getIntent().getStringExtra("buscar");

        v02.tvc1.setEnabled(false);
        //v02.tvc2.setEnabled(false);
        v02.tvc3.setEnabled(false);
        v02.tvc4.setEnabled(false);
        v02.tvc5.setEnabled(false);
        v02.tvc6.setEnabled(false);
        v02.tvc7.setEnabled(false);


        v02.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v02.switch1.isChecked()){
                    v02.tvc1.setEnabled(true);
                    //v02.tvc2.setEnabled(true);
                    v02.tvc3.setEnabled(true);
                    v02.tvc4.setEnabled(true);
                    v02.tvc5.setEnabled(true);
                    v02.tvc6.setEnabled(true);
                    v02.tvc7.setEnabled(true);
                }else{
                    v02.tvc1.setEnabled(false);
                    //v02.tvc2.setEnabled(false);
                    v02.tvc3.setEnabled(false);
                    v02.tvc4.setEnabled(false);
                    v02.tvc5.setEnabled(false);
                    v02.tvc6.setEnabled(false);
                    v02.tvc7.setEnabled(false);
                }


            }
        });


        String ip = getString(R.string.ip);
        String URL = ip+"/conexion_php/buscar_usuario.php?cedula="+ buscar;

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    try {

                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            jsonObject = response.getJSONObject(i);
                            v02.tvc1.setText(jsonObject.getString("nombre"));
                            v02.tvc2.setText(jsonObject.getString("cedula"));
                            v02.tvc3.setText(jsonObject.getString("contrasena"));
                            v02.tvc4.setText(jsonObject.getString("telefono"));
                            v02.tvc5.setText(jsonObject.getString("direccion"));
                            v02.tvc6.setText(jsonObject.getString("nodo"));
                            v02.tvc7.setText(jsonObject.getString("ap"));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Sin incidencias que mostrar", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(getApplicationContext(), "Usuario no registrado",Toast.LENGTH_SHORT).show();
//                    v02.tvc1.getText().clear();
//                    v02.tvc2.getText().clear();
//                    v02.tvc3.getText().clear();
//                    v02.tvc4.getText().clear();
//                    v02.tvc5.getText().clear();
//                    v02.tvc6.getText().clear();


                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Error de conexion",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Usuario no registrado",Toast.LENGTH_SHORT).show();

            }
        });
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonArrayRequest);

/*************************//////////////////////////////

        v02.btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = getString(R.string.ip);
                String URL=ip+"/conexion_php/modificar_usuario.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("oliver",response);
                        if(response.equals("1")){
                            Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                            v02.tvc1.getText().clear();
                            v02.tvc2.setText("");
                            v02.tvc3.getText().clear();
                            v02.tvc4.getText().clear();
                            v02.tvc5.getText().clear();
                            v02.tvc6.getText().clear();
                            v02.tvc7.getText().clear();

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
                        parametros.put("nombre",v02.tvc1.getText().toString().trim());
                        parametros.put("cedula",v02.tvc2.getText().toString().trim());
                        parametros.put("contrasena",v02.tvc3.getText().toString().trim());
                        parametros.put("telefono",v02.tvc4.getText().toString().trim());
                        parametros.put("direccion",v02.tvc5.getText().toString().trim());
                        parametros.put("ap",v02.tvc7.getText().toString().trim());
                        return parametros;
                    }
                };
                VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);

            }
        });



////////////////**********/////////////////////////////////
    }
}