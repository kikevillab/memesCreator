package miw.upm.es.memegenerator.model;

import android.graphics.Bitmap;

/**
 * Created by Enrique on 10/11/2016.
 */
public class Meme {

    private long id;
    private String bottomText;
    private String topText;
    private String font;
    private int fontSize;
    private String baseImage;
    private Bitmap image;


    public Meme(long id, String bottomText, String topText, String font, int fontSize, String baseImage, Bitmap image) {
        this.id = id;
        this.bottomText = bottomText;
        this.topText = topText;
        this.font = font;
        this.fontSize = fontSize;
        this.baseImage = baseImage;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getBaseImage() {
        return baseImage;
    }

    public void setBaseImage(String baseImage) {
        this.baseImage = baseImage;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
