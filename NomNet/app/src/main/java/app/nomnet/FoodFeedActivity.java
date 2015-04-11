package app.nomnet;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class FoodFeedActivity extends ActionBarActivity {

    private Toolbar topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);

       topbar = (Toolbar) findViewById(R.id.topbar);
       setSupportActionBar(topbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent mainIntent = new Intent(FoodFeedActivity.this,settings.class);
                FoodFeedActivity.this.startActivity(mainIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
