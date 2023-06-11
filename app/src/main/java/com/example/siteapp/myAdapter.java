package com.example.siteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ContenetViews> {

    ArrayList<Incidencias> items;

    public myAdapter(ArrayList<Incidencias> items) {

        this.items=items;
    }

    @NonNull
    @Override
    public myAdapter.ContenetViews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist, parent, false);

        return new ContenetViews(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter.ContenetViews holder, int position) {

        String comentario=(items.get(position).comentario);
        String cedula=items.get(position).cedula;
        String departamento=items.get(position).departamento;


        holder.tipo.setText(items.get(position).tipo);
        holder.idIncidencias.setText("#"+items.get(position).idIncidencias);
        holder.fecha.setText(items.get(position).hora);
        holder.estado.setText(items.get(position).estado);
        holder.idClient.setText(items.get(position).idUser);
        String lp = items.get(position).lp;
        String pl=items.get(position).idIncidencias;

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences admin=v.getContext().getSharedPreferences("x", Context.MODE_PRIVATE);
                String tip_usuario=admin.getString("tip_usuario","");

                String ip=admin.getString("ip","");
                Log.i("resulhp", "Array: " + ip.toString());

                    switch (tip_usuario.toString()) {
                        case "C":

                            Intent intent=new Intent(v.getContext(),interfaz_incidencias_usuario.class);
                            intent.putExtra("idClient",holder.idClient.getText().toString());
                            intent.putExtra("idIncidencia",pl.toString());
                            intent.putExtra("tipo",holder.tipo.getText().toString());
                            v.getContext().startActivity(intent);

                            break;
                        case "D":

                            intent = new Intent(v.getContext(), interfaz_mostrar_incidencias_tecnicas_nivel_usuario.class);
                            intent.putExtra("idClient", holder.idClient.getText().toString());
                            intent.putExtra("idIncidencia", pl.toString());
                            intent.putExtra("cedula", cedula);
                            intent.putExtra("departamento", departamento);
                            intent.putExtra("estado", holder.estado.getText().toString());
                            intent.putExtra("comentario", comentario);
                            intent.putExtra("tipo", holder.tipo.getText().toString());
                            v.getContext().startActivity(intent);

                            break;

                        case "T":
                            intent = new Intent(v.getContext(), interfaz_mostrar_incidencias_tecnicas_nivel_usuario.class);
                            intent.putExtra("idClient", holder.idClient.getText().toString());
                            intent.putExtra("idIncidencia", pl.toString());
                            intent.putExtra("cedula", cedula);
                            intent.putExtra("departamento", departamento);
                            intent.putExtra("estado", holder.estado.getText().toString());
                            intent.putExtra("comentario", comentario);
                            intent.putExtra("tipo", holder.tipo.getText().toString());
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


    public class ContenetViews extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tipo;
        TextView idIncidencias;
        TextView fecha;
        TextView estado;
        TextView idClient;
        ImageView icono;


        public ContenetViews(@NonNull View itemView) {
            super(itemView);

            cv=itemView.findViewById(R.id.cv);
            tipo=itemView.findViewById(R.id.tipo);
            idIncidencias=itemView.findViewById(R.id.comentario);
            fecha=itemView.findViewById(R.id.fecha);
            estado=itemView.findViewById(R.id.refactor);
            icono=itemView.findViewById(R.id.icono);
            idClient=itemView.findViewById(R.id.estado);

        }


    }

}
