package com.example.siteapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.siteapp.databinding.ActivityInterfazTecnicoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class interfaz_tecnico extends General {

    private ActivityInterfazTecnicoBinding v6;
    RequestQueue requestQueue = null;
    int count=0;
    MenuItem menuItem;
    Context ct;
    TextView notification;
    String trampa = "2";
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private PendingIntent pendingIntent;
    String items= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v6 = ActivityInterfazTecnicoBinding .inflate(getLayoutInflater());
        View view = v6.getRoot();
        setContentView(view);

        SharedPreferences admin=this.getSharedPreferences("x",MODE_PRIVATE);
        ct=view.getContext();

        time time = new time();
        time.execute();



////*****************//////////////
        /*********///////

        ///***Botones***/////////

        v6.btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),interfaz_tecnico_usuario.class);
                startActivity(intent);
            }
        });

        v6.btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), interfaz_mostrar_incidencias_nivel_tecnico.class);
                intent.putExtra("trampa", trampa);
                startActivity(intent);
            }
        });

        v6.btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),interfaz_mostrar_graficas.class);
                startActivity(intent);
            }
        });

        v6.btn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

        v6.botonenvionotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),interfaz_envio_notificacion.class);
                startActivity(intent);
            }
        });
    }

    /////////////////////*****notificacion desde aqui*****/////////////

    public void inc(){

        String URL = "http://192.168.101.5/conexion_php/item_sugerencia.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {

                if(!response.isEmpty()) {

                    try {
                        //AppController.getInstance().getRequestQueue().getCache().remove(key);

                        JSONObject objUser= new JSONObject(response);
                        items = String.valueOf(Integer.parseInt(objUser.getString("CI")));
                        Log.i("results", String.valueOf(items));

                        count= Integer.parseInt(items);
                        if (count==0){}else{
                            menuItem.setActionView(R.layout.notificacion_badgee);
                            // get the view from the nav item
                            View view = menuItem.getActionView();
                            // get the text view of the action view for the nav item
                            notification = view.findViewById(R.id.notification);
                            //notification.setEnabled(false);
                            // set the pending notifications value
                            notification.setText(String.valueOf(count));

                            notification.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.i("result","xxxxxxxxxxxxxxxxxxxxxx");
                                    Intent intent = new Intent(getApplicationContext(), interfaz_aviso.class);
                                    startActivity(intent);

                                }
                            });}


                        if(Integer.parseInt(items) == 0){
                            items = null;
                            objUser = null;
                            objUser= new JSONObject(response);
                            deleteNotificationChannel();
                            new time().execute();


                        } else {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                showNotification();

                                Runtime.getRuntime().gc();
                                System.gc();

                            } else {
                                showNewNotification();
                                Runtime.getRuntime().gc();
                                System.gc();
                            }
                        }






                        }


                    catch (JSONException e) {
                    Log.i("Error",e.getMessage());
                    }
                }else{
                    new time().execute();
                }
            }
        },
                new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.clear();
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
        requestQueue.cancelAll(requestQueue);
        /*
        stringRequest.setShouldCache(false);

        requestQueue.getCache().remove(URL);
        requestQueue.stop();
        close();
        delete();



        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
                stringRequest.setShouldCache(false);
            }
        });


         */





    }

    private void delete() {
    }

/////////******aqui termina notificacion**/////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador=getMenuInflater();
        inflador.inflate(R.menu.cerrar,menu);

        menuItem = menu.findItem(R.id.notify);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.cerrar:

                SharedPreferences admin=ct.getSharedPreferences("x",ct.MODE_PRIVATE);
                SharedPreferences.Editor data=admin.edit();
                data.remove("estado");
                data.remove("nombre");
                data.remove("cedula");
                data.remove("tip_usuario");
                data.remove("id");
                data.remove("ap");
                data.apply();

                Intent intent = new Intent( getApplicationContext(),MainActivity.class);
                startActivity(intent);

                break;

            case R.id.notify:

                intent = new Intent(getApplicationContext(), interfaz_aviso.class);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
//////////*****************////////////
    /***************************/

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification() {

        NotificationChannel CHANNEL = new NotificationChannel (CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(CHANNEL);
        showNewNotification();

    }

    private void showNewNotification() {

        setPendingIntent(interfaz_aviso.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_add_black_24dp)
                .setContentTitle("Usted tiene notificaciones pendientes")
                .setContentText("")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify( 1, builder.build());
    }


    private void setPendingIntent(Class<?> clsActivity){

        Intent intent = new Intent(this, clsActivity);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(clsActivity);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

    }
///*******//////

    public void deleteNotificationChannel(){

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // The id of the channel.
        String id = "CHANNEL_ID";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.deleteNotificationChannel(id);
        } else {
            NotificationManager manager = ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE));
            manager.cancelAll();

        }
    }
/////////****************////////

////***************//////

    public void ejecutar (){
        time time = new time();
        time.execute();
    }

////********///////

    /////////***********////////    /
    public void hilo (){
        try{
            Thread.sleep(4500);
            inc();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }

    /***********/////////
    public class time extends AsyncTask<Void, Integer, Boolean> {
///*********//////

        @SuppressLint("WrongThread")
        @Override
        protected Boolean doInBackground(Void... voids) {

            for (int i = 0; i <=1; i++) {
                hilo();
                if(i<=1){
                    //cancel(true);
                    //new time().execute();
                    //finishAffinity();
                    //finish();
                    //cancel(true);
                    //close();
                    Runtime.getRuntime().gc();
                    System.gc();
                    //onStop();
                    break;

                }else{
                    cancel(true);
                    //new time().execute();
                    Runtime.getRuntime().gc();
                    System.gc();
                    break;
                }

            }
            return true;
        }

        @Override
        protected void onPostExecute (Boolean aBoolean){
            //super.onPostExecute(aBoolean);
             //onStart();
            Toast.makeText(getApplicationContext(), "cargando", Toast.LENGTH_SHORT).show();
            Runtime.getRuntime().gc();
            System.gc();
            //new time().execute();
            //cancel(true);

        }
        @Override
        protected void onCancelled(){
            super.onCancelled();
            cancel(true);
        }
    }

}