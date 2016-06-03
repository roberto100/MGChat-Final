package umg.umgchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CambioContrasena extends AppCompatActivity {
    Button actualizar;
    EditText tcontraa;
    EditText tcontran;
    EditText tcontran2;
    String datos[]; // datos del uuario y contraseña

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_contrasena);

        actualizar = (Button) findViewById(R.id.btnContra);
        tcontraa =(EditText) findViewById(R.id.txtContraActual);
        tcontran =(EditText) findViewById(R.id.txtContraN);
        tcontran2 =(EditText) findViewById(R.id.txtContraN2);

        DatosLogin datoslogin = new DatosLogin();
        datos= datoslogin.leer(CambioContrasena.this);

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarc();

            }
        });


    }

    public void actualizarc (){

        String contraa2 = tcontraa.getText().toString();
        if (contraa2.equals(datos[1])) {

            if (tcontran.getText().toString().equals(tcontran2.getText().toString()) && !(tcontran.getText().toString().equals(""))    ) {
                DatosLogin datos2 = new DatosLogin();
                datos2.escribir(CambioContrasena.this, datos[0], tcontran2.getText().toString());

                datos[1] = tcontran2.getText().toString();

                AsyncHttpClient client = new AsyncHttpClient();
                String url="http://api.odemac.org/editarpassword.php";
                RequestParams parametros = new RequestParams();
                parametros.add("Usuario", datos[0]);
                parametros.add("Password", tcontran2.getText().toString());

                RequestHandle post = client.post(url, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(statusCode==200) {
                            try {
                                Crouton.makeText(CambioContrasena.this, "Contraseña Actualizada Correctamente :)", Style.CONFIRM).show();
                                tcontran2.setText("");
                                tcontran.setText("");
                                tcontraa.setText("");

                            } catch (Exception e) {
                                Crouton.makeText(CambioContrasena.this, "No hay conexion a internet :(", Style.ALERT).show();
                                tcontran2.setText("");
                                tcontran.setText("");
                                tcontraa.setText("");
                            }
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }

                });

            } else {
                Crouton.makeText(CambioContrasena.this, "Las contraseñas ingresadas no coinciden", Style.ALERT).show();
                tcontran2.setText("");
                tcontran.setText("");
            }

        }
        else
        {
            Crouton.makeText(CambioContrasena.this, "La contraseña actual es incorrecta", Style.ALERT).show();
            tcontraa.setText("");
        }
    }
}
