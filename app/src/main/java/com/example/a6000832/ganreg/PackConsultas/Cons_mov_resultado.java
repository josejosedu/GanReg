package com.example.a6000832.ganreg.PackConsultas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.Auxiliares.VistaRecyclerMovimientos;
import com.example.a6000832.ganreg.Modelos.BajasMod;
import com.example.a6000832.ganreg.Modelos.ComprasMod;
import com.example.a6000832.ganreg.Modelos.NacimientosMod;
import com.example.a6000832.ganreg.Modelos.VentasMod;
import com.example.a6000832.ganreg.R;

import java.util.ArrayList;

/**
 * Created by Jose Eduardo Martin.
 */

public class Cons_mov_resultado extends CustomDrawerActivity{

    private RecyclerView recyclerView;
    private VistaRecyclerMovimientos recyclerViewAdapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ArrayList<Object> lista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cons_animal_resultado);
        iniciarElementos();
    }

    @Override
    public void iniciarElementos() {

        lista = (ArrayList<Object>) getIntent().getSerializableExtra("movimientos");   //Obtengo el array de animales obtenidos de la consulta, los que quiero mostrar

        recyclerView = (RecyclerView) findViewById(R.id.rec_view_consulta);
        recylerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recylerViewLayoutManager);    //Establezco para el recyclerview,la vista donde se han de ordenar/visualizar los elementos
        recyclerViewAdapter = new VistaRecyclerMovimientos(getApplicationContext(), lista);    //Creo el adaptador y le envío a éste lo que ha de mostrar
        recyclerView.setAdapter(recyclerViewAdapter);   //Al recyclerview le establezco el adaptador que he creado
        //Para saber en que elemento he hecho click
        recyclerViewAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoMovimiento(v);
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); //Lineas de separacion entre los elementos
    }

    public void infoMovimiento(View v){
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Información");
        String msg="";

        Object obj=lista.get(recyclerView.getChildAdapterPosition(v));
        if(obj instanceof NacimientosMod){
            NacimientosMod nac=((NacimientosMod) obj);
            msg=nac.getContinua().equals("0")?"No continua en la explotación":"Continua en la explotación";
        }
        else if (obj instanceof BajasMod) {
            BajasMod baja = ((BajasMod) obj);
            msg=baja.getMotivo();
        }
        else if (obj instanceof ComprasMod) {
            ComprasMod compra = ((ComprasMod) obj);
            msg=compra.getDetalles();
        }
        else if (obj instanceof VentasMod) {
            VentasMod venta = ((VentasMod) obj);
            msg="**"+R.string.comprador+": "+venta.getComprador()+"**"+R.string.detalles+": "+venta.getDetalles()+"**"+R.string.importe+": "+venta.getImporte();
        }
        confirm.setMessage(msg);
        confirm.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        confirm.setCancelable(false);   //Para que no aparezca el botón cancelar
        confirm.show();
    }

}
