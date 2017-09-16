package com.example.a6000832.ganreg.Auxiliares;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.a6000832.ganreg.R;

/**
 * Created by Jose Eduardo Martin.
 */

//Vista de navegador de la noticia que se ha pulsado en la pantalla de inicio

public class VistaWebNoticia extends AppCompatActivity{

    private WebView vistaWeb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_detalle_noticia);

        String url=getIntent().getStringExtra("enlace");

        vistaWeb=(WebView)findViewById(R.id.webview);
        vistaWeb.loadUrl(url);

    }

    @Override
    protected void onStop() {   //Cuando salga del navegador web, que se cierre la actividad, que se destruya y se libere de la pila de actividades en memoria
        super.onStop();
        finish();
    }
}
