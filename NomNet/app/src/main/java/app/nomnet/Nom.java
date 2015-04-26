package app.nomnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Albert on 4/11/2015.
 */
public class Nom implements Serializable {
    private String creator; //Replace w/ user class later
    private int upvotes, imageID;
    private Recipe recipe; //Might be better to change recipe information to String[] so we can use scroll text fields later
    Map<String, Boolean> tags;

    Nom(String creator, int upvotes, int image, Recipe recipe, Map<String, Boolean> tags){
        this.tags = new HashMap<String, Boolean>();

        this.creator = creator;
        this.upvotes = upvotes;
        this.imageID = image;
        this.recipe = recipe;
        this.tags = tags;
    }

    public String getCreator(){
        return creator;
    }

    public int getUpvotes(){
        return upvotes;
    }

    public int getImageID() { return imageID; }

    public String getName() { return recipe.getName(); }

    public String getIngredients(){ return recipe.getIngredients(); }

    public String getDirections() { return recipe.getDirections(); }

    public void addUpvote(){ upvotes++; }

    public void subtractUpvote(){ upvotes--; }

    public Set<String> getTags(){
        Set<String> results = new HashSet();

        for(Map.Entry<String, Boolean> entry : tags.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();

            if(value){
                results.add(key);
            }
        }

        return results;
    }

    // Checks to see if Nom has target tag (passed in)
    public Boolean hasTag(String targetTag){
        Boolean result = false;

        for(Map.Entry<String, Boolean> entry : tags.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();

            if(key == targetTag && value){
                result = true;
            }
        }

        return result;
    }

}
