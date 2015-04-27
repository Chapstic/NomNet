/*
 * TEAM NOMNET
 * -----------
 * Izzy Benavente
 * Mingzhe (Elliscope) Fang
 * Sydney Liu
 * Rebecca Wu
 * Albert Yue
 */
package app.nomnet;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//implements AbsListView.OnScrollListener

public class ViewNom extends ActionBarActivity {
    private Nom currentNom;
    private Toolbar topbar;
    private TextView creatorText, upvotesText, dishNameText, ingredientsText, directionsText, tagsText;
    private ImageView appImageView;

    ListView list_com;
    CustomList adapter;

    //Nomification Part
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;

    private Set<String> tags;
    private String stringTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nom);

        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

        // Sets the top toolbar to be the one we specifically created
        topbar = (Toolbar) findViewById(R.id.topbar);
        setSupportActionBar(topbar);

        // Grabbing Nom object from previous activity
        Bundle b = getIntent().getExtras();
        currentNom = b.getParcelable("Nom");

        // Backbutton initialization
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);


        creatorText = (TextView) findViewById(R.id.creatorText);
        creatorText.setText(currentNom.getCreator());

        upvotesText = (TextView) findViewById(R.id.upvotesText);
        upvotesText.setText(Integer.toString(currentNom.getUpvotes()));

        appImageView = (ImageView) findViewById(R.id.foodImage);
        int imageID = currentNom.getImageID();
        appImageView.setImageBitmap(((MyApplication) this.getApplication()).getImagewithID(imageID).getBitmap());

        dishNameText = (TextView) findViewById(R.id.dishNameText);
        dishNameText.setText(currentNom.getName());

        ingredientsText = (TextView) findViewById(R.id.ingredientsText);
        ingredientsText.setText(currentNom.getIngredients());

        directionsText = (TextView) findViewById(R.id.directionsText);
        directionsText.setText(currentNom.getDirections());

        //Pop Up Nomification Implementaion
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        // Initialize tags w/ noms tags
        tags = currentNom.getTags();
        tagsText = (TextView) findViewById(R.id.tagsText);
        stringTags = "";
        for (String s : tags) {
            stringTags = stringTags + s + " ";
        }
        tagsText.setText(stringTags);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //go back
            onBackPressed();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            return true;
        } else {
            TopBarActions tba = new TopBarActions(item, this);
            return tba.handledSelection();
        }
    }


    public String getCreator() {
        return currentNom.getCreator();
    }

    public int getUpvotes() {
        return currentNom.getUpvotes();
    }

    //Nomification Event Handler


    //wait for user info from database to be past in->change parameters and make it dynamic
    public void notiButtonOnClicked(View view) {

        //suposed to be dynamically passed in as user profile image

        notification.setSmallIcon(R.drawable.isabella);

        notification.setTicker("New Nomification");
        notification.setWhen(System.currentTimeMillis());

        notification.setContentTitle("Izzy");
        notification.setContentText("liked your post");

        //The class that should be sent to once clicked
        Intent intent = new Intent(this, Nomification.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Build notification and issue it
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }
}
