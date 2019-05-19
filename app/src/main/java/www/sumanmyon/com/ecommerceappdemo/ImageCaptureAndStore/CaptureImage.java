package www.sumanmyon.com.ecommerceappdemo.ImageCaptureAndStore;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import www.sumanmyon.com.ecommerceappdemo.DataBase.SharePreferenceForUserCredential;
import www.sumanmyon.com.ecommerceappdemo.LoginAndRegister.LoginActivity;

import static android.app.Activity.RESULT_OK;


public class CaptureImage implements View.OnClickListener {

    Activity activity;
    ImageView imageView;
    String whichActivity;

    public static final int CAPTURE_IMAGE_REQUEST = 1;
    File photoFile = null;
    String mCurrentPhotoPath;
    String productImageURl = "";
    private static final String IMAGE_DIRECTORY_NAME = "ECOMMERCE";

    public CaptureImage(Activity activity, ImageView imageView, String whichActivity) {
        this.activity = activity;
        this.imageView = imageView;
        this.whichActivity = whichActivity;
    }

    @Override
    public void onClick(View view) {
       captureImage();
    }

    private void captureImage() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile();
                    showMessage(photoFile.getAbsolutePath());

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(activity,
                                "www.sumanmyon.com.ecommerceappdemo.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        activity.startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    showMessage(ex.getMessage().toString());
                }


            }else
            {
               showMessage("Nullll");
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            int nh = (int) (myBitmap.getHeight() * (512.0 / myBitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);
            imageView.setImageBitmap(scaled);
            productImageURl = photoFile.getAbsolutePath();

            if(whichActivity.equals("mainActivity")){
                //put data in share preference
                new SharePreferenceForUserCredential(activity)
                        .setProfileCredential(photoFile.getAbsolutePath());
                showMessage("Success Image Store");
            }
        }
        else
        {
            showMessage("Request cancelled or something went wrong.");
        }
    }

    public void onPermission(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }

    }

    public void showMessage(String message) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }

    public String getProductImageURl() {
        return productImageURl;
    }
}
