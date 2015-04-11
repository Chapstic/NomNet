package app.nomnet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private Toolbar topbar;
    private ListView categoriesListView;
    private ArrayList<String> categories;
    private ArrayAdapter<String> categoriesArrayAdapter;
  //  private ImageButton home, search, camera, notifications, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toast.makeText(getApplicationContext(), "Search",
                Toast.LENGTH_LONG).show();

       // topbar = (Toolbar) findViewById(R.id.topbar);
       // setSupportActionBar(topbar);

        //categories list
        categoriesListView = (ListView) findViewById(R.id.searchListView);
        categories = new ArrayList<String>();
        categories.add("VEGAN");
        categories.add("VEGETARIAN");
        categories.add("BREAKFAST");
        categories.add("LUNCH");
        categories.add("DINNER");
        categoriesArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.centerlistitems,categories);
        categoriesListView.setAdapter(categoriesArrayAdapter);


      //  bottomBarActions();

    }

    //category actions

    //link bottom bar to other activities
   /* public void bottomBarActions(){
        home = (ImageButton) findViewById(R.id.ActionBarHome);
        search = (ImageButton) findViewById(R.id.ActionBarSearch);
        camera = (ImageButton) findViewById(R.id.ActionBarCamera);
        notifications = (ImageButton) findViewById(R.id.ActionBarNotification);
        profile = (ImageButton) findViewById(R.id.ActionBarProfile);

        home.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent homeIntent;
                homeIntent = new Intent(SearchActivity.this, FoodFeedActivity.class);
            }
        });

        search.setClickable(false);
        search.setEnabled(false);

    }
*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_bottom_bar, menu);

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

        return super.onOptionsItemSelected(item);
    }
}
