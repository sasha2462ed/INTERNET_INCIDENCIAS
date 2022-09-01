package com.example.siteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityInterfazTecnicoUsuarioBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class interfaz_tecnico_usuario extends AppCompatActivity {
    private ActivityInterfazTecnicoUsuarioBinding v5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_tecnico_usuario);
        v5 = ActivityInterfazTecnicoUsuarioBinding.inflate(getLayoutInflater());
        View view = v5.getRoot();
        setContentView(view);


        v5.icono506.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ip = getString(R.string.ip);
                String URL2=ip+"/conexion_php/listap.php";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL2, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray strs = null;
                        JSONArray strss = null;


                        try {

                            strss = response.getJSONArray(0);
                            strs = response.getJSONArray(1);


                            String op = strss.toString();
                            op = strss.toString().replace(",", " ");
                            op = op.replace("[", "");
                            op = op.replace("]", "");
                            String[] z = op.split(" ");
                            Log.i("z", "Array: " + Arrays.toString(z));

                            String ab = strs.toString();
                            ab = strs.toString().replace(",", " ");
                            ab = ab.replace("[", "");
                            ab = ab.replace("]", "");
                            ab = ab.replace("\"", "");
                            String[] v = ab.split(" ");
                            Log.i("v", "Array: " + Arrays.toString(v));


                            String[] hp = new String[strs.length()];
                            int i ;
                            for ( i = 0; i < hp.length; i++) {
                                hp[i] = Arrays.toString(new String[]{z[i] + " --> " + v[i]});
                                hp[i] = hp[i].replace("[", "");
                                hp[i] = hp[i].replace("]", "");

                            }


                            //String [] hp = new String[] {z[0] +" --> "+ v[0],z[1] +" --> "+ v[1],z[2] +" --> "+ v[2]};
                            // String [] hp = new String[] {Arrays.toString(z.clone()) +" --> "+ Arrays.toString(v.clone())};
                            Log.i("resulhp", "Array: " + Arrays.toString(hp));

                            AlertDialog.Builder info = new AlertDialog.Builder(interfaz_tecnico_usuario.this);

                            info.setItems(hp, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });


                            info.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog inf = info.create();
                            info.setCancelable(false);
                            inf.setTitle("APs");

                            inf.show();


                        } catch (Exception e) {
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

                VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonArrayRequest);




            }
        });

        v5.btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v5.txp6.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Campo nombre vacio",Toast.LENGTH_SHORT).show();
                } else {
                    if (v5.txp7.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Campo cedula vacio",Toast.LENGTH_SHORT).show();

                    }else {
                        if (v5.txp8.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Campo contrasena vacio", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            if (v5.txp9.getText().toString().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Campo telefono vacio", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                if (v5.txp10.getText().toString().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Campo direccion vacio", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    if (v5.txp12.getText().toString().isEmpty()) {
                                        Toast.makeText(getApplicationContext(), "Campo ap vacio", Toast.LENGTH_SHORT).show();

                                    }
                                    else {

                                        String ip = getString(R.string.ip);
                                        insertarproducto(ip+"/conexion_php/insertar_usuario.php");

                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        v5.btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),interfaz_consultar.class);
                intent.putExtra("buscar", v5.txp7.getText()+"");
                startActivity(intent);

            }
        });


        v5.btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),interfaz_tecnico.class);
                startActivity(intent);
            }
        });

        v5.btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void insertarproducto (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("oliver",response);
                if(response.equals("1")){
                    Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                    v5.txp6.getText().clear();
                    v5.txp7.getText().clear();
                    v5.txp8.getText().clear();
                    v5.txp9.getText().clear();
                    v5.txp10.getText().clear();
                    v5.txp12.getText().clear();


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
                parametros.put("nombre",v5.txp6.getText().toString().trim());
                parametros.put("cedula",v5.txp7.getText().toString().trim());
                parametros.put("contrasena",v5.txp8.getText().toString().trim());
                parametros.put("telefono",v5.txp9.getText().toString().trim());
                parametros.put("direccion",v5.txp10.getText().toString().trim());
                parametros.put("ap",v5.txp12.getText().toString().trim());
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }


}