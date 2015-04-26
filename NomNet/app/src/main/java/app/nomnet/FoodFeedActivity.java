package app.nomnet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FoodFeedActivity extends ActionBarActivity implements AbsListView.OnScrollListener {
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

        //initialize bottombar buttons
        bottombarButtons = new ImageButton[5];
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

        // Planning
        // If logged in, show bottom toolbar
        if (((MyApplication) this.getApplication()).getIsLoggedIn()) {
            textViewCreateAcct.setVisibility(View.GONE); // hide the text view
        }
        // If not logged in, hide bottom toolbar
        // Prompt to make an account, link to registration activity
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
        // Toast.makeText(getApplicationContext(), String.valueOf(((MyApplication)this.getApplication()).getIsLoggedIn()),
        //             Toast.LENGTH_LONG).show();

    } // End of onCreate

    private void getNoms() {
        String[] userNames = {"Sydney", "Izzy", "Rebecca", "Elliscope", "Albert"};
        String[] names = {"food1", "food2", "food3", "food4", "food5"};

        int[] imageIDs = {0, 1, 2, 3, 4};
        int[] upvotes = {20, 24, 36, 70, 14};

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

        for (int i = 0; i < names.length; i++) {
            Recipe recipe = new Recipe(names[i], ingredients, directions);
            Nom newNom = new Nom(userNames[i], upvotes[i], imageIDs[i], recipe, tags);
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






