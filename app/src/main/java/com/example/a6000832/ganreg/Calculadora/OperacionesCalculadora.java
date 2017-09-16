package com.example.a6000832.ganreg.Calculadora;

import java.util.ArrayList;

/**
 * Created by Jose Eduardo Martin.
 */

public class OperacionesCalculadora {

    private ArrayList<Double> numeros;
    private String cadena;
    private String simbolo;

    public OperacionesCalculadora(){
        this.cadena="";
        numeros=new ArrayList();
    }

    public void setCadena(String cadena) {
        this.cadena += cadena;
    }

    public void vaciarCadena(){
        this.cadena="";
    }

    public String getCadena(){
        return this.cadena;
    }

    public void anadirNumero(){
        numeros.add(Double.parseDouble(cadena));
        this.cadena="";
    }

    public int cuantosCompletos(){
        return numeros.size();
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Double getNumeros(int numero) {
        return numeros.get(numero);
    }
    //Se podría hacer todoo con un método "realizarOperacion" con un switch en función del símbolo
    public void sumar(){
        this.numeros.set(0, numeros.get(0)+numeros.get(1));
    }

    public void restar(){
        this.numeros.set(0, numeros.get(0)-numeros.get(1));
    }

    public void multiplicar(){
        this.numeros.set(0, numeros.get(0)*numeros.get(1));
    }

    public void dividir(){
        this.numeros.set(0, numeros.get(0)/numeros.get(1));
    }

    public void modulo(){
        this.numeros.set(0, numeros.get(0)%numeros.get(1));
    }

    public void vaciarDos(){
        this.numeros.remove(1);
    }

    public void allClear(){
        numeros.clear();
        this.cadena="";
        this.simbolo="";
    }

}
