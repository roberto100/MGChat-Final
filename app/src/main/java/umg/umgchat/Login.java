package umg.umgchat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Login extends AppCompatActivity {
    Button registrar;
    Button iniciar;
    EditText tusuario;
    EditText tcontrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar barra= getSupportActionBar();
        barra.hide();
        registrar=(Button) findViewById(R.id.btnRegistrar);
        iniciar=(Button) findViewById(R.id.btniniciar);
        tusuario=(EditText) findViewById(R.id.textCarneR);
        tcontrasenia=(EditText) findViewById(R.id.textPasswordR);

        //------------------- programacion de los botones..-----------------------
       registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(),Registro.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               iniciarSession(getBaseContext(),tusuario.getText().toString(),tcontrasenia.getText().toString());

            }
        });

        //---------------------fin programacion de los botones
    }

    public void iniciarSession(final Context contexto, final String Usuario, final String Contrasenia)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.odemac.org/Login.php";
        RequestParams parametros = new RequestParams();
        parametros.add("Usuario",Usuario);
        parametros.add("Contrasenia",Contrasenia);

       RequestHandle post=  client.post(url, parametros, new AsyncHttpResponseHandler() {
        String usuario = null;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200)
                {
                    try{
                        JSONObject o = new JSONObject(new String(responseBody));
                        usuario=o.getString("Usuario");

                        if(!TextUtils.isEmpty(usuario))
                        {
                                DatosLogin datos = new DatosLogin();
                                datos.escribir(contexto,Usuario,Contrasenia);
                            Intent intent1 = new Intent(getBaseContext(),Principal.class);
                            startActivity(intent1);
                        }
                        else{
                            Crouton.makeText(Login.this,"Datos incorrectos", Style.ALERT).show();
                            tcontrasenia.setText("");
                            tusuario.setText("");
                        }

                    }catch(JSONException e){
                        Crouton.makeText(Login.this,e.toString(), Style.ALERT).show();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Crouton.makeText(Login.this,"No hay conexión a internet :(", Style.ALERT).show();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {   // si presionan el boton fisico

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
