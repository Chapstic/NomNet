package app.nomnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class FoodFeedActivity extends ActionBarActivity {
    private Nom testNom;                    // Probably replace w/ set or linked list of Noms?

    private Toolbar topbar;                 // This is the topbar that says NomNet
    private ListView listView;              // The feed
    private FoodFeedListAdapter adapter;    // Adapter populates the feed with noms
    private List<Nom> nomList;              // The list of noms to add to te feed
    private Intent intent;                  // Intent that allows us to go to other pages

    private ImageButton home, search, camera, notifications, profile; //bottombar buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);

        topbar = (Toolbar) findViewById(R.id.topbar);
        topbar.setLogo(R.drawable.logosmall);
        topbar.setTitle("");
        setSupportActionBar(topbar);

        createBottomBarActions();
        //set bottombar home button to already selected
        home.setSelected(true);
        home.setClickable(false);


    // Create and populate list of noms
    nomList = new ArrayList<>();
    getNoms();

    // Initialize list view, feed nomList into adapter, set adapter for list view
    listView = (ListView)findViewById(R.id.listView);
    intent = new Intent(this, ViewNom.class);
    adapter = new FoodFeedListAdapter(this, nomList, intent);
    listView.setAdapter(adapter);

    // Planning
    // If logged in, show bottom toolbar
    if(((MyApplication)this.getApplication()).getIsLoggedIn()){

    }
    // If not logged in, prompt hide bottom toolbar, prompt to make an account
    else{

    }



    // For testing the global variables
        // Toast.makeText(getApplicationContext(), String.valueOf(((MyApplication)this.getApplication()).getIsLoggedIn()),
        //             Toast.LENGTH_LONG).show();
    }

    private void getNoms(){
        String[] names = {"food1", "food2", "food3", "food4", "food5"};
        int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5};
        int[] upvotes = {20, 24, 36, 70, 14};

        //***********HARD-CODED data for testing purposes only. REMOVE WHEN DONE.****************
        //Probably want to create functions to parse ingredients and directions based on user input
        String ingredients = "Water - 4 cups" + '\n' +
                "Salt - 3 tablespoons";
        String directions = "1. Mix mix, swirl mix" + '\n' +
                "2. Drink" + '\n';
        Recipe recipe = new Recipe("Pad Thai", ingredients, directions);
        //testNom = new Nom("Albert", 128, recipe); //Testing functionality of Nom constructor and ViewNom compatibility

        for(int i = 0; i < names.length; i++){
            Nom newNom = new Nom(names[i], upvotes[i], images[i], recipe);
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


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setSelected(false);
                search.setSelected(true);
                camera.setSelected(false);
                notifications.setSelected(false);
                profile.setSelected(false);

                startActivity(new Intent(FoodFeedActivity.this, SearchActivity.class));
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

                startActivity(new Intent(FoodFeedActivity.this, Camera.class));
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

               // startActivity(new Intent(FoodFeedActivity.this, NomificationActivity.class));
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

                startActivity(new Intent(FoodFeedActivity.this, Profile.class));
            }
        });
    }
}

