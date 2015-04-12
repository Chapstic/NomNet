package app.nomnet;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import app.nomnet.R;

public class Nomification extends Activity {
    ListView list;



//hard coded part
//override with dynamic input
//passed in username array and imagearry

    String[] web= {
            "Sydney Liu commented on your post",
            "Rebecca Wu commented on your post",
            "Izzy Benavente commented on your post",
            "Albert Yue commented on your post",
            "Elliscope Fang commented on your post",
    };


    Integer[] imageId= {
            R.drawable.albert,
            R.drawable.albert,
            R.drawable.albert,
            R.drawable.albert,
            R.drawable.albert,
            R.drawable.albert,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomification);
        CustomList adapter = new
                CustomList(Nomification.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Nomification.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}

