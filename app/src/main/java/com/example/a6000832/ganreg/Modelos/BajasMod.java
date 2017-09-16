package com.example.a6000832.ganreg.Modelos;

import java.io.Serializable;

/**
 * Created by Jose Eduardo Martin.
 */

public class BajasMod implements Serializable {

    private String crotal;
    private String fecha;
    private String motivo;
    private String idVenta;

    public BajasMod(String crotal, String fecha, String motivo, String idVenta) {
        this.crotal = crotal;
        this.fecha = fecha;
        this.motivo = motivo;
        this.idVenta = idVenta;
    }

    public String getCrotal() {
        return crotal;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getIdVenta() {
        return idVenta;
    }
}
