package app.nomnet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;


public class Profile extends ActionBarActivity {

    private Toolbar topbar;                 // This is the topbar that says NomNet

    private ImageButton[] bottombarButtons; //bottombar buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        BottomButtonActions bba = new BottomButtonActions(bottombarButtons, Profile.this, 4);

        Button nomi_button = (Button)findViewById(R.id.nomif_button);
        nomi_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, Nomification.class);
                startActivity(i);
            }
        });

        Button return_button = (Button)findViewById(R.id.return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, FoodFeedActivity.class);
                startActivity(i);
            }
        });


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
}
