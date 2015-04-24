package app.nomnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodFeedActivity extends ActionBarActivity {
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


        textViewCreateAcct = (TextView) findViewById(R.id.create_account);
        bottomBarLayout = (LinearLayout) findViewById(R.id.bottombar);

        // Planning
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


        // For testing the global variables

        // Toast.makeText(getApplicationContext(), String.valueOf(((MyApplication)this.getApplication()).getIsLoggedIn()),
        //             Toast.LENGTH_LONG).show();

    } // End of onCreate

    private void getNoms() {
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
            nomList.add(newNom);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_feed, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // TopBarActions tba = new TopBarActions(item);
       // return tba.handleSelection();
        return super.onOptionsItemSelected(item);
    }

}






