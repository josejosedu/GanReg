package com.example.a6000832.ganreg.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jose Eduardo Martin.
 */
public class UsoBD {

    //Campos sin modificador de acceso, para que sean accesibles únicamente desde la propia clase, y desde el mismo paquete.

    static final String NOMBRE_BD = "explotacion";
    static final int VERSION_BD = 1;


    static final String TABLA_ANIMALES = "animales";
    public static final String TABLA_COMPRAS = "compras";
    public static final String TABLA_VENTAS = "ventas";
    static final String TABLA_MEDICINAS = "medicinas";
    static final String TABLA_CROTALES_NUEVOS = "crotales_nuevos";
    public static final String TABLA_BAJAS = "bajas";
    public static final String TABLA_NACIMIENTOS = "nacimientos";


    //OJO A LOS CAMPOS TEXT, CREO QUE SOLO ENTRAN 256 CARACTERES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //TABLA ANIMALES---------------------------------------------------------------------------------------------------------------------------------------------
    static final String CLAVE_CROTAL_AN = "crotal";
    static final String CLAVE_CROTMAD_AN = "crotmad";
    static final String CLAVE_CROTPAD_AN = "crotpad";
    static final String CLAVE_SEXO_AN = "sexo";
    static final String CLAVE_FECHA_NACIMIENTO_AN = "fenac";
    static final String CLAVE_RAZA_AN = "raza";
    static final String CLAVE_PARIDA_AN = "parida";
    static final String CLAVE_IDCOMPRA_AN = "idcompra";
    static final String CLAVE_FECHA_CUBIERTA_AN = "fecub";
    static final String CLAVE_IDMEDICINAS_AN = "idmed";
    static final String CLAVE_RUTA_FOTO_AN = "rutafoto";
    static final String CLAVE_DETALLES_AN = "detallesan";

/*
*
*
*
*
*
*
*
 */

    //TABLA COMPRAS----------------------------------------------------------------------------------------------------------------------------------------------
    static final String CLAVE_FECHA_ENTRADA_COMPRA = "fentra";
    static final String CLAVE_DETALLES_COMPRA = "detalles";

    private static final String CREAR_COMPRAS = "CREATE TABLE " + TABLA_COMPRAS + "(" + CLAVE_IDCOMPRA_AN + " INTEGER PRIMARY KEY AUTOINCREMENT," + CLAVE_FECHA_ENTRADA_COMPRA + " TEXT NOT NULL," + CLAVE_DETALLES_COMPRA + " TEXT)";

    //TABLA VENTAS-----------------------------------------------------------------------------------------------------------------------------------------------
    static final String CLAVE_ID_VENTAS = "idventa";
    static final String CLAVE_CANTIDAD_VENTAS = "cantidad";
    static final String CLAVE_FECHA_SALIDA_VENTAS = "fesalida";
    static final String CLVAE_IMPORTE_VENTAS = "importevent";
    static final String CLAVE_DETALLES_VENTAS = "detallesvent";
    static final String CLAVE_COMPRADOR_VENTAS = "comprador";

/*
*
*
*
*
*
 */

    //TABLA MEDICINAS--------------------------------------------------------------------------------------------------------------------------------------------

    private static final String CREAR_MEDICINAS = "CREATE TABLE " + TABLA_MEDICINAS + "(" + CLAVE_IDMEDICINAS_AN + " INTEGER PRIMARY KEY AUTOINCREMENT)";

    //TABLA CROTALES NUEVOS--------------------------------------------------------------------------------------------------------------------------------------------

    static final String CLAVE_ID_CROTAL_NUEVO = "nuevo_crotal";

    private static final String CREAR_NUEVOS_CROTALES = "CREATE TABLE " + TABLA_CROTALES_NUEVOS + "(" + CLAVE_ID_CROTAL_NUEVO + " VARCHAR(14) PRIMARY KEY NOT NULL)";

    //TABLA BAJAS--------------------------------------------------------------------------------------------------------------------------------------------

/*
*
*
*
*
*
 */
    //TABLA NACIMIENTOS--------------------------------------------------------------------------------------------------------------------------------------------

    static final String NACIMIENTO_CONTINUA_EXP="continua"; //Campo para verificar si el animal registrado en ese nacimiento continua o no en la explotación al momento de hacer la consulta

    private static final String CREAR_NACIMIENTOS="CREATE TABLE "+TABLA_NACIMIENTOS+"("+CLAVE_CROTAL_AN+" VARCHAR(14) PRIMARY KEY,"+CLAVE_FECHA_NACIMIENTO_AN+ " TEXT NOT NULL,"+NACIMIENTO_CONTINUA_EXP+" INTEGER)";

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    private final Context context;
    private CrearBD crearDB;
    private SQLiteDatabase db;

    public UsoBD(Context cx) {
        this.context = cx;
        crearDB = new CrearBD(cx);
    }

    public static class CrearBD extends SQLiteOpenHelper {

        public CrearBD(Context context) {
            super(context, NOMBRE_BD, null, VERSION_BD);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREAR_ANIMALES);
            db.execSQL(CREAR_COMPRAS);
            db.execSQL(CREAR_VENTAS);
            db.execSQL(CREAR_MEDICINAS);
            db.execSQL(CREAR_NUEVOS_CROTALES);
            db.execSQL(CREAR_BAJAS);
            db.execSQL(CREAR_NACIMIENTOS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    /*public UsoBD open(){
        db=crearDB.getWritableDatabase();
        return this;
    }*/

    public SQLiteDatabase getDataBase() {
        db = crearDB.getWritableDatabase();
        return db;
    }

    public void close() {
        crearDB.close();
    }

}
