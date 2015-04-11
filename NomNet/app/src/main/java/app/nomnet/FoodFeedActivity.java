package app.nomnet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class FoodFeedActivity extends ActionBarActivity {

    private Toolbar topbar;
    private Nom testNom; //Probably replace w/ set or linked list of Noms?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);

        topbar = (Toolbar) findViewById(R.id.topbar);
        setSupportActionBar(topbar);


        //***********HARD-CODED data for testing purposes only. REMOVE WHEN DONE.****************
        //Probably want to create functions to parse ingredients and directions based on user input
        String ingredients = "Water - 4 cups" + '\n' +
                            "Salt - 3 tablespoons";
        String directions = "1. Mix mix, swirl mix" + '\n' +
                            "2. Drink" + '\n';
        Recipe recipe = new Recipe("Pad Thai", ingredients, directions);
        testNom = new Nom("Albert", 128, recipe); //Testing functionality of Nom constructor and ViewNom compatibility
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_feed, menu);
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

    public void clickedNom(View view) {
        Intent intent = new Intent(this, ViewNom.class);
        //**************Probably want to insert some findViewById() to figure out which Nom in list was selected***************
        intent.putExtra("Nom", testNom); //Passes Nom object in Map<Key,Value> format to next activity (view_nom)
        startActivity(intent); //Starts next activity (view_nom)
    }
}
