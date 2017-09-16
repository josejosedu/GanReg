package com.example.a6000832.ganreg.RegistroUsuarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6000832.ganreg.Auxiliares.ConexionServidor;
import com.example.a6000832.ganreg.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jose Eduardo Martin.
 */

public class Registro extends AppCompatActivity {

    private EditText nombre, apellidos, cea, pass,pass2, email;
    private Button enviar;
    private String POST_NOMBRE="nombre",POST_APELLIDOS="apellidos",POST_CEA="cea",POST_PASS="pass",POST_EMAIL="email";
    private int tipo;
    private String info;
    //private String cadenaParaEnviar;
    //private String cadenaRecibida;
    private final String MENSAJE_DIALOGO="Registrando";
    private String FICHERO_SERVIDOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        FICHERO_SERVIDOR=getString(R.string.insertarUsuario);

        nombre=(EditText)findViewById(R.id.txtNombre);
        apellidos=(EditText)findViewById(R.id.txtApellidos);
        cea=(EditText)findViewById(R.id.txtCea);
        pass=(EditText)findViewById(R.id.txtContra);
        pass2=(EditText)findViewById(R.id.txtRepContra);
        email=(EditText)findViewById(R.id.txtEmail);
        enviar=(Button)findViewById(R.id.enviarDatos);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //Al tocar el boton "registrarse", enviará los datos. Realizar las comprobaciones necesarias de los datos antes
                if(!pass.getText().toString().equals(pass2.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Contraseñas no coinciden",Toast.LENGTH_LONG).show();
                else if(pass.getText().toString().length()<6)
                    Toast.makeText(getApplicationContext(),"Mínimo 6 caracteres",Toast.LENGTH_LONG).show();
                else    //Si los datos introducidos son correctos, se procede a enviar los datos
                        enviarDatos();
            }
        });
    }


    public void enviarDatos(){
        Map<String, String> params = new HashMap<String, String>();
        params.put(POST_NOMBRE,nombre.getText().toString());
        params.put(POST_APELLIDOS, apellidos.getText().toString());
        params.put(POST_CEA, cea.getText().toString());
        params.put(POST_PASS, pass.getText().toString());
        params.put(POST_EMAIL, email.getText().toString());
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
        if (tipo == 1) {
            Toast.makeText(Registro.this, info, Toast.LENGTH_LONG).show();
            Intent confirmar=new Intent(Registro.this,ConfirmarRegistro.class);
            confirmar.putExtra("email",email.getText().toString());     //Le envío el email, así buscaré para ese email los dos códigos
            startActivity(confirmar);

        }
        else if (tipo == 0) {  //Caso de error, notificar que el email o el CEA ya están registrados
            Toast.makeText(Registro.this, info, Toast.LENGTH_LONG).show();
            cea.setText("");
        } else if (tipo == 2) {
            Toast.makeText(Registro.this, info, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}