package app.nomnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private Toolbar topbar;
    private ListView categoriesListView;
    private ArrayList<String> categories;
    private ArrayAdapter<String> categoriesArrayAdapter;
    private TabHost bottomTabHost;
    private TabHost.TabSpec home, search, camera, notifications, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        topbar = (Toolbar) findViewById(R.id.topbar);
        setSupportActionBar(topbar);
/*
        //Build Bottom Bar
        bottomTabHost = (TabHost) findViewById(android.R.id.tabhost);
        home = bottomTabHost.newTabSpec("Home");
        search= bottomTabHost.newTabSpec("Search");
        camera= bottomTabHost.newTabSpec("Camera");
        notifications= bottomTabHost.newTabSpec("Notifications");
        profile= bottomTabHost.newTabSpec("Profile");
        home.setIndicator(null, getResources().getDrawable(R.drawable.hometab));
        search.setIndicator(null, getResources().getDrawable(R.drawable.search));
        camera.setIndicator(null, getResources().getDrawable(R.drawable.cameratab));
        notifications.setIndicator(null, getResources().getDrawable(R.drawable.notificationtab));
        profile.setIndicator(null, getResources().getDrawable(R.drawable.profiletab));
        linkBar();
        bottomTabHost.addTab(home);
        bottomTabHost.addTab(search);
        bottomTabHost.addTab(camera);
        bottomTabHost.addTab(notifications);
        bottomTabHost.addTab(profile);
*/


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



    }

    private void linkBar(){//link the bottom bar to respective activities
        search.setContent(new Intent(this, SearchActivity.class));
        profile.setContent(new Intent(this, Profile.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_food_feed, menu);

        //disable search button on create
        menu.getItem(1).setEnabled(false);

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

        // Handle item selection
/*
        switch (item.getItemId()) {
            case R.id.ActionBarHome:
                item.setEnabled(true);
                startActivity(new Intent(SearchActivity.this, Profile.class));
                return true;
            case R.id.ActionBarSearch:
                item.setEnabled(false);
                return true;
            case R.id.ActionBarCamera:
                return true;
            case R.id.ActionBarNotification:
                return true;
            case R.id.ActionBarProfile:
                item.setEnabled(true);
                startActivity(new Intent(SearchActivity.this, Profile.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return super.onOptionsItemSelected(item);
    }
}
