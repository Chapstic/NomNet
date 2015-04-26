package app.nomnet;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ViewNom extends ActionBarActivity {
    private Nom currentNom;
    private Toolbar topbar;
    private TextView creatorText, upvotesText, dishNameText, ingredientsLabel, ingredientsText, directionsText, tagsText;
    private ImageView appImageView;



    //Nomification Part
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;

    private Set<String> tags;
    private String stringTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nom);

        // Sets the top toolbar to be the one we specifically created
        topbar = (Toolbar) findViewById(R.id.topbar);
        setSupportActionBar(topbar);

        Intent intent = getIntent();
        currentNom = (Nom)intent.getSerializableExtra("Nom");

        creatorText = (TextView) findViewById(R.id.creatorText);
        creatorText.setText(currentNom.getCreator());

        upvotesText = (TextView) findViewById(R.id.upvotesText);
        upvotesText.setText(Integer.toString(currentNom.getUpvotes()) );

        appImageView = (ImageView) findViewById(R.id.foodImage);
        appImageView.setImageDrawable(getResources().getDrawable(currentNom.getImageID() ));

        dishNameText = (TextView) findViewById(R.id.dishNameText);
        dishNameText.setText(currentNom.getName() );

        ingredientsLabel = (TextView) findViewById(R.id.ingredientsLabel);
        ingredientsLabel.setText("Ingredients" );

        ingredientsText = (TextView) findViewById(R.id.ingredientsText);
        ingredientsText.setText(currentNom.getIngredients() );

        directionsText = (TextView) findViewById(R.id.directionsText);
        directionsText.setText(currentNom.getDirections() );



        //Nomification Implementaion
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        // Initialize tags w/ noms tags
        tags = currentNom.getTags();
        tagsText = (TextView) findViewById(R.id.tagsText);
        stringTags = "";
        for(String s: tags){
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
        TopBarActions tba = new TopBarActions(item, this);
        return tba.handledSelection();
    }


    public String getCreator(){
        return currentNom.getCreator();
    }

    public int getUpvotes(){
        return currentNom.getUpvotes();
    }

    //Nomification Event Handler
    public void notiButtonOnClicked(View view) {
        //suposed to be dynamically passed in as user profile image

        notification.setSmallIcon(R.drawable.isabella);

        notification.setTicker("New Nomification");
        notification.setWhen(System.currentTimeMillis());

        notification.setContentTitle("Izzy");
        notification.setContentText("liked your post");

        //The class that should be sent to once clicked
        Intent intent = new Intent(this,Nomification.class);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Build notification and issue it
        NotificationManager nm =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }
}
