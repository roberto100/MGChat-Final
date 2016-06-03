package umg.umgchat;

import android.graphics.drawable.Drawable;

/**
 * Created by LuIs-HD on 24/05/2016.
 */
public class DatosPubliListView {

    protected Drawable Emoticon;
    protected String Nombre;
    protected String Contenido;
    protected long Id;

    public DatosPubliListView(Drawable emoticon, String nombre, long id, String contenido) {
        Emoticon = emoticon;
        Nombre = nombre;
        Id = id;
        Contenido = contenido;
    }

    public Drawable getEmoticon() {
        return Emoticon;
    }

    public void setEmoticon(Drawable emoticon) {
        Emoticon = emoticon;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String contenido) {
        Contenido = contenido;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
}
