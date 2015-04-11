package app.nomnet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewNom extends ActionBarActivity {
    Nom currentNom;
    private Toolbar topbar;
    private TextView creatorText, upvotesText;
    private ImageView appImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nom);
        Intent intent = getIntent();
        currentNom = (Nom)intent.getSerializableExtra("Nom");

        creatorText = (TextView) findViewById(R.id.creatorText);
        creatorText.setText(currentNom.getCreator());

        upvotesText = (TextView) findViewById(R.id.upvotesText);
        upvotesText.setText(Integer.toString(currentNom.getUpvotes()) );

        //appImageView = (ImageView) findViewById(R.id.imageView);
        //appImageView.setImageDrawable(getResources().getDrawable(R.drawable.image01) );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_nom, menu);
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

    public String getCreator(){
        return currentNom.getCreator();
    }

    public int getUpvotes(){
        return currentNom.getUpvotes();
    }
}
