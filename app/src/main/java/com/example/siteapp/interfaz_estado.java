package com.example.siteapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class interfaz_estado extends AppCompatActivity {
    private ActivityInterfazEstadoBinding v30;
    //int valor3=0;
    private Spinner sping;
    String sc;
    String idCliente;
    String idIncidencia;
    String cedula;
    String departamento;
    String tipo;
    int state;
    String stt;
    Context ct;
    private static final int Read_Permission = 101;
    ActivityResultLauncher<Intent> mTakePhoto;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_estado);
        v30 = ActivityInterfazEstadoBinding.inflate(getLayoutInflater());
        View view = v30.getRoot();
        setContentView(view);

        SharedPreferences admin=this.getSharedPreferences("x",MODE_PRIVATE);
        ct=view.getContext();

        String tip_usuario=admin.getString("tip_usuario","");

        if(tip_usuario.equals("D")) {
            sc="NS";

        }else{

            sc="SC";
        }

        gestionIncidencias();

        v30.btnescale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scale(sc);

            }
        });


        mTakePhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if(result.getResultCode() == RESULT_OK && null  != result.getData()){
                            Uri filePath = result.getData().getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);

                                AlertDialog.Builder camara = new AlertDialog.Builder(interfaz_estado.this);

                                LayoutInflater camara_imagen = LayoutInflater.from(getApplicationContext());
                                final View vista = camara_imagen.inflate(R.layout.dialog_camara, null);

                                camara.setPositiveButton("Guardar imagen", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Toast.makeText(getApplicationContext(), "Imagen guardada correctamente", Toast.LENGTH_SHORT).show();

                                    }
                                }) .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.cancel();

                                    }
                                });

                                AlertDialog image = camara.create();
                                camara.setCancelable(false);
                                image.setTitle("Imagen");
                                image.setView(vista);
                                image.show();
                                ImageView imageView = image.findViewById(R.id.imageView);
                                Objects.requireNonNull(imageView).setImageBitmap(bitmap);
                                if (imageView != null) {
                                    imageView.setImageBitmap(bitmap);
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

        v30.icono055.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(),  Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(interfaz_estado.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Read_Permission);
                    return;

                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }

                mTakePhoto.launch(intent);

            }
        });


        ///*********/////////



        idCliente = getIntent().getStringExtra("idClient");
        idIncidencia = getIntent().getStringExtra("idIncidencia");
        cedula = getIntent().getStringExtra("cedula");
        departamento = getIntent().getStringExtra("departamento");
        tipo = getIntent().getStringExtra("tipo");
//        Log.i("result", "Datagestion: " + idCliente);
//        Log.i("result", "Datagestion: " + idIncidencia);
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
                                        sc="NS";
                                        scale(sc);

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



    private  String getStringImagen(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    private void scale(String sc){

        if (v30.resoluciong.getText().toString().trim().isEmpty()) {
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
                    parametros.put("scale", sc.toString());

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

                    if (bitmap != null) {

                        String imagen = getStringImagen(bitmap);
                        parametros.put("referencia",imagen);
                    }else{
                        String kaios = "";
                        parametros.put("referencia",kaios);
                    }
                    String ip = getString(R.string.ip);
                    parametros.put("ip",ip);
                    parametros.put("id_tec", id);
                    parametros.put("cedula", cedula );
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

    private void gestionIncidencias(){
        RecyclerView lisst=v30.listg;
        ArrayList<Gestion> itemRec5;
        itemRec5=new ArrayList();

        String ip = getString(R.string.ip);
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
                                            gestion.getString("idtec").toString(),
                                            gestion.getString("referencia").toString()



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
                Log.i("resul",""+ idCliente);
                Log.i("resul",""+ idIncidencia);
                //parametros.put("tipo", tipo);
                return parametros;

                ///
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest1);
    }
}
