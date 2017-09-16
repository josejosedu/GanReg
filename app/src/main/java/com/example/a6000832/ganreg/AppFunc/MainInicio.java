package com.example.a6000832.ganreg.AppFunc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Xml;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.a6000832.ganreg.Auxiliares.CustomDrawerActivity;
import com.example.a6000832.ganreg.Auxiliares.RssFeedListAdapter;
import com.example.a6000832.ganreg.Modelos.Noticia;
import com.example.a6000832.ganreg.Auxiliares.VistaWebNoticia;
import com.example.a6000832.ganreg.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose Eduardo Martin.
 */

public class MainInicio  extends CustomDrawerActivity {

    //Variables para saber el tipo de permiso que estoy pidiendo en cada caso
    boolean doubleBackToExitPressedOnce = false;
    private static final int PERMISSIONS_CAMERA = 100;
    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 101;

    private RecyclerView mRecyclerView;
    private RssFeedListAdapter mRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    private List<Noticia> mFeedModelList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pant_menu);
        //Si la versión de android es mayor que la establecida, se pedirán los permisos, ya que en versiones superiores, hay que concederlos explicitamente
        verifyPermission();

        iniciarElementos();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {       //Sobreescribo este método, para que cuándo pulse la tecla atrás haga lo que yo quiero, en este caso:
        //Cuando se pulse la tecla atrás estando en el menú principal, dirá que si se quiere salir, se pulse dos veces, mientras tanto no se saldra de la app
        //Si en el tiempo marcado en el manejador mas abajo no se vuelve a pulsar el botón atrás, la variable doubleBackToExitPressedOnce se pondrá a false y será necesario volver a pulsar dos veces para poder salir
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            cerrarAplicacion();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.dosAtras, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {  //Manejador. Una vez que pase el tiemp oespecificado, desde que comienza su llamada, producirá el evento en el run

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);
    }

    //Método para cerrar la aplicación
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void cerrarAplicacion() {
        this.finish();  //Finalizo primero la actividad en la que estoy
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();   //Destruye esta actividad y todas las asociadas a ella
    }

    @Override
    public void iniciarElementos() {

        RelativeLayout fondo = (RelativeLayout) findViewById(R.id.fondo_menu);
        View toAdd = getLayoutInflater().inflate(R.layout.rss_vista_principal, null);
        fondo.addView(toAdd);

        mRecyclerView = (RecyclerView) toAdd.findViewById(R.id.rec_view_noticias);
        mSwipeLayout = (SwipeRefreshLayout) toAdd.findViewById(R.id.swipeForUpdate);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL)); //Lineas de separacion entre los elementos
        //Aquí le estoy diciendo lo que quiero que se realice cuando se recargue la página mediante swipe
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);   //Ejecuto la tarea que lee el feed desde internet
            }
        });

        //Al iniciar la actividad hago que se carguen las noticias solas por defecto, no haya que darle a actualizar:
        new FetchFeedTask().execute((Void) null);


    }

    private boolean verifyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_CAMERA);
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    //Permisos concedidos
                    verifyPermission();
                else {  //Si el usuario no concede el permiso, lo vuelvo a pedir hasta que el usuario lo acepte, para usar la aplicación correctamente
                    Toast.makeText(this, R.string.permCam, Toast.LENGTH_SHORT).show();
                    verifyPermission();
                }
                break;
            case PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    //Permisos concedidos
                    verifyPermission();
                else {  //Si el usuario no concede el permiso, lo vuelvo a pedir hasta que el usuario lo acepte, para usar la aplicación correctamente
                    Toast.makeText(this, R.string.permMem, Toast.LENGTH_SHORT).show();
                    verifyPermission();
                }
                break;
        }
    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink="******************************";

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
            //urlLink = mEditText.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //if (TextUtils.isEmpty(urlLink))
            //  return false;

            try {

                URL url = new URL(urlLink);  //Me conecto a la url y obtengo el XML
                InputStream inputStream = url.openConnection().getInputStream();
                mFeedModelList = parseo(inputStream);   //Decodifico el XML a mi gusto para obtener los datos
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mSwipeLayout.setRefreshing(false);  //Quito el boton de swipe, ya ha acabado la tarea que lo mantenia activo

            if (success) {      //Si doInBackground devuelve true:
                mRecyclerViewAdapter=new RssFeedListAdapter(mFeedModelList);    //Se crea el adaptador enviandole la lista obtenida, con los datos ya decodificados
                mRecyclerViewAdapter.setOnClickListener(new View.OnClickListener() {    //Cuando se haga click en un item de la lista, se abrirá en el navegador la noticia seleccionada
                    @Override
                    public void onClick(View v) {
                        Intent verNoticiaWeb=new Intent(MainInicio.this, VistaWebNoticia.class);
                        verNoticiaWeb.putExtra("enlace",mFeedModelList.get(mRecyclerView.getChildAdapterPosition(v)).getGuid());
                        startActivity(verNoticiaWeb);
                    }
                });
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
            } else {    //Si hay un error en la conexión o de url, doInBackground devolverá false y entonces se mostrará este mensaje
                Toast.makeText(MainInicio.this,
                        R.string.comprobarWeb,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public List<Noticia> parseo(InputStream inputStream) throws XmlPullParserException,IOException{

        List<Noticia> noticias = null;

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null); //Al objeto parser le digo de donde ha de sacar los datos

            int evento = parser.getEventType(); //Tipo de evento al principio del documento
            Noticia current = null; //Creo una noticia que iré rellenando según avanzo en cada item del XML

            parser.nextTag();   //Salto al siguiente evento del XML
            while (evento != XmlPullParser.END_DOCUMENT) {

                String etiqueta = null;

                switch (evento) {
                    case XmlPullParser.START_DOCUMENT:  //Cuando se detecte el inicio del documento inicializo el array de noticias
                        noticias = new ArrayList<Noticia>();
                        break;

                    case XmlPullParser.START_TAG:   //Cuando encuentre un tag, se realizarán diferentes acciones en función de el
                        etiqueta = parser.getName();
                        if (etiqueta.equals("item")) {  //Cuando encuentre un item, creo una noticia nueva (cada item es una noticia)
                            current = new Noticia();
                        } else if (current != null) {
                            if (etiqueta.equals("link")) {
                                current.setLink(parser.nextText());
                            } else if (etiqueta.equals("description")) {    //Aqui obtener la imagen en miniatura. El String obtenido de aqui es de tipo html y contiene una descripción sin ninguna etiqueta, tiene un parrafo "p", dentro un enlace "a", con una imagen "img"
                                Document doc = Jsoup.parse(parser.nextText());  //Cojo el registro correspondiente a la descripción
                                //Elements content = doc.getElementsByTag("p");  //Obtengo el párrafo correspondiente, crea un array pero solo hay un párrafo
                                Elements linkImg = doc.getElementsByTag("a");    //Obtengo el primer parrafo(solo hay uno) y de él obtengo el enlace "a"
                                current.setLink(doc.select("a").attr("href"));  //Obtengo el link de la noticia
                                String urlImagen=doc.select("img").attr("src"); //Cojo el src de la imagen para mostrarla
                                if(!urlImagen.isEmpty())    //Si no tiene src esque no hay imagen
                                    current.setMiniaturaImg(Picasso.with(this).load(urlImagen).get());  //Si tiene imagen, la guardo como atributo para mostrarla posteriormente
                                current.setDescripcion(doc.text()); //Obtengo el texto literal de la noticia para mostrarlo en los detalles
                            } else if (etiqueta.equals("pubDate")) {
                                current.setFecha(parser.nextText());
                            } else if (etiqueta.equals("title")) {
                                current.setTitulo(parser.nextText());
                            } else if (etiqueta.equals("guid")) {
                                current.setGuid(parser.nextText());
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG: //Al finalizar el documento, añado la última noticia
                        etiqueta = parser.getName();

                        if (etiqueta.equals("item") && current != null) {
                            noticias.add(current);
                        }
                        break;
                }
                evento = parser.next(); //Paso al siguiente evento del XML hasta que sea el final
            }
        } finally {
            inputStream.close();
        }
        return noticias;
    }
}
