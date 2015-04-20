package app.nomnet;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class CreateNom extends ActionBarActivity implements View.OnClickListener {

    private Toolbar topbar;
    private int nomImage;
    private Nom newNom;
    private Recipe newRecipe;

    private ImageView nomPhoto;
    private TextView createLabel;
    private EditText dishEdit, ingredientsEdit, directionsEdit;
    private String ingredients; //change to String[] later
    private String dishname, directions;
    private Button postButton;

    private Intent intent;

    //Possibly add additional method to parse text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = new Intent(this, ViewNom.class); //Users view the Nom immediately after posting. Check line 40 activity_view_nom
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_nom);

        // Sets the top toolbar to be the one we specifically created
        topbar = (Toolbar) findViewById(R.id.topbar);
        setSupportActionBar(topbar);

        createLabel = (TextView) findViewById(R.id.createLabel);
        createLabel.setText("Create a Nom!");

        //Initialize and access the Image based on camera
        nomImage = R.drawable.food1; //***********HARDCODED image should eventually be swapped out w/ Camera photo******************
        nomPhoto = (ImageView) findViewById(R.id.foodImage);
        nomPhoto.setImageDrawable(getResources().getDrawable(nomImage) );

        //Access the dishEdit field in create_nom.xml
        dishEdit = (EditText) findViewById(R.id.dishEdit);
        dishEdit.setTextColor(Color.parseColor("#D32F2F")); //Sets input text color to red

        //Access the dishEdit field in create_nom.xml
        ingredientsEdit = (EditText) findViewById(R.id.ingredientsEdit);
        ingredientsEdit.setTextColor(Color.parseColor("#D32F2F")); //Sets input text color to red

        //Access the ingredientsEdit field in create_nom.xml
        directionsEdit = (EditText) findViewById(R.id.directionsEdit);
        directionsEdit.setTextColor(Color.parseColor("#D32F2F")); //Sets input text color to red

        //Access the PostButton in create_nom.xml
        postButton = (Button) findViewById(R.id.postButton);
        postButton.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_nom, menu);
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

    @Override
    public void onClick(View v) {
        // Also add that value to the list shown in the ListView
        dishname = String.valueOf(dishEdit.getText() );
        ingredients = String.valueOf(ingredientsEdit.getText() );
        directions = String.valueOf(directionsEdit.getText() );

        newRecipe = new Recipe(dishname, ingredients, directions);
        newNom = new Nom("Placeholder Username", 0, nomImage, newRecipe);

        intent.putExtra("Nom", newNom); //Passes Nom object in Map<Key,Value> format to next activity (view_nom)
        v.getContext().startActivity(intent); //Creates Nom and immediately goes to ViewNom

    }

}
