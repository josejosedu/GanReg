package com.example.a6000832.ganreg.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a6000832.ganreg.Modelos.Animal;
import com.example.a6000832.ganreg.Modelos.BajasMod;
import com.example.a6000832.ganreg.Modelos.ComprasMod;
import com.example.a6000832.ganreg.Modelos.NacimientosMod;
import com.example.a6000832.ganreg.Modelos.VentasMod;

import java.util.ArrayList;

/**
 * Created by Jose Eduardo Martin.
 */

//Esta clase generará una instancia de la misma, desde la cual se podrán realizar todas las operaciones sobre la base de datos, definiendolas previamente en esta clase
//Singleton del gestor, para que exista únicamente una instancia en toda la aplicacion

public final class GestorBD {

    private static GestorBD instance;
    private Context cx;
    private UsoBD database;

    private SQLiteDatabase db;

    private GestorBD(Context cx){
        this.cx=cx;
        database=new UsoBD(cx);
        db=database.getDataBase();
    }

    public static synchronized GestorBD getInstance(Context cx){
        if(instance==null)
            instance=new GestorBD(cx);
        return instance;
    }

    public void closeDataBase(){
        database.close();
    }
//todo******************************************************************************************************************************************************************
    public int generarCompra(String fechaCompra, String detalles){

        ContentValues valores = new ContentValues();

        valores.put(UsoBD.CLAVE_FECHA_ENTRADA_COMPRA, fechaCompra);
        valores.put(UsoBD.CLAVE_DETALLES_COMPRA,detalles);
        db.insert(UsoBD.TABLA_COMPRAS,null,valores);    //Guardo la compra (se genera un id que es autonumerico), que posteriormente lo obtendré para guardarlo en la tabla animales

        //Cojo el último id que haya para poner el siguiente
        Cursor resul=db.rawQuery("SELECT MAX("+UsoBD.CLAVE_IDCOMPRA_AN+") FROM "+UsoBD.TABLA_COMPRAS,null);
        resul.moveToFirst();

        int ultimoIndice=Integer.parseInt(resul.getString(0));

        return ultimoIndice;
    }
//todo******************************************************************************************************************************************************************
    public long insertarAnimalCompra(String crotal, String raza, String sexo, String fechanac, String parida,String idcompra) {

        ContentValues valores = new ContentValues();

        valores.put(UsoBD.CLAVE_CROTAL_AN, crotal);
        valores.put(UsoBD.CLAVE_CROTMAD_AN,"-");
        valores.put(UsoBD.CLAVE_CROTPAD_AN,"-");
        valores.put(UsoBD.CLAVE_SEXO_AN,sexo);
        valores.put(UsoBD.CLAVE_FECHA_NACIMIENTO_AN,fechanac);
        valores.put(UsoBD.CLAVE_RAZA_AN,raza);
        valores.put(UsoBD.CLAVE_PARIDA_AN,parida);
        valores.put(UsoBD.CLAVE_IDCOMPRA_AN,idcompra);
        valores.put(UsoBD.CLAVE_FECHA_CUBIERTA_AN,"-");
        valores.put(UsoBD.CLAVE_IDMEDICINAS_AN,"-");
        valores.put(UsoBD.CLAVE_RUTA_FOTO_AN,"-");
        valores.put(UsoBD.CLAVE_DETALLES_AN,"-");

        return db.insert(UsoBD.TABLA_ANIMALES, null, valores);
    }
    //todo******************************************************************************************************************************************************************
    public long insertarAnimalNacimiento(String crotal, String crotmad, String crotpad, String sexo, String fechanac, String raza, String rutafoto, String detalles){
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
 */
    }
//todo******************************************************************************************************************************************************************
    //
    public ArrayList<String> listaCrotalesEspec(String sexo){   //Para mostar registros específicos según el sexo

        sexo=sexo.equals("")?UsoBD.CLAVE_SEXO_AN:"'"+sexo+"'";  //Si hay sexo, se busca por sexo, si no devuelve todos los crotales de animales

        ArrayList<String> lista=new ArrayList<>();

        Cursor resul=db.rawQuery("SELECT "+UsoBD.CLAVE_CROTAL_AN+" FROM "+UsoBD.TABLA_ANIMALES+" WHERE "+UsoBD.CLAVE_SEXO_AN+"="+sexo,null);

        //Nos aseguramos de que existe al menos un registro
        if (resul.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                lista.add(resul.getString(0));
            } while(resul.moveToNext());    //Mientras haya un registro siguiente, avanzamos en la lista
        }

        return lista;
    }
//todo******************************************************************************************************************************************************************
    //Para registrar un nuevo crotal para los nuevos nacimientos
    public boolean insertarNuevoCrotal(String nuevo){

        ContentValues valores=new ContentValues();

        valores.put(UsoBD.CLAVE_ID_CROTAL_NUEVO,nuevo);

        db.insert(UsoBD.TABLA_CROTALES_NUEVOS,null,valores);

        return true;
    }
//todo******************************************************************************************************************************************************************
    //Devuelve una lista de crotales que aún no han sido asignados
    public ArrayList<String> listaCrotalesNuevos() {
        ArrayList<String> lista = new ArrayList<>();

        Cursor resul = db.rawQuery("SELECT " + UsoBD.CLAVE_ID_CROTAL_NUEVO + " FROM " + UsoBD.TABLA_CROTALES_NUEVOS+" ORDER BY "+UsoBD.CLAVE_ID_CROTAL_NUEVO+" ASC", null);

        //Nos aseguramos de que existe al menos un registro
        if (resul.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                lista.add(resul.getString(0));
            }
            while (resul.moveToNext());    //Mientras haya un registro siguiente, avanzamos en la lista
        }
        return lista;
    }
