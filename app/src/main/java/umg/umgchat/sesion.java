package umg.umgchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by David Velasquez on 18/04/2016.
 */
public class sesion {
    String datos[]; // datos del uuario y contraseña

    public  void conectar(Context contexto)
    {
        // primero si el archivo interno esta vacio que nos mande a login
        DatosLogin datoslogin = new DatosLogin();
        datos= datoslogin.leer(contexto);
        if(datos[0]=="" && datos[1]=="") //0=Usuario 1=Contraseña
        {
            Intent intent1= new Intent(contexto,Login.class);
            contexto.startActivity(intent1);
        }
        else {
            Intent intent1= new Intent(contexto,MainActivity.class);
            contexto.startActivity(intent1);
        }
    }
}
