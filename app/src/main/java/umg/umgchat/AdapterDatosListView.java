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
 * Created by David Velasquez on 19/05/2016.
 */
public class AdapterDatosListView extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<DatosListView> items;

    // consructor
    public AdapterDatosListView(Activity activity, ArrayList<DatosListView> items) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v =view;
        if(view==null)
        {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inf.inflate(R.layout.itemchats,null);
        }
        DatosListView datos = items.get(i); // cremos el objeto
        //llenamos los atributos
        TextView chat = (TextView)v.findViewById(R.id.textChat);
        chat.setText(datos.getChat());

        ImageView emoticon = (ImageView) v.findViewById(R.id.imageEmoticon);
        emoticon.setImageDrawable(datos.getEmoticon());


        return v;
    }
}
