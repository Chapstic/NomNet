package app.nomnet;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private String query;

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
       new BottomButtonActions(bottombarButtons, SearchResults.this, 1, "searchResults");

        nomResults = new ArrayList<>();//initialize results list

        intent = getIntent();
        query = intent.getExtras().getString("query");
        search(query);

        //saves search queries to content provider for recent search suggestions
        SearchRecentSuggestions srs = new SearchRecentSuggestions(this, SearchSuggestions.AUTHORITY, SearchSuggestions.MODE);
        //FOR TESTING PURPOSES ONLY!!!!!!!!!!!!!
        //ADD CLEAR HISTORY CODE TO SETTINGS PAGE
        //(or maybe keep? Clear History option doesn't always stay at top of list...)
        if(query.equals("Clear History")){
            srs.clearHistory();
        }
        else{
            srs.saveRecentQuery(query, null);
            srs.saveRecentQuery("Clear History", null);//adds clear history option to suggestions
        }

        // Initialize nomListView, feed into adapter, set adapter
        nomListView = (ListView)findViewById(R.id.resultsList);

        //if click nom on food feed, go to ViewNom
        intent = new Intent(this, ViewNom.class);
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
        getMenuInflater().inflate(R.menu.menu_search, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);//allows SearchView to always show


        //Style searchView
        /*Based on Google source code:
         *search_view.xml - https://android.googlesource.com/platform/frameworks/base/+/refs/heads/master/core/res/res/layout/search_view.xml
         *SearchView.java - https://android.googlesource.com/platform/frameworks/base/+/refs/heads/master/core/java/android/widget/SearchView.java
         */
        //go down embedded linearlayouts ----- hack-y way. won't work in the future if Google changes hierarchy
        LinearLayout l1 = (LinearLayout)searchView.getChildAt(0);
        LinearLayout l2 = (LinearLayout)l1.getChildAt(2);
        LinearLayout l3 = (LinearLayout)l2.getChildAt(1);
        LinearLayout l4 = (LinearLayout)l2.getChildAt(2);
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)l3.getChildAt(0);//auto complete box
        ImageView clearIcon = (ImageView) l3.getChildAt(1);//clear search button
        ImageView voiceIcon = (ImageView) l4.getChildAt(1);//voice icon
        ImageView searchIcon = (ImageView) l2.getChildAt(0);//search icon

        searchAutoComplete.setTextColor(Color.WHITE);//typing color
        searchAutoComplete.setHintTextColor(Color.LTGRAY);//hint color
        searchAutoComplete.setHint("Search Noms");
        searchAutoComplete.setDropDownBackgroundResource(R.drawable.abc_list_selector_disabled_holo_dark);

        clearIcon.setImageResource(R.drawable.clear);
        clearIcon.getLayoutParams().height = 64;
        clearIcon.getLayoutParams().width = 200;
        voiceIcon.setImageResource(R.drawable.mic);
        voiceIcon.getLayoutParams().height = 80;
        l4.getLayoutParams().width = 200;

        searchIcon.setImageResource(R.drawable.search_bar_icon);//search icon
        searchIcon.getLayoutParams().height = 80;

        // l1.setBackgroundColor(Color.GRAY); //FOR SEEING SPACING


        l3.setBackgroundResource(R.drawable.abc_textfield_search_default_mtrl_alpha);//searchplate underline color

        l4.setBackgroundResource(R.drawable.abc_textfield_search_default_mtrl_alpha);//voiceicon underline

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TopBarActions tba = new TopBarActions(item, this);
        return tba.handledSelection();
    }
}
