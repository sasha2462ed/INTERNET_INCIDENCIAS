package com.example.siteapp;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityInterfazGraficosBinding;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class interfaz_graficos extends General {

    ActivityInterfazGraficosBinding layout;
    RequestQueue requestQueue;
    private Spinner months;
    int mesIndice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout=ActivityInterfazGraficosBinding.inflate(getLayoutInflater());

        setContentView(layout.getRoot());

        GraphView graph=layout.myGraph;

        //***/
        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions();
        }
        //***/

        months = layout.months;
        String [] mont = { "Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        ArrayAdapter<String> adapter01 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_estado, mont);
        months.setAdapter(adapter01);

        months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mesIndice = Integer.parseInt(String.valueOf(position+1));
                Log.i("result5", String.valueOf(mesIndice));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        layout.btngrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//////******************//////////////////
                String ip = getString(R.string.ip);
                String URL = ip+"/conexion_php/graficoso.php?mes="+mesIndice;


                JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("result","r: "+response.toString());
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


                            Log.i("mayor", String.valueOf(barras.length()));
                            /********************/

                            DataPoint[] cordenadas = new DataPoint[barras.length()];

                            for (int cont = 0; cont < barras.length(); cont++) {
                                cordenadas[cont] = new DataPoint(cont, y[cont]);
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

                            /////********////
                            Bitmap bitmap = graph.takeSnapshot();
                            ////*******////////

                            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                                @Override
                                public int get(DataPoint data) {
                                    //return Color.rgb(100,60,100);
                                    return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
                                }
                            });

                            //**** tamanio texto y color de las variables de las barras
                            series.setSpacing(50);
                            series.setValuesOnTopSize(60);
                            series.setDrawValuesOnTop(true);
                            series.setValuesOnTopColor(Color.CYAN);
                            /******/

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
                VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonArrayRequest);

//**************************//////////

            }
        });




//        layout.write.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View arg0) {
//                Bitmap bitmap;
//                graph.setDrawingCacheEnabled(true);
//                bitmap = Bitmap.createBitmap(graph.getDrawingCache());
//                graph.setDrawingCacheEnabled(false);
//
//
//                String filename = "imagen";
//
//
//                FileOperations fop = new FileOperations();
//                fop.write(filename, bitmap);
//                if (fop.write(filename,bitmap)) {
//                    Toast.makeText(getApplicationContext(),
//                            filename + ".pdf created", Toast.LENGTH_SHORT)
//                            .show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "I/O error",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });




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

                intent = new Intent(getApplicationContext(), interfaz_graficos.class);
                startActivity(intent);

                break;


            case R.id.salir:


                    intent = new Intent(getApplicationContext(), interfaz_mostrar_graficas.class);
                    startActivity(intent);




                break;


        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 200) {
            if(grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

}