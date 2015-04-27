package app.nomnet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by Rebecca on 4/20/2015.
 */
public class TopBarActions {
    private MenuItem item;
    private Activity activity;

    public TopBarActions(MenuItem mi, Activity activity){
        item = mi;
        this.activity = activity;
    }
    public boolean handledSelection(){
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent mainIntent = new Intent(activity, Settings.class);
                activity.startActivity(mainIntent);
                return true;
            case R.id.action_logout:
                Intent mainIntents = new Intent(activity, SignIn.class);
                activity.startActivity(mainIntents);
                ((MyApplication) activity.getApplication()).setIsLoggedIn(false);
                return true;
            default:
                return activity.onOptionsItemSelected(item);
        }
    }
}