package com.example.a6000832.ganreg.Modelos;

import android.graphics.Bitmap;

/**
 * Created by Jose Eduardo Martin.
 */

public class Noticia {

    public String titulo;
    public String descripcion;
    public String link;
    public Bitmap miniaturaImg;
    public String guid;
    public String fecha;


    public Noticia(){

    }

    public Noticia(String titulo, String descripcion, String link, Bitmap miniaturaImg) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.link = link;
        this.miniaturaImg =miniaturaImg;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setMiniaturaImg(Bitmap miniaturaImg) {
        this.miniaturaImg = miniaturaImg;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLink() {
        return link;
    }

    public String getGuid() {
        return guid;
    }
}
