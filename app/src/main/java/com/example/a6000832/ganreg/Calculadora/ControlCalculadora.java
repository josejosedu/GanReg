package com.example.a6000832.ganreg.Calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.R;

/**
 * Created by Jose Eduardo Martin.
 */

public class ControlCalculadora extends CustomDrawerActivity implements View.OnClickListener{

    private OperacionesCalculadora operar;
    private TextView resOp;
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnmas,btnmenos,btnpor,btnentre,btnmodulo,btnigual,btnac,btnpunt;
    private Button btnConversor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculador_vista);

        iniciarElementos();
    }

    public ControlCalculadora(){
    }

    @Override
    public void onClick(View v) {
        Button pulsado=(Button)v;
        String valor=pulsado.getText().toString();
        if(!valor.equals("AC"))//Si lo pulsado no es All Clear
        {
            //Cuando lo pulsado no sea un operador o el igual. Creo una cadena y voy añadiendo los numeros pulsados
            if(!valor.equals("=") &&
                    !valor.equals("+") &&
                    !valor.equals("-") &&
                    !valor.equals("*") &&
                    !valor.equals("/")&&
                    !valor.equals("%"))
            {
                operar.setCadena(valor);
                resOp.setText(operar.getCadena());//Voy cambiando la cadena en el texfield, para que se vea el numero pulsado hasta entonces
            }//FIN IF
            else    //Cuando pulsemos un simbolo de operacion o el igual
            {
                if(!operar.getCadena().equals(""))  //Si la cadena no está vacia, guardo el numero que llevaba hasta que he pulsado el operador
                    operar.anadirNumero();
                if(!valor.equals("=") && operar.cuantosCompletos()==1)//Si no se ha pulsado el igual y ya hay un numero metido, al entrar en este if, quiere decir que lo que se
                    operar.setSimbolo(valor);                         //ha pulsado ha sido un operador, por lo que se guarda ese símbolo para operar con el
            }//FIN ELSE
            if(operar.cuantosCompletos()==2)    //Cuando ya tengamos dos numeros disponibles para operar, inmediatamente al meter el segundo se hace la operacion, pero se pueden seguir metiendo
            //numeros, el nuevo numero que metamos operará directamente con el resultado de la operacion de los anteriores dos
            {
                switch(operar.getSimbolo())
                {
                    case "+":
                        operar.sumar();
                        break;

                    case "-":
                        operar.restar();
                        break;

                    case "*":
                        operar.multiplicar();
                        break;

                    case "/":
                        operar.dividir();
                        break;
                    case "%":
                        operar.modulo();
                        break;
                }
                operar.vaciarDos();//Cuando realicemos la operacion vacio el segundo numero para que este disponible por si se quiere introducir otro numero, y el resultado de la operacion
                //se guarda en el primer numero, reemplazando al que estuviese
                operar.vaciarCadena();  //Vacio la cadena que iba guardando las pulsaciones de los botones numéricos
                operar.setSimbolo(valor);   //esto es por si al final de realizar la operacion con dos numeros, en vez de darle al igual, pulsamos otro simbolo para introducir otro numero
            }//FIN IF
            if(valor.equals("=") && operar.cuantosCompletos()!=0){ //Al pulsar el igual  cambia el valor del textfield para mostrar el resultado y el simbolo se vacia, para dar paso al nuevo si es que lo hay, el distinto de cero es por si al principio se le da a igual y no hay ningun nuemero a
                resOp.setText(operar.getNumeros(0)+"");
                operar.setSimbolo("");
            }
        }
        else    //Si lo pulsado es All Clear (AC)
        {
            operar.allClear();
            resOp.setText("");
        }

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
