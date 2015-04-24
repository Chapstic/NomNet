package app.nomnet;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.List;

/**
 * Created by Albert on 4/11/2015.
 */
public class Nom implements Serializable {
    private String creator; //Replace w/ user class later
    private int upvotes, image;
    private Recipe recipe; //Might be better to change recipe information to String[] so we can use scroll text fields later
    Map<String, Boolean> tags;


    //Comments under the Nom
    public List<String> Comments_list;
    public List<Integer> Comments_user_profile;


    Nom(String creator, int upvotes, int image, Recipe recipe, Map<String, Boolean> tags){
        this.tags = new HashMap<String, Boolean>();


        this.creator = creator;
        this.upvotes = upvotes;
        this.image = image;
        this.recipe = recipe;

        this.tags = tags;


        Comments_list = new ArrayList<String>();
        Comments_user_profile = new ArrayList<Integer>();


        addComments(R.drawable.sydney,"Oh this food is amazing!!");
        addComments(R.drawable.albert, "So good!!");
        addComments(R.drawable.rebecca, "Let's go together!");
        //System.out.println(Comments_list.size());
    }

    public String getCreator(){
        return creator;
    }

    public int getUpvotes(){
        return upvotes;
    }

    public int getImage() { return image; }

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
    public Boolean hasTag(String targetTag) {
        Boolean result = false;

        for (Map.Entry<String, Boolean> entry : tags.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();


            if (key == targetTag && value) {
                result = true;
            }
        }

        return result;
    }

    //username to image format conversion
    //public String userProfile(String username){ return "R.drawable."+username; }

    //idea function style ->wait for database connection
    //public vlid addComments(User user, String comments_string)
    public void addComments(Integer ImagID, String comment_string){
        Comments_user_profile.add(ImagID);
        Comments_list.add(comment_string);
        System.out.println(Comments_list.size());
    }

    public Integer[] getC_userProfile(){
        Integer[] Cprofile_array = new Integer[Comments_user_profile.size()];
        for(int i = 0; i<Comments_user_profile.size();i++){
            Cprofile_array[i]=Comments_user_profile.get(i);
        }
        return Cprofile_array ;
    }

    public String[] getComments(){
        String[] Comments_array = new String[Comments_list.size()];
        Comments_list.toArray(Comments_array);
        return Comments_array;
    }

}
