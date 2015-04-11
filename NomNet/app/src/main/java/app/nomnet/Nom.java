package app.nomnet;

import java.io.Serializable;

/**
 * Created by Albert on 4/11/2015.
 */
public class Nom implements Serializable {
    private String creator; //Replace w/ user class later
    private int upvotes;
    private Recipe recipe; //Might be better to change recipe information to String[] so we can use scroll text fields later

    Nom(String creator, int upvotes, Recipe recipe){
        this.creator = creator;
        this.upvotes = upvotes;
        this.recipe = recipe;
    }

    public String getCreator(){
        return creator;
    }

    public int getUpvotes(){
        return upvotes;
    }

    public String getName() { return recipe.getName(); }

    public String getIngredients(){ return recipe.getIngredients(); }

    public String getDirections() { return recipe.getDirections(); }


}
