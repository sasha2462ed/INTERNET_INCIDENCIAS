package com.example.siteapp;

import androidx.appcompat.app.AppCompatActivity;

public class General extends AppCompatActivity {





//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(getApplicationContext(), interfaz_tecnico.class);
//        startActivity(intent);
//    }

/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (String.valueOf(keyCode).equals(4)) {
            return false;
        }

        return true;
    }

 */

    //    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//
//        Log.i("key",""+String .valueOf(keyCode));
//
//
//        if (keyCode==KEYCODE_HOME) {
//            Log.i("key",""+String .valueOf(keyCode));
//            Log.i("key",""+"dnfwjefnkwe");
//            return false;
//        }
//
//        return true;
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) { oneheydown y up
//
//        switch(keyCode)
//        {
//            case KeyEvent.KEYCODE_HOME:
//                iii();
//                Toast.makeText(getApplicationContext(),"aqui home", Toast.LENGTH_SHORT).show();
//                Log.i("key",""+"home");
//                return true;
//            case KeyEvent.KEYCODE_BACK:
//                Toast.makeText(getApplicationContext(),"aquii atras ", Toast.LENGTH_SHORT).show();
//                Log.i("key",""+"atras");
//                return true;
//            case KeyEvent.KEYCODE_MENU:
//                Toast.makeText(getApplicationContext(),"aqui menu " , Toast.LENGTH_SHORT).show();
//                Log.i("key",""+"menu");
//                return true;
//
//
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }


    ////********** andoid mpchard

