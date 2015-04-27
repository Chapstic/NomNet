package app.nomnet;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.graphics.Matrix;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Camera extends ActionBarActivity {

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "NomNet Camera";

    private Uri fileUri; // file url to store image/video

    private Toolbar topbar;

    private ImageButton[] bottombarButtons; //bottombar buttons


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        topbar = (Toolbar) findViewById(R.id.topbar);
        topbar.setLogo(R.drawable.logosmall);
        topbar.setTitle("");
        setSupportActionBar(topbar);

        //initialize bottombar buttons
        bottombarButtons = new ImageButton[5];
        bottombarButtons[0] = (ImageButton) findViewById(R.id.BottomBarHome);
        bottombarButtons[1] = (ImageButton) findViewById(R.id.BottomBarSearch);
        bottombarButtons[2] = (ImageButton) findViewById(R.id.BottomBarCamera);
        bottombarButtons[3] = (ImageButton) findViewById(R.id.BottomBarNotification);
        bottombarButtons[4] = (ImageButton) findViewById(R.id.BottomBarProfile);

        //Create click actions from bottom toolbar
        //Third parameter references the current activity: 0 - FoodFeed, 1 - Search, etc
        new BottomButtonActions(bottombarButtons, Camera.this, 2, "camera");

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // close the app if the device does't have camera
            finish();
        }

        captureImage();
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // has a camera
            return true;
        } else {
            // no camera
            return false;
        }
    }
    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // image capture successful and acceptable
                goToCreate();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture
                Toast.makeText(getApplicationContext(),
                        "Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    // Image captured successfully, move on to CreateNom
    private void goToCreate() {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

            ExifInterface ei = null;
            try {
                ei = new ExifInterface(fileUri.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch(orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    Intent imagepass = new Intent(Camera.this,CreateNom.class);
                    imagepass.putExtra("imagepass", bitmap );
                    startActivity(imagepass);

                case ExifInterface.ORIENTATION_ROTATE_90:
                    Bitmap adjustedBitmap = rotateImage(bitmap, 90);
                    imagepass = new Intent(Camera.this,CreateNom.class);
                    imagepass.putExtra("imagepass", adjustedBitmap );

                    startActivity(imagepass);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    adjustedBitmap = rotateImage(bitmap, 180);
                    imagepass = new Intent(Camera.this,CreateNom.class);
                    imagepass.putExtra("imagepass", adjustedBitmap );

                    startActivity(imagepass);
                    break;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    Bitmap rotateImage(Bitmap bitmap, int degrees){
        Matrix matrix = new Matrix();

        matrix.postRotate(degrees);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);

        return rotatedBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}