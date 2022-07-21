package com.example.siteapp;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class adapter_notify1 extends RecyclerView.Adapter<adapter_notify1.adapter_notificacion> {

    ArrayList<list_notificacion1> items;

    public adapter_notify1(ArrayList<list_notificacion1> items) {

        this.items=items;


    }

    @NonNull
    @Override
    public adapter_notify1.adapter_notificacion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notificacion1, parent, false);
        return new adapter_notificacion(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_notify1.adapter_notificacion holder, int position) {

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


                switch (origen) {
                    case "1":
                        Intent intent=new Intent(v.getContext(),interfaz_noti_detalle.class);
                        //intent.putExtra("estado",estado);
                        intent.putExtra("comentario",comentario);
                        intent.putExtra("asunto",holder.asunto.getText().toString());
                        intent.putExtra("idNoti",idNoti);
                        intent.putExtra("idNoti",origen);

                        v.getContext().startActivity(intent);


                        break;
                    case "2":
                        intent = new Intent(v.getContext(), interfaz_suge_detalle.class);
                        //intent.putExtra("estado",estado);
                        intent.putExtra("comentario",comentario);
                        intent.putExtra("asunto",holder.asunto.getText().toString());
                        intent.putExtra("idNoti",idNoti);
                        intent.putExtra("idNoti",origen);

                        v.getContext().startActivity(intent);

                        break;

                }

            }
        });

        /**/
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




        public adapter_notificacion (@NonNull View itemView) {
            super(itemView);

            cv=itemView.findViewById(R.id.cv);
            asunto=itemView.findViewById(R.id.asunto);
            estado=itemView.findViewById(R.id.estado);
            fecha=itemView.findViewById(R.id.fecha);
            icono=itemView.findViewById(R.id.icono);

//            if (estado.findViewById(R.id.notii).equals("V")){
//
//                notii = itemView.findViewById(R.id.notii);
//                notii.findViewById(R.id.notii).setVisibility(View.INVISIBLE);
//            }
//
//            else{
//                notii = itemView.findViewById(R.id.notii);
//                notii.findViewById(R.id.notii).setVisibility(View.VISIBLE);
//            }


            /*cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("result","CardView");
                }
            });*/



        }


    }

//    public void updateData(ArrayList<Incidencias> items) {
//        this.items = items;
////        items.clear();
////        items.addAll(items);
//
//        //notifyDataSetChanged();
//    }

}
