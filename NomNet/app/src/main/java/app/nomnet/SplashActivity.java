package app.nomnet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    private Activity activity = this;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        // Get the images that will be in the food feed
        int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5};
        int[] imageIDs = {0,1,2,3,4};

        // Optimizes feed images into bitmaps
        // Also employs multithreading
        for(int i = 0; i < images.length; i++){
            OptimizeImageThread oit = new OptimizeImageThread(images[i], imageIDs[i]);
            oit.start();
        }

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,FoodFeedActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    // For each image in the feed, optimize to a bitmap image that scales to screen
    class OptimizeImageThread extends Thread {
        private int image;
        private int imageID;
        public OptimizeImageThread(int image, int imageID){
            this.image = image;
            this.imageID = imageID;
        }

        public void run(){
            BitmapWorkerTask task = new BitmapWorkerTask(activity, imageID);
            task.execute(image);    // convert image to a smaller bitmap
        }
    }
}