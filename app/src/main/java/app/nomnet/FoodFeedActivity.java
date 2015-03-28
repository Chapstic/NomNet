package app.nomnet;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class FoodFeedActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private NomPopulator nomPopulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.feed_list);
        nomPopulator = new NomPopulator(FoodFeedActivity.this, getNoms());
        recyclerView.setAdapter(nomPopulator);
        recyclerView.setLayoutManager(new LinearLayoutManager(FoodFeedActivity.this));

        setContentView(R.layout.activity_food_feed);
    }

    public static List<Nom> getNoms(){
        List<Nom> data = new ArrayList();
        int[] images = {R.drawable.food1,R.drawable.food2, R.drawable.food3};
        String[] titles = {"Food 1", "Food 2", "Food 3"};

        for(int i = 0; i < images.length; i++){
            Nom cur = new Nom();
            cur.image = images[i];
            cur.title = titles[i];
            data.add(cur);
        }
        return data;
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
