package com.example.a6000832.ganreg.PackBajas;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6000832.ganreg.Auxiliares.CustomArrayAdapter;
import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.BaseDatos.GestorBD;
import com.example.a6000832.ganreg.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jose Eduardo Martin.
 */

public class Ventas extends CustomDrawerActivity {

    private TextView fechaVenta,currentDialog;
    private EditText importe,detalles,comprador;
    private AutoCompleteTextView crotal;
    private NumberPicker cantidadAnimales;
    private Button btnRegistrar;
    private AlertDialog ad;
    private int mYear, mMonth, mDay;
    private int dialogoActual=0,NUMERO_ANIMALES_VENTA=1;
    private ArrayList<String> crotalesVenta=new ArrayList<>();
    private GestorBD db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pant_ventas);
        iniciarElementos();

    }

    @Override
    public void iniciarElementos() {

        db= GestorBD.getInstance(this);

        fechaVenta=(TextView)findViewById(R.id.venta_fecha_salida);
        importe=(EditText)findViewById(R.id.venta_importe);
        detalles=(EditText)findViewById(R.id.venta_detalles);
        comprador=(EditText)findViewById(R.id.venta_comprador);
        cantidadAnimales=(NumberPicker) findViewById(R.id.venta_cantidadAnim);
        btnRegistrar=(Button) findViewById(R.id.venta_btn_registrar);

        cantidadAnimales.setMinValue(1);    //Cantidad mínima permitida de animales
        cantidadAnimales.setMaxValue(200);  //Cantidad máxima permitida de animales
        cantidadAnimales.setWrapSelectorWheel(false);   //Esto está en false para que solo puedas avanzar de uno hacia adelante. Para que antes del uno no ponga el valor máximo
        cantidadAnimales.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                NUMERO_ANIMALES_VENTA=newVal;
            }

        });

        fechaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                //Para el calendario, por defecto se importa esto: android.icu.util.Calendar, para que funcione con todas las versiones, se importará java.util.Calendar
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                //Cuadro de diálogo para selección de fecha
                DatePickerDialog datePickerDialog = new DatePickerDialog(Ventas.this,
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
                                fechaVenta.setText(year + "-" + mes + "-" + dia);
                                //fecha.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoCrotalesVentas().show();
            }
        });

    }

    public AlertDialog dialogoCrotalesVentas(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_registrar_ventas, null);
        configurarDialogo(v);
        builder.setView(v);


        builder.setPositiveButton(R.string.siguiente, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.anterior, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        ad=builder.create();

        currentDialog.setText((dialogoActual+1)+"");


        ad.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button siguiente = ad.getButton(AlertDialog.BUTTON_POSITIVE);
                siguiente.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        //Se comprueba que cuando se pulse siguiente, no queden campos sin completar
                        //En el campo detalles, al principio siempre hay un espacio, por si no se completa que no de error... Hay que arreglar esto
                        String cr=crotal.getText().toString();
                        boolean uno= cr.equals("");
                        if(uno)
                            Toast.makeText(Ventas.this, R.string.faltaCrotal,Toast.LENGTH_SHORT).show();
                        else    //Dismiss si toodo está correcto, y guardamos en el array los datos
                        {
                            if (crotalesVenta.size() == dialogoActual) {
                                crotalesVenta.add(cr);
                                dialogoActual++;
                                if (dialogoActual < NUMERO_ANIMALES_VENTA)
                                    crotal.setText("");
                                else
                                    ad.dismiss();
                            }else if(crotalesVenta.size()-dialogoActual-1==0 && dialogoActual < NUMERO_ANIMALES_VENTA) {
                                crotalesVenta.set(dialogoActual, cr);
                                dialogoActual++;
                                crotal.setText("");
                            }
                            else if (crotalesVenta.size()-dialogoActual-1>0)
                            {
                                crotalesVenta.set(dialogoActual, cr);
                                dialogoActual++;
                                String fichaAnterior = crotalesVenta.get(dialogoActual);    //Esto es para actualizar el campo, poner el campo anterior
                                crotal.setText(fichaAnterior);
                            }
                            currentDialog.setText((dialogoActual+1)+"");
                        }
                        if(crotalesVenta.size()==NUMERO_ANIMALES_VENTA)//Aquí guardamos los registros que están en el array en la base de datos
                        {
                            guardarRegistros();
                        }
                    }
                });

                Button anterior = ad.getButton(AlertDialog.BUTTON_NEGATIVE);
                anterior.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if(dialogoActual>0)
                        {
                            if(crotalesVenta.size()!=dialogoActual)
                            {
                                String cr = crotal.getText().toString();
                                crotalesVenta.set(dialogoActual, cr);
                            }
                            dialogoActual--;
                            currentDialog.setText((dialogoActual + 1) + "");
                            String fichaAnterior = crotalesVenta.get(dialogoActual);    //Esto es para actualizar el campo, poner el campo anterior
                            crotal.setText(fichaAnterior);
                        }
                    }
                });

                Button cancelar = ad.getButton(AlertDialog.BUTTON_NEUTRAL);
                cancelar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                    }
                });
            }
        });

        ad.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                crotalesVenta.clear();
                dialogoActual=0;
            }
        });

        return ad;
    }

    public void configurarDialogo(View v){
        crotal=(AutoCompleteTextView)v.findViewById(R.id.venta_crotal);
        currentDialog=(TextView)v.findViewById(R.id.venta_animalActual);

        List<String> crotales=db.listaCrotalesEspec("");
        //El constructor de la clase CustomArrayAdapter es el mismo que el de ArrayAdapter, ya que esta implementa a ArrayAdapter
        CustomArrayAdapter namesAdapterCrotales = new CustomArrayAdapter(
                this,
                R.layout.pant_muertes,
                R.id.lbl_name,
                crotales
        );
        crotal.setAdapter(namesAdapterCrotales);
        crotal.setThreshold(1);

    }

    public void guardarRegistros(){
        if(db.registrarVentas(crotalesVenta,fechaVenta.getText().toString(),NUMERO_ANIMALES_VENTA+"",importe.getText().toString(),detalles.getText().toString(),comprador.getText().toString()))
            Toast.makeText(this,R.string.ventaOk,Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,R.string.error1,Toast.LENGTH_SHORT).show();


    }

}
