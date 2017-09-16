package com.example.a6000832.ganreg.Auxiliares;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a6000832.ganreg.Modelos.BajasMod;
import com.example.a6000832.ganreg.Modelos.ComprasMod;
import com.example.a6000832.ganreg.Modelos.NacimientosMod;
import com.example.a6000832.ganreg.Modelos.VentasMod;
import com.example.a6000832.ganreg.R;

import java.util.ArrayList;

/**
 * Created by Jose Eduardo Martin.
 */

//Clase para rellenar el recyclerview de los movimientos

public class VistaRecyclerMovimientos extends RecyclerView.Adapter<VistaRecyclerMovimientos.ViewHolderMov> implements View.OnClickListener{

/*
*
*
*
*
*
*
*
*
 */

    public VistaRecyclerMovimientos(Context context, ArrayList<Object> movimientos) {
        this.movimientos = movimientos;
        this.mContext = context;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }


    public static class ViewHolderMov extends RecyclerView.ViewHolder {
        //Elementos que voy a mostrar en cada fila
        public TextView txtCrotal,txtSexo,txtFecha;

        public ViewHolderMov(View itemView) {
            super(itemView);

            txtCrotal = (TextView) itemView.findViewById(R.id.txt_consulta_crotal);
            txtSexo = (TextView) itemView.findViewById(R.id.txt_consulta_sexo);
            txtFecha = (TextView) itemView.findViewById(R.id.txt_consulta_fechaNac);

        }
    }

    @Override
    public ViewHolderMov onCreateViewHolder(ViewGroup parent, int viewType) {
        view1 = LayoutInflater.from(mContext).inflate(R.layout.animales_recycler_fila,parent,false);
        viewHolder1 = new ViewHolderMov(view1);
        view1.setOnClickListener(this);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolderMov holder, int position) {
        //En función del tipo de movimiento que sea, se rellenarán los datos de una manera o de otra
        if(movimientos.get(position) instanceof NacimientosMod)
        {
            NacimientosMod nac=((NacimientosMod) movimientos.get(position));
            holder.txtCrotal.setText("Nacimiento");
            holder.txtFecha.setText(nac.getCrotal());
            holder.txtSexo.setText(nac.getFechaNac());
        }else if (movimientos.get(position) instanceof BajasMod)
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
    }

    @Override
    public int getItemCount() {
        return movimientos.size();
    }

}
