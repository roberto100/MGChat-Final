package umg.umgchat;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Emoticon extends AppCompatActivity implements View.OnClickListener{
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    RadioButton r4;
    RadioButton r5;
    Button acep;
    String datos[]; // datos del uuario y contraseña
    String val = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoticon);

        acep = (Button) findViewById(R.id.btnAceptar);
        r1 = (RadioButton) findViewById(R.id.rd1);
        r2 = (RadioButton) findViewById(R.id.rd2);
        r3 = (RadioButton) findViewById(R.id.rd3);
        r4 = (RadioButton) findViewById(R.id.rd4);
        r5 = (RadioButton) findViewById(R.id.rd5);

        DatosLogin datoslogin = new DatosLogin();
        datos = datoslogin.leer(Emoticon.this);

        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);
        r4.setOnClickListener(this);
        r5.setOnClickListener(this);

        acep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                acept();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rd1:
                val = "1";
                break;

            case R.id.rd2:
                val = "2";
                break;

            case R.id.rd3:
                val = "3";
                break;

            case R.id.rd4:
                val = "4";
                break;

            case R.id.rd5:
                val = "5";
                break;
        }
    }

    private void acept (){
        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://api.odemac.org/editaremoticon.php";
        RequestParams parametros = new RequestParams();
        parametros.add("Usuario", datos[0]);
        parametros.add("Emoticon", val);

        RequestHandle post = client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200) {
                    try {
                        Crouton.makeText(Emoticon.this, "Emoticon Actualizado Correctamente :)", Style.CONFIRM).show();
                    } catch (Exception e) {
                        Crouton.makeText(Emoticon.this, "No hay conexion a internet :(", Style.ALERT).show();

                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

        });
    }

}
