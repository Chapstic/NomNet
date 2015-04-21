package app.nomnet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class FoodFeedListAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Nom> nomItems;
    private Intent intent;
    private boolean upvoted = false;
    public int numItemsInFeed = 3;      // initial amount

    public FoodFeedListAdapter(Activity activity, List<Nom> nomItems, Intent intent){
        this.activity = activity;
        this.nomItems = nomItems;
        this.intent = intent;
    }

    public int getMaxItems(){
        return nomItems.size();
    }

    @Override
    public int getCount() {
        return numItemsInFeed;
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

        // Grab the current nom from the list
        final Nom currentNom = nomItems.get(pos);

        // Find the UI elements that will be used to display each item
        TextView name = (TextView)view.findViewById(R.id.nom_name);
        TextView creator = (TextView)view.findViewById(R.id.nom_creator);
        TextView textViewUpvotes = (TextView)view.findViewById(R.id.upvotesText);
        ImageView image = (ImageView)view.findViewById(R.id.nom_pic);
        ImageView comments = (ImageView)view.findViewById(R.id.nom_comment);

        // Set the contents of the UI elements
        name.setText(currentNom.getName());
        creator.setText(currentNom.getCreator());
        textViewUpvotes.setText(String.valueOf(currentNom.getUpvotes()));

        // Optimizes feed images into bitmaps
        // Also employs multithreading
        image.setImageResource(R.drawable.logo);    // just use an small image placeholder
        OptimizeImageThread oit = new OptimizeImageThread(currentNom.getImage(), image);
        oit.start();

        // When an item picture is hit, move to appropriate activity
        view.findViewById(R.id.nom_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Nom", currentNom); //Passes Nom object in Map<Key,Value> format to next activity (view_nom)
                v.getContext().startActivity(intent);
            }
        });

        // When the like button is pressed, change the upvote image
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

        return view;
    }

    // For each image in the feed, optimize to a bitmap image that scales to screen
    class OptimizeImageThread extends Thread {
        private ImageView imageView;
        private int imageID;
        public OptimizeImageThread(int imageID, ImageView imageView){
            this.imageView = imageView;
            this.imageID = imageID;
        }

        public void run(){
            BitmapWorkerTask task = new BitmapWorkerTask(imageView, activity);
            task.execute(imageID);    // convert image to a smaller bitmap
        }
    }
}
