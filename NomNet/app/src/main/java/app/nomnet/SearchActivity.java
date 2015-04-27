/*
 * TEAM NOMNET
 * -----------
 * Izzy Benavente
 * Mingzhe (Elliscope) Fang
 * Sydney Liu
 * Rebecca Wu
 * Albert Yue
 */
package app.nomnet;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private Toolbar topbar;
    private ListView categoriesListView;
    private ArrayList<String> categories;
    private ArrayAdapter<String> categoriesArrayAdapter;

    private String query, catQuery; //holds user's search query or category name

    private ImageButton[] bottombarButtons; //bottombar buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        topbar = (Toolbar) findViewById(R.id.topbar);
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
        new BottomButtonActions(bottombarButtons, SearchActivity.this, 1, "search");


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
        Intent i = getIntent();

        categoryClicks();

        //save user's searches to content provider (SearchSuggestions)
        if(Intent.ACTION_SEARCH.equals(i.getAction())){
            query = i.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions srs = new SearchRecentSuggestions(this, SearchSuggestions.AUTHORITY, SearchSuggestions.MODE);
            //FOR TESTING PURPOSES ONLY!!!!!!!!!!!!!
            //ADD CLEAR HISTORY CODE TO SETTINGS PAGE
            //(or maybe keep? Clear History option doesn't always stay at top of list...)
            if(query.equalsIgnoreCase("Clear History")){
                srs.clearHistory();
            }
            else{
                srs.saveRecentQuery(query, null);
                srs.saveRecentQuery("Clear History", null);//adds clear history option to suggestions

                //start SearchResults activity (pass query)
                Intent goToResultsPage = new Intent(SearchActivity.this, SearchResults.class);
                goToResultsPage.putExtra("query", query);
                startActivity(goToResultsPage);
            }

        }//end content provider code
    }

    public void categoryClicks(){//handle clicks on categories
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private Intent goToResultsPage;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: //Vegan
                            goToResultsPage = new Intent(SearchActivity.this, SearchResults.class);
                            goToResultsPage.putExtra("catQuery", "Vegan");
                            startActivity(goToResultsPage);
                            break;
                    case 1: //Vegetarian
                            goToResultsPage = new Intent(SearchActivity.this, SearchResults.class);
                            goToResultsPage.putExtra("catQuery", "Vegetarian");
                            startActivity(goToResultsPage);
                            break;
                    case 2: //Breakfast
                            goToResultsPage = new Intent(SearchActivity.this, SearchResults.class);
                            goToResultsPage.putExtra("catQuery", "Breakfast");
                            startActivity(goToResultsPage);
                            break;
                    case 3: //Lunch
                            goToResultsPage = new Intent(SearchActivity.this, SearchResults.class);
                            goToResultsPage.putExtra("catQuery", "Lunch");
                            startActivity(goToResultsPage);
                            break;
                    case 4: //Dinner
                            goToResultsPage = new Intent(SearchActivity.this, SearchResults.class);
                            goToResultsPage.putExtra("catQuery", "Dinner");
                            startActivity(goToResultsPage);
                            break;
                    default:
                            break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_search, menu);

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
