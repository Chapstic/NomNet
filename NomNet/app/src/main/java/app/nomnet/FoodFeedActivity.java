package app.nomnet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class FoodFeedActivity extends ActionBarActivity {

    private Toolbar topbar;
    private Toolbar bottombar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);

        Toast.makeText(getApplicationContext(), "FoodFeed",
                Toast.LENGTH_LONG).show();

        //topbar = (Toolbar) findViewById(R.id.topbar);
       // setSupportActionBar(topbar);

        bottombar = (Toolbar) findViewById(R.id.bottomBar);
      //  bottombar.inflateMenu(R.menu.menu_bottom_bar, );

      startActivity(new Intent(FoodFeedActivity.this, SearchActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

     /*   new MenuInflater(this).inflate(R.menu.menu_bottom_bar, menu);
       RelativeLayout  relativeLayout = (RelativeLayout) menu.findItem(R.id.ActionBarHome);

        View view = getLayoutInflater().inflate(R.layout.activity_food_feed, null);

        relativeLayout.addView(view);*/
       getMenuInflater().inflate(R.menu.menu_bottom_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

    /*    switch (item.getItemId()) {
            case R.id.ActionBarHome:
                findViewById(R.id.ActionBarHome).setEnabled(false);
                findViewById(R.id.ActionBarHome).setClickable(false);
                return true;
            case R.id.ActionBarSearch:
                findViewById(R.id.ActionBarSearch).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(FoodFeedActivity.this, SearchActivity.class));
                    }
                });
                return true;
            case R.id.ActionBarCamera:
                return true;
            case R.id.ActionBarNotification:
                return true;
            case R.id.ActionBarProfile:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return super.onOptionsItemSelected(item);
    }
}

