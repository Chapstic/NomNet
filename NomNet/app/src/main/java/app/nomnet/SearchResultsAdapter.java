package app.nomnet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SearchResultsAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Nom> nomItems;
    private Intent intent;
    private boolean upvoted = false;
    public int numItemsInFeed = 5;      // initial amount

    public SearchResultsAdapter(Activity activity, List<Nom> nomItems, Intent intent){
        this.activity = activity;
        this.nomItems = nomItems;
        this.intent = intent;
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

        final Nom currentNom = nomItems.get(pos);

        TextView name = (TextView)view.findViewById(R.id.nom_name);
        TextView creator = (TextView)view.findViewById(R.id.nom_creator);
        TextView textViewUpvotes = (TextView)view.findViewById(R.id.upvotesText);
        ImageView image = (ImageView)view.findViewById(R.id.nom_pic);
        ImageView comments = (ImageView)view.findViewById(R.id.nom_comment);

        name.setText(currentNom.getName());
        creator.setText(currentNom.getCreator());

        int imageID = currentNom.getImageID();
        image.setImageBitmap(((MyApplication) activity.getApplication()).getImagewithID(imageID).getBitmap() );

        view.findViewById(R.id.nom_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Nom", currentNom); //Passes Nom object in Map<Key,Value> format to next activity (view_nom)
                v.getContext().startActivity(intent);
            }
        });

        view.findViewById(R.id.likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imgBtnLike = (ImageView) v.findViewById(R.id.likeBtn);
                if (!upvoted){
                    imgBtnLike.setImageResource(R.drawable.triangle_red);
                    currentNom.addUpvote(); //increment
                    upvoted = true;
                }else{
                    imgBtnLike.setImageResource(R.drawable.triangle);
                    currentNom.subtractUpvote(); //decrement
                    upvoted = false;
                }
            }
        });
        textViewUpvotes.setText(String.valueOf(currentNom.getUpvotes()));
        return view;
    }

    public int getMaxItems(){
        return nomItems.size();
    }
}
