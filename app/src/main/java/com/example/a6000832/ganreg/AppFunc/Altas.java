package com.example.a6000832.ganreg.AppFunc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.PackAltas.Compras;
import com.example.a6000832.ganreg.PackAltas.Nacimiento;
import com.example.a6000832.ganreg.R;

/**
 * Created by Jose Eduardo Martin.
 */

public class Altas extends CustomDrawerActivity {

    private Button btnNacimientos,btnCompras;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pant_altas);

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

        //Elementos en la pantalla de altas
        btnNacimientos=(Button)findViewById(R.id.btnNacimientos);
        btnCompras=(Button)findViewById(R.id.btnCompras);

        btnNacimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nacimiento=new Intent(Altas.this,Nacimiento.class);
                startActivity(nacimiento);
            }
        });
        btnCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compras=new Intent(Altas.this,Compras.class);
                startActivity(compras);
            }
        });

    }
}
