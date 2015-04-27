package app.nomnet;

import android.app.Activity;
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
    } // End of onCreate

    private void getNoms() {
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

        String cIngredients = "1 lb boneless chicken thighs\n " +
                "1/2 tablespoon freshly grated ginger\n" +
                "1-2 clove, of freshly grated galic\n" +
                "3 tablespoons soy sauce\n" +
                "1 tablespoon sake (Japanese rice wine)\n" +
                "ground black pepper, for additional spiciness\n" +
                "1 cup potato starch, to dust\n" +
                "vegetable oil (for frying)";
        String cDirections = "Cut chicken in bite size pieces. In a plastic container or large " +
                "ziploc bag, combine grated ginger, garlic, soy sauce and Sake." +
                "Put the chicken in the marinade and mix chicken well. Put the chicken " +
                "in the fridge for 15 to 20 minutes." +
                "In a deep frying pan or a wok, heat vegetable oil to 180 degree " +
                "Celsius or 350°F" +
                "Dust the chicken with potato starch and fry in the oil until golden. " +
                "To make them really crispy, lift chicken from the oil with chopsticks or" +
                " tongs from time to time so that the chicken will be aired out (so to speak).";

        String ingredients = "1 vanilla bean\n" +
                "1/2 cup sugar, plus 3 tablespoons\n" +
                "2 tablespoons ground cinnamon\n" +
                "1 stick butter\n" +
                "1/4 teaspoon salt\n" +
                "1 cup water\n" +
                "1 cup all-purpose flour\n" +
                "4 eggs\n" +
                "Olive oil, for frying\n";

        String directions = "Cut open the vanilla bean lengthwise. Using the back of a knife, " +
                "scrape along the inside of the vanilla bean to collect the seeds. Scrape vanilla" +
                " bean seeds into a small bowl. Add the 1/2 cup sugar and cinnamon and stir to combine. Set aside.\n" +
                "\n" +
                "In a medium saucepan combine the butter, salt, 3 tablespoons of sugar," +
                " and water over medium heat. Bring to a boil. Take pan off the heat and stir in the flour. " +
                "Return pan to the heat and stir continuously until mixture forms a ball, about 3 to 5 minutes. " +
                "Transfer the flour mixture to a medium bowl. Using an electric hand mixer on low speed, add eggs, " +
                "1 at a time, incorporating each egg completely before adding the next. Beat until smooth. " +
                "If not frying immediately, cover with plastic wrap and reserve in the refrigerator.\n" +
                "\n" +
                "Meanwhile, pour enough oil into a large frying pan to reach a depth of 2 inches. " +
                "Heat the oil over medium heat until a deep-fry thermometer registers 375 degrees F.\n" +
                "\n" +
                "Using a small ice-cream scooper or 2 small spoons, carefully drop about a tablespoon of " +
                "the dough into the hot olive oil, frying in batches. Turn the zeppole once or twice, cooking " +
                "until golden and puffed up, about 5 minutes. Drain on paper towels. Toss with cinnamon-sugar. " +
                "Arrange on a platter and serve immediately.\n";

        Set<String> tags = new HashSet<String>();
        tags.add("breakfast");
        tags.add("lunch");
        tags.add("dinner");

        for (int i = 0; i < totalNoms; i++) {
            Recipe recipe = new Recipe(names[i], ingredients, directions);
            if(i==0){
                recipe = new Recipe(names[i], cIngredients, cDirections);
            }
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






