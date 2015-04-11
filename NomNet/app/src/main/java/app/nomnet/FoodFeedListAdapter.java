package app.nomnet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FoodFeedListAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<NomItem> nomItems;

    public FoodFeedListAdapter(Activity activity, List<NomItem> nomItems){
        this.activity = activity;
        this.nomItems = nomItems;
    }

    @Override
    public int getCount() {
        return nomItems.size();
    }

    @Override
    public Object getItem(int pos) {
        return nomItems.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    // Set the contents for each food feed item
    @Override
    public View getView(int pos, View view, ViewGroup parent){
        if(inflater == null){
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = inflater.inflate(R.layout.food_feed_item, null);
        }

        TextView name = (TextView)view.findViewById(R.id.nom_name);
        ImageView image = (ImageView)view.findViewById(R.id.nom_pic);
        final ImageView imgBtnLike = (ImageView)view.findViewById(R.id.likeBtn);

        NomItem item = nomItems.get(pos);

        name.setText(item.getName());
        image.setImageResource(item.getImage());
        imgBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBtnLike.setImageResource(R.drawable.triangle_red);
            }
        });

        return view;
    }

}
