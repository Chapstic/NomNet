package app.nomnet;

import java.io.Serializable;

/**
 * Created by Albert on 4/11/2015.
 */
public class Recipe implements Serializable{
    String dishName, ingredients, directions;

    Recipe(String dishName, String ingredients, String directions){
        this.dishName = dishName;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    String getName(){
        return dishName;
    }

    String getIngredients(){
        return ingredients;
    }

    String getDirections(){
        return directions;
    }
}
