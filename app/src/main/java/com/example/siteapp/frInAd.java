package com.example.siteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.FragmentFrInAdBinding;
import com.example.siteapp.databinding.FragmentFrInTecBinding;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frInAd#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class frInAd extends Fragment {

    FragmentFrInAdBinding layout;
    private Spinner spinner_estado_ad;
    String state_frag1;
    int ga;

    public frInAd() {
        // Required empty public constructor
    }

    public static frInAd newInstance(String param1, String param2) {
        frInAd fragment = new frInAd();

        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        /**/

        layout=FragmentFrInAdBinding.inflate(inflater,container,false);
        View v=layout.getRoot();
        //View vista= inflater.inflate(R.layout.fragment_fr_in_tec, container, false);
        String ip = getString(R.string.ip);
        RecyclerView list=layout.lista;
        ArrayList<Incidencias> itemRec;

        itemRec=new ArrayList();
        /*******************************/

        /****************************************/

        String URL=ip+"/conexion_php/item_estados.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray nodos=new JSONArray(response);

                    JSONArray id=new JSONArray(nodos.get(0).toString());
                    JSONArray name=new JSONArray(nodos.get(1).toString());

                    String[] opciones = new String[name.length()];

                    JSONObject nods=new JSONObject();


                    for (int i=0;i<name.length();i++){
                        opciones[i]=name.get(i).toString();
                        nods.put(name.get(i).toString(), id.get(i).toString());
                    }

                    spinner_estado_ad = layout.spinnerEstadoAd;
                    ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_estado, opciones);
                    spinner_estado_ad.setAdapter(adapter5);
                    Log.i("result","Data: "+state_frag1);


                    spinner_estado_ad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            state_frag1 = spinner_estado_ad.getItemAtPosition(position).toString();
                            //Log.i("result2",statenod);


                            try {
                                ga = Integer.parseInt(String.valueOf(nods.getString(state_frag1)));
                                Log.i("result5", String.valueOf(ga));

                                layout.btnFragAd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String ip = getString(R.string.ip);
                                String URL =ip+"/conexion_php/buscar_incidenciastec.php";

                                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(!response.isEmpty()) {
                                            try {
                                                JSONArray object= null;

                                                object = new JSONArray(response);
                                                Log.i("result","Data: "+response);
                                                itemRec.clear();
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
                                                RecyclerView.Adapter adapter= new myAdapter(itemRec);
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
                                        //Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();

                                    }

                                }){
                                    @Override
                                    protected Map<String, String> getParams () throws AuthFailureError {
                                        Map<String,String> parametros = new HashMap<String, String>();

                                        parametros.put("departamento", String.valueOf(2));
                                        parametros.put("estado", String.valueOf(ga));


                                        return parametros;
                                    }
                                };
                                        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);


                                    }
                                });



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
        /****************************************/

///////////////////*******************///////////////////////////////////////////////

        /********************************/
        return v;

    }
}