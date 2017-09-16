package com.example.a6000832.ganreg.Calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a6000832.ganreg.AppFunc.MainInicio;
import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.R;

/**
 * Created by Jose Eduardo Martin.
 */

public class CUnidades extends CustomDrawerActivity{

    private EditText euros,pesetas;
    private Button btnCalculadora;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pant_conversor);

        iniciarElementos();

        euros.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(euros.hasFocus() && !s.toString().equals("")) {
                    String texto = s+"";
                    if (s.toString().contains(","))  //Si el usuario introduce comas, que se eliminen a la hora de hacer los cálculos
                        texto = s.toString().replace(",", "");
                    double euros = Double.parseDouble(texto);
                    double conver = euros * 166.386;
                    pesetas.setText(String.format("%,.3f", conver));
                }
                else if(s.toString().equals("") && euros.hasFocus())
                    pesetas.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pesetas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pesetas.hasFocus() && !s.toString().equals("")) {
                    String texto = s+"";
                    if (s.toString().contains(","))  //Si el usuario introduce comas, que se eliminen a la hora de hacer los cálculos
                        texto = s.toString().replace(",", "");
                    double pesetas = Double.parseDouble(texto);
                    double conver = pesetas / 166.386;
                    euros.setText(String.format("%,.3f", conver));
                }
                else if(s.toString().equals("") && pesetas.hasFocus())
                    euros.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainInicio.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //Con esto consigo que si ya existe una instancia de la actividad, la abra en vez de crear una nueva
        startActivity(intent);


    }

    @Override
    public void iniciarElementos() {

        euros=(EditText)findViewById(R.id.etEuros);
        pesetas=(EditText)findViewById(R.id.etPesetas);

        btnCalculadora=(Button)findViewById(R.id.btn_ir_calculadora);

        btnCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculadora = new Intent(CUnidades.this, ControlCalculadora.class);
                startActivity(calculadora);
            }
        });

    }
}
