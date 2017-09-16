package com.example.a6000832.ganreg.Auxiliares;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6000832.ganreg.AppFunc.Altas;
import com.example.a6000832.ganreg.AppFunc.Bajas;
import com.example.a6000832.ganreg.BaseDatos.GestorBD;
import com.example.a6000832.ganreg.Calculadora.CUnidades;
import com.example.a6000832.ganreg.AppFunc.Consultas;
import com.example.a6000832.ganreg.AppFunc.GCrotales;
import com.example.a6000832.ganreg.AppFunc.MainInicio;
import com.example.a6000832.ganreg.Calculadora.ControlCalculadora;
import com.example.a6000832.ganreg.R;

/**
 * Created by Jose Eduardo Martin.
 */

//Clase de la que extenderán todas las actividades, para que todas ellas tengan todoo en común y sean idénticas, tengan el mismo menú, mismo fondo, mismo tema, colores,...
//Aqui se define principalmente el menu lateral izquierdo y las acciones a realizar cuando se pulsen las distintas opciones

public abstract class CustomDrawerActivity extends AppCompatActivity implements View.OnTouchListener,DrawerLayout.DrawerListener{

    //Defino estas variables como estáticas, para poder usarlas en cualquier momento, ya que son datos importantes si se necesitan
    public static String NOMBRE_USUARIO;
    public static String EMAIL_USUARIO;
    public static String CEA_USUARIO;

    protected DrawerLayout fullLayout;
    protected RelativeLayout frameLayout;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationViewInicio;
    private ActionBarDrawerToggle mDrawerToggle;
    private GestureDetector gestureDetector;
    private TextView nameUs,emailUs;

    private ImageButton addNote;
    private AutoCompleteTextView txtNotas;

    public abstract void iniciarElementos();

    @Override
    public void setContentView(int layoutResID) {

        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer, null);
        frameLayout = (RelativeLayout) fullLayout.findViewById(R.id.fondo_drawer);
        navigationViewInicio = (NavigationView) fullLayout.findViewById(R.id.navview);
        mDrawerLayout = (DrawerLayout) fullLayout.findViewById(R.id.drawer_layout);
        //A la pantalla el añado un detector de gestos, para que cuando detecte lo definido en el, se realicen las acciones necesarias
        gestureDetector = new GestureDetector(this, new GestureListener(){
            @Override
            public void onSwipeRight() {        //Sobreescribo esta clase para que haga lo que yo quiero cuando detecte este gesto
                if(!mDrawerLayout.isDrawerOpen(GravityCompat.START))    //Si el drawer no está desplegado y se desliza hacia la derecha, se desplegará
                {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        }); //Le añado un detector de gestos, para que cuándo deslice hacia la derecha el dedo, aparezca el menu desplegable.
        //Sin esto anterior, para que aparezca el menú deslizando, solo se podría hacer si se desliza desde el borde, lo que es mas complicado.

        mDrawerLayout.setOnTouchListener(this);    //Al layout de fondo, le añado un listener, que escuchará a esta propia clase cuando se produzca un evento de toque


        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        //Cambio la actionbar a una personalizada
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        View view =getSupportActionBar().getCustomView();   //Para coger y usar los elementos que necesite, como botones,...de la actionbar

        addNote=(ImageButton)view.findViewById(R.id.btn_bar_addnote);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoNota();
            }
        });

        super.setContentView(fullLayout);

        navigationViewInicio.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Toast.makeText(CustomDrawerActivity.this,"SELECCION: "+item.getTitle().toString(),Toast.LENGTH_LONG).show();  //Para visualizar lo seleccionado

                //El siguiente bloque, es para comprobar en la actividad que estamos, y que si ya está abierta, no la vuelva a abrir
                String nombre=currentIntent();
                //En función de lo que se haga click en el el drawer, nos llevará a una parte o a otra de la aplicación
                switch (item.getTitle().toString()) {
                    case "ALTAS":
                        if(!nombre.equals(Altas.class.getSimpleName()))     //Si la activity actual, es la misma que voy a abrir, entonces no la abro
                        {
                            Intent altas = new Intent(CustomDrawerActivity.this, Altas.class);
                            startActivity(altas);
                        }
                        break;
                   /*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
 */
                }
                mDrawerLayout.closeDrawers();   //Cuando seleccione una opción, se cerrará el menú desplegable
                return false;
            }
        });
        //Defino el drawer, lo que ha de tener y hacer
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                 //la actividad
                mDrawerLayout,         //el drawerLayout que desplegará
                R.string.app_name,  //descripción al abrir
                R.string.app_name  //descripción al cerrar
        ) {};

        mDrawerLayout.addDrawerListener(this);      //Al drawer le añado un escuchador, para que se manejar los distintos eventos si está abierto, cerrado o en curso

        //Mostramos el botón en la barra de la aplicación
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Activamos el click en el icono de la aplicación
        //getSupportActionBar().setHomeButtonEnabled(true);

        //Esto es para establecer el nombre de usuario y el CEA en el desplegable lateral en toodo momento
        NavigationView navigationView = (NavigationView) findViewById(R.id.navview);
        View header=navigationView.getHeaderView(0);
        nameUs= (TextView)header.findViewById(R.id.nombreUsuario);
        nameUs.setText(NOMBRE_USUARIO+"\n"+CEA_USUARIO);
        emailUs= (TextView)header.findViewById(R.id.emailUsuario);
        emailUs.setText(EMAIL_USUARIO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
/*
*
*
*
*
*
*
*
*
*
*
*
*
 */
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    //Igual con la configuración
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    //Activo el click en el icono superior izquierdo para desplegar el menu lateral
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
/*
*
*
*
*
*
*
*
*
*
*
*
*
 */
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    //Estos 4 métodos son para manejar las distintos eventos que sucederán en cada estado diferente del drawer
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        mDrawerLayout.setOnTouchListener(null); //Mientras se está deslizando, también lo quito
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        mDrawerLayout.setOnTouchListener(null);     //Una vez abierto, quito el escuchador, para que no de problemas con el propio escuchador del drawer a la hora de cerrarlo (para que no detecte el deslizamiento a la izquierda, ya que lo hace por defecto el drawer)
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        mDrawerLayout.setOnTouchListener(this);     //Cuando cierre el drawer, vuelvo a añadirle el escuchador para que se pueda abrir deslizando a la derecha desde cualquier lado de la pantalla
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    //Para saber el nombre de la actividad en la que estoy, para saber si abrir una nueva o no abrir otra actividad igual a la que ya estoy
    public String currentIntent(){
        String nombre="";
        PackageManager packageManager = getPackageManager();
        try {
            ActivityInfo info = packageManager.getActivityInfo(getComponentName(), 0);  //Nombre de la activity actual, antes de crear otra
            nombre=info.name.split("\\.")[5]; //Simple nombre de la clase, ya que esto devuelve el nombre completo, con el paquete,...
            //Lo anterior es para hacer el split por el punto literal, pero hay que ponerle escape para que no lo interprete y si lo haga para el split
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return nombre;
    }

    //Esto es generico a toda la aplicación, por ello está aqui, cuando se pulse se abrirá un diálogo para introducir una nota rápida
    //La finalidad de este dialogo falta por implementar a fecha de entrega 02/06/2017
    public void dialogoNota(){
        /*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
 */
    }

    public void configurarDialogoNotas(View v){
/*
*
*
*
*
*
*
*
*
 */
    }

}