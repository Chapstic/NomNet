package app.nomnet;

public class NomItem {
    private String name;
    private int image;

    public NomItem(String name, int image){
        this.name = name;
        this.image = image;
    }

    public String getName(){
        return this.name;
    }

    public int getImage(){
        return this.image;
    }
}