//    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animateY){
//        chart.getDescription().setText(description);
//        chart.getDescription().setTextSize(15);
//        chart.setBackgroundColor(background);
//        chart.animateY(animateY);
//        legend(chart);
//        return chart;
//    }
//
//    private void legend (Chart chart) {
//
//        Legend legend = chart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//
//        String ip = getString(R.string.ip);
//        String URL = ip+"/conexion_php/graficoso.php?mes="+mesIndice;
//
//
//        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.i("result","r: "+response.toString());
//                JSONArray barras = null;
//                JSONArray strss = null;
//                JSONArray strss1=null;
//
//                try {
//
//                    barras = response.getJSONArray(1);
//
//                    int[] y = new int[barras.length()];
//                    for (int c = 0; c < barras.length(); c++) {
//                        y[c] = (int) barras.get(c);
//                    }
//
//                    ////*********////
//                    int mayor = y[0];
//                    for (int o = 0; o < y.length; o++) {
//                        if (mayor < y[o]) {
//                            mayor = y[o];
//                        }
//                    }
//
//                    strss = response.getJSONArray(2);
//                    Log.i("result","barras: "+barras.length());
//                    Log.i("result","Array: "+strss.toString());
//                    Log.i("result","Array: 01"+strss.get(1).toString());
//
//
//                    Log.i("mayor", String.valueOf(barras.length()));
//                    /********************/
//
//                    DataPoint[] cordenadas = new DataPoint[barras.length()];
//
//                    for (int cont = 0; cont < barras.length(); cont++) {
//                        cordenadas[cont] = new DataPoint(cont, y[cont]);
//                    }
////                            graph.removeAllSeries();
////
////                            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(cordenadas);
////
////                            graph.getViewport().setXAxisBoundsManual(true);
////                            graph.getViewport().setMinX(0);
////                            graph.getViewport().setMaxX(barras.length()-1);
////                            //graph.getViewport().setMaxX(mayor);
////
////                            graph.getViewport().setYAxisBoundsManual(true);
////                            graph.getViewport().setMinY(0);
////                            //graph.getViewport().setMaxY(barras.length()-1);
////                            graph.getViewport().setMaxY(mayor+1);
////
////                            graph.getViewport().setScrollable(true);
////                            graph.getViewport().setScrollableY(true);
////                            graph.getViewport().setScalable(true);
////                            graph.getViewport().setScalableY(true);
////                            //graph.getLegendRenderer().setVisible(true);
////                            graph.addSeries(series);
//
//                    //*** titulos del para ejes x / Y
//                    // use static labels for horizontal and vertical labels
//                    // StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//
//                    String xy=strss.toString().replace(","," ");
//                    xy=xy.replace("[","");
//                    xy=xy.replace("]","");
//                    xy=xy.replace("\"","");
//                    String[] z=xy.split(" ");
//
//
//                    strss1 = response.getJSONArray(1);
//                    String xy1=strss1.toString().replace(","," ");
//                    xy1=xy1.replace("[","");
//                    xy1=xy1.replace("]","");
//                    xy1=xy1.replace("\"","");
//                    String[] z1=xy1.split(" ");
//
//
//                    ArrayList<LegendEntry> entries = new ArrayList<>();
//                    for(int i=0; i< z.length ;i++){
//                        LegendEntry entry = new LegendEntry();
//                        entry.formColor = colors[i];
//                        entry.label=z[i];
//                        entries.add(entry);
//                    }
//                    legend.setCustom(entries);
//
//
//                    Log.i("result","z: "+ Arrays.toString(z));
//                    Log.i("result","z1: "+ Arrays.toString(z1));
////                            staticLabelsFormatter.setHorizontalLabels(z);
////                            //staticLabelsFormatter.setVerticalLabels(z);
////                            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
//
//                    /////********////
//
//                    ////*******////////
//
////                            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
////                                @Override
////                                public int get(DataPoint data) {
////                                    //return Color.rgb(100,60,100);
////                                    return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
////                                }
////                            });
////
////                            //**** tamanio texto y color de las variables de las barras
////                            series.setSpacing(50);
////                            series.setValuesOnTopSize(60);
////                            //series.setDrawValuesOnTop(true);
////                            series.setValuesOnTopColor(Color.CYAN);
//                    /******/
//
//                    /******/
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Error de conexion",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
//
//
//    }
//
////    private ArrayList<BarEntry>getBarEntries() {
////        ArrayList<BarEntry> entries = new ArrayList<>();
////
////        for (int i = 0; i < i.length; i++)
////            entries.add(new BarEntry(i[i]));
////        return entries;
////
////    }
//
//    private ArrayList<PieEntry>getPieEntries() {
//        ArrayList<PieEntry> entries = new ArrayList<>();
//
//        for (int i = 0; i < i.length; i++)
//            entries.add(new PieEntry(i[i]));
//        return entries;
//
//    }
//
////    private void axisX(XAxis axis){
////        axis.setGranularityEnabled(true);
////        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
////        axis.setValueFormatter(new IndexAxisValueFormatter(aps));
////    }
////
////    private void axisLeft (YAxis axis){
////        axis.setSpaceTop(#mayor);
////        axis.setAxisMinimum(0);
////    }
////
////    private void axisRight (YAxis axis){
////        axis.setEnabled(false);
////    }
//
//    public void createCharts(){
////        barChart=(BarChart)getSameChart(barChart, "Incidentes", Color.REd, Color.CYAN, 3000);
////        barChart.setDrawGridBackground(true);
////        barChart.setDrawBarShadow(true);
////        barChart.setData(getBarData());
////        barChart.invalidate();
////
////        axisX(barChart.getXAxis());
////        axisLeft(barChart.getXAxisLeft());
////        axisRight(barChart.getXAxisRight());
//
//
//        pieChart=(PieChart)getSameChart(pieChart,"Aps", Color.RED, Color.CYAN, 3000);
//        pieChart.setHoleRadius(10);
//        pieChart.setTransparentCircleRadius(12);
//        // pieChart.setDrawHoleEnable(false);
//        pieChart.setData(getPieData());
//        pieChart.invalidate();
//
//    }
//
//    private Dataset getData (Dataset dataSet){
//        dataSet.setColors(colors);
//        dataSet.setvalueTextSize(Color.WHITE);
//        dataSet.setValueTextSize(10);
//        dataSet.
//        return dataSet;
//    }
//
//    //    private BarData getBarData (){
////        BarDataSet barDataSet=(BarDataSet) getData(new BarDataSet(getBarEntries(),""));
////
////        barDataSet.setBarShadowColor(Color.Gray);
////        BarDataSet barData=new BarData(barDataSet);
////        barData.setBarWidth(0.45f);
////
////        return barData;
////    }
//    private PieData getPieData (){
//        PieDataSet pieDataSet=(PieDataSet) getData(new PieDataSet(getPieEntries(),""));
//
//        pieDataSet.setSliceSpace(2);
//        pieDataSet.setValueFormatter(new PercentFormatter());
//
//        return new PieData(pieDataSet);
//    }
//}


}
