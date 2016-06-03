package umg.umgchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Bundle;
import android.view.KeyEvent;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Publicaciones extends AppCompatActivity {
    AdapterPubliListView adapter;
    String idc;
    Button comentar;
    EditText tcomentario;
    ListView lista2;
    String datos[]; //Leer datos
    Button rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        comentar = (Button) findViewById(R.id.btnComentar);
        tcomentario = (EditText) findViewById(R.id.editTextComentar);
        rec = (Button) findViewById(R.id.btnF5);

        lista2=(ListView) findViewById(R.id.listViewp);

        DatosLogin datoslogin = new DatosLogin();
        datos= datoslogin.leer(Publicaciones.this);

        Bundle bundle = getIntent().getExtras();
        idc = bundle.getString("IdChat");
        String Nombre = bundle.getString("Nombre");

        titulo(Nombre);
        recargar();
        ocultar();

        //Seleccionar para borrar
        lista2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    DatosPubliListView datos2 = (DatosPubliListView)adapter.getItem(position);
                    final String texto = Long.toString(datos2.getId());

                AsyncHttpClient client2 = new AsyncHttpClient();
                String url="http://api.odemac.org/buscarpublicacion.php";
                RequestParams parametros = new RequestParams();
                parametros.add("Usuario", datos[0]);
                parametros.add("IdPublicacion", texto);

                RequestHandle post = client2.post(url, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(statusCode==200) {
                            try {

                                JSONArray o = new JSONArray(new String(responseBody));
                                borrador(Publicaciones.this, texto);

                            } catch (JSONException e) {
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Crouton.makeText(Publicaciones.this, "No hay conexion a internet :(", Style.ALERT).show();
                    }
                });
                return false;
            }

            });


        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicioComentario(getBaseContext(), tcomentario.getText().toString());
                tcomentario.setText("");
                recargar();
                InputMethodManager imm = (InputMethodManager) getSystemService(Publicaciones.this.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recargar();
            }
        });


        tcomentario.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    InputMethodManager imm = (InputMethodManager) getSystemService(Publicaciones.this.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    return true;
                }
                return false;
            }
        });


    } //Finaliza OnCreate

    public void ocultar(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Publicaciones.this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
    //Programacion del Titulo
    public void titulo(String Nombre){
        android.support.v7.app.ActionBar barra = getSupportActionBar(); // para
        barra.setDisplayShowHomeEnabled(true);                         // mostrar
        barra.setIcon(R.mipmap.ic_launcher);                            // el icono
        barra.setTitle(Nombre);
    }

    public void recargar() {
        //Todo lo neceasrio para el listview
        //____________________________________________
        AsyncHttpClient client2 = new AsyncHttpClient();
        String url="http://api.odemac.org/listarpublicaciones.php";

        RequestParams parametros = new RequestParams();
        parametros.add("IdChat", idc);
        client2.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        ArrayList<DatosPubliListView> arraydatos = new ArrayList<DatosPubliListView>();
                        JSONArray o = new JSONArray(new String(responseBody));
                        DatosPubliListView datos;

                        for (int i = 0; i < o.length(); i++) {
                            String emoti = o.getJSONObject(i).getString("emotion");
                            Long idPublicacion = Long.parseLong(o.getJSONObject(i).getString("Id"));
                            String usuario1 = o.getJSONObject(i).getString("Nombre_Completo");
                            String conten = o.getJSONObject(i).getString("Contenido");
                            String fecha = o.getJSONObject(i).getString("Fecha");
                            if (emoti.equals("1")) {
                                datos = new DatosPubliListView(getResources().getDrawable(R.mipmap.emo1), usuario1 + " | Enviado: " + fecha, idPublicacion, conten);
                                arraydatos.add(datos);
                            }
                            if (emoti.equals("2")) {
                                datos = new DatosPubliListView(getResources().getDrawable(R.mipmap.emo2), usuario1 + " | Enviado: " + fecha, idPublicacion, conten);
                                arraydatos.add(datos);
                            }
                            if (emoti.equals("3")) {
                                datos = new DatosPubliListView(getResources().getDrawable(R.mipmap.emo3), usuario1 + " | Enviado: " + fecha, idPublicacion, conten);
                                arraydatos.add(datos);
                            }
                            if (emoti.equals("4")) {
                                datos = new DatosPubliListView(getResources().getDrawable(R.mipmap.emo4), usuario1 + " | Enviado: " + fecha, idPublicacion, conten);
                                arraydatos.add(datos);
                            }
                            if (emoti.equals("5")) {
                                datos = new DatosPubliListView(getResources().getDrawable(R.mipmap.emo5), usuario1 + " | Enviado: " + fecha, idPublicacion, conten);
                                arraydatos.add(datos);
                            }

                        }
                        adapter = new AdapterPubliListView(Publicaciones.this, arraydatos);
                        lista2.setAdapter(adapter);

                    } catch (JSONException e) {
                        Crouton.makeText(Publicaciones.this, "No hay comentarios :(", Style.ALERT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Crouton.makeText(Publicaciones.this, "No hay conexion a internet :(", Style.ALERT).show();
            }
        });
    }

    public void borrador (final Context contexto, final String idb){
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle("Eliminar")
                .setMessage("¿Esta seguro que desea eliminar su comentario?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AsyncHttpClient client = new AsyncHttpClient();
                                String url="http://api.odemac.org/borrarpublicacion.php";
                                DatosLogin datoslogin = new DatosLogin();
                                datos= datoslogin.leer(contexto);
                                RequestParams parametros = new RequestParams();
                                parametros.add("IdPublicacion", idb);

                                client.post(url, parametros, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        if(statusCode==200) {
                                            Crouton.makeText((Activity) contexto, "Se ha eliminado correctamente", Style.CONFIRM).show();
                                            recargar();
                                        }
                                    }
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        Crouton.makeText((Activity) contexto, "No hay conexion a internet :(", Style.ALERT).show();
                                    }
                                });

                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

        builder.create();
        builder.show();
    }



    public void inicioComentario (Context contexto, String Nombre) {
        if (Nombre.equals("")) {
            Crouton.makeText(Publicaciones.this, "Por favor, agregue un comentario", Style.ALERT).show();
        }
        else{

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.odemac.org/publicar.php";
        RequestParams parametros = new RequestParams();
        parametros.add("Contenido", Nombre);
        parametros.add("Id", idc);
        parametros.add("Usuario", datos[0]);

        RequestHandle post = client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Crouton.makeText(Publicaciones.this, "Mensaje Enviado", Style.CONFIRM).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Crouton.makeText(Publicaciones.this, "No hay conexion a internet :(", Style.ALERT).show();
            }
        });


    }
    }
}

