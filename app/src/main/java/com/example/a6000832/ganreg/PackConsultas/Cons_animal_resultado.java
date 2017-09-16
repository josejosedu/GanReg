package com.example.a6000832.ganreg.PackConsultas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.a6000832.ganreg.Modelos.Animal;
import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.Auxiliares.VistaRecyclerConsultasAnimales;
import com.example.a6000832.ganreg.R;

import java.util.ArrayList;

/**
 * Created by Jose Eduardo Martin.
 */

public class Cons_animal_resultado extends CustomDrawerActivity {

    private RecyclerView recyclerView;
    private VistaRecyclerConsultasAnimales recyclerViewAdapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ArrayList<Animal> animales;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cons_animal_resultado);
        iniciarElementos();
    }

    @Override
    public void iniciarElementos() {

        animales = (ArrayList<Animal>) getIntent().getSerializableExtra("resultadoConsulta");   //Obtengo el array de animales obtenidos de la consulta, los que quiero mostrar

        recyclerView=(RecyclerView)findViewById(R.id.rec_view_consulta);
        recylerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recylerViewLayoutManager);    //Establezco para el recyclerview,la vista donde se han de ordenar/visualizar los elementos
        recyclerViewAdapter = new VistaRecyclerConsultasAnimales(getApplicationContext(), animales);    //Creo el adaptador y le envío a éste lo que ha de mostrar
        recyclerView.setAdapter(recyclerViewAdapter);   //Al recyclerview le establezco el adaptador que he creado
        //Para saber en que elemento he hecho click
        recyclerViewAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Cons_animal_resultado.this,"Pulsado crotal: "+animales.get(recyclerView.getChildAdapterPosition(v)).getCrotal(),Toast.LENGTH_LONG).show();
                Intent verAnimal=new Intent(Cons_animal_resultado.this,Vista_ficha_animal.class);
                verAnimal.putExtra("animales",animales);
                verAnimal.putExtra("posicion",recyclerView.getChildAdapterPosition(v)); //Le envio el numero de posición que se ha pulsado, para saber dónde está en eñ arraylist
                verAnimal.putExtra("actividad",Vista_ficha_animal.VISTA_TOTAL);
                startActivity(verAnimal);

            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL)); //Lineas de separacion entre los elementos
    }
}
