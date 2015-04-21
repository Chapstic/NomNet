package app.nomnet;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

import app.nomnet.R;
import android.widget.Button;


public class Nomification extends ActionBarActivity {
    ListView list;

private static final String TAG= "buckysMessage";

    private ImageButton[] bottombarButtons; //bottombar buttons
    private Toolbar topbar;                 // This is the topbar that says NomNet


//hard coded part
//override with dynamic input
//passed in username array and imagearry

    String[] web= {
            "Sydney Liu \n commented on your post",
            "Rebecca Wu \n commented on your post",
            "Izzy Benavente \n commented on your post",
            "Albert Yue \n commented on your post",
            "Elliscope Fang \n commented on your post",
    };


    Integer[] imageId= {

            R.drawable.sydney,
            R.drawable.rebecca,
            R.drawable.isabella,
            R.drawable.albert,
            R.drawable.elliscope,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomification);

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
        BottomButtonActions bba = new BottomButtonActions(bottombarButtons, Nomification.this, 3);


        //create Nomification List View
        CustomList adapter = new
                CustomList(Nomification.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Nomification.this, web[+ position]+ "viewed your post recently ", Toast.LENGTH_SHORT).show();
            }
        });


        Button return_button = (Button)findViewById(R.id.ReturnButton);
        return_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Nomification.this, Profile.class);
                startActivity(i);
            }
        });

        Log.i(TAG,"OnCreate");
    }
}

