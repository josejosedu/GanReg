package com.example.a6000832.ganreg.PackConsultas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6000832.ganreg.Auxiliares.GuardarImagen;
import com.example.a6000832.ganreg.BaseDatos.GestorBD;
import com.example.a6000832.ganreg.Modelos.Animal;
import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jose Eduardo Martin.
 */

public class Vista_ficha_animal extends CustomDrawerActivity{

    private ImageView imagenAnimal;
    private Button btnSiguiente,btnAnterior;
    private TextView crotal,sexo,raza,fecha,madre,padre,parida, textoParida,cambiar_aniadir_foto,detalles,txt_detalles;
    private ArrayList<Animal> animales;
    private Animal animal;
    private int posicionActual;
    public static final int VISTA_TOTAL=101;
    public static final int VISTA_INDIV=102;
    private int fromIntent;
    private AutoCompleteTextView detallesEdit;  //Dialogo cambiar detalles
    private GestorBD db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cons_ficha_animal);
        iniciarElementos();

        mostrarAnimal();
    }

    @Override
    public void iniciarElementos() {

        fromIntent=getIntent().getIntExtra("actividad",VISTA_TOTAL);    //Esto es para saber de donde viene la llamada a esta clase, si de un conjunto de animales o solo de uno individual
        db=GestorBD.getInstance(this);

        imagenAnimal=(ImageView)findViewById(R.id.ficha_img);
        crotal=(TextView)findViewById(R.id.ficha_crotal);
        textoParida=(TextView)findViewById(R.id.txt_parida);
        sexo=(TextView)findViewById(R.id.ficha_sexo);
        raza=(TextView)findViewById(R.id.ficha_raza);
        fecha=(TextView)findViewById(R.id.ficha_fechanac);
        madre=(TextView)findViewById(R.id.ficha_crotmad);
        padre=(TextView)findViewById(R.id.ficha_crotpad);
        parida=(TextView)findViewById(R.id.ficha_parida);
        detalles=(TextView)findViewById(R.id.ficha_detalles);
        txt_detalles=(TextView)findViewById(R.id.txt_detalles);
        btnAnterior=(Button)findViewById(R.id.btnAnterior_ficha);
        btnSiguiente=(Button)findViewById(R.id.btnSiguiente_ficha);
        cambiar_aniadir_foto=(TextView)findViewById(R.id.cambiar_aniadir_foto);

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posicionActual>0)
                    posicionActual--;
                mostrarAnimal();
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posicionActual<animales.size()-1)
                    posicionActual++;
                mostrarAnimal();
            }
        });

        cambiar_aniadir_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCameraHardware()) { //Si el dispositivo tiene cámara
                    Intent camaraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camaraIntent, 0);    //Lanzo la actividad de la cámara, la cámara se abrirá para hacer la foto
                }
            }
        });

        detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoCambiarDetalles();
            }
        });

        txt_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoCambiarDetalles();
            }
        });

        if(fromIntent==VISTA_TOTAL) {
            animales = (ArrayList<Animal>) getIntent().getSerializableExtra("animales");   //Obtengo el array de animales, por si se pulsa siguiente o anterior, saber donde ir
            posicionActual = getIntent().getIntExtra("posicion", 0);
        }else if(fromIntent==VISTA_INDIV){
            animal=(Animal)getIntent().getSerializableExtra("animal");
            btnAnterior.setVisibility(View.INVISIBLE);
            btnSiguiente.setVisibility(View.INVISIBLE);
        }

    }

    public void mostrarAnimal(){
        Animal an=null;
        if(fromIntent==VISTA_TOTAL) {
            an = animales.get(posicionActual);
        }
        else if(fromIntent==VISTA_INDIV){
            an=animal;
        }
        if(!an.getRutafoto().equals("-"))   //Si tiene ruta de foto, la pongo, si no no la establezco (FileNotFoundException)
            imagenAnimal.setImageBitmap(BitmapFactory.decodeFile(an.getRutafoto()));
        else        //Si no tiene foto, pongo una por defecto
            imagenAnimal.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icono_foto_hacer));
        crotal.setText(an.getCrotal());
        sexo.setText(an.getSexo());
        raza.setText(an.getRaza());
        fecha.setText(an.getFenac());
        madre.setText(an.getCrotmad());
        padre.setText(an.getCrotpad());
        detalles.setText(an.getDetalles());
        if(an.getSexo().equals("Macho"))//Si es un macho, oculto el texto de parida
        {
            textoParida.setVisibility(View.INVISIBLE);
            parida.setVisibility(View.INVISIBLE);
        }
        else
        {
            textoParida.setVisibility(View.VISIBLE);
            parida.setVisibility(View.VISIBLE);
        }

        parida.setText(an.getParida().equals("0")?"NO":"SI");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Bitmap newImg=(Bitmap) data.getExtras().get("data");
            imagenAnimal.setImageBitmap(newImg);  //Guardo la foto como atributo de clase, para después cuando el usuario guarde el registro, ésta se guarde con él
            Animal an=null;
            if(fromIntent==VISTA_TOTAL) {
                an = animales.get(posicionActual);
            }
            else if(fromIntent==VISTA_INDIV){
                an=animal;
            }
            String ruta="";
            try {
                ruta=new GuardarImagen().guardarImagen(this,newImg,an.getCrotal());
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.cambiarFotoAnimal(an.getCrotal(),ruta);
            an.setRutafoto(ruta);
        }
    }

    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            //El dispositivo si tiene camara
            return true;
        } else {
            //El dispositivo no tiene camara
            return false;
        }
    }

    public void dialogoCambiarDetalles(){
        AlertDialog.Builder setDetalles = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_registrar_ventas, null); //Reutilizo ese dialogo, para no crear un layout nuevo, ya que tiene el mismo elemento, solo sobra uno, qu elo pondre invisible y al texto del edittext lo cambiaré
        configurarDialogo(v);
        setDetalles.setView(v);

        setDetalles.setTitle("Cambiar información");
        setDetalles.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {  //Cuando le de a aceptar se guardara lo nuevo establecido en el dialogo
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String detail=detallesEdit.getText().toString();
                detalles.setText(detail);

                Animal an=null;
                if(fromIntent==VISTA_TOTAL) {
                    an = animales.get(posicionActual);
                }
                else if(fromIntent==VISTA_INDIV){
                    an=animal;
                }
                db.cambiarDetalles(an.getCrotal(),detail);
                an.setDetalles(detail);

                Toast.makeText(Vista_ficha_animal.this,R.string.actOk,Toast.LENGTH_SHORT).show();
            }
        });
        setDetalles.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        setDetalles.setCancelable(true);
        setDetalles.show();
    }

    public void configurarDialogo(View v){
        detallesEdit=(AutoCompleteTextView)v.findViewById(R.id.venta_crotal);
        TextView currentDialog=(TextView)v.findViewById(R.id.venta_animalActual);
        currentDialog.setVisibility(View.INVISIBLE);    //No lo necesito en esta vista
        detallesEdit.setHint("Detalles...");    //Cambio el valor, ya que por defecto pone "crotal", ya que estoy reutilizando esta vista
    }

}
