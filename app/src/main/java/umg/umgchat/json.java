package umg.umgchat;
import android.content.Context;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by David Velasquez on 20/04/2016.
 */
public class json {

    public void obtenerUsuario(final Context contexto2)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.odemac.org/conexionjson.php";
        RequestParams parametros = new RequestParams();

        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200)
                {
                    obtDatosJSON(new String(responseBody), contexto2);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }

    public void obtDatosJSON(String responde,Context contexto){
            ArrayList listado = new ArrayList();

            try
            {
                JSONArray jsonArray = new JSONArray(responde);
                String texto;
                for(int i=0;i<jsonArray.length();i++)
                {
                    texto =jsonArray.getJSONObject(i).getString("Usuario")+" "+jsonArray.getJSONObject(i).getString("Contrasenia");
                    Toast.makeText(contexto,texto, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e)
            {
                Toast.makeText(contexto,e.toString(), Toast.LENGTH_LONG).show();
            }

    }
}
