package com.example.siteapp;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazDepartamentoAdministrativoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class interfaz_departamento_administrativo extends AppCompatActivity {

    private ActivityInterfazDepartamentoAdministrativoBinding v4;

    int val;
    String valor;
    private ListView lv2;
    Context ct;
    private static final int Read_Permission = 101;
    ActivityResultLauncher<Intent> mTakePhoto;
    Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_departamento_administrativo);
        v4 = ActivityInterfazDepartamentoAdministrativoBinding.inflate(getLayoutInflater());
        View view = v4.getRoot();
        setContentView(view);
        ct=view.getContext();


        addList();


        mTakePhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if(result.getResultCode() == RESULT_OK && null  != result.getData()){
                            Uri filePath = result.getData().getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);

                                AlertDialog.Builder camara = new AlertDialog.Builder(interfaz_departamento_administrativo.this);

                                LayoutInflater camara_imagen = LayoutInflater.from(getApplicationContext());
                                final View vista = camara_imagen.inflate(R.layout.dialog_camara, null);

                                camara.setPositiveButton("Guardar imagen", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Toast.makeText(getApplicationContext(), "Imagen insertada", Toast.LENGTH_SHORT).show();

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


        v4.icono89.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(),  Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(interfaz_departamento_administrativo.this,
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


        v4.btn6adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent( getApplicationContext(),interfaz_usuario.class);
                    startActivity(intent);


            }
        });


   }



    private  String getStringImagen(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    private void insertList(){
        String ip = getString(R.string.ip);
        String URL= ip+"/conexion_php/insertar_incidencias.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("oliver",response);
                if(response.equals("1")){
                    Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                    v4.txp4adm.getText().clear();
                    bitmap = null;


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

                if (bitmap != null) {

                    String imagen = getStringImagen(bitmap);
                    parametros.put("referencia",imagen);
                }else{
                    String kaios = "";
                    parametros.put("referencia",kaios);
                }
                String ip = getString(R.string.ip);
                parametros.put("ip",ip);
                parametros.put("tipoo", String.valueOf(val));
                Log.i("olivertipo", String.valueOf(val));
                parametros.put("departamento", String.valueOf(2));
                parametros.put("comentario",v4.txp4adm.getText().toString());
                SharedPreferences admin=ct.getSharedPreferences("x",MODE_PRIVATE);
                String id=admin.getString("id","");
                String ap=admin.getString("ap","");
                String cedula=admin.getString("cedula","");
                parametros.put("id_usuarios", id);
                Log.i("oliverid",id);
                parametros.put("ap", ap);
                Log.i("oliverap",ap);
                parametros.put("cedula", cedula);
                parametros.put("group_inc","");
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void addList(){
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

                                        insertList();


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
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), interfaz_usuario.class);
        startActivity(intent);
    }

}
