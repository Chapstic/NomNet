package app.nomnet;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Albert on 4/11/2015.
 */

public class Nom implements Parcelable {
    private String creator; // Replace w/ user class later
    private int upvotes, imageID;
    private Recipe recipe; // Might be better to change recipe information to String[] so we can use scroll text fields later
    private Set<String> tags;


    // Standard basic constructor for non-parcel object
    Nom(String creator, int upvotes, int imageID, Recipe recipe, Set<String> tags){
        this.creator = creator;
        this.upvotes = upvotes;
        this.imageID = imageID;
        this.recipe = recipe;
        this.tags = tags;
    }

    // Constructor used when re-constructing object from parcel
    protected Nom(Parcel in) {
        creator = in.readString();
        upvotes = in.readInt();
        imageID = in.readInt();
        recipe = (Recipe) in.readValue(Recipe.class.getClassLoader());
        tags = (Set) in.readValue(Set.class.getClassLoader());
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

    public Set<String> getTags(){ return tags; }

    // Checks to see if Nom has target tag (passed in)
    public Boolean hasTag(String targetTag){

        for(String s : tags) {
            if(targetTag.equals(s) ){
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




