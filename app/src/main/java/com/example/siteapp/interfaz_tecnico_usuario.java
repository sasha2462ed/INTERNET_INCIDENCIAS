package com.example.siteapp;

//import static com.example.siteapp.R.id.imageView;

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
import android.widget.GridView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazTecnicoUsuarioBinding;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class interfaz_tecnico_usuario extends AppCompatActivity {
    private ActivityInterfazTecnicoUsuarioBinding v5;

    private static final int Read_Permission = 101;
    ActivityResultLauncher<Intent> mTakePhoto;
    Bitmap bitmap;
    Uri imageUri;
    GridView gvImagenes;

    List<Uri> listaImagenes = new ArrayList<>();
    List<String> listaBase64Imagenes = new ArrayList<>();

    GridViewAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_tecnico_usuario);
        v5 = ActivityInterfazTecnicoUsuarioBinding.inflate(getLayoutInflater());
        View view = v5.getRoot();
        setContentView(view);


        mTakePhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if(result.getResultCode() == RESULT_OK && null  != result.getData()){
                            Uri filePath = result.getData().getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);

                                AlertDialog.Builder camara = new AlertDialog.Builder(interfaz_tecnico_usuario.this);

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


                v5.icono89.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(ContextCompat.checkSelfPermission(getApplicationContext(),  Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(interfaz_tecnico_usuario.this,
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
                            inf.setTitle("ID-->APs");

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

                if (v5.txp6.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Campo nombre vacio",Toast.LENGTH_SHORT).show();
                } else {
                    if (v5.txp7.getText().toString().trim().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Campo cedula vacio",Toast.LENGTH_SHORT).show();

                    }else {
                        if (v5.txp8.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Campo contrasena vacio", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            if (v5.txp9.getText().toString().trim().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Campo telefono vacio", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                if (v5.txp10.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Campo direccion vacio", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    if (v5.txp12.getText().toString().trim().isEmpty()) {
                                        Toast.makeText(getApplicationContext(), "Campo ap vacio", Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        if (v5.tx13.getText().toString().trim().isEmpty()) {
                                            Toast.makeText(getApplicationContext(), "Campo referencia vacio", Toast.LENGTH_SHORT).show();

                                        }else {

                                            String ip = getString(R.string.ip);
                                            insertarproducto(ip + "/conexion_php/insertar_usuario.php");
                                        }

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

                if(v5.txp7.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Consulta Vacia", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), interfaz_consultar.class);
                    intent.putExtra("buscar", v5.txp7.getText().toString());
                    startActivity(intent);
                }
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
                finishAffinity();
                System.exit(0);
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
                    v5.txp6.getText().clear();
                    v5.txp7.getText().clear();
                    v5.txp8.getText().clear();
                    v5.txp9.getText().clear();
                    v5.txp10.getText().clear();
                    v5.txp12.getText().clear();
                    v5.tx13.getText().clear();

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
                parametros.put("nombre",v5.txp6.getText().toString().trim());
                parametros.put("cedula",v5.txp7.getText().toString().trim());
                parametros.put("contrasena",v5.txp8.getText().toString().trim());
                parametros.put("telefono",v5.txp9.getText().toString().trim());
                parametros.put("direccion",v5.txp10.getText().toString().trim());
                parametros.put("ap",v5.txp12.getText().toString().trim());
                parametros.put("ubicacion",v5.tx13.getText().toString().trim());
                parametros.put("referencia",imagen);
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
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

        private  String getStringImagen(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }
//    public void subirImagenes() {
//
//        listaBase64Imagenes.clear();
//
//        for(int i = 0 ; i < listaImagenes.size() ; i++) {
//            try {
//                InputStream is = getContentResolver().openInputStream(listaImagenes.get(i));
//                Bitmap bitmap = BitmapFactory.decodeStream(is);
//
//                String cadena = convertirUriToBase64(bitmap);
//
//                //enviarImagenes("nomIma"+i, cadena);
//
//                bitmap.recycle();
//
//            } catch (IOException e) { }
//
//        }
//    }
//
//    private String convertirUriToBase64(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] bytes = baos.toByteArray();
//        String encode = Base64.encodeToString(bytes, Base64.DEFAULT);
//
//        return encode;
//    }

}