package com.example.siteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazEstadoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class interfaz_estado extends AppCompatActivity {
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
    Context ct;

    String KEY_IMAGE="foto";
    Bitmap bitmap;
    int pick_IMAGE_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_estado);
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
        ct=view.getContext();

        ////////////********************///////////////////////////////////////////
        String ip = getString(R.string.ip);
        String URL=ip+"/conexion_php/item_estados.php";

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
                                            String ip = getString(R.string.ip);
                                            String URL1 = ip+"/conexion_php/modificar_estado.php";

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
                                            VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);


                                            //////************////////
                                            ip = getString(R.string.ip);
                                            String URL11 = ip+"/conexion_php/insertar_cierre.php";

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
                                                    SharedPreferences admin=ct.getSharedPreferences("x",MODE_PRIVATE);
                                                    String id=admin.getString("id","");
                                                    parametros.put("id_tec", id);
                                                    parametros.put("id_user", idCliente );
                                                    parametros.put("id_inc", idIncidencia);
                                                    parametros.put("cierre", v30.resoluciong.getText().toString().trim());





                                                    return parametros;
                                                }
                                            };
                                            VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest11);
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
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);

        /****************************************/
        /***/

        /****APS/
         /****************************************/

        ip = getString(R.string.ip);
        String URL3 = ip+"/conexion_php/detalle_gestion_fn.php";

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
                                    gestion.getString("idd").toString() ,
                                    gestion.getString("idtec").toString()



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
                ///
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest1);



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
/*
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

 */

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

//    public  String getStringImagen(Bitmap bmp){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
//        return encodedImage;
//    }
//
//    public void uploadImage(){
//
//        String ip = getString(R.string.ip);
//        String URL03 = ip+"/conexion_php/detalle_gestion_fn.php";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL03, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("result", error.toString());
//
//            }
//
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> parametros = new HashMap<String, String>();
//                String imagen = getStringImagen(bitmap);
//                parametros.put("tipo", departamento);
//                // ojo con ese tipo y departamento es para el php de gestion de cierre
//                parametros.put("KEY_IMAGE", imagen);
//                //parametros.put("tipo", tipo);
//                return parametros;
//                ///
//            }
//        };
//        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
//
//
//    }
//
//    private void showFileChooser(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Seleccione imagen"),pick_IMAGE_REQUEST);
//    }
}
