package com.example.siteapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
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
import com.example.siteapp.databinding.ActivityInterfazTecAdmBinding;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class interfaz_tec_adm extends AppCompatActivity {

    private ActivityInterfazTecAdmBinding layout;
    private static final int Read_Permission = 101;
    ActivityResultLauncher<Intent> mTakePhoto;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_tec_adm);
        layout= ActivityInterfazTecAdmBinding.inflate(getLayoutInflater());
        View view = layout.getRoot();
        setContentView(view);


        layout.btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String ip = getString(R.string.ip);
                insertarproducto(ip + "/conexion_php/insertar_usuario.php");
            }
        });

        layout.btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),interfaz_dependiente.class);
                startActivity(intent);
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

                                AlertDialog.Builder camara = new AlertDialog.Builder(interfaz_tec_adm.this);

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
                                    layout.vimg01.setImageBitmap(bitmap);
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

        layout.vimg01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(),  Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(interfaz_tec_adm.this,
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



    }

    private void insertarproducto (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("oliver",response);
                if(response.toString().trim().equals("1")) {
                    Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                    layout.txp6.getText().clear();
                    layout.txp7.getText().clear();
                    layout.txp8.getText().clear();
                    layout.txp9.getText().clear();
                    layout.vimg01.setImageResource(R.mipmap.ic_launcher);


                } else if(response.toString().trim().equals("2")){

                    Toast.makeText(getBaseContext(), "ERROR USUARIO YA REGISTRADO ", Toast.LENGTH_SHORT).show();

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
                String imagen = getStringImagen(bitmap);
                String ip = getString(R.string.ip);
                parametros.put("ip",ip);
                //parametros.put("id".toString().toString());
                parametros.put("nombre",layout.txp6.getText().toString().trim());
                parametros.put("cedula",layout.txp7.getText().toString().trim());
                parametros.put("contrasena",layout.txp8.getText().toString().trim());
                parametros.put("telefono",layout.txp9.getText().toString().trim());
                parametros.put("tip_usuario","T");
                parametros.put("direccion","");
                parametros.put("ubicacion","");
                parametros.put("Ip","");
                parametros.put("cod_color","");
                parametros.put("ap","");
                parametros.put("group_inc","");
                parametros.put("referencia",imagen);
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private  String getStringImagen(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( getApplicationContext(),interfaz_dependiente.class);
        startActivity(intent);
    }


}