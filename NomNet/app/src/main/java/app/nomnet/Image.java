package app.nomnet;

import android.graphics.Bitmap;

public class Image {
    private int imageID;
    private String name;
    private android.media.Image image;
    private Bitmap bitmap;

    public Image(int id, String name){
        this.imageID = id;
        this.name = name;
    }

    public int getImageID(){
        return imageID;
    }

    public String getName(){
        return name;
    }

    public android.media.Image getImage(){
        return image;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
}
