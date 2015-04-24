package app.nomnet;

import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by Rebecca on 4/20/2015.
 */
public class TopBarActions {
    private MenuItem item;

    public TopBarActions(MenuItem mi){
        item = mi;
    }
/*
    public boolean handledSelection(){
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent mainIntent = new Intent(FoodFeedActivity.this,Settings.class);
                FoodFeedActivity.this.startActivity(mainIntent);
                return true;
            case R.id.action_logout:
                Intent mainIntents = new Intent(FoodFeedActivity.this, SignIn.class);
                FoodFeedActivity.this.startActivity(mainIntents);
                ((MyApplication) this.getApplication()).setIsLoggedIn(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
}
