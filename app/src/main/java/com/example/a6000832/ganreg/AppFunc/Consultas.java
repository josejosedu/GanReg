package com.example.a6000832.ganreg.AppFunc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.PackConsultas.Cons_animal_todos;
import com.example.a6000832.ganreg.PackConsultas.Cons_movimientos;
import com.example.a6000832.ganreg.PackConsultas.Cons_anim_indiv;
import com.example.a6000832.ganreg.R;

/**
 * Created by Jose Eduardo Martin.
 */

public class Consultas extends CustomDrawerActivity implements View.OnClickListener{

    private Button animal,total,movim;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pant_consultas);

        iniciarElementos();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainInicio.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //Con esto consigo que si ya existe una instancia de la actividad, la abra en vez de crear una nueva
        startActivity(intent);
    }

    @Override
    public void iniciarElementos() {

        //Elementos en la pantalla de consultas
        animal=(Button)findViewById(R.id.btn_cons_animal);
        total=(Button)findViewById(R.id.btn_cons_censo);
        movim=(Button)findViewById(R.id.btn_cons_movim);

        animal.setOnClickListener(this);
        total.setOnClickListener(this);
        movim.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //En función del botón que se toque en la pantalla
        switch (v.getId())
        {
            case R.id.btn_cons_animal:
                Intent ani=new Intent(this, Cons_animal_todos.class);
                startActivity(ani);

                break;
            case R.id.btn_cons_censo:
                Intent tot=new Intent(this, Cons_anim_indiv.class);
                startActivity(tot);

                break;

            case R.id.btn_cons_movim:
                Intent mov=new Intent(this, Cons_movimientos.class);
                startActivity(mov);

                break;
        }
    }
}
