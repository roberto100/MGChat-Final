package umg.umgchat;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by David Velasquez on 18/05/2016.
 */
public class AsignarCursos {
    String datos[]; // datos del uuario y contraseña


    public void desasignar (final Context contexto){
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle("Desasignar")
                .setMessage("¿Esta seguro que desea desasignarse cursos?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AsyncHttpClient client = new AsyncHttpClient();
                                String url="http://api.odemac.org/desasignarchat.php";
                                DatosLogin datoslogin = new DatosLogin();
                                datos= datoslogin.leer(contexto);
                                RequestParams parametros = new RequestParams();
                                parametros.add("Usuario", datos[0]);


                                client.post(url, parametros, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        if(statusCode==200) {
                                            Crouton.makeText((Activity) contexto, "Se ha desasignado correctamente", Style.CONFIRM).show();
                                            Intent inicioCon = new Intent(contexto, MainActivity.class);
                                            contexto.startActivity(inicioCon);
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

    public void enviarApi (ArrayList lista, final Context contexto){
        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://api.odemac.org/asignarchat.php";
        DatosLogin datoslogin = new DatosLogin();
        datos= datoslogin.leer(contexto);

        for (int x=0; x<lista.size(); x++){
            RequestParams parametro = new RequestParams();
            parametro.add("Usuario", datos[0]);
            parametro.add("Chat", lista.get(x).toString());

            RequestHandle post = client.post(url, parametro, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Crouton.makeText((Activity) contexto, "No hay conexion a internet :(", Style.ALERT).show();
                }
            });

        }
        Crouton.makeText((Activity) contexto, "Reasignacion de Cursos Correctamente", Style.CONFIRM).show();
        Intent inicioCon = new Intent(contexto, MainActivity.class);
        contexto.startActivity(inicioCon);

    }



    public boolean listarcursos(final Context contexto)
    {
        // conectar con JSON
        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://api.odemac.org/listarchat.php";
        RequestParams parametros = new RequestParams();


        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200)
                {
                    try{



                        final  AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                        final ArrayList itemsSeleccionados = new ArrayList();

                        JSONArray o = new JSONArray(new String(responseBody));
                        final CharSequence[] items = new CharSequence[o.length()];
                        for(int i=0;i<o.length();i++)
                        {
                            items[i]=o.getJSONObject(i).getString("Chat");
                        }



                        builder.setTitle("Seleccione Cursos")
                                .setPositiveButton("ACEPTAR",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //aqui viene el codigo
                                                enviarApi(itemsSeleccionados, contexto);
                                            }
                                        })
                                .setNegativeButton("CANCELAR",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // aqui viene el codigo
                                            }
                                        })
                                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            // Guardar indice seleccionado
                                            itemsSeleccionados.add(items[which].toString());

                                        } else {
                                            // Remover indice sin selección
                                            itemsSeleccionados.remove(items[which]);
                                        }
                                    }
                                });

                        builder.create();
                        builder.show();


                    }catch(JSONException e){
                        Crouton.makeText((Activity) contexto,e.toString(), Style.ALERT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    return true;
    }
}
