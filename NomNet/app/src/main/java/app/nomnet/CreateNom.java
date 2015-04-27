package app.nomnet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CreateNom extends ActionBarActivity implements View.OnClickListener {

    private Toolbar topbar;
    private Bitmap bitmap;
    private Nom newNom;
    private Recipe newRecipe;

    private ImageView nomPhoto;
    private EditText dishEdit, ingredientsEdit, directionsEdit;
    private String ingredients; //change to String[] later
    private String dishname, directions;
    private int imageID;
    private Image image;
    private Button postButton;

    private Intent intent;

    private Map<String, Boolean> newTags;
    private Set<String> refinedTags;


    //Possibly add additional method to parse text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = new Intent(this, ViewNom.class); //Users view the Nom immediately after posting. Check line 40 activity_view_nom
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_nom);

        // Initialize tags list and booleans
        newTags = new HashMap<String, Boolean>();
        refinedTags = new HashSet<String>();

        // Sets the top toolbar to be the one we specifically created
        topbar = (Toolbar) findViewById(R.id.topbar);
        setSupportActionBar(topbar);

        bitmap = getIntent().getExtras().getParcelable("imagepass");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize and access the Image based on data from camera
        nomPhoto = (ImageView) findViewById(R.id.foodImage);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, true);
        nomPhoto.setImageBitmap(scaledBitmap);

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
        getMenuInflater().inflate(R.menu.menu_master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TopBarActions tba = new TopBarActions(item, this);
        return tba.handledSelection();
    }


    @Override
    public void onClick(View v) {
        // Also add that value to the list shown in the ListView
        String username = ((MyApplication) this.getApplication()).getCurrentUser();
        dishname = String.valueOf(dishEdit.getText() );
        ingredients = String.valueOf(ingredientsEdit.getText() );
        directions = String.valueOf(directionsEdit.getText() );
        parseTags();
        createImage();

        newRecipe = new Recipe(dishname, ingredients, directions);
        newNom = new Nom(username, 0, imageID, newRecipe, refinedTags);

        intent.putExtra("Nom", newNom); //Passes Nom object in Map<Key,Value> format to next activity (view_nom)
        v.getContext().startActivity(intent); //Creates Nom and immediately goes to ViewNom

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked and update booleans accordingly
        switch(view.getId()) {
            case R.id.checkbox_vegan:
                if (checked){
                    newTags.put("Vegan", true);
                } else{
                    newTags.put("Vegan", false);
                }
                break;

            case R.id.checkbox_vegetarian:
                if (checked){
                    newTags.put("Vegetarian", true);
                } else{
                    newTags.put("Vegetarian", false);
                }
                break;

            case R.id.checkbox_bfast:
                if (checked){
                    newTags.put("Breakfast", true);
                } else{
                    newTags.put("Breakfast", false);
                }
                break;

            case R.id.checkbox_lunch:
                if (checked){
                    newTags.put("Lunch", true);
                } else{
                    newTags.put("Lunch", false);
                }
                break;

            case R.id.checkbox_dinner:
                if (checked){
                    newTags.put("Dinner", true);
                } else{
                    newTags.put("Dinner", false);
                }
                break;
        }
    }

    public void parseTags(){
        for(Map.Entry<String, Boolean> entry : newTags.entrySet() ) { // Iterate through map looking for keys that are true
            String key = entry.getKey();
            Boolean value = entry.getValue();

            if(value == true){
                refinedTags.add(key);
            }
        }
    }

    public void createImage(){
        imageID = ((MyApplication) this.getApplication()).getImgListSize();
        image = new Image(imageID, dishname);
        //Bitmap scaledBitmap = scaleDown(bitmap, 800, true);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, true);
        image.setBitmap(scaledBitmap);
        ((MyApplication) this.getApplication()).addImage(image);
    }
}
