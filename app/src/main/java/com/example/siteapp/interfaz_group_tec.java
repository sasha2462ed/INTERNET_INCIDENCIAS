package com.example.siteapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.siteapp.databinding.ActivityInterfazGroupTecBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class interfaz_group_tec extends AppCompatActivity {

    ActivityInterfazGroupTecBinding layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interfaz_group_tec);

        layout=ActivityInterfazGroupTecBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());


        list();






    }

    public void list (){

        RecyclerView list=layout.lista;
        ArrayList<GrupoList> itemRec;

        itemRec=new ArrayList();

        String ip = getString(R.string.ip);
        String URL=ip+"/conexion_php/grupo_list_inc.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.isEmpty()) {
                    try {
                        JSONArray object= null;

                        object = new JSONArray(response);
                        Log.i("result","Data: "+response);

                        for(int i=0;i<object.length();i++) {
                            JSONObject groupinc = object.getJSONObject(i);

                            itemRec.add(new GrupoList(

                                            groupinc.getString("ap").toString(),
                                            groupinc.getString("nodo").toString(),
                                            groupinc.getString("CI").toString(),
                                    groupinc.getString("group_inc").toString(),
                                    groupinc.getString("id").toString(),
                                    groupinc.getString("id_dep").toString()
                                    )
                            );
                        }

                        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        RecyclerView.Adapter<ListAP.ContenetList> adapter= new ListAP(itemRec);
                        list.setAdapter(adapter);
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Sin nuevas notificaciones", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("result",error.toString());

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
}