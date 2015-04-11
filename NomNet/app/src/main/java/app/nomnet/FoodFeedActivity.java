package app.nomnet;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;



public class FoodFeedActivity extends ActionBarActivity {
    private Nom testNom; //Probably replace w/ set or linked list of Noms?

    private Toolbar topbar;                 // This is the topbar that says NomNet
    private ListView listView;              // The feed
    private FoodFeedListAdapter adapter;    // Adapter populates the feed with noms
    private List<Nom> nomList;          // The list of noms to add to te feed
    private Intent intent;              // Intent that allows us to go to other pages

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);

        // Sets the top toolbar to be the one we specifically created
        topbar = (Toolbar) findViewById(R.id.topbar);
        setSupportActionBar(topbar);

        // Create and populate list of noms
        nomList = new ArrayList<>();
        getNoms();

        // Initialize list view, feed nomList into adapter, set adapter for list view
        listView = (ListView)findViewById(R.id.listView);
        intent = new Intent(this, ViewNom.class);
        adapter = new FoodFeedListAdapter(this, nomList, intent);
        listView.setAdapter(adapter);

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