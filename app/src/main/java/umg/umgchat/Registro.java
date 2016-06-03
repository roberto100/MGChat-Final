package umg.umgchat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Registro extends AppCompatActivity {
    Button registro;
    EditText tcarne;
    EditText tnombre;
    EditText tcorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        android.support.v7.app.ActionBar barra= getSupportActionBar();
        barra.hide();
        registro=(Button) findViewById(R.id.btnRegistrar);
        tcarne=(EditText) findViewById(R.id.textCarneR);
        tnombre=(EditText) findViewById(R.id.textNombreR);
        tcorreo=(EditText) findViewById(R.id.textCorreoR);

        //------------------- programacion de los botones..-----------------------
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicioRegistro(getBaseContext(), tnombre.getText().toString(), tcarne.getText().toString(), tcorreo.getText().toString()+"@miumg.edu.gt");
                Intent intent1 = new Intent(getBaseContext(),Login.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }

        });

        //---------------------fin programacion de los botones
    }


    public void inicioRegistro(final Context contexto, final String Nombre, final String Carne, final String Correo)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.odemac.org/Registrousuario.php";
        RequestParams parametros = new RequestParams();
        parametros.add("Nombre_Completo",Nombre);
        parametros.add("Usuario",Carne);
        parametros.add("Email",Correo);
        parametros.add("Tipo_Usuario","1");

        RequestHandle post=  client.post(url, parametros, new AsyncHttpResponseHandler() {
            String usuario = null;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //Toast.makeText(contexto, Correo, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Crouton.makeText(Registro.this,"No hay conexion a internet :(", Style.ALERT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {   // si presionan el boton fisico

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
