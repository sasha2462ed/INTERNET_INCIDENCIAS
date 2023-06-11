package com.example.siteapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.siteapp.databinding.ActivityInterfazGestionImageBinding;

public class interfaz_gestion_image extends AppCompatActivity {
    private ActivityInterfazGestionImageBinding v30;
    String imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_gestion_image);
        v30 = ActivityInterfazGestionImageBinding.inflate(getLayoutInflater());
        View view = v30.getRoot();
        setContentView(view);

        imagen = getIntent().getStringExtra("imagen");

        Log.i("imagen","Data: "+imagen);

        v30.btnvr01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ImageRequest imageRequest=new ImageRequest(imagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                //Bitmap bitmap = response;//SE MODIFICA
                if (response != null) {
                    v30.imageView2.setImageBitmap(response);
                    ///imageView.setImageBitmap(bitmap);
                } else{
                    Toast.makeText(getApplicationContext(),"No hay imagen que mostrar",Toast.LENGTH_SHORT).show();
                }




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
}