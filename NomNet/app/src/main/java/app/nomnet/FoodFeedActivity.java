package app.nomnet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FoodFeedActivity extends ActionBarActivity implements AbsListView.OnScrollListener {
    private Activity activity = this;
    private Nom testNom;                    // Probably replace w/ set or linked list of Noms?

    private Toolbar topbar;                 // This is the topbar that says NomNet
    private ListView listView;              // The feed
    private FoodFeedListAdapter adapter;    // Adapter populates the feed with noms
    private List<Nom> nomList;              // The list of noms to add to te feed
    private Intent intent;                  // Intent that allows us to go to other pages

    private TextView textViewCreateAcct;
    private LinearLayout bottomBarLayout; // include of bottombar layout

    private ImageButton[] bottombarButtons; //bottombar buttons


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);

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

        new BottomButtonActions(bottombarButtons, FoodFeedActivity.this, 0, "foodfeed");

        // Create and populate list of noms
        nomList = new ArrayList<>();
        getNoms();

        // Initialize list view, feed nomList into adapter, set adapter for list view
        listView = (ListView)findViewById(R.id.listView);

        //if click nom on food feed, go to ViewNom
        intent = new Intent(this, ViewNom.class);

        adapter = new FoodFeedListAdapter(this, nomList, intent);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);


        textViewCreateAcct = (TextView) findViewById(R.id.create_account);
        bottomBarLayout = (LinearLayout) findViewById(R.id.bottombar);

        // If logged in, show bottom toolbar
        if (((MyApplication) this.getApplication()).getIsLoggedIn()) {
            textViewCreateAcct.setVisibility(View.GONE); // hide the text view
        }
        // If not logged in, hide bottom toolba
        // Prompt to make an account, link to registration activityr
        else{
            bottomBarLayout.setVisibility(View.GONE);
            textViewCreateAcct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(FoodFeedActivity.this, Register.class);
                    startActivity(i);
                }
            });
        }
    } // End of onCreate

    private void getNoms() {
        Random rand = new Random();
        int totalNoms = 18;
        String[] userNames = {"Sydney", "Izzy", "Rebecca", "Elliscope", "Albert"};
        String[] names = {"Beef Potstickers", "Shumai", "Clam Chowder Bread Bowl",
                        "Jambalaya", "Spicy Wontons", "Japanese Beef Bowl",
                        "Pork Katsu with Curry", "Brick Toast", "Breakfast Sandwich",
                        "Pullout Bread", "Chicken Cordon Bleu", "Croissant Sandwich",
                        "Strawberry Brick Toast", "Brownies", "Chicken Karaage",
                        "Zeppoles", "Strawberry Green Tea Brick Toast",
                        "Ground Beef Stew", "Shawarma on Hummus", "Italian Pork Sausage"};

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

        Boolean breakfast, lunch, dinner;
        breakfast = lunch = dinner = true;

        Map<String, Boolean> tags = new HashMap<String, Boolean>();
        tags.put("breakfast", true);
        tags.put("lunch", true);
        tags.put("dinner", true);

        for (int i = 0; i < totalNoms; i++) {
            Recipe recipe = new Recipe(names[i], ingredients, directions);
            Nom newNom = new Nom(userNames[rand.nextInt(5)], upvotes.get(i), imageIDs.get(i), recipe, tags);
            nomList.add(newNom);
        }
    }

    // For never-ending feed
    public void onScroll(AbsListView alv, int first, int numVisible, int total) {

        // Can add a padding
        boolean loadMore = first + numVisible >= total;

        // Load more items if there are more to load
        if(loadMore && adapter.getCount() < adapter.getMaxItems()-1)
        {
            adapter.numItemsInFeed += numVisible; // or any other amount

            if(adapter.getCount() >= adapter.getMaxItems()){
                adapter.numItemsInFeed = adapter.getMaxItems(); // keep in bounds
            }

            adapter.notifyDataSetChanged();
        }
    }

    public void onScrollStateChanged(AbsListView alv, int i) { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // Only show menu if logged in
        if(((MyApplication) this.getApplication()).getIsLoggedIn()){
            getMenuInflater().inflate(R.menu.menu_master, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TopBarActions tba = new TopBarActions(item, this);
        return tba.handledSelection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindDrawables(findViewById(R.id.FoodFeedItem));
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}






