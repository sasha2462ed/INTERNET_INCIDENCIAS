package com.example.siteapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazMostrarIncidenciasTecnicasBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class interfaz_mostrar_incidencias_tecnicas_nivel_usuario extends AppCompatActivity {

    private ActivityInterfazMostrarIncidenciasTecnicasBinding v29;
    String idCliente;
    String idIncidencia;
    String cedula;
    String estado;
    String departamento;
    String comentario;
    String tipo;
    Context ct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_mostrar_incidencias_tecnicas);
        v29 = ActivityInterfazMostrarIncidenciasTecnicasBinding.inflate(getLayoutInflater());
        View view = v29.getRoot();
        setContentView(view);


        idCliente = getIntent().getStringExtra("idClient");
        idIncidencia = getIntent().getStringExtra("idIncidencia");
        estado = getIntent().getStringExtra("estado");
        comentario = getIntent().getStringExtra("comentario");
        cedula = getIntent().getStringExtra("cedula");
        departamento = getIntent().getStringExtra("departamento");
        tipo = getIntent().getStringExtra("tipo");
        Log.i("result0","estado:"+estado);

        Log.i("result0","estado:"+tipo);

        SharedPreferences admin=this.getSharedPreferences("x",MODE_PRIVATE);
        ct=view.getContext();

        String tip_usuario=admin.getString("tip_usuario","");

        if(tip_usuario.equals("T") || tip_usuario.equals("D")) {
            v29.btnestado.setVisibility(View.VISIBLE);
            v29.maps.setVisibility(View.VISIBLE);
        }else{

            v29.btnestado.setVisibility(View.INVISIBLE);
            v29.maps.setVisibility(View.INVISIBLE);
        }


        if(tip_usuario.equals("C") && estado.equals("Encurso")) {
            v29.btnestad.setVisibility(View.VISIBLE);
        }
        else if (tip_usuario.equals("C") && estado.equals("Receptado")) {
            v29.btnestad.setVisibility(View.VISIBLE);
            }
        else{

            v29.btnestad.setVisibility(View.INVISIBLE);
        }


        v29.btnestado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getBaseContext(),interfaz_estado.class);
                intent.putExtra("idClient",idCliente);
                intent.putExtra("idIncidencia",idIncidencia);
                intent.putExtra("cedula",cedula);
                intent.putExtra("departamento",departamento);
                intent.putExtra("tipo",tipo);
                startActivity(intent);
            }


        });




        String ip = getString(R.string.ip);
        String URL=ip+"/conexion_php/detalle.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                Log.i("result",response.toString());

                if(!response.isEmpty()) {
                    try {
                        JSONObject objUser= new JSONObject(response.toString());
                        Log.i("result","data:"+response.toString());

                        v29.tvmc1.setText(objUser.getString("nombre"));
                        v29.tvmc2.setText(objUser.getString("cedula"));
                        //v29.tvmc3.setText(objUser.getString("contrasena"));
                        v29.tvmc4.setText(objUser.getString("telefono"));
                        v29.tvmc5.setText(objUser.getString("direccion"));
                        v29.tvmc6.setText(objUser.getString("nodo")+" -- --> "+objUser.getString("ap"));
                        //v29.tvmc7.setText(objUser.getString("ap"));
                        v29.tvm9.setText(comentario);
                        v29.tvm8.setText(objUser.getString("estado"));
                        v29.tvmc05.setText(objUser.getString("ubicacion"));
                        v29.tvmc006.setText(objUser.getString("ip")+" -- --> "+objUser.getString("cod_color"));
                        String so = objUser.getString("referencia").toString();
                        String su = objUser.getString("indicio").toString();



                        if (!so.isEmpty()) {
                            v29.REF.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //cargarimagen(so);
                                    Intent intent=new Intent(getApplicationContext(),interfaz_gestion_image.class);
                                    intent.putExtra("imagen",so.toString());
                                    startActivity(intent);
                                }
                            });

                        }


                        if (!su.isEmpty()) {
                            v29.REFF.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //cargarimagen(so);
                                    Intent intent=new Intent(getApplicationContext(),interfaz_gestion_image.class);
                                    intent.putExtra("imagen",su.toString());
                                    startActivity(intent);
                                }
                            });

                        }




                                v29.maps.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
/*
                                        String Uni=v29.tvmc5.getText().toString();
                                        //new URL(Uni).openStream().close();
                                        Uri link = Uri.parse(Uni);
                                        startActivity( new Intent(Intent.ACTION_VIEW, link));

 */
                                        String Uni=v29.tvmc5.getText().toString();
                                        if(Uni.equals("")){

                                            Toast.makeText(getBaseContext(), "No hay dirección para buscar", Toast.LENGTH_SHORT).show();

                                        }else{
                                            try {
/*
                                            new URL(Uni).openStream().close();
                                            Uri link = Uri.parse(Uni);
                                            startActivity( new Intent(Intent.ACTION_VIEW, link));

 */
                                                if (URLUtil.isValidUrl(Uni.toString())) {
                                                    Uri link = Uri.parse(Uni);
                                                    startActivity( new Intent(Intent.ACTION_VIEW, link));
                                                }  else {
                                                    Toast.makeText(getBaseContext(), "No se ha encontrado la Direccion", Toast.LENGTH_SHORT).show();
                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Toast.makeText(getBaseContext(), "Direccion Invalida", Toast.LENGTH_SHORT).show();
                                            }

                                        }


                                    }
                                });

                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();

            }

        }){
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("id", idIncidencia);

                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);


        /********************************/
        v29.btnestad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = getString(R.string.ip);
                String URL= ip+"/conexion_php/modificar_estado.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("oliver",response);
                        if(response.equals("1")){
                            Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();

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
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        //parametros.put("id".toString().toString());
                        parametros.put("cedula", cedula.toString());
                        parametros.put("estado", String.valueOf(3));
                        parametros.put("idIncidencia",idIncidencia);

                        return parametros;
                    }
                };
                VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);

            }


        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.regresar,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.salir:


                finishAffinity();
                System.exit(0);

                break;


            }

        return super.onOptionsItemSelected(item);
    }


}