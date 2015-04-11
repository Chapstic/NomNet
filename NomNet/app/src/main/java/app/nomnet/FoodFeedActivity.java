package app.nomnet;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class FoodFeedActivity extends Activity {

    private Toolbar topbar;                 // This is the topbar that says NomNet
    private ListView listView;              // The feed
    private FoodFeedListAdapter adapter;    // Adapter populates the feed with noms
    private List<NomItem> nomList;          // The list of noms to add to te feed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);

        // Sets the top toolbar to be the one we specifically created
        topbar = (Toolbar) findViewById(R.id.topbar);
        //setSupportActionBar(topbar);

        //
        nomList = new ArrayList<>();
        getNoms();
        listView = (ListView)findViewById(R.id.listView);
        adapter = new FoodFeedListAdapter(this, nomList);
        listView.setAdapter(adapter);
    }

    private void getNoms(){
        String[] names = {"food1", "food2", "food3"};
        int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3};

        for(int i = 0; i < names.length; i++){
            NomItem newNom = new NomItem(names[i], images[i]);
            nomList.add(newNom);
        }
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
}
