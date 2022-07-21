package com.example.siteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.example.siteapp.databinding.ActivityInterfazGraficoNBinding;
import com.example.siteapp.databinding.ActivityMainBinding;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class interfaz_graficoN extends General {

    private Spinner spinnergrfN;
    private Spinner spinnergrfap;
    String statenod;
    String stateap;
    RequestQueue requestQueue;
    ActivityInterfazGraficoNBinding layout;
    int ga;
    int go;
    private Spinner meses;
    private ListView lv50;
    int mesIndice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_grafico_n);
        layout = ActivityInterfazGraficoNBinding.inflate(getLayoutInflater());
        View view = layout.getRoot();
        setContentView(view);

        GraphView graph=layout.myGraph;

        meses = layout.meses;
        String [] mes ={ "Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        ArrayAdapter<String> adapter10 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_estado, mes);
        meses.setAdapter(adapter10);

        meses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mesIndice = Integer.parseInt(String.valueOf(position+1));
                Log.i("result5", String.valueOf(mesIndice));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /****NODOS/
         /****************************************/

        String URL="http://192.168.101.5/conexion_php/spinnern.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray nodos=new JSONArray(response);

                    JSONArray id=new JSONArray(nodos.get(0).toString());
                    JSONArray name=new JSONArray(nodos.get(1).toString());

                    spinnergrfN = layout.spinnergrfN;
                    String[] opciones = new String[name.length()];

                    JSONObject nods=new JSONObject();


                    for (int i=0;i<name.length();i++){
                        opciones[i]=name.get(i).toString();
                        nods.put(name.get(i).toString(), id.get(i).toString());
                    }

                    ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_estado, opciones);

                    spinnergrfN.setAdapter(adapter7);


                    spinnergrfN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            statenod = spinnergrfN.getItemAtPosition(position).toString();
                            Log.i("result2",statenod);


                            try {
                                Log.i("result1",nods.getString(statenod));

                                ga = Integer.parseInt(String.valueOf(nods.getString(statenod)));
                                Log.i("result5", String.valueOf(ga));


                                /***Aquiii poner aps*/
                                String URL="http://192.168.101.5/conexion_php/spinnera.php";

                                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {

                                            //spinnergrfap.setAdapter(null);
                                            JSONArray aps=new JSONArray(response);

                                            JSONArray id=new JSONArray(aps.get(0).toString());
                                            JSONArray name=new JSONArray(aps.get(1).toString());

                                            spinnergrfap = layout.spinnergrfap;
                                            String[] opciones1 = new String[name.length()];

                                            JSONObject apss=new JSONObject();

                                            //spinnergrfap.setAdapter(null);
                                            for (int i=0;i<name.length();i++){
                                                opciones1[i]=name.get(i).toString();
                                                apss.put(name.get(i).toString(), id.get(i).toString());
                                            }
                                            //spinnergrfap.setAdapter(null);
                                            ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_estado, opciones1);
                                            spinnergrfap.setAdapter(adapter8);
                                            //spinnergrfap.setAdapter(null);

                                            spinnergrfap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    stateap = spinnergrfap.getSelectedItem().toString();

                                                    try {
                                                        Log.i("resultap",apss.getString(stateap));
                                                        go = Integer.parseInt(String.valueOf(apss.getString(stateap)));
                                                        Log.i("result5", String.valueOf(go));


                                                        //////////*////////////////

                                                        layout.btngrf.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                                String URL= "http://192.168.101.5/conexion_php/graficosand.php?id_ap="+go+"&mes="+mesIndice;


                                                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                                                                    @Override
                                                                    public void onResponse(JSONArray response) {
                                                                        Log.i("result", response.toString());
                                                                        JSONArray barras = null;
                                                                        JSONArray strss = null;

                                                                        try {


                                                                            barras = response.getJSONArray(1);
                                                                            int[] y = new int[barras.length()];
                                                                            for (int c = 0; c < barras.length(); c++) {
                                                                                y[c] = (int) barras.get(c);
                                                                            }
                                                                            ////*********////
                                                                            int mayor = y[0];
                                                                            for (int o = 0; o < y.length; o++) {
                                                                                if (mayor < y[o]) {
                                                                                    mayor = y[o];
                                                                                }
                                                                            }

                                                                            strss = response.getJSONArray(2);
                                                                            Log.i("result","Array: "+strss.toString());
                                                                            Log.i("result","Array: 01"+strss.get(1).toString());
                                                                            //Log.i("mayor", String.valueOf(barras.length()));
                                                                            /********************/
                                                                            DataPoint[] cordenadas = new DataPoint[barras.length()];
                                                                            int cont;
                                                                            for (cont = 0; cont < barras.length(); cont++) {
                                                                                cordenadas[cont] = new DataPoint(cont, y[cont]);
                                                                                //Log.i("mayor33", String.valueOf(y[cont]));
                                                                            }
                                                                            graph.removeAllSeries();

                                                                            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(cordenadas);
                                                                            graph.getViewport().setXAxisBoundsManual(true);
                                                                            graph.getViewport().setMinX(0);
                                                                            graph.getViewport().setMaxX(barras.length()-1);

                                                                            graph.getViewport().setYAxisBoundsManual(true);
                                                                            graph.getViewport().setMinY(0);
                                                                            graph.getViewport().setMaxY(mayor);

                                                                            graph.getViewport().setScrollable(true);
                                                                            graph.getViewport().setScrollableY(true);
                                                                            graph.getViewport().setScalable(true);
                                                                            graph.getViewport().setScalableY(true);
                                                                            //graph.getLegendRenderer().setVisible(true);
                                                                            graph.addSeries(series);

                                                                            //////******************////
                                                                            /// ESCALA ROJA DE LA IZQUIERDA
                                                                            BarGraphSeries<DataPoint> series3 = new BarGraphSeries<DataPoint>(cordenadas);
                                                                            graph.getSecondScale().addSeries(series3);
                                                                            graph.getSecondScale().setMinY(0);
                                                                            graph.getSecondScale().setMaxY(mayor);
                                                                            series3.setColor(Color.BLACK);
                                                                            graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.BLACK);

                                                                            ///// LINEAS ROJAS
                                                                            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(cordenadas);
                                                                            graph.addSeries(series2);


                                                                            //*** titulos del para ejes x / Y
                                                                            // use static labels for horizontal and vertical labels
                                                                            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);

                                                                            String xy=strss.toString().replace(","," ");
                                                                            xy=xy.replace("[","");
                                                                            xy=xy.replace("]","");
                                                                            xy=xy.replace("\"","");
                                                                            String[] z=xy.split(" ");

                                                                            Log.i("result","str: "+z.toString());
                                                                            staticLabelsFormatter.setHorizontalLabels(z);
                                                                            //staticLabelsFormatter.setVerticalLabels(mes);
                                                                            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                                                                            ////*******////////

                                                                            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                                                                                @Override
                                                                                public int get(DataPoint data) {
                                                                                    //return Color.rgb(100,60,100);
                                                                                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                                                                                }
                                                                            });
                                                                            //**** tamanio texto y color de las variables de las barras
                                                                            series.setSpacing(70);
                                                                            series.setValuesOnTopSize(60);
                                                                            series.setDrawValuesOnTop(true);
                                                                            series.setValuesOnTopColor(Color.CYAN);
                                                                            /******/

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }, new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        Toast.makeText(getApplicationContext(), "Error de conexion",Toast.LENGTH_SHORT).show();

                                                                    }
                                                                });
                                                                requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                                requestQueue.add(jsonArrayRequest);
                                                            }
                                                        });

                                                        ///********************/

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
                                        parametros.put("id_nodo", String.valueOf(ga));
                                        return parametros;
                                    }
                                };

                                requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(stringRequest);

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        /****************************************/
        /***/

        /****APS/
         /****************************************/

        /****************************************/
        /***/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.graficos,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        SharedPreferences admin=getBaseContext().getSharedPreferences("x", Context.MODE_PRIVATE);
        String tip_usuario=admin.getString("tip_usuario","");

        switch (item.getItemId())
        {

            case R.id.nod:

                Intent intent = new Intent( getApplicationContext(),interfaz_graficoN.class);
                startActivity(intent);

                break;


            case R.id.ap:

                intent = new Intent(getApplicationContext(), graficos.class);
                startActivity(intent);

                break;


            case R.id.salir:



                    intent = new Intent(getApplicationContext(), interfaz_mostrar_graficas.class);
                    startActivity(intent);



                break;


        }

        return super.onOptionsItemSelected(item);
    }
}