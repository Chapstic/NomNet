package app.nomnet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ViewNom extends ActionBarActivity {
    private Nom currentNom;
    private Toolbar topbar;
    private TextView creatorText, upvotesText, dishNameText, ingredientsLabel, ingredientsText, directionsText;
    private ImageView appImageView;

    private List<Boolean> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nom);

        // Initialize tags with tags from Nom
        tags = new ArrayList<Boolean>(); //Shallow copy? Not sure need to double check
        //tags = currentNom.getTags();

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
        appImageView.setImageDrawable(getResources().getDrawable(currentNom.getImage() ));

        dishNameText = (TextView) findViewById(R.id.dishNameText);
        dishNameText.setText(currentNom.getName() );

        ingredientsLabel = (TextView) findViewById(R.id.ingredientsLabel);
        ingredientsLabel.setText("Ingredients" );

        ingredientsText = (TextView) findViewById(R.id.ingredientsText);
        ingredientsText.setText(currentNom.getIngredients() );

        directionsText = (TextView) findViewById(R.id.directionsText);
        directionsText.setText(currentNom.getDirections() );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_nom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent mainIntent = new Intent(ViewNom.this,Settings.class);
                ViewNom.this.startActivity(mainIntent);
                return true;
            case R.id.action_logout:
                Intent mainIntents = new Intent(ViewNom.this, SignIn.class);
                ViewNom.this.startActivity(mainIntents);
                ((MyApplication) this.getApplication()).setIsLoggedIn(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    public String getCreator(){
        return currentNom.getCreator();
    }

    public int getUpvotes(){
        return currentNom.getUpvotes();
    }

}
