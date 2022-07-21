package com.example.siteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityDepartamentoAdministrativoBinding;
import com.example.siteapp.databinding.ActivityInterfazSugerenciaBinding;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class interfaz_sugerencia extends General {

    private ActivityInterfazSugerenciaBinding v9;
    RequestQueue requestQueue;
    String trampa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_sugerencia);
        v9 = ActivityInterfazSugerenciaBinding.inflate(getLayoutInflater());
        View view = v9.getRoot();
        setContentView(view);

        trampa = getIntent().getStringExtra("trampa");

        v9.botonsugerencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registroSugerencias("http://192.168.101.5/conexion_php/insertar_notisug.php");

            }
        });

        v9.botonregresarsugerencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences admin=getBaseContext().getSharedPreferences("x", Context.MODE_PRIVATE);
                String tip_usuario=admin.getString("tip_usuario","");

                if (tip_usuario.equals("C")){

                    Intent intent = new Intent( getApplicationContext(),interfaz_usuario.class);
                    startActivity(intent);

                }

                else if (tip_usuario.equals("D")){

                    Intent intent = new Intent( getApplicationContext(),interfaz_dependiente.class);
                    startActivity(intent);


                }
            }
        });
    }
    private void registroSugerencias (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("oliver",response);
                if(response.equals("1")){
                    Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                    v9.textosuge.getText().clear();
                    v9.textosugerencia.getText().clear();


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
                parametros.put("asunto", v9.textosuge.getText().toString());
                parametros.put("descripcion",v9.textosugerencia.getText().toString());
                SharedPreferences admin=getApplicationContext().getSharedPreferences("x",MODE_PRIVATE);
                String id=admin.getString("id","");
                parametros.put("id_usuarios", id);
                parametros.put("id_tip_noti", String.valueOf(2));
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

}
