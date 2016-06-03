package umg.umgchat;

import android.graphics.drawable.Drawable;

/**
 * Created by David Velasquez on 19/05/2016.
 */
public class DatosListView {
    protected Drawable Emoticon;
    protected String Chat;
    protected long Id;

    public DatosListView(Drawable emoticon, String chat, long id) {  // consturctor
        this.Chat = chat;
        this.Emoticon = emoticon;
        this.Id = id;
    }

    public Drawable getEmoticon() {
        return Emoticon;
    }

    public void setEmoticon(Drawable emoticon) {
        Emoticon = emoticon;
    }

    public String getChat() {
        return Chat;
    }

    public void setChat(String chat) {
        Chat = chat;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

}
