package app.nomnet;

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
    private Context context;
    private int currentButton;

    public BottomButtonActions(ImageButton[] b, Context c, int i){
        buttons = b;
        context = c;
        currentButton = i;
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
                                    break;
                            case 1: v.getContext().startActivity(new Intent(context, SearchActivity.class));
                                    break;
                            case 2: v.getContext().startActivity(new Intent(context, Camera.class));
                                    break;
                            case 3: v.getContext().startActivity(new Intent(context, Nomification.class));
                                    break;
                            case 4: v.getContext().startActivity(new Intent(context, Profile.class));
                                    break;
                            default: break;
                        }
                    }
                });
            }
        }
    }

}
