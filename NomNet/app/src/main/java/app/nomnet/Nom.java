/*
 * TEAM NOMNET
 * -----------
 * Izzy Benavente
 * Mingzhe (Elliscope) Fang
 * Sydney Liu
 * Rebecca Wu
 * Albert Yue
 */
package app.nomnet;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import java.util.List;

/**
 * Created by Albert on 4/11/2015.
 */


public class Nom implements Parcelable {
    private String creator; // Replace w/ user class later
    private int upvotes, imageID;
    private Recipe recipe; // Might be better to change recipe information to String[] so we can use scroll text fields later
    private Set<String> tags;
    private Comments comments;

    //Comments under the Nom
    public List<String> Comments_list;
    public List<Integer> Comments_user_profile;

    Nom(String creator, int upvotes, int imageID, Recipe recipe, Set<String> tags){
        this.creator = creator;
        this.upvotes = upvotes;
        this.imageID = imageID;
        this.recipe = recipe;
        this.tags = tags;
        comments = new Comments();


        comments.addComments(R.drawable.sydney,"Oh this food is amazing!!");
        comments.addComments(R.drawable.albert, "So good!!");
        comments.addComments(R.drawable.rebecca, "Let's go together!");
        //System.out.println(Comments_list.size());

    }

    // Constructor used when re-constructing object from parcel
    protected Nom(Parcel in) {
        creator = in.readString();
        upvotes = in.readInt();
        imageID = in.readInt();
        recipe = (Recipe) in.readValue(Recipe.class.getClassLoader());
        tags = (Set) in.readValue(Set.class.getClassLoader());
        comments = (Comments) in.readValue(Comments.class.getClassLoader());
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
    public Comments getCommentsObject(){return comments;}
    public Set<String> getTags(){ return tags; }

    // Checks to see if Nom has target tag (passed in)
    public Boolean hasTag(String targetTag){

        for(String s : tags) {
            if(targetTag.equalsIgnoreCase(s) ){
                return true;
            }
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(creator);
        parcel.writeInt(upvotes);
        parcel.writeInt(imageID);
        parcel.writeValue(recipe);
        parcel.writeValue(tags);
        parcel.writeValue(comments);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Nom> CREATOR = new Parcelable.Creator<Nom>() {
        @Override
        public Nom createFromParcel(Parcel in) {
            return new Nom(in);
        }

        @Override
        public Nom[] newArray(int size) {
            return new Nom[size];
        }
    };

}


