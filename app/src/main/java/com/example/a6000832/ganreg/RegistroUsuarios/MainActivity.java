package com.example.a6000832.ganreg.RegistroUsuarios;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.example.a6000832.ganreg.AppFunc.MainInicio;
import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.R;

/**
 * Created by Jose Eduardo Martin.
 */


public class MainActivity extends AppCompatActivity {

    private CheckBox recordarDatos;
    public static boolean recuerda;

    private Button iniciar;
    private TextView registro;

    private Button btnProbar;

    private TextView iniEmail,iniPass;

    private StringRequest sr;
    private String FICHERO_SERVIDOR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs=getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        boolean recordado=prefs.getBoolean("recordar",false);
        if(!recordado) {
            setContentView(R.layout.activity_main);

            iniciar = (Button) findViewById(R.id.btnIniciar);
            registro = (TextView) findViewById(R.id.registro);
            recordarDatos = (CheckBox) findViewById(R.id.recordarDatos);

            btnProbar = (Button) findViewById(R.id.btnPruebas);

            iniEmail = (EditText) findViewById(R.id.iniEmail);
            iniPass = (EditText) findViewById(R.id.iniPass);

            iniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        new IniciarSesion(iniEmail.getText().toString(), iniPass.getText().toString(), MainActivity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            registro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registro = new Intent(MainActivity.this, Registro.class);
                    startActivity(registro);
                }
            });

            btnProbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent acceso=new Intent(getApplicationContext(), MainInicio.class);
                    CustomDrawerActivity.NOMBRE_USUARIO="USUARIO demo";
                    CustomDrawerActivity.EMAIL_USUARIO="DEMO@DEMO.com";
                    CustomDrawerActivity.CEA_USUARIO="ES00demo00";
                    startActivity(acceso);
                }
            });

            recordarDatos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    recuerda = isChecked;
                }
            });
        }
        else
        {
            String email=prefs.getString("email","acceso@acceso.com");
            String cea=prefs.getString("ceaUsuario","ESpordefecto");
            String nombre=prefs.getString("nombre","NOMpordefecto");

            CustomDrawerActivity.NOMBRE_USUARIO=nombre;
            CustomDrawerActivity.EMAIL_USUARIO=email;
            CustomDrawerActivity.CEA_USUARIO=cea;

            Intent acceso=new Intent(getApplicationContext(), MainInicio.class);
            startActivity(acceso);
        }
    }
}
