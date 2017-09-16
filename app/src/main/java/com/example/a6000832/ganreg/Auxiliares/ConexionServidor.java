package com.example.a6000832.ganreg.Auxiliares;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a6000832.ganreg.R;

import java.util.Map;

/**
 * Created by Jose Eduardo Martin.
 */

//Clase para realizar las conexiones con el servidor, en la aplicación sirve a fecha de entrega 2/06/2017, únicamente para la parte del registro de usuarios

public class ConexionServidor{

    private ProgressDialog progressDialog;
    private StringRequest sr;

    public ConexionServidor(){
    }

    public void conexion(final Map<String,String> parametros, Context cx, final String MensajeDialogo, final String fichero, final VolleyCallback callback){

        progressDialog = new ProgressDialog(cx);
        progressDialog.setTitle("Espere, por favor...");
        progressDialog.setMessage(MensajeDialogo);
        progressDialog.show();
        //Obtengo una instancia de volley, para obtener la cola de peticiones, que es única (singleton)
        VolleySingleton v=VolleySingleton.getInstance(cx);
        RequestQueue queue = v.getRequestQueue();
        //Esto se ejecuta asíncronamente con la interfaz del usuario, no interfiere en su funcionamiento
        sr=new StringRequest(Request.Method.POST, cx.getString(R.string.ipServidorActual)+fichero, new Response.Listener<String>() {
            //Acciones a realizar cuando reciba una respuesta
            @Override
            public void onResponse(String response) {
                callback.onSuccesResponse(response);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {   //Acciones a realizar si se recibe un error
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                return parametros;
            }
        };
        //Esto es para que solo se haga un envio de la petición. Se puede poner para que se hagan mas si hay error,...
        sr.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
    }

    //Esto se llamará cuando se reciba respuesta de la petición volley(http), en donde se haga uso, habrá que definir lo que se quiere que se haga
    public interface VolleyCallback{
        void onSuccesResponse(String result);
    }
}