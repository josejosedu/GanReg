package com.example.a6000832.ganreg.Modelos;

import java.io.Serializable;

/**
 * Created by Jose Eduardo Martin.
 */

public class NacimientosMod implements Serializable {

    private String crotal;
    private String fechaNac;
    private String continua;

    public NacimientosMod(String crotal, String fechaNac, String continua) {
        this.crotal = crotal;
        this.fechaNac = fechaNac;
        this.continua = continua;
    }

    public String getCrotal() {
        return crotal;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getContinua() {
        return continua;
    }
}
