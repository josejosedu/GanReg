package com.example.a6000832.ganreg.Auxiliares;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a6000832.ganreg.Modelos.Animal;
import com.example.a6000832.ganreg.R;

import java.util.ArrayList;

/**
 * Created by Jose Eduardo Martin.
 */

//Clase para la definición del RecyclerView, la vista de lista en las consultas

public class VistaRecyclerConsultasAnimales extends RecyclerView.Adapter<VistaRecyclerConsultasAnimales.ViewHolder> implements View.OnClickListener{

    private ArrayList<Animal> mAnimal;
    private Context mContext;
    View view1;
    ViewHolder viewHolder1;
    private View.OnClickListener listener;


    public VistaRecyclerConsultasAnimales(Context context, ArrayList<Animal> animales) {
        mAnimal = animales;
        mContext = context;
    }
    //Listener para cuando se pulse en cada elemento, lo tendré que redefinir en la clase que se usa, para que haga lo que se requiera
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Elementos que voy a mostrar en cada fila
        public TextView txtCrotal,txtSexo,txtFecha;

        public ViewHolder(View itemView) {
            super(itemView);

            txtCrotal = (TextView) itemView.findViewById(R.id.txt_consulta_crotal);
            txtSexo = (TextView) itemView.findViewById(R.id.txt_consulta_sexo);
            txtFecha = (TextView) itemView.findViewById(R.id.txt_consulta_fechaNac);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/*
*
*
*
*
*
*
*
*
*
*
*
*
 */

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtCrotal.setText(mAnimal.get(position).getCrotal());
        holder.txtFecha.setText(mAnimal.get(position).getFenac());
        holder.txtSexo.setText(mAnimal.get(position).getSexo());

    }

    @Override
    public int getItemCount() {
        return mAnimal.size();
    }



}
