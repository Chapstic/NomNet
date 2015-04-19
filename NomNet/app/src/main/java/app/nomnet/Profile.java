package app.nomnet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class Profile extends ActionBarActivity {

    private Toolbar topbar;                 // This is the topbar that says NomNet

    private ImageButton home, search, camera, notifications, profile; //bottombar buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        topbar = (Toolbar) findViewById(R.id.topbar);
        topbar.setLogo(R.drawable.logosmall);
        topbar.setTitle("");
        setSupportActionBar(topbar);

        createBottomBarActions();
        //set bottombar profile button to already selected
        profile.setSelected(true);
        profile.setClickable(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

    //Handle click actions from bottom toolbar
    public void createBottomBarActions(){
        //initialize bottombar buttons
        home = (ImageButton) findViewById(R.id.BottomBarHome);
        search = (ImageButton) findViewById(R.id.BottomBarSearch);
        camera = (ImageButton) findViewById(R.id.BottomBarCamera);
        notifications = (ImageButton) findViewById(R.id.BottomBarNotification);
        profile = (ImageButton) findViewById(R.id.BottomBarProfile);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setSelected(true);
                search.setSelected(false);
                camera.setSelected(false);
                notifications.setSelected(false);
                profile.setSelected(false);

                startActivity(new Intent(Profile.this, FoodFeedActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setSelected(false);
                search.setSelected(true);
                camera.setSelected(false);
                notifications.setSelected(false);
                profile.setSelected(false);

                startActivity(new Intent(Profile.this, SearchActivity.class));
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

                startActivity(new Intent(Profile.this, Camera.class));
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

                // startActivity(new Intent(Profile.this, NomificationActivity.class));
            }
        });
    }
}
