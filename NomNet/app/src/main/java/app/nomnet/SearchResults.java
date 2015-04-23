package app.nomnet;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SearchResults extends ActionBarActivity {

    private ArrayList<Nom> nomResults; //arraylist holding resulting noms from search
    private Toolbar topbar;
    private ImageButton[] bottombarButtons;
    private ListView nomListView;
    private SearchResultsAdapter searchResultsAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        topbar = (Toolbar) findViewById(R.id.topbar);
        topbar.setLogo(R.drawable.logosmall);
        topbar.setTitle("");
        setSupportActionBar(topbar);

        bottombarButtons = new ImageButton[5];
        //initialize bottombar buttons
        bottombarButtons[0] = (ImageButton) findViewById(R.id.BottomBarHome);
        bottombarButtons[1] = (ImageButton) findViewById(R.id.BottomBarSearch);
        bottombarButtons[2] = (ImageButton) findViewById(R.id.BottomBarCamera);
        bottombarButtons[3] = (ImageButton) findViewById(R.id.BottomBarNotification);
        bottombarButtons[4] = (ImageButton) findViewById(R.id.BottomBarProfile);

        //Create click actions from bottom toolbar
        //Third parameter references the current activity: 0 - FoodFeed, 1 - Search, etc
       new BottomButtonActions(bottombarButtons, SearchResults.this, 1);

        nomResults = new ArrayList<>();//initialize results list

        // Get the intent, verify the action and get the query
        intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            search(query);//populate arraylist of resulting noms from query
        }

        // Initialize nomListView, feed into adapter, set adapter
        nomListView = (ListView)findViewById(R.id.resultsList);

        //if click nom on food feed, go to ViewNom
        intent = new Intent(this, ViewNom.class);
        searchResultsAdapter = new SearchResultsAdapter(this, nomResults, intent);
        nomListView.setAdapter(searchResultsAdapter);

        searchResultsAdapter = new SearchResultsAdapter(this, nomResults, intent);
        nomListView.setAdapter(searchResultsAdapter);
    }

    //search for and populate arraylist of resulting Noms
    public void search(String q){

        String[] userNames = {"Sydney", "Izzy", "Rebecca", "Elliscope", "Albert"};
        String[] names = {"food1", "food2", "food3", "food4", "food5"};

        int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5};
        int[] upvotes = {20, 24, 36, 70, 14};

        //***********HARD-CODED data for testing purposes only. REMOVE WHEN DONE.****************
        //Probably want to create functions to parse ingredients and directions based on user input
        String ingredients = "Water - 4 cups" + '\n' +
                "Salt - 3 tablespoons";
        String directions = "1. Mix mix, swirl mix" + '\n' +
                "2. Drink" + '\n';

        Boolean breakfast, lunch, dinner;
        breakfast = lunch = dinner = true;

        Map<String, Boolean> tags = new HashMap<String, Boolean>();
        tags.put("breakfast", true);
        tags.put("lunch", true);
        tags.put("dinner", true);

        for (int i = 0; i < names.length; i++) {
            Recipe recipe = new Recipe(names[i], ingredients, directions);
            Nom newNom = new Nom(userNames[i], upvotes[i], images[i], recipe, tags);
            nomResults.add(newNom);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        return true;
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
