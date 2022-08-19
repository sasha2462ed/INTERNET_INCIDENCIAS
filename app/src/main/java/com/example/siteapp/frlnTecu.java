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

import com.example.siteapp.databinding.FragmentFrlnTecuBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class frlnTecu extends Fragment {
    FragmentFrlnTecuBinding layout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frlnTecu() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static frlnTecu newInstance(String param1, String param2) {
        frlnTecu fragment = new frlnTecu();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout= FragmentFrlnTecuBinding.inflate(inflater,container,false);
        View v=layout.getRoot();

        RecyclerView list=layout.lista;
        ArrayList<Incidencias> itemRec;
        String ip = getString(R.string.ip);
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

                parametros.put("departamento", String.valueOf(1));

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
