package com.example.a6000832.ganreg.AppFunc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.PackBajas.Muertes;
import com.example.a6000832.ganreg.PackBajas.Ventas;
import com.example.a6000832.ganreg.R;

/**
 * Created by Jose Eduardo Martin.
 */

public class Bajas extends CustomDrawerActivity {

    private Button btnMuertes,btnVentas;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pant_bajas);

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

        //Elementos en la pantalla de bajas
        btnMuertes=(Button)findViewById(R.id.btnMuertes);
        btnVentas=(Button)findViewById(R.id.btnVentas);

        btnMuertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent muertes=new Intent(Bajas.this, Muertes.class);
                startActivity(muertes);

            }
        });

        btnVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventas=new Intent(Bajas.this, Ventas.class);
                startActivity(ventas);
            }
        });


    }
}
