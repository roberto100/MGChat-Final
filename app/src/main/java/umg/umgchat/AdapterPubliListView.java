package umg.umgchat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LuIs-HD on 24/05/2016.
 */
public class AdapterPubliListView extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<DatosPubliListView> items;

    public AdapterPubliListView(Activity activity, ArrayList<DatosPubliListView> items) {
        this.activity = activity;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i); // posision
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View v =view;
        if(view==null)
        {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inf.inflate(R.layout.itempublicacion,null);
        }
        DatosPubliListView datos = items.get(i); // cremos el objeto
        //llenamos los atributos
        TextView publicacion = (TextView)v.findViewById(R.id.textPublicacion);
        TextView usuario = (TextView)v.findViewById(R.id.textNombreUsuario);
        publicacion.setText(datos.getContenido());
        usuario.setText(datos.getNombre());

        ImageView emoticon = (ImageView) v.findViewById(R.id.imageEmoticonUsuario);
        emoticon.setImageDrawable(datos.getEmoticon());


        return v;
    }
}
