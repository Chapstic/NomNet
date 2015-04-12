package app.nomnet;

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
    private Nom newNom;
    private Recipe newRecipe;

    private ImageView nomPhoto;
    private TextView createLabel;
    private EditText dishEdit;
    private String ingredients; //change to String[] later
    private String dishname, directions;
    private Button postButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_nom);

        // Sets the top toolbar to be the one we specifically created
        topbar = (Toolbar) findViewById(R.id.topbar);
        setSupportActionBar(topbar);

        createLabel = (TextView) findViewById(R.id.createLabel);
        createLabel.setText("Create a Nom!");

        //Initialize and access the Image based on camera
        int nomImage = R.drawable.food1; //***********HARDCODED image should eventually be swapped out w/ Camera photo******************
        nomPhoto = (ImageView) findViewById(R.id.foodImage);
        nomPhoto.setImageDrawable(getResources().getDrawable(nomImage) );

        //Access the EditText field in create_nom.xml
        dishEdit = (EditText) findViewById(R.id.dishEdit);

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


        //newRecipe == new Recipe(dishname, ingredients, directions);
        //newNom = new Nom("Placeholder Username", 0, nomImage, Recipe recipe);
    }
}
