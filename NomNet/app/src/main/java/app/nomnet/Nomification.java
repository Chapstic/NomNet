package app.nomnet;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class Nomification extends ActionBarActivity {
    ListView list;

    private static final String TAG= "buckysMessage";

    private ImageButton[] bottombarButtons; //bottombar buttons
    private Toolbar topbar;                 // This is the topbar that says NomNet


    public List<String> Nomi_list;
    public List<Integer> Nomi_user_profile;

//hard coded part
//override with dynamic input
//passed in username array and imagearry

    public Nomification(){

        Nomi_list = new ArrayList<String>();
        Nomi_user_profile = new ArrayList<Integer>();

        addNomi(R.drawable.sydney,"Sydney");
        addNomi(R.drawable.albert, "Albert");
        addNomi(R.drawable.rebecca, "Rebecca");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomification);

        topbar = (Toolbar) findViewById(R.id.topbar);
        topbar.setLogo(R.drawable.logosmall);
        topbar.setTitle("");
        setSupportActionBar(topbar);

        bottombarButtons = new ImageButton[5];
        //initialize bottombar buttons
        bottombarButtons[0] = (ImageButton) findViewById(R.id.BottomBarHome);
        bottombarButtons[1] = (ImageButton) findViewById(R.id.BottomBarSearch);
        bottombarButtons[2] = (ImageButton) findViewById(R.id.BottomBarCamera);
        bottombarButtons[3] = (ImageButton) findViewById(R.id.BottomBarNotification);
        bottombarButtons[4] = (ImageButton) findViewById(R.id.BottomBarProfile);

        //Create click actions from bottom toolbar
        //Third parameter references the current activity: 0 - FoodFeed, 1 - Search, etc

        new BottomButtonActions(bottombarButtons, Nomification.this, 3, "nomification");

        //receive the
//        Intent i = getIntent();
//        String  = i.getExtras().getString("inputText");

        //create Nomification List View
        CustomList adapter = new
                CustomList(Nomification.this, getComments(), getC_userProfile());
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Nomification.this, getComments()[+ position]+ "viewed your post recently ", Toast.LENGTH_SHORT).show();
            }
        });

        Button return_button = (Button)findViewById(R.id.ReturnButton);
        return_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent i = new Intent(Nomification.this, Profile.class);
//                startActivity(i);
                addNomi(R.drawable.elliscope,"Elliscope Fang");
                updateNomi();
            }
        });

        Log.i(TAG,"OnCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // Only show menu if logged in
        if(((MyApplication) this.getApplication()).getIsLoggedIn()){
            getMenuInflater().inflate(R.menu.menu_master, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TopBarActions tba = new TopBarActions(item, this);
        return tba.handledSelection();
    }

    public void addNomi(Integer ImagID,String username){
        Nomi_user_profile.add(ImagID);
        Nomi_list.add(username + "\n Commented on your post");
    }

    public Integer[] getC_userProfile(){
        Integer[] Cprofile_array = new Integer[Nomi_user_profile.size()];
        for(int i = 0; i<Nomi_user_profile.size();i++){
            Cprofile_array[i]=Nomi_user_profile.get(i);
        }
        return Cprofile_array ;
    }

    public String[] getComments(){
        String[] Comments_array = new String[Nomi_list.size()];
        Nomi_list.toArray(Comments_array);
        return Comments_array;
    }

    public void updateNomi(){
        CustomList adapter1 = new
                CustomList(Nomification.this, getComments(), getC_userProfile());
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Nomification.this, getComments()[+ position]+ "viewed your post recently ", Toast.LENGTH_SHORT).show();
            }
        });
        Utility.setListViewHeightBasedOnChildren(list);
    }


}

