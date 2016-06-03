package umg.umgchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Chats extends AppCompatActivity {
    String datos[]; //Leer datos
    AdapterDatosListView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

            android.support.v7.app.ActionBar barra = getSupportActionBar(); // para
            barra.setDisplayShowHomeEnabled(true);                         // mostrar
            barra.setIcon(R.mipmap.ic_launcher);                            // el icono
            barra.setTitle(" Cursos Disponibles");


        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://api.odemac.org/listarchatusuario.php";

        DatosLogin datoslogin = new DatosLogin();
        datos= datoslogin.leer(Chats.this);

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

                Intent intent = new Intent(Chats.this, Publicaciones.class);
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
                                    Crouton.makeText(Chats.this, "Por favor asignese 1 Curso", Style.ALERT).show();
                                }

                                for (int i = 0; i < o.length(); i++) {
                                    String curso = o.getJSONObject(i).getString("Chat");
                                    Long id = Long.parseLong(o.getJSONObject(i).getString("Id"));
                                    datos = new DatosListView(getResources().getDrawable(R.mipmap.ic_chat), curso, id);
                                    arraydatos.add(datos);

                                }
                                 adapter = new AdapterDatosListView(Chats.this, arraydatos);
                                lista.setAdapter(adapter);

                            } catch (JSONException e) {
                                Crouton.makeText(Chats.this, "Por favor asignese 1 Curso", Style.ALERT).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Crouton.makeText(Chats.this, "No hay conexion a internet :(", Style.ALERT).show();
                    }
                });
    }
}
