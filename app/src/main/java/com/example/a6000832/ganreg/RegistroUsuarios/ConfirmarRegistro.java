package com.example.a6000832.ganreg.RegistroUsuarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6000832.ganreg.AppFunc.MainInicio;
import com.example.a6000832.ganreg.Auxiliares.ConexionServidor;
import com.example.a6000832.ganreg.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jose Eduardo Martin.
 */

public class ConfirmarRegistro extends AppCompatActivity {

    private String email;
    private EditText ceaEt,pinPassEt;
    private Button btnConfirmar;
    private int tipo;
    private String info;
    private final String MENSAJE_DIALOGO="Comprobando datos";
    private String FICHERO_SERVIDOR;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        FICHERO_SERVIDOR=getString(R.string.confirmarRegistro);

        ceaEt=(EditText)findViewById(R.id.ceaTxt);
        pinPassEt=(EditText)findViewById(R.id.pinpassTxt);
        btnConfirmar=(Button)findViewById(R.id.btnConfirmar);

        Intent anterior=getIntent();
        email=anterior.getStringExtra("email");

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    enviarDatos();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void enviarDatos(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("email",email);
        params.put("cea", ceaEt.getText().toString());
        params.put("pinPass", pinPassEt.getText().toString());
        new ConexionServidor().conexion(params, this, MENSAJE_DIALOGO, FICHERO_SERVIDOR, new ConexionServidor.VolleyCallback() {
            @Override
            public void onSuccesResponse(String result) {
                mostrarMensaje(result);
            }
        });
    }

    public void mostrarMensaje(String cadenaRecibida){
        tipo = Integer.parseInt(cadenaRecibida.split("#")[0]);
        info = cadenaRecibida.split("#")[1];
        if (tipo == 1)  //Si devuelve 1, creamos la base de datos y accedemos al inicio
        {
            Toast.makeText(ConfirmarRegistro.this, info, Toast.LENGTH_LONG).show();
            Intent acceso=new Intent(this, MainInicio.class);
            acceso.putExtra("ceaUsuario",ceaEt.getText().toString());  //Se lo envio por si luego lo necesito
            startActivity(acceso);
        }
        else if (tipo == 0) {  //Caso de error, acciones a realizar...
            Toast.makeText(ConfirmarRegistro.this, info, Toast.LENGTH_LONG).show();
        }
    }
}
