package com.example.a6000832.ganreg.Modelos;

import java.io.Serializable;

/**
 * Created by Jose Eduardo Martin.
 */

public class VentasMod implements Serializable {

    private String idVenta;
    private String cantidad;
    private String fechaSalida;
    private String importe;
    private String detalles;
    private String comprador;

    public VentasMod(String idVenta, String cantidad, String fechaSalida, String importe, String detalles, String comprador) {
        this.idVenta = idVenta;
        this.cantidad = cantidad;
        this.fechaSalida = fechaSalida;
        this.importe = importe;
        this.detalles = detalles;
        this.comprador = comprador;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public String getImporte() {
        return importe;
    }

    public String getDetalles() {
        return detalles;
    }

    public String getComprador() {
        return comprador;
    }
}
