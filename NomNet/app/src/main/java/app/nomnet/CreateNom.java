package app.nomnet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


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

    List<Boolean> newTags;
    Boolean veganChecked, vegetarianChecked, bfastChecked, lunchChecked, dinnerChecked;


    //Possibly add additional method to parse text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = new Intent(this, ViewNom.class); //Users view the Nom immediately after posting. Check line 40 activity_view_nom
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_nom);

        // Initialize tags list and booleans
        newTags = new ArrayList<Boolean>();
        veganChecked = vegetarianChecked = bfastChecked = lunchChecked = dinnerChecked = false;

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

        finalizeTags();

        newRecipe = new Recipe(dishname, ingredients, directions);
        newNom = new Nom("Placeholder Username", 0, nomImage, newRecipe, newTags);

        intent.putExtra("Nom", newNom); //Passes Nom object in Map<Key,Value> format to next activity (view_nom)
        v.getContext().startActivity(intent); //Creates Nom and immediately goes to ViewNom

    }

    public void finalizeTags(){
        newTags.add(veganChecked);
        newTags.add(vegetarianChecked);
        newTags.add(bfastChecked);
        newTags.add(lunchChecked);
        newTags.add(dinnerChecked);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked and update booleans accordingly
        switch(view.getId()) {
            case R.id.checkbox_vegan:
                if (checked){
                    veganChecked = true;
                } else{
                    veganChecked = false;
                }
                break;

            case R.id.checkbox_vegetarian:
                if (checked){
                    vegetarianChecked = true;
                } else{
                    vegetarianChecked = false;
                }
                break;

            case R.id.checkbox_bfast:
                if (checked){
                    bfastChecked = true;
                } else{
                    bfastChecked = false;
                }
                break;

            case R.id.checkbox_lunch:
                if (checked){
                    lunchChecked = true;
                } else{
                    lunchChecked = false;
                }
                break;

            case R.id.checkbox_dinner:
                if (checked){
                    dinnerChecked = true;
                } else{
                    dinnerChecked = false;
                }
                break;
        }
    }

}
