package com.example.a6000832.ganreg.PackConsultas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.BaseDatos.GestorBD;
import com.example.a6000832.ganreg.BaseDatos.UsoBD;
import com.example.a6000832.ganreg.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jose Eduardo Martin.
 */

public class Cons_movimientos extends CustomDrawerActivity implements CompoundButton.OnCheckedChangeListener{

    private CheckBox nacimientos,muertes,compras,ventas;
    private boolean nac,muer,comp,vent;
    private TextView fe1,fe2;
    private Button verMovimiento;
    private ArrayList<Object> lista=new ArrayList<>();  //Lo llenaré con distintos tipos de movimientos, pero todos en el mismo, para mostrarlo en el mismo recyclerview
    private GestorBD db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cons_movimientos);
        iniciarElementos();
    }

    @Override
    public void iniciarElementos() {

        db= GestorBD.getInstance(this);

        nacimientos=(CheckBox)findViewById(R.id.mov_nacimiento);
        muertes=(CheckBox)findViewById(R.id.mov_muerte);
        compras=(CheckBox)findViewById(R.id.mov_compra);
        ventas=(CheckBox)findViewById(R.id.mov_venta);
        fe1=(TextView)findViewById(R.id.mov_animal_fechaUno);
        fe2=(TextView)findViewById(R.id.mov_animal_fechaDos);
        verMovimiento=(Button)findViewById(R.id.btn_ver_movim);

        nacimientos.setOnCheckedChangeListener(this);
        muertes.setOnCheckedChangeListener(this);
        compras.setOnCheckedChangeListener(this);
        ventas.setOnCheckedChangeListener(this);

        fe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFecha(fe1.getId());
            }
        });

        fe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFecha(fe2.getId());
            }
        });

        verMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nac && !muer && !comp && !vent) //Si no hay ninguno seleccionado
                {
                    Toast.makeText(Cons_movimientos.this,R.string.selecMov,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(nac)
                        lista.addAll(db.getListaMovimientos(UsoBD.TABLA_NACIMIENTOS,fe1.getText().toString(),fe2.getText().toString()));
                    if(muer)
                        lista.addAll(db.getListaMovimientos(UsoBD.TABLA_BAJAS,fe1.getText().toString(),fe2.getText().toString()));
                    if(vent)
                        lista.addAll(db.getListaMovimientos(UsoBD.TABLA_VENTAS,fe1.getText().toString(),fe2.getText().toString()));
                    if(comp)
                        lista.addAll(db.getListaMovimientos(UsoBD.TABLA_COMPRAS,fe1.getText().toString(),fe2.getText().toString()));
                    Intent resultado=new Intent(Cons_movimientos.this,Cons_mov_resultado.class);
                    resultado.putExtra("movimientos",lista);
                    startActivity(resultado);
                    lista.clear();  //Una vez enviado, lo borro, para que si se vuelve a esta pantalla en la misma ejecución, esté a cero y se inicie con las opciones elegidas, si no hago esto, se añadirían a lo elegido anteriormente
                }

            }
        });

    }

    public void cambiarFecha(final int id){
        int mYear,mMonth,mDay;
        //Para el calendario, por defecto se importa esto: android.icu.util.Calendar, para que funcione con todas las versiones, se importará java.util.Calendar
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        //Cuadro de diálogo para selección de fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(Cons_movimientos.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String mes,dia;
                        mes=(monthOfYear+1)+"";
                        dia=dayOfMonth+"";
                        if(monthOfYear+1<10)    //Si es menor que 10, para que se añada un cero a la izquierda
                            mes=0+mes;
                        if(dayOfMonth<10)
                            dia=0+dia;
                        switch (id)  //en función del TextView donde haga click, se pondrá una fecha u otra
                        {
                            case R.id.mov_animal_fechaUno:
                                fe1.setText(year + "-" + mes + "-" + dia);
                                break;

                            case R.id.mov_animal_fechaDos:
                                fe2.setText(year + "-" + mes + "-" + dia);
                                break;
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId())
        {
            case R.id.mov_nacimiento:
                nac=isChecked;
                break;

            case R.id.mov_muerte:
                muer=isChecked;
                break;

            case R.id.mov_compra:
                comp=isChecked;
                break;

            case R.id.mov_venta:
                vent=isChecked;
                break;
        }
    }
}