package com.example.siteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityInterfazNotiDetalleBinding;
import com.example.siteapp.databinding.ActivityInterfazSugeDetalleBinding;

import java.util.HashMap;
import java.util.Map;

public class interfaz_suge_detalle extends General {
    private ActivityInterfazSugeDetalleBinding v58;

    String comentario;
    String asunto;
    String idNoti;
    String origen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_suge_detalle);

        v58 = ActivityInterfazSugeDetalleBinding.inflate(getLayoutInflater());
        View view = v58.getRoot();
        setContentView(view);

        asunto = getIntent().getStringExtra("asunto");
        idNoti = getIntent().getStringExtra("idNoti");
        comentario = getIntent().getStringExtra("comentario");
        origen = getIntent().getStringExtra("origen");

        v58.tvs3.setText(asunto);
        v58.tvs4.setText(comentario);

        v58.btns.setOnClickListener(new View.OnClickListener() {
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

                            Intent intent = new Intent( getApplicationContext(),interfaz_aviso.class);
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
}