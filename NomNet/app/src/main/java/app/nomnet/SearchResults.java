package app.nomnet;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class SearchResults extends ActionBarActivity {

    private ArrayList<Nom> nomResults; //arraylist holding resulting noms from search
    private Toolbar topbar;
    private ImageButton[] bottombarButtons;
    private ListView nomListView;
    private SearchResultsAdapter searchResultsAdapter;
    private Intent intent;
    private String query, catQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        topbar = (Toolbar) findViewById(R.id.topbar);
        topbar.setLogo(R.drawable.logosmall);
        topbar.setTitle("");
        setSupportActionBar(topbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        topbar.getChildAt(0).getLayoutParams().width = 60;

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

        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);//transition


        nomResults = new ArrayList<>();//initialize results list

        intent = getIntent();
        SearchRecentSuggestions srs = new SearchRecentSuggestions(this, SearchSuggestions.AUTHORITY, SearchSuggestions.MODE);

        //category clicked
        catQuery = intent.getExtras().getString("catQuery");
        if(catQuery!=null)search(catQuery);

        //saves search queries to content provider for recent search suggestions
        //FOR TESTING PURPOSES ONLY!!!!!!!!!!!!!
        //ADD CLEAR HISTORY CODE TO SETTINGS PAGE
        //(or maybe keep? Clear History option doesn't always stay at top of list...)
        if(query!=null && query.equalsIgnoreCase("Clear History")){
            srs.clearHistory();
        }
        else{
            srs.saveRecentQuery(query, null);
            srs.saveRecentQuery("Clear History", null);//adds clear history option to suggestions

            search(query);
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

        Random rand = new Random();
        int totalNoms = 15;
        String[] userNames = {"Sydney", "Izzy", "Rebecca", "Elliscope", "Albert"};
        String[] names = {"Chicken Karaage", "Zeppoles", "Clam Chowder Bread Bowl",
                "Ground Beef Stew", "Spicy Wontons", "Japanese Beef Bowl",
                "Pork Katsu with Curry", "Brick Toast", "Breakfast Sandwich",
                "Pullout Bread", "Chicken Cordon Bleu", "Croissant Sandwich",
                "Strawberry Brick Toast", "Brownies", "Beef Potstickers",
                "Shumai", "Strawberry Green Tea Brick Toast",
                "Jambalaya", "Shawarma on Hummus", "Italian Pork Sausage"};

        List<Integer> imageIDs = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            imageIDs.add(i);
        }
        List<Integer> upvotes = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            upvotes.add(rand.nextInt(50));
        }

        //***********HARD-CODED data for testing purposes only. REMOVE WHEN DONE.****************
        //Probably want to create functions to parse ingredients and directions based on user input
        String ingredients = "Water - 4 cups" + '\n' +
                "Salt - 3 tablespoons";
        String directions = "1. Mix mix, swirl mix" + '\n' +
                "2. Drink" + '\n';

        Set<String> tags = new HashSet<String>();
        tags.add("breakfast");
        tags.add("lunch");
        tags.add("dinner");

        for (int i = 0; i < totalNoms; i++) {
            Recipe recipe = new Recipe(names[i], ingredients, directions);
            Nom newNom = new Nom(userNames[rand.nextInt(5)], upvotes.get(i), imageIDs.get(i), recipe, tags);
            nomResults.add(newNom);
        }
    }

    // For never-ending feed
    public void onScroll(AbsListView alv, int first, int numVisible, int total) {

        // Can add a padding
        boolean loadMore = first + numVisible >= total;

        // Load more items if there are more to load
        if(loadMore && searchResultsAdapter.getCount() < searchResultsAdapter.getMaxItems()-1)
        {
            searchResultsAdapter.numItemsInFeed += numVisible; // or any other amount

            if(searchResultsAdapter.getCount() >= searchResultsAdapter.getMaxItems()){
                searchResultsAdapter.numItemsInFeed = searchResultsAdapter.getMaxItems(); // keep in bounds
            }

            searchResultsAdapter.notifyDataSetChanged();
        }
    }

    public void onScrollStateChanged(AbsListView alv, int i) { }

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
        if(item.getItemId()==android.R.id.home){
            //go back 
            onBackPressed();
            return true;
        }
        else{
            TopBarActions tba = new TopBarActions(item, this);
            return tba.handledSelection();
        }
    }
}
