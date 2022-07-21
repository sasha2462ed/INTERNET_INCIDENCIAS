package com.example.siteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityInterfazNotiDetalleBinding;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class interfaz_noti_detalle extends General {
    private ActivityInterfazNotiDetalleBinding v55;
    String comentario;
    String asunto;
    String idNoti;
    String origen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_noti_detalle);
        v55 = ActivityInterfazNotiDetalleBinding.inflate(getLayoutInflater());
        View view = v55.getRoot();
        setContentView(view);

        asunto = getIntent().getStringExtra("asunto");
        idNoti = getIntent().getStringExtra("idNoti");
        comentario = getIntent().getStringExtra("comentario");
        origen = getIntent().getStringExtra("origen");

        v55.tvn3.setText(asunto);
        v55.tvn4.setText(comentario);

        v55.btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String URL="http://192.168.101.5/conexion_php/modificar_estado_notificaciones.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("oliver",response);
                        if(response.equals("1")){
                        }else{
                        }

                            Intent intent = new Intent( getApplicationContext(),interfaz_notificaciones.class);
                            startActivity(intent);


                    }

                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }){
                    @Override
                    protected Map<String, String> getParams () throws AuthFailureError {
                        Map<String,String> parametros = new HashMap<String, String>();
                        //parametros.put("id".toString().toString());

                        parametros.put("estado","V");
                        parametros.put("id",idNoti);
                        return parametros;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

            }
        });

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
}