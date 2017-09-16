package com.example.a6000832.ganreg.Modelos;

import java.io.Serializable;

/**
 * Created by Jose Eduardo Martin.
 */

public class ComprasMod implements Serializable {

    private String idCompra;
    private String fechaEntrada;
    private String detalles;

    public ComprasMod(String idCompra, String fechaEntrada, String detalles) {
        this.idCompra = idCompra;
        this.fechaEntrada = fechaEntrada;
        this.detalles = detalles;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public String getDetalles() {
        return detalles;
    }
}
