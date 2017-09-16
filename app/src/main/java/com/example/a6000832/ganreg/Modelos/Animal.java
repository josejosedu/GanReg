package com.example.a6000832.ganreg.Modelos;

import java.io.Serializable;

/**
 * Created by Jose Eduardo Martin.
 */

public class Animal implements Serializable {

    private String crotal;
    private String crotmad;
    private String crotpad;
    private String sexo;
    private String fenac;
    private String raza;
    private String parida;
    private String idcompra;
    private String fecub;
    private String idmed;
    private String rutafoto;
    private String detalles;

    public Animal(String crotal, String crotmad, String crotpad, String sexo, String fenac, String raza, String parida, String idcompra, String fecub, String idmed, String rutafoto, String detalles) {
        this.crotal = crotal;
        this.crotmad = crotmad;
        this.crotpad = crotpad;
        this.sexo = sexo;
        this.fenac = fenac;
        this.raza = raza;
        this.parida = parida;
        this.idcompra = idcompra;
        this.fecub = fecub;
        this.idmed = idmed;
        if(rutafoto.equals("-"))
            this.rutafoto = rutafoto;
        else
            this.rutafoto=rutafoto+"/"+crotal+".jpg";
        this.detalles = detalles;
    }

    public String getCrotal() {
        return crotal;
    }

    public String getCrotmad() {
        return crotmad;
    }

    public String getCrotpad() {
        return crotpad;
    }

    public String getSexo() {
        return sexo;
    }

    public String getFenac() {
        return fenac;
    }

    public String getRaza() {
        return raza;
    }

    public String getParida() {
        return parida;
    }

    public String getIdcompra() {
        return idcompra;
    }

    public String getFecub() {
        return fecub;
    }

    public String getIdmed() {
        return idmed;
    }

    public String getRutafoto() {
        return rutafoto;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setRutafoto(String rutafoto) {
        this.rutafoto=rutafoto+"/"+crotal+".jpg";
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