//todo******************************************************************************************************************************************************************
    //Devuelve una lista de animales en función de los parámetros recibidos
    public ArrayList<Animal> consultaAnimal(String crotal, String madre, String padre, String sexo, String parida, int tipoFecha, String fechaNacimiento, String fechaUno, String fechaDos, int tipoMeses, String meses){
        ArrayList<Animal> lista = new ArrayList<>();
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
 */
        return lista;
    }
//todo******************************************************************************************************************************************************************
    //Para dar de baja un animal en la explotación registrar el motivo (muerte o venta individual) y hacer las acciones necesarias para las relaciones con los demás animales
    public boolean registrarBaja(String crotal, String fecha, String motivo, String identificador){

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
 */
    }
//todo******************************************************************************************************************************************************************
    //Para registrar una venta y dar de baja en la explotación todos los animales vendidos
    public boolean registrarVentas(ArrayList<String> listaCrotales, String fechaSalida, String cantidad, String importe, String detalles, String comprador){

        String motivoBaja="venta";
        ContentValues valores;
        //Primero compruebo que existan todos los crotales que van a ser eliminados
        boolean existen=true;
        for(String d:listaCrotales){    //Lo recorro para comprobar uno a uno que existan todos los introducidos, esto no hará falta si se restringe el método de entrada de datos a los que ya existan
            Cursor resul = db.rawQuery("SELECT * FROM "+UsoBD.TABLA_ANIMALES+" WHERE "+UsoBD.CLAVE_CROTAL_AN+"='"+d+"'",null);
            if(!resul.moveToFirst()) //Si hay al menos un registro (el cursor se posicionará en éste si existe)
                existen=false;
        }

        if(existen)     //Si existen todos los crotales introducidos, procedemos a grabar la venta y registrar las bajas
        {
            valores = new ContentValues();
            valores.put(UsoBD.CLAVE_CANTIDAD_VENTAS, cantidad);
            valores.put(UsoBD.CLAVE_FECHA_SALIDA_VENTAS, fechaSalida);
            valores.put(UsoBD.CLVAE_IMPORTE_VENTAS, importe);
            valores.put(UsoBD.CLAVE_DETALLES_VENTAS, detalles);
            valores.put(UsoBD.CLAVE_COMPRADOR_VENTAS, comprador);
            db.insert(UsoBD.TABLA_VENTAS, null, valores);   //Guardo la venta
            //Obtengo el último índice que se ha guardado de compra, que será el identificador que irá en la tabla bajas
            Cursor resul=db.rawQuery("SELECT MAX("+UsoBD.CLAVE_ID_VENTAS+") FROM "+UsoBD.TABLA_VENTAS,null);
            resul.moveToFirst();
            String ultimoIndice=resul.getString(0);


            for (String dCrotales : listaCrotales) {
                registrarBaja(dCrotales, fechaSalida, motivoBaja,ultimoIndice); //Si la baja se hace correctamente, lo registro en la tabla ventas
            }

        }
        else    //No se ha registrado la compra porque algún crotal no existe
            return false;
        return true;
    }
//todo******************************************************************************************************************************************************************
    //Para obtener una lista del tipo de movimiento que se reciba por parámetro con los datos indicados
    public ArrayList getListaMovimientos(String movimiento, String fechaUno, String fechaDos){
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
 */

    }
//todo******************************************************************************************************************************************************************
    //Para obtener un objeto animal, con todos sus datos
    public Animal getAnimal(String crotal){

        Cursor consulta=db.rawQuery("SELECT * FROM "+UsoBD.TABLA_ANIMALES+" WHERE "+UsoBD.CLAVE_CROTAL_AN+"='"+crotal+"'",null);
        Animal an=null;
        if (consulta.moveToFirst()) {
            do {
                an=new Animal(consulta.getString(0),consulta.getString(1),consulta.getString(2),consulta.getString(3),consulta.getString(4),consulta.getString(5),consulta.getString(6),consulta.getString(7),consulta.getString(8),consulta.getString(9),consulta.getString(10),consulta.getString(11));
            }
            while (consulta.moveToNext());
        }
        return an;
    }

//todo******************************************************************************************************************************************************************
    //Para cambiar la foto de un animal
    public void cambiarFotoAnimal(String crotal, String rutaFoto){
        ContentValues ruta=new ContentValues();
        ruta.put(UsoBD.CLAVE_RUTA_FOTO_AN,rutaFoto);
        db.update(UsoBD.TABLA_ANIMALES,ruta,UsoBD.CLAVE_CROTAL_AN+"='"+crotal+"'",null);
    }

//todo******************************************************************************************************************************************************************
    //Para cambiar los detalles de un animal
    public void cambiarDetalles(String crotal, String detalles){
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

//todo******************************************************************************************************************************************************************

    public void eliminarCrotalNuevo(String crotal){
        db.execSQL("DELETE FROM "+UsoBD.TABLA_CROTALES_NUEVOS+" WHERE "+UsoBD.CLAVE_ID_CROTAL_NUEVO+"='"+crotal+"'");
    }

//todo******************************************************************************************************************************************************************
}

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