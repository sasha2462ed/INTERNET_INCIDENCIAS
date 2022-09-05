package com.example.siteapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazConsultarBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class interfaz_consultar extends AppCompatActivity {
    private ActivityInterfazConsultarBinding v02;
    String buscar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_consultar);
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

                                    }else{
                                        v02.tvc1.setEnabled(false);
                                        //v02.tvc2.setEnabled(false);
                                        v02.tvc3.setEnabled(false);
                                        v02.tvc4.setEnabled(false);
                                        v02.tvc5.setEnabled(false);
                                        //v02.tvc6.setEnabled(false);
                                        v02.tvc7.setEnabled(false);
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

                            Intent intent = new Intent( getApplicationContext(),interfaz_tecnico_usuario.class);
                            startActivity(intent);

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

/////////////////////////////////////////////////////




       v02.icono505.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


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
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (Objects.equals(String.valueOf(keyCode), 4)) {
            Intent intent = new Intent( getApplicationContext(),interfaz_tecnico.class);
            startActivity(intent);
            return  true;
        }

        return super.onKeyDown(keyCode, event);
    }

 */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( getApplicationContext(),interfaz_tecnico_usuario.class);
        startActivity(intent);
    }



    /*
    @Override
public boolean onKeyDown(int keyCode, KeyEvent event)
{
    if ((keyCode == KeyEvent.KEYCODE_BACK))
    {
        //codigo adicional
        finish();
    }
    return super.onKeyDown(keyCode, event);
}
     */

    /*
    @Override
    public void onBackPressed() {
        //Si llamas super.onBackPressed(), esto internamente ejecuta finish().
        super.onBackPressed();
    }

     */
}