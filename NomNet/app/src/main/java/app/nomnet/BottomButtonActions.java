package app.nomnet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Rebecca on 4/19/2015.
 */
public class BottomButtonActions {

    private ImageButton[] buttons;
    private Context context; //context from calling activity
    private int currentButton;
    private String callingActivity; //name of calling activity

    public BottomButtonActions(ImageButton[] b, Context c, int i, String ca){
        buttons = b;
        context = c;
        currentButton = i;
        callingActivity = ca;
        setListeners();
    }


    private void setListeners(){

        for(int i=0; i<buttons.length; i++){
            if(i==currentButton){//set button to already selected if currently on respective page
                buttons[i].setSelected(true);
                buttons[i].setEnabled(false);
            }
            else{//otherwise, it is not selected and clickable
                buttons[i].setSelected(false);
                buttons[i].setClickable(true);
                final int j = i;
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch(j){
                            case 0: v.getContext().startActivity(new Intent(context, FoodFeedActivity.class));
                                    ((Activity) context).overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                                    break;
                            case 1: v.getContext().startActivity(new Intent(context, SearchActivity.class));
                                    if(callingActivity.equals("foodfeed")){
                                        ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                                    }
                                    else ((Activity) context).overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                                    break;
                            case 2: v.getContext().startActivity(new Intent(context, Camera.class));
                                    if(callingActivity.equals("foodfeed")||callingActivity.equals("search")){
                                        ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                                    }
                                    else ((Activity) context).overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                                    break;
                            case 3: v.getContext().startActivity(new Intent(context, Nomification.class));
                                    if(callingActivity.equals("profile")){
                                        ((Activity) context).overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                                    }
                                    else ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                                    break;
                            case 4: v.getContext().startActivity(new Intent(context, Profile.class));
                                    ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                                    break;
                            default: break;
                        }
                    }
                });
            }
        }
    }

}
