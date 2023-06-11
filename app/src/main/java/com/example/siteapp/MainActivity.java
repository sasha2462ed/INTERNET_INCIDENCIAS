package com.example.siteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding v1;
    Context ct;
    CheckBox sesion;
    SharedPreferences app;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v1 = ActivityMainBinding.inflate(getLayoutInflater());
        View view = v1.getRoot();
        setContentView(view);

        new General();

        app=getApplicationContext().getSharedPreferences("myApp",MODE_PRIVATE);
        sesion=v1.checkBox;
        ct=view.getContext();
        SharedPreferences admin=getApplicationContext().getSharedPreferences("x",MODE_PRIVATE);
        boolean login=admin.getBoolean("estado",false);
        String tip_usuario=admin.getString("tip_usuario","");
        ip = getString(R.string.ip);

        v1.vien1.setVisibility(View.INVISIBLE);

        v1.vien1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v1.txp2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                //v1.txp2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                v1.vien.setVisibility(View.VISIBLE);
                v1.vien1.setVisibility(View.INVISIBLE);


            }
        });

        v1.vien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v1.txp2.setInputType(InputType.TYPE_CLASS_TEXT);
                //v1.txp2.setInputType(InputType.TYPE_CLASS_NUMBER);
                v1.vien.setVisibility(View.INVISIBLE);
                v1.vien1.setVisibility(View.VISIBLE);


            }
        });

        String ip = getString(R.string.ip);

        if(login){
            iniSesion(tip_usuario);
        }


        v1.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v1.txp1.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo nombre vacio", Toast.LENGTH_SHORT).show();
                } else {
                    if (v1.txp2.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Campo contrasena vacio", Toast.LENGTH_SHORT).show();
                    } else {
                        validarUsuario(ip+"/conexion_php/hash.php");
                    }
                }
            }
        });
    }

    private void validarUsuario(String URL){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()) {
                    try {
                        JSONObject objUser= new JSONObject(response);
                        Toast.makeText( MainActivity.this, "Bienvenido "+objUser.getString("nombre"), Toast.LENGTH_SHORT).show();
                        Intent activity=null;

                        SharedPreferences admin=ct.getSharedPreferences("x",ct.MODE_PRIVATE);
                        SharedPreferences.Editor data=admin.edit();

                        if(sesion.isChecked())
                        {
                            data.putBoolean("estado",true);
                        }

                        data.putString("nombre",objUser.getString("nombre"));
                        data.putString("cedula",objUser.getString("cedula"));
                        data.putString("tip_usuario",objUser.getString("tip_usuario"));
                        data.putString("id",objUser.getString("id"));
                        data.putString("ap",objUser.getString("ap"));
                        data.putString("ip",ip.toString());
                        data.putString("contrasena",objUser.getString("contrasena"));
                        data.putString("id_groupInc",objUser.getString("group_inc"));
                        data.apply();

                        iniSesion(objUser.getString("tip_usuario"));

                    } catch (JSONException e) {
                        Log.i("Error",e.getMessage());
                    }

                }else{
                    Toast.makeText(MainActivity.this, "usuario/contrasena incorrecto", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Error al conectarse al servidor", Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("cedula",v1.txp1.getText().toString());
                parametros.put("contrasena",v1.txp2.getText().toString());
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    void iniSesion(String type){
        Intent activity=null;

        if(type.equals("T")){
            activity = new Intent(getBaseContext(), interfaz_tecnico.class);

        }else if (type.equals("C")){
            activity= new Intent(getBaseContext(), interfaz_usuario.class);

        } else if (type.equals("D")){
            activity= new Intent(getBaseContext(), interfaz_dependiente.class);
        }

        startActivity(activity);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (String.valueOf(keyCode).equals(4)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.onboard,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId())
        {

            case R.id.guia:

                SharedPreferences.Editor data=app.edit();
                data.remove("status");
                data.apply();

                Intent intent = new Intent( getApplicationContext(), interfaz_menuOnboarding.class);
                startActivity(intent);

                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
