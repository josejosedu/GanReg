package com.example.a6000832.ganreg.PackConsultas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6000832.ganreg.Modelos.Animal;
import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.BaseDatos.GestorBD;
import com.example.a6000832.ganreg.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jose Eduardo Martin.
 */

public class Cons_animal_todos extends CustomDrawerActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

    private EditText crot,crotMad,crotPad,meses;
    private TextView fechaNac,fechaUno,fechaDos;
    private RadioGroup sexo,paridas,fechas,masDeMenosDe;
    private Button consultar;
    private int fechaATratar=3,mesesATratar=0; //Valores marcados por defecto
    private String sexoATratar=2+"",paridaATratar=2+"";
    private GestorBD db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cons_animal);
        iniciarElementos();

    }

    @Override
    public void iniciarElementos() {

        db= GestorBD.getInstance(this);

        crot=(EditText)findViewById(R.id.cons_animal_crotal);
        crotMad=(EditText)findViewById(R.id.cons_animal_madre);
        crotPad=(EditText)findViewById(R.id.cons_animal_padre);
        fechaNac=(TextView)findViewById(R.id.cons_animal_fechaNac);
        fechaUno=(TextView)findViewById(R.id.cons_animal_fechaUno);
        fechaDos=(TextView)findViewById(R.id.cons_animal_fechaDos);
        meses=(EditText)findViewById(R.id.cons_animal_fechaEdad);

        sexo=(RadioGroup)findViewById(R.id.cons_animal_sexo);
        paridas=(RadioGroup)findViewById(R.id.cons_animal_paridas);
        fechas=(RadioGroup)findViewById(R.id.cons_animal_fechas);
        masDeMenosDe=(RadioGroup)findViewById(R.id.cons_masMenosMeses);

        sexo.setOnCheckedChangeListener(this);
        paridas.setOnCheckedChangeListener(this);
        fechas.setOnCheckedChangeListener(this);
        masDeMenosDe.setOnCheckedChangeListener(this);

        fechaNac.setOnClickListener(this);
        fechaUno.setOnClickListener(this);
        fechaDos.setOnClickListener(this);

        consultar=(Button)findViewById(R.id.btn_cons_consultar);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean completos=true;
                switch(fechas.getCheckedRadioButtonId()) {  //Comprobación de que ningun campo necesario esté vacio, cuando esté marcado su botón de fecha, si está marcado el de cualquier fecha no se hace comprobación
                    case R.id.cons_fechaNac:
                        if(fechaNac.getText().toString().equals(""))    //Caso de campos vacios
                        {
                            Toast.makeText(Cons_animal_todos.this, R.string.compNac, Toast.LENGTH_SHORT).show();
                            completos = false;
                        }
                        break;

                    case R.id.cons_nacidosEntre:
                        if(fechaUno.getText().toString().equals("") || fechaDos.getText().toString().equals(""))    //Caso de campos vacios
                        {
                            Toast.makeText(Cons_animal_todos.this,R.string.comDos,Toast.LENGTH_SHORT).show();
                            completos = false;
                        }
                        break;

                    case R.id.cons_edad_meses:
                        if(meses.getText().toString().equals(""))    //Caso de campos vacios
                        {
                            Toast.makeText(Cons_animal_todos.this,R.string.comMes,Toast.LENGTH_SHORT).show();
                            completos = false;
                        }
                        break;
                }
                if(completos)   //Si no ha entrado en ningún if anterior, "completos" estará en true por lo que entrará en este if, para realizar la operación de consulta que proceda
                {
                    ArrayList<Animal> animales=db.consultaAnimal(crot.getText().toString(),crotMad.getText().toString(),crotPad.getText().toString(),sexoATratar,paridaATratar,fechaATratar,fechaNac.getText().toString(),fechaUno.getText().toString(),fechaDos.getText().toString(),mesesATratar,meses.getText().toString());
                    if(animales.size()>0)   //Si la consulta devuelve un resultado, el array "animales" tendrá mas de un campo, si no, es que no ha habido coincidencias
                    {
                        Intent resultado=new Intent(Cons_animal_todos.this,Cons_animal_resultado.class);
                        resultado.putExtra("resultadoConsulta",animales);
                        startActivity(resultado);
                    }
                    else
                        Toast.makeText(Cons_animal_todos.this,R.string.noCoincidencias,Toast.LENGTH_LONG).show();   //Caso de que no haya coincidencias
                }
            }
        });


    }

    @Override
    public void onClick(final View v) {

        int mYear,mMonth,mDay;

        // Get Current Date
        //Para el calendario, por defecto se importa esto: android.icu.util.Calendar, para que funcione con todas las versiones, se importará java.util.Calendar
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        //Cuadro de diálogo para selección de fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(Cons_animal_todos.this,
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
                        switch (v.getId())  //en función del TextView donde haga click, se pondrá una fecha u otra
                        {
                            case R.id.cons_animal_fechaNac:
                                fechaNac.setText(year + "-" + mes + "-" + dia);
                                break;

                            case R.id.cons_animal_fechaUno:
                                fechaUno.setText(year + "-" + mes + "-" + dia);
                                break;

                            case R.id.cons_animal_fechaDos:
                                fechaDos.setText(year + "-" + mes + "-" + dia);
                                break;
                        }


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    //Cada vez que cambie un check de las opciones, cambiará el valor del atributo que será enviado luego para realizar la consulta
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.cons_fechaNac:
                fechaATratar=0;
                break;

            case R.id.cons_nacidosEntre:
                fechaATratar=1;
                break;

            case R.id.cons_edad_meses:
                fechaATratar=2;
                break;

            case R.id.cons_fechaCualquiera:
                fechaATratar=3;
                break;

            case R.id.cons_sexo_macho:
                sexoATratar="Macho";
                break;

            case R.id.cons_sexo_hembra:
                sexoATratar="Hembra";
                break;

            case R.id.cons_sexo_ambos:
                sexoATratar=2+"";
                break;

            case R.id.cons_paridas_no:
                paridaATratar=0+"";
                break;

            case R.id.cons_paridas_si:
                paridaATratar=1+"";
                break;

            case R.id.cons_paridas_ambas:
                paridaATratar=2+"";
                break;

            case R.id.cons_masDeMeses:
                mesesATratar=0;
                break;

            case R.id.cons_menosDeMeses:
                mesesATratar=1;
                break;

        }
    }
}