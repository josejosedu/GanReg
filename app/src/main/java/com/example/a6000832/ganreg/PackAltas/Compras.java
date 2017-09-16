package com.example.a6000832.ganreg.PackAltas;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.BaseDatos.GestorBD;
import com.example.a6000832.ganreg.R;

import java.util.ArrayList;

/**
 * Created by Jose Eduardo Martin.
 */

public class Compras extends CustomDrawerActivity {

    private GestorBD db;
    private Button btnFechaEntrada,btnRegistrarCompras,btnFechaNacimiento;
    private TextView textoFecha,animalActual;
    private int mYear, mMonth, mDay,NUMERO_ANIMALES_ALTA=1; //Pongo 1 por defecto, por si solo se va a usar uno, pero no se toca la rueda de selección, no se marca ningún valor
    private NumberPicker cantidadAnimales;
    private ArrayList<String> valores=new ArrayList<>();
    private EditText crotal,raza,fecha,detalles;
    private Spinner sexo;
    private CheckBox parida;
    private AlertDialog ad;
    private int dialogoActual=0;
    private int se;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pant_compras);

        iniciarElementos();

    }

//******************************************Método para la creación de cuadro de diálogo, para recogida de los datos de la compra de cada animal
    public AlertDialog crearDialogoAltasCompras(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_altas_compras, null);
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

        animalActual.setText((dialogoActual+1)+"");

        //Esto es para que cuando se pulse possitivebutton, no se cierre el diálogo
        ad.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {
                //Los bloques de else-if dentro de los botones positivo y negativc, son para cuando se avance o se retroceda (anterior o siguiente) en el cuadro de diálogo
                //se vayan cambiando los elementos en función de lo establecido anteriormente, y que se pueda modificar algo ya introducido anteriormente
                Button siguiente = ad.getButton(AlertDialog.BUTTON_POSITIVE);
                siguiente.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        //Se comprueba que cuando se pulse siguiente, no queden campos sin completar
                        //En el campo detalles, al principio siempre hay un espacio, por si no se completa que no de error... Hay que arreglar esto
                        String cr=crotal.getText().toString();
                        String ra=raza.getText().toString();
                        //se, para sexo
                        String fe=fecha.getText().toString();
                        int par=(parida.isChecked())?1:0;  //Operador ternario, si parida está marcado, valdrá 1, si no, 0
                        boolean uno,dos,tres;
                        uno= cr.equals("");
                        dos=ra.equals("");
                        tres=fe.equals("");
                        if(uno || dos || tres)
                            Toast.makeText(Compras.this, "Faltan datos",Toast.LENGTH_SHORT).show();
                        else    //Dismiss si toodo está correcto, y guardamos en el array los datos
                        {
                            if (valores.size() == dialogoActual) {
                                valores.add(cr + "#" + ra + "#" + se + "#" + fe + "#"+ par);
                                dialogoActual++;
                                if (dialogoActual < NUMERO_ANIMALES_ALTA)
                                    limpiarCamposDialogo();
                                else
                                    ad.dismiss();
                            }else if(valores.size()-dialogoActual-1==0 && dialogoActual < NUMERO_ANIMALES_ALTA) {
                                valores.set(dialogoActual, cr + "#" + ra + "#" + se + "#" + fe + "#"+ par);
                                dialogoActual++;
                                limpiarCamposDialogo();
                            }
                            else if (valores.size()-dialogoActual-1>0)
                            {
                                valores.set(dialogoActual, cr + "#" + ra + "#" + se + "#" + fe + "#"+ par);
                                dialogoActual++;
                                actualizarCamposDialogo();
                            }
                            animalActual.setText((dialogoActual+1)+"");
                        }
                        if(valores.size()==NUMERO_ANIMALES_ALTA)//Aquí guardamos los registros que están en el array en la base de datos
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
                            if(valores.size()!=dialogoActual)
                            {
                                String cr = crotal.getText().toString();
                                String ra = raza.getText().toString();
                                //se, para sexo
                                String fe = fecha.getText().toString();
                                int par=(parida.isChecked())?1:0;  //Operador ternario, si parida está marcado, valdrá 1, si no, 0
                                valores.set(dialogoActual, cr + "#" + ra + "#" + se + "#" + fe + "#"+ par);
                            }
                            dialogoActual--;
                            animalActual.setText((dialogoActual + 1) + "");
                            actualizarCamposDialogo();
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
        //Cuando se cierre el cuádro de diálogo se borrarán todos los datos del array y se reiniciarán las variables, se guardarán en la base de datos los registros (en el possitivebutton cuando sea el final)
        ad.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                valores.clear();
                dialogoActual=0;
            }
        });

        return ad;
    }

    public void actualizarCamposDialogo(){
        String fichaAnterior = valores.get(dialogoActual);
        crotal.setText(fichaAnterior.split("#")[0]);
        raza.setText(fichaAnterior.split("#")[1]);
        sexo.setSelection(Integer.parseInt(fichaAnterior.split("#")[2]));
        fecha.setText(fichaAnterior.split("#")[3]);
        parida.setChecked(Integer.parseInt(fichaAnterior.split("#")[4])==1);
    }

    public void limpiarCamposDialogo(){
        crotal.setText("");
        raza.setText("");
        sexo.setSelection(0);
        fecha.setText("");
    }
//**************************************Método para la configuración del cuádro de diálogo
    public void configurarDialogo(View v){
        crotal=(EditText)v.findViewById(R.id.crotCompra);
        raza=(EditText)v.findViewById(R.id.razaCompra);
        sexo=(Spinner)v.findViewById(R.id.sexoCompra);
        fecha=(EditText)v.findViewById(R.id.fechaNacimCompra);
        fecha.setKeyListener(null);     //Le quitamos el listener para que no se pueda escribir en el (editable del xml está deprecated)
        btnFechaNacimiento=(Button)v.findViewById(R.id.btnFechaNacimiento);
        btnFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //Para el calendario, por defecto se importa esto: android.icu.util.Calendar, para que funcione con todas las versiones, se importará java.util.Calendar
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                //Cuadro de diálogo para selección de fecha
                DatePickerDialog datePickerDialog = new DatePickerDialog(Compras.this,
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
                                fecha.setText(year + "-" + mes + "-" + dia);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        parida =(CheckBox)v.findViewById(R.id.paridaCompra);
        animalActual=(TextView)v.findViewById(R.id.animalActual);

        sexo.setAdapter(new ArrayAdapter<String>(this, R.layout.aux_item_spinner, getResources().getStringArray(R.array.array_sexos)));
        sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                Toast.makeText(adapterView.getContext(), sexo.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                se=position;
                //Cada vez que se marque el item "Macho", el valor de la casilla "parida" se pondrá a false a la vez que invisible para que no se pueda modificar
                if(position==0) {
                    parida.setChecked(false);
                    parida.setVisibility(View.INVISIBLE);
                }
                else
                    parida.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio
            }
        });
    }

    public void guardarRegistros(){
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
 */
    }

    @Override
    public void iniciarElementos() {

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
}