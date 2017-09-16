package com.example.a6000832.ganreg.AppFunc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.BaseDatos.GestorBD;
import com.example.a6000832.ganreg.R;

/**
 * Created by Jose Eduardo Martin.
 */

public class Crot_Manual extends CustomDrawerActivity {

    private EditText introducir;
    private Button guardar;
    private GestorBD db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gcrot_registrar_manual);

        db= GestorBD.getInstance(this);

        iniciarElementos();

    }

    @Override
    public void iniciarElementos() {

        introducir=(EditText)findViewById(R.id.nuevo_crotal);
        guardar=(Button)findViewById(R.id.btnGuardarCrotal);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevo=introducir.getText().toString();
                if(nuevo.equals(""))
                    Toast.makeText(Crot_Manual.this,R.string.completaCampo,Toast.LENGTH_SHORT).show();
                else {
                    db.insertarNuevoCrotal(nuevo);
                    introducir.setText("");
                }
            }
        });

    }
}
