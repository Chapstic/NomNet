package app.nomnet;
import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/*
    This class will hold our global variables
    We can access these variables throughout the whole app

    USAGE EXAMPLES
    Set:   ((MyApplication) this.getApplication()).setSomeVariable("foo");
    Get:   String s = ((MyApplication) this.getApplication()).getSomeVariable();
 */
public class MyApplication extends Application {

    private String currentUser;
    private boolean isLoggedIn = false;
    private boolean isDoneConvertingImages = false;
    private List<Image> images = new ArrayList<>();

    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
    public boolean getIsLoggedIn(){
        return isLoggedIn;
    }
    public void setIsLoggedIn(boolean isLoggedIn){
        this.isLoggedIn = isLoggedIn;
    }

    public void addImage(Image i){
        images.add(i);
    }

    public int getImgListSize(){
        return images.size();
    }

    public void setIsDoneConvertingImages(boolean b){
        isDoneConvertingImages = b;
    }
    public boolean getIsDoneConvertingImages(){
        return isDoneConvertingImages;
    }

    public Image getImagewithID(int id){
        for(int i = 0; i < images.size(); i++){
            if(images.get(i).getImageID() == id){
                return images.get(i);
            }
        }
        return null; //Image with that id could not be found
    }
}