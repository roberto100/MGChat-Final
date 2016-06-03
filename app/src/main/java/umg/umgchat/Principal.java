package umg.umgchat;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {
    Button cerrar;
    Button asignarcursos;
    Button chat;
    Button des;
    Button con;
    Button emoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal2);
        sesion iniciar = new sesion();
        iniciar.conectar(this);

        //crearemos los controles de la actividad
        cerrar = (Button) findViewById(R.id.btncerrarsession);
        asignarcursos = (Button) findViewById(R.id.btnasignarcursos);
        chat = (Button)findViewById(R.id.btnchat);
        des = (Button)findViewById(R.id.btnDes);
        con = (Button)findViewById(R.id.btnContra);
        emoti = (Button)findViewById(R.id.btnEmo);

        // ---------------- eventos botones---------------------
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatosLogin datos = new DatosLogin();
                datos.borrar(Principal.this); //cerramos sesion
                Intent intent = new Intent(Principal.this, Principal.class);
                startActivity(intent);
            }
        });

        asignarcursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsignarCursos cursos = new AsignarCursos();
                cursos.listarcursos(Principal.this);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inicioEstu = new Intent(Principal.this, Chats.class);
                startActivity(inicioEstu);
            }
        });

        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsignarCursos cursos = new AsignarCursos();
                cursos.desasignar(Principal.this);
            }
        });

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inicioCon = new Intent(Principal.this, CambioContrasena.class);
                startActivity(inicioCon);
            }
        });

        emoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inicioCon = new Intent(Principal.this, Emoticon.class);
                startActivity(inicioCon);
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
