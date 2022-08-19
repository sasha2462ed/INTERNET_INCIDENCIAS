package com.example.siteapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.FragmentFrlnAduBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class frlnAdu extends Fragment {

    FragmentFrlnAduBinding layout;

    public frlnAdu() {

    }

    // TODO: Rename and change types and number of parameters
    public static frlnAdu newInstance(String param1, String param2) {
        frlnAdu fragment = new frlnAdu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout= FragmentFrlnAduBinding.inflate(inflater,container,false);
        View v=layout.getRoot();
        String ip = getString(R.string.ip);
        RecyclerView list=layout.lista;
        ArrayList<Incidencias> itemRec;

        itemRec=new ArrayList();

        String URL = ip+"/conexion_php/buscar_incidencias.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.isEmpty()) {
                    try {
                        JSONArray object= null;

                        object = new JSONArray(response);

                        for(int i=0;i<object.length();i++) {
                            JSONObject indicencia = object.getJSONObject(i);

                            itemRec.add(new Incidencias(
                                            indicencia.getString("idIncidencias"),
                                            indicencia.getString("tipo").toString(),
                                            indicencia.getString("comentario").toString(),
                                            indicencia.getString("hora").toString(),
                                            indicencia.getString("estado").toString(),
                                            indicencia.getString("id").toString(),
                                            indicencia.getString("cedula").toString(),
                                    indicencia.getString("departamento").toString()

                                    )
                            );
                        }

                        list.setLayoutManager(new LinearLayoutManager(requireContext()));
                        RecyclerView.Adapter<myAdapter.ContenetViews> adapter= new myAdapter(itemRec);
                        adapter.notifyDataSetChanged();
                        list.setAdapter(adapter);
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                   Toast.makeText(requireContext(), "Sin incidencias que mostrar", Toast.LENGTH_SHORT).show();
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
                parametros.put("departamento", String.valueOf(2));
                SharedPreferences admin=requireContext().getSharedPreferences("x", Context.MODE_PRIVATE);
                String id=admin.getString("id","");
                parametros.put("id", id);

                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
        return v;

    }
}

