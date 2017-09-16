package com.example.a6000832.ganreg.RegistroUsuarios;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.a6000832.ganreg.AppFunc.MainInicio;
import com.example.a6000832.ganreg.Auxiliares.ConexionServidor;
import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jose Eduardo Martin.
 */

public class IniciarSesion{


    private String iniEmail,iniPass;
    private int tipo;
    private String info,CEA_USUARIO,NOMBRE_USUARIO;
    private Activity cx;
    private final String MENSAJE_DIALOGO="Comprobando datos";
    private String FICHERO_SERVIDOR;




    public IniciarSesion(String iniEmail, String iniPass, Activity cx) throws Exception {
        this.iniEmail=iniEmail;
        this.iniPass=iniPass;
        this.cx=cx;
        FICHERO_SERVIDOR=cx.getString(R.string.iniciarSesion);
        comprobarUsuario();
    }

    public void comprobarUsuario() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email",iniEmail);
        params.put("pass", iniPass);
        new ConexionServidor().conexion(params, cx, MENSAJE_DIALOGO, FICHERO_SERVIDOR, new ConexionServidor.VolleyCallback() {
            @Override
            public void onSuccesResponse(String result) {
                mostrarMensaje(result);
            }
        });
    }

    public void mostrarMensaje(String cadenaRecibida){
        tipo = Integer.parseInt(cadenaRecibida.split("#")[0]);
        info = cadenaRecibida.split("#")[1];
        CEA_USUARIO=cadenaRecibida.split("#")[2];
        NOMBRE_USUARIO=cadenaRecibida.split("#")[3];
        //Podría ser común el toast, pero lo pongo con un if para realizar distintas acciones en función del mensaje devuelto
        if(tipo==1)//Si el usuario es correcto, no muestro mensaje, simplemente inicio la sesión, accedo a la aplicación...
        {
            SharedPreferences prefs=cx.getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", iniEmail);
            editor.putString("ceaUsuario",CEA_USUARIO);
            editor.putString("nombre",NOMBRE_USUARIO);
            editor.putBoolean("recordar", MainActivity.recuerda);
            editor.commit();

            Intent acceso=new Intent(cx.getApplicationContext(), MainInicio.class);
            CustomDrawerActivity.NOMBRE_USUARIO=NOMBRE_USUARIO;
            CustomDrawerActivity.EMAIL_USUARIO=iniEmail;
            CustomDrawerActivity.CEA_USUARIO=CEA_USUARIO;
            cx.startActivity(acceso);
        }
        else
        {
            Toast.makeText(cx.getApplicationContext(),info,Toast.LENGTH_LONG).show();
        }
    }
}