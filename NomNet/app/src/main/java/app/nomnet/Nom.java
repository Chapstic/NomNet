package app.nomnet;

import java.io.Serializable;

/**
 * Created by Albert on 4/11/2015.
 */
public class Nom implements Serializable {
    private String creator; //Replace w/ user class later
    private int upvotes;

    Nom(String creator, int upvotes){
        this.creator = creator;
        this.upvotes = upvotes;
    }

    public String getCreator(){
        return creator;
    }

    public int getUpvotes(){
        return upvotes;
    }


}
