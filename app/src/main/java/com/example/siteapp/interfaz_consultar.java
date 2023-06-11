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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazConsultarBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class interfaz_consultar extends AppCompatActivity {
    private ActivityInterfazConsultarBinding v02;
    String buscar;
    private static final int Read_Permission = 101;
    ActivityResultLauncher<Intent> mTakePhoto;
    Bitmap bitmap;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_consultar);
        v02 = ActivityInterfazConsultarBinding.inflate(getLayoutInflater());
        View view = v02.getRoot();
        setContentView(view);

        mTakePhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if(result.getResultCode() == RESULT_OK && null  != result.getData()){
                            Uri filePath = result.getData().getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);

                                AlertDialog.Builder camara = new AlertDialog.Builder(interfaz_consultar.this);

                                LayoutInflater camara_imagen = LayoutInflater.from(getApplicationContext());
                                final View vista = camara_imagen.inflate(R.layout.dialog_camara, null);

                                camara.setPositiveButton("Guardar imagen", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String ip = getString(R.string.ip);
                                        modificar_imagen(ip + "/conexion_php/modificar_imagen.php");

                                    }
                                }) .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

                                AlertDialog image = camara.create();
                                camara.setCancelable(false);
                                image.setTitle("Imagen");
                                image.setView(vista);
                                image.show();
                                ImageView imageView = image.findViewById(R.id.imageView);
                                Objects.requireNonNull(imageView).setImageBitmap(bitmap);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

        /////********//

        buscar = getIntent().getStringExtra("buscar");

        v02.tvc1.setEnabled(false);
        //v02.tvc2.setEnabled(false);
        v02.tvc3.setEnabled(false);
        v02.tvc4.setEnabled(false);
        v02.tvc5.setEnabled(false);
        v02.tvc6.setEnabled(false);
        v02.tvc7.setEnabled(false);
        v02.tx131.setEnabled(false);
        v02.tvc006.setEnabled(false);
        v02.tvc0061.setEnabled(false);





        String ip = getString(R.string.ip);
        String URL = ip+"/conexion_php/buscar_usuario.php?cedula="+ buscar;

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    Log.i("responseimagen", "Array: " + response.toString());

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
                            v02.tx131.setText(jsonObject.getString("ubicacion"));
                            v02.tvc0061.setText(jsonObject.getString("ip"));
                            v02.tvc006.setText(jsonObject.getString("cod_color"));

                            String so = jsonObject.getString("referencia").toString();
                            Log.i("responseimagen", "Array: " + so.toString());

                            v02.icono055.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cargarimagen(so);
                                }
                            });


                            JSONObject finalJsonObject = jsonObject;
                            v02.switch1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    v02.tvc7.setText("");

                                    try {
                                        v02.tvc7.setText(finalJsonObject.getString("idap"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    if(v02.switch1.isChecked()){
                                        v02.tvc1.setEnabled(true);
                                        //v02.tvc2.setEnabled(true);
                                        v02.tvc3.setEnabled(true);
                                        v02.tvc4.setEnabled(true);
                                        v02.tvc5.setEnabled(true);
                                        //v02.tvc6.setEnabled(true);
                                        v02.tvc7.setEnabled(true);
                                        v02.tx131.setEnabled(true);
                                        v02.tvc006.setEnabled(true);
                                        v02.tvc0061.setEnabled(true);

                                    }else{
                                        v02.tvc1.setEnabled(false);
                                        //v02.tvc2.setEnabled(false);
                                        v02.tvc3.setEnabled(false);
                                        v02.tvc4.setEnabled(false);
                                        v02.tvc5.setEnabled(false);
                                        //v02.tvc6.setEnabled(false);
                                        v02.tvc7.setEnabled(false);
                                        v02.tx131.setEnabled(false);
                                        v02.tvc006.setEnabled(false);
                                        v02.tvc0061.setEnabled(false);
                                    }


                                }
                            });



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
//                            v02.tvc1.getText().clear();
//                            v02.tvc2.setText("");
//                            v02.tvc3.getText().clear();
//                            v02.tvc4.getText().clear();
//                            v02.tvc5.getText().clear();
//                            v02.tvc6.setText("");
//                            v02.tvc7.getText().clear();
//
//                            Intent intent = new Intent( getApplicationContext(), interfaz_tecnico_usuario.class);
//                            startActivity(intent);

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
                        parametros.put("ubicacion",v02.tx131.getText().toString().trim());
                        parametros.put("ap",v02.tvc7.getText().toString().trim());
                        parametros.put("ip",v02.tvc0061.getText().toString().trim());
                        parametros.put("cod_color",v02.tvc006.getText().toString().trim());
                        return parametros;
                    }
                };
                VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);

            }
        });

