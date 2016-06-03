package umg.umgchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String datos[]; //Leer datos
    AdapterDatosListView adapter;
    public String aNombre="";
    public String aEmoticon1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       android.support.v7.app.ActionBar barra = getSupportActionBar();
        barra.hide();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //-------------------------dentro del oncreat------------------------------


        /*android.support.v7.app.ActionBar barra = getSupportActionBar(); // para
        barra.setDisplayShowHomeEnabled(true);                         // mostrar
        barra.setIcon(R.mipmap.ic_launcher);                            // el icono
        barra.setTitle(" Cursos Disponibles");
        */

        cargar();
        //llamar funcion
        cargar2();
        //----------------------------------------------------------

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add) {
            AsignarCursos cursos = new AsignarCursos();
            if(cursos.listarcursos(MainActivity.this))
            {

            }

        } else if (id == R.id.nav_gallery) {
            AsignarCursos cursos = new AsignarCursos();
            cursos.desasignar(MainActivity.this);
        } else if (id == R.id.nav_slideshow) {
            Intent inicioCon = new Intent(MainActivity.this, CambioContrasena.class);
            startActivity(inicioCon);
        } else if (id == R.id.nav_manage) {
            Intent inicioEstu = new Intent(MainActivity.this, Emoticon.class);
            startActivity(inicioEstu);
        } else if (id == R.id.nav_sesion) {
            Crouton.makeText(MainActivity.this, ":(", Style.ALERT).show();
            DatosLogin datos = new DatosLogin();
            datos.borrar(MainActivity.this); //cerramos sesion
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        cargar();
        return true;
    }

    //---------------------------------

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {   // si presionan el boton fisico

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



    public void cargar(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://api.odemac.org/listarchatusuario.php";

        DatosLogin datoslogin = new DatosLogin();
        datos= datoslogin.leer(MainActivity.this);

        RequestParams parametros = new RequestParams();
        parametros.add("Usuario", datos[0]);
        final ListView lista = (ListView) findViewById(R.id.listViewChat);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String texto = (lista.getItemAtPosition(position).toString());
                DatosListView datos = (DatosListView)adapter.getItem(position);
                String texto = Long.toString(datos.getId());
                String texto2 = (datos.getChat());

                Intent intent = new Intent(MainActivity.this, Publicaciones.class);
                intent.putExtra("IdChat", texto.toString());
                intent.putExtra("Nombre", texto2.toString());
                startActivity(intent);
                return;
            }
        });

        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200) {
                    try {

                        ArrayList<DatosListView> arraydatos = new ArrayList<DatosListView>();

                        JSONArray o = new JSONArray(new String(responseBody));
                        DatosListView datos;

                        if (o.length()>0){

                        }else {
                            Crouton.makeText(MainActivity.this, "Por favor asignese 1 Curso", Style.ALERT).show();
                        }

                        for (int i = 0; i < o.length(); i++) {
                            String curso = o.getJSONObject(i).getString("Chat");
                            Long id = Long.parseLong(o.getJSONObject(i).getString("Id"));
                            datos = new DatosListView(getResources().getDrawable(R.mipmap.ic_chat), curso, id);
                            arraydatos.add(datos);

                        }
                        adapter = new AdapterDatosListView(MainActivity.this, arraydatos);
                        lista.setAdapter(adapter);

                    } catch (JSONException e) {
                        Crouton.makeText(MainActivity.this, "Por favor asignese 1 Curso", Style.ALERT).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Crouton.makeText(MainActivity.this, "No hay conexion a internet :(", Style.ALERT).show();
            }
        });


    }

    public void cargar2() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://api.odemac.org/perfil.php";

        DatosLogin datoslogin = new DatosLogin();
        datos= datoslogin.leer(MainActivity.this);

        RequestParams parametros = new RequestParams();
        parametros.add("Usuario", datos[0]);

        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200) {
                    try {
                        JSONArray o = new JSONArray(new String(responseBody));
                        DatosListView datos;

                        for (int i = 0; i < o.length(); i++) {
                            aNombre = o.getJSONObject(i).getString("Nombre_Completo");
                            aEmoticon1 = o.getJSONObject(i).getString("emotion");

                            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                            navigationView.removeHeaderView(navigationView.getHeaderView(0));
                            View hView = navigationView.inflateHeaderView(R.layout.nav_header_main);
                            ImageView imagen = (ImageView) hView.findViewById(R.id.imageView);
                            TextView nombre =(TextView) hView.findViewById(R.id.textView);

                            //Validacion
                            nombre.setText(aNombre);
                            if (aEmoticon1.equals("1")) {
                                imagen.setImageResource(R.mipmap.emo1);
                            }
                            if (aEmoticon1.equals("2")) {
                                imagen.setImageResource(R.mipmap.emo2);
                            }
                            if (aEmoticon1.equals("3")) {
                                imagen.setImageResource(R.mipmap.emo3);
                            }
                            if (aEmoticon1.equals("4")) {
                                imagen.setImageResource(R.mipmap.emo4);
                            }
                            if (aEmoticon1.equals("5")) {
                                imagen.setImageResource(R.mipmap.emo5);
                            }
                            navigationView.setNavigationItemSelectedListener(MainActivity.this);
                        }

                    } catch (Exception e) {
                        Crouton.makeText(MainActivity.this, "No hay datos", Style.ALERT).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Crouton.makeText(MainActivity.this, "No hay conexion a internet :(", Style.ALERT).show();
            }
        });
    }
}
