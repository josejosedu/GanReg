package com.example.a6000832.ganreg.Auxiliares;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Jose Eduardo Martin.
 */

//Clase singleton, que representa un cliente http, para conexiones http, solo se creará una instancia de esta clase, se creará una cola de peticiones que se irán realizando cuando sea necesario

public final class VolleySingleton {

    private static VolleySingleton singleton;
    private static Context appCx;
    private static RequestQueue requestQueue;


    private VolleySingleton(Context cx) {
        requestQueue = Volley.newRequestQueue(cx);
    }

    public static synchronized VolleySingleton getInstance(Context cx) {
        if(singleton==null)
            singleton=new VolleySingleton(cx.getApplicationContext());
        return singleton;
    }

    public static synchronized RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(appCx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}