/////////////////////////////////////////////////////




        v02.icono505.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String URL2=ip+"/conexion_php/listap.php";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL2, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray str = null;
                        JSONArray strs = null;
                        JSONArray strss = null;


                        try {

                            strss = response.getJSONArray(0);
                            strs = response.getJSONArray(1);
                            str = response.getJSONArray(2);


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


                            String mn = str.toString();
                            mn = str.toString().replace(",", " ");
                            mn = mn.replace("[", "");
                            mn = mn.replace("]", "");
                            mn = mn.replace("\"", "");
                            String[] w = mn.split(" ");
                            Log.i("w", "Array: " + Arrays.toString(w));


                            String[] hp = new String[strs.length()];
                            int i ;
                            for ( i = 0; i < hp.length; i++) {
                                hp[i] = Arrays.toString(new String[]{z[i] + " --> " + w[i]  + " --> " +v[i]});
                                hp[i] = hp[i].replace("[", "");
                                hp[i] = hp[i].replace("]", "");

                            }


                            //String [] hp = new String[] {z[0] +" --> "+ v[0],z[1] +" --> "+ v[1],z[2] +" --> "+ v[2]};
                            // String [] hp = new String[] {Arrays.toString(z.clone()) +" --> "+ Arrays.toString(v.clone())};
                            Log.i("resulhp", "Array: " + Arrays.toString(hp));

/*
Esta es la propia la que vale
       String[] hp = new String[strs.length()];
                           int i ;
                           for ( i = 0; i < hp.length; i++) {
                               hp[i] = Arrays.toString(new String[]{z[i]+ " --> " + v[i]});
                           }
 */
                           /*
                                      String[] problemas = new String[strss.length()];
                           for (int i = 0; i < strss.length(); i++) {
                               problemas[i] = strss.get(i).toString();
                               Log.i("resultpro", "Array: " + problemas);
                           }
                           String[] problemas1 = new String[strs.length()];
                           for (int i = 0; i < strs.length(); i++) {
                               problemas1[i] = strs.get(i).toString();
                               Log.i("resultpro1", "Array: " + problemas);
                           }
                            */
                           /*
                           Log.i("resultvi","Array: "+v[i]);
                           Log.i("resultz1","Array: "+z[i]);
                           Log.i("resultz1","Array: "+hp[i]);
                            */

                           /*
                           int i = 1;
                           i++;
                           String [] hp =  {strss.get(i).toString() +" --> "+ strs.get(i).toString()};
                            */



/*
   strss = response.getJSONArray(0);
                           strs = response.getJSONArray(1);
                           String op = strss.toString();
                           op = strss.toString().replace(",", " ");
                           op = op.replace("[", "");
                           op = op.replace("]", "");
                           String[] z = op.split(" ");
                           String ab = strs.toString();
                           ab = strs.toString().replace(",", " ");
                           ab = ab.replace("[", "");
                           ab = ab.replace("]", "");
                           ab = ab.replace("\"", "");
                           String[] v = ab.split(" ");
                           String[] problemas = new String[strss.length()];
                           for (int i = 0; i < strss.length(); i++) {
                               problemas[i] = strss.get(i).toString();
                           }
                           String[] problemas1 = new String[strs.length()];
                           for (int i = 0; i < strs.length(); i++) {
                               problemas1[i] = strs.get(i).toString();
                           }
 */


                            AlertDialog.Builder info = new AlertDialog.Builder(interfaz_consultar.this);

                            //info.setMessage("ID-->Ap \n");
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
////////////////**********/////////////////////////////////
    }

    private void cargarimagen(String so) {

        ImageRequest imageRequest=new ImageRequest(so, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                //Bitmap bitmap = response;//SE MODIFICA
                Log.i("imag", String.valueOf(response));
                Log.i("ima", String.valueOf(bitmap));
                AlertDialog.Builder camara = new AlertDialog.Builder(interfaz_consultar.this);

                LayoutInflater camara_imagen = LayoutInflater.from(getApplicationContext());
                final View vista = camara_imagen.inflate(R.layout.dialog_camara, null);

                camara.setPositiveButton("Cambiar Foto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pick();


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
                Objects.requireNonNull(imageView).setImageBitmap(response);
                //imagen.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error al cargar la imagen",Toast.LENGTH_SHORT).show();
                Log.i("ERROR IMAGEN","Response -> "+error);
            }
        });
        //  request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(imageRequest);
    }

    private void pick(){
    if(ContextCompat.checkSelfPermission(getApplicationContext(),  Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(interfaz_consultar.this,
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
    private  String getStringImagen(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    private void modificar_imagen (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("oliver",response);
                if(response.toString().trim().equals("1")) {
                    Toast.makeText(getBaseContext(), "Imagen reemplazada correctamente", Toast.LENGTH_SHORT).show();


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
                parametros.put("cedula",v02.tvc2.getText().toString().trim());
                parametros.put("referencia",imagen);
                parametros.put("ip",ip);
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}