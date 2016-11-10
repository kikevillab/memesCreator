package miw.upm.es.memegenerator.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class Image {

    private long id;
    private String name;
    private Uri image;

    public Image(String name){
        this.name = name;
    }

    public Image(String name, Uri image) {
        this.name = name;
        this.image = image;
    }

    public Image(long id, String name, Uri image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
