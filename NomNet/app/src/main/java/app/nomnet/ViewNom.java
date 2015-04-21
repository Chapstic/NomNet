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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;


public class ViewNom extends ActionBarActivity implements AbsListView.OnScrollListener{
    private Nom currentNom;
    private Toolbar topbar;
    private TextView creatorText, upvotesText, dishNameText, ingredientsLabel, directionsLabel, ingredientsText, directionsText;
    private ImageView appImageView;





    ListView list_com;
    CustomList adapter;

    String[] web1= {
            "Sydney Liu \n commented on your post",
            "Rebecca Wu \n commented on your post",
            "Izzy Benavente \n commented on your post",
            "Albert Yue \n commented on your post",
            "Elliscope Fang \n commented on your post",
    };


    Integer[] imageId1= {

            R.drawable.sydney,
            R.drawable.rebecca,
            R.drawable.isabella,
            R.drawable.albert,
            R.drawable.elliscope,
    };


    //Nomification Part
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;


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
        appImageView.setImageDrawable(getResources().getDrawable(currentNom.getImage() ));

        dishNameText = (TextView) findViewById(R.id.dishNameText);
        dishNameText.setText(currentNom.getName() );

        ingredientsLabel = (TextView) findViewById(R.id.ingredientsLabel);
        ingredientsLabel.setText("Ingredients" );

        ingredientsText = (TextView) findViewById(R.id.ingredientsText);
        ingredientsText.setText(currentNom.getIngredients() );

        directionsLabel = (TextView) findViewById(R.id.directionsLabel);
        directionsLabel.setText("Directions" );

        directionsText = (TextView) findViewById(R.id.directionsText);
        directionsText.setText(currentNom.getDirections() );


        //Dynamic Comments under Nom
//        adapter = new
//                CustomList(ViewNom.this, web1, imageId1);
//        list_com=(ListView)findViewById(R.id.Comments_listView);
//        list_com.setAdapter(adapter);
//        list_com.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(ViewNom.this, web1[+position] + "viewed your post recently ", Toast.LENGTH_SHORT).show();
//            }
////        });
//        list_com.setOnScrollListener(this);


        Button return_button = (Button)findViewById(R.id.ReturnButton);
        return_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ViewNom.this, Profile.class);
                startActivity(i);
            }
        });

        //Pop Up Nomification Implementaion
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);




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

    //Nomification Event Handler


    //wait for user info from database to be past in->change parameters and make it dynamic
    public void notiButtonOnClicked( View view) {
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // Can add a padding
        boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

        // Load more items if there are more to load
        if(loadMore && adapter.getCount() < adapter.getMaxItems()-1)
        {
            adapter.numComments += visibleItemCount; // or any other amount

            if(adapter.getCount() >= adapter.getMaxItems()){
                adapter.numComments = adapter.getMaxItems(); // keep in bounds
            }

            adapter.notifyDataSetChanged();
        }
    }
}
