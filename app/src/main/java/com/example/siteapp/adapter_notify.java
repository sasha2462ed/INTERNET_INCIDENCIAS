package com.example.siteapp;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapter_notify extends RecyclerView.Adapter<adapter_notify.adapter_notificacion> {

    ArrayList<list_notificacion> items;

    public adapter_notify(ArrayList<list_notificacion> items) {

        this.items=items;


    }

    @NonNull
    @Override
    public adapter_notify.adapter_notificacion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notificacion, parent, false);
        return new adapter_notificacion(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_notify.adapter_notificacion holder, @SuppressLint("RecyclerView") int position) {

        //String estado =items.get(position).estado;
        String comentario= items.get(position).comentario;
        holder.asunto.setText(items.get(position).asunto);
        holder.estado.setText(items.get(position).estado);
        holder.fecha.setText(items.get(position).fecha);
        String idNoti = items.get(position).idNoti;
        String origen= items.get(position).origen;

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idNoti = String.valueOf(items.get(position).idNoti);

                switch (origen) {
                    case "1":

                        Intent intent=new Intent(v.getContext(),interfaz_noti_detalle.class);
                        //intent.putExtra("estado",estado);
                        intent.putExtra("comentario",comentario);
                        intent.putExtra("asunto",holder.asunto.getText().toString());
                        intent.putExtra("idNoti",idNoti);
                        intent.putExtra("origen",origen);
                        v.getContext().startActivity(intent);

                        break;
                    case "2":
                        intent = new Intent(v.getContext(), interfaz_suge_detalle.class);
                        //intent.putExtra("estado",estado);
                        intent.putExtra("comentario", comentario);
                        intent.putExtra("asunto", holder.asunto.getText().toString());
                        intent.putExtra("idNoti", idNoti);
                        intent.putExtra("origen", origen);
                        v.getContext().startActivity(intent);
                        break;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class adapter_notificacion extends RecyclerView.ViewHolder {

        CardView cv;
        TextView asunto;
        TextView estado;
        TextView fecha;
        ImageView icono;
        ImageView notii;



        public adapter_notificacion (@NonNull View itemView) {
            super(itemView);

            cv=itemView.findViewById(R.id.cv);
            asunto=itemView.findViewById(R.id.asunto);
            estado=itemView.findViewById(R.id.estado);
            fecha=itemView.findViewById(R.id.fecha);
            icono=itemView.findViewById(R.id.icono);

        }
    }
}
