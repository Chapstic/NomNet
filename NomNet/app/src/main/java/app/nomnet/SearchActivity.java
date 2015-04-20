package app.nomnet;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private Toolbar topbar;
    private ListView categoriesListView;
    private ArrayList<String> categories;
    private ArrayAdapter<String> categoriesArrayAdapter;

    private String query; //holds user's search query or category name

    private ImageButton home, search, camera, notifications, profile; //bottombar buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        topbar = (Toolbar) findViewById(R.id.topbar);
        topbar.setTitle("");
        setSupportActionBar(topbar);

        createBottomBarActions();
        //set bottombar search button to already selected
        search.setSelected(true);
        search.setClickable(false);

        //categories list
        categoriesListView = (ListView) findViewById(R.id.searchListView);
        categories = new ArrayList<>();
        categories.add("VEGAN");
        categories.add("VEGETARIAN");
        categories.add("BREAKFAST");
        categories.add("LUNCH");
        categories.add("DINNER");
        categoriesArrayAdapter = new ArrayAdapter<>(this,
                R.layout.stylelistitems,categories);
        categoriesListView.setAdapter(categoriesArrayAdapter);

        //Receive search query
        Intent i = getIntent();
        if(Intent.ACTION_SEARCH.equals(i.getAction())){
            query = i.getStringExtra(SearchManager.QUERY);
            search(query);
        }
        //clicked category


    }

    private void search(String query) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_search, menu);


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

    //Handle click actions from bottom toolbar
    public void createBottomBarActions(){
        //initialize bottombar buttons
        home = (ImageButton) findViewById(R.id.BottomBarHome);
        search = (ImageButton) findViewById(R.id.BottomBarSearch);
        camera = (ImageButton) findViewById(R.id.BottomBarCamera);
        notifications = (ImageButton) findViewById(R.id.BottomBarNotification);
        profile = (ImageButton) findViewById(R.id.BottomBarProfile);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setSelected(true);
                search.setSelected(false);
                camera.setSelected(false);
                notifications.setSelected(false);
                profile.setSelected(false);

                startActivity(new Intent(SearchActivity.this, FoodFeedActivity.class));
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setSelected(false);
                search.setSelected(false);
                camera.setSelected(true);
                notifications.setSelected(false);
                profile.setSelected(false);

                startActivity(new Intent(SearchActivity.this, Camera.class));
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setSelected(false);
                search.setSelected(false);
                camera.setSelected(false);
                notifications.setSelected(true);
                profile.setSelected(false);

                // startActivity(new Intent(SearchActivity.this, NomificationActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setSelected(false);
                search.setSelected(false);
                camera.setSelected(false);
                notifications.setSelected(false);
                profile.setSelected(true);

                startActivity(new Intent(SearchActivity.this, Profile.class));
            }
        });
    }
}
