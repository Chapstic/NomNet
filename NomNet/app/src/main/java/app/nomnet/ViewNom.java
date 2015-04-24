package app.nomnet;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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

//implements AbsListView.OnScrollListener
public class ViewNom extends ActionBarActivity {
    private Nom currentNom;
    private Toolbar topbar;
    private TextView creatorText, upvotesText, dishNameText, ingredientsLabel, directionsLabel, ingredientsText, directionsText;
    private ImageView appImageView;


    ListView list_com;
    CustomList adapter;


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
        adapter = new
                CustomList(ViewNom.this, currentNom.getComments(), currentNom.getC_userProfile());
        list_com=(ListView)findViewById(R.id.Comments_listView);
        list_com.setAdapter(adapter);

        list_com.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ViewNom.this, currentNom.getComments()[+position], Toast.LENGTH_SHORT).show();
            }
        });
        Utility.setListViewHeightBasedOnChildren(list_com);



        //Comments Input text edit set up
        EditText editText = (EditText) findViewById(R.id.com_input);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String inputText = v.getText().toString();
                    Intent i = new Intent(ViewNom.this,Nomification.class);
                    i.putExtra("inputText",inputText);

                    //input user image id ->connect with database
                    currentNom.addComments(R.drawable.sydney,inputText);

                    //reinitialize the adapter
                    CustomList adapter1 = new
                            CustomList(ViewNom.this, currentNom.getComments(), currentNom.getC_userProfile());
                    list_com=(ListView)findViewById(R.id.Comments_listView);
                    adapter1.notifyDataSetChanged();
                    list_com.setAdapter(adapter1);

                    Utility.setListViewHeightBasedOnChildren(list_com);

                    handled = true;
                }
                return handled;
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

//
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        }
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        // Can add a padding
//        boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
//
//        // Load more items if there are more to load
//        if(loadMore && adapter.getCount() < adapter.getMaxItems()-1)
//        {
//            adapter.numComments += visibleItemCount; // or any other amount
//
//            if(adapter.getCount() >= adapter.getMaxItems()){
//                adapter.numComments = adapter.getMaxItems(); // keep in bounds
//            }
//
//            adapter.notifyDataSetChanged();
//        }
//    }

//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//
//        int totalHeight = 0;
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        setListViewHeight(listView, totalHeight);
//    }
//    public static void setListViewHeight(ListView listView, int height) {
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = height + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1));
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//    }
}
