package www.sumanmyon.com.ecommerceappdemo.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;

import java.io.File;

import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.DataBase.ProductDataBase;
import www.sumanmyon.com.ecommerceappdemo.R;

import static www.sumanmyon.com.ecommerceappdemo.ImageCaptureAndStore.CaptureImage.CAPTURE_IMAGE_REQUEST;

public class EditActivity extends AppCompatActivity {
    private String id;
    private String uid;
    private String productName;
    private String stock;
    private String price;
    private String phoneNo;
    private String image;
    private String location;
    private String description;

    EditText productEditText, stockEditText, priceEditText, phoneEditText, descriptionEditText;
    ImageView imageView, locationImageView;
    TextView locationTextView;
    Button updateButton;

    ProductDataBase productDataBase;

    int PLACE_PICKER_REQUEST = 2;
    String locationPick = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //casting
        casting();

        Bundle bundle = getIntent().getExtras();
        getValues(bundle);
        setValuesInFields();

        locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try{
                    startActivityForResult(builder.build(EditActivity.this),PLACE_PICKER_REQUEST);
                }catch (Exception e){
                    e.getMessage();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isTrue = checkingEditTextFieldsAreNotNull();
                if(isTrue) {
                    productDataBase = new ProductDataBase(EditActivity.this);
                    boolean isUpdated = productDataBase.update(id,productEditText.getText().toString(),
                            stockEditText.getText().toString(), priceEditText.getText().toString(),
                            phoneEditText.getText().toString(), image,
                            descriptionEditText.getText().toString(),
                            locationPick, uid);
                    if (isUpdated) {
                        showMessage("Data Updated Successfully");
                        //redirect to page
                        redirect();
                    } else {
                        showMessage("Data Updating failed");
                    }
                }
            }
        });
    }

    private void setValuesInFields() {
        productEditText.setText(productName);
        stockEditText.setText(stock);
        priceEditText.setText(price);
        phoneEditText.setText(phoneNo);
        descriptionEditText.setText(description);
        locationTextView.setText(location);

        try{
    //        Uri uri = Uri.fromFile(new File(image));
    //        Picasso.get().load(uri).into(imageView);
            Bitmap myBitmap = BitmapFactory.decodeFile(image);
            int nh = (int) ( myBitmap.getHeight() * (512.0 / myBitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);
            imageView.setImageBitmap(scaled);
        }catch (Exception e){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,EditActivity.this);
                String lat = String.valueOf(place.getLatLng().latitude);
                String lng = String.valueOf(place.getLatLng().longitude);
                locationTextView.setText("location: "+ lat +", "+lng);
                locationPick = lat+"::"+lng;
            }
        }
    }

    private void getValues(Bundle bundle) {
        if(bundle != null){
            id = bundle.getString("id");
            uid = bundle.getString("uid");
            productName = bundle.getString("productName");
            stock = bundle.getString("stock");
            price = bundle.getString("price");
            phoneNo = bundle.getString("phoneNo");
            image = bundle.getString("image");
            location = bundle.getString("location");
            description = bundle.getString("description");
        }
    }

    private boolean checkingEditTextFieldsAreNotNull() {
        if (TextUtils.isEmpty(productEditText.getText())) {
            productEditText.setError("Please enter username");
            return false;
        } else if (TextUtils.isEmpty(stockEditText.getText())) {
            stockEditText.setError("Please enter password");
            return false;
        } else if (TextUtils.isEmpty(priceEditText.getText())) {
            priceEditText.setError("Please enter password");
            return false;
        }else if (TextUtils.isEmpty(phoneEditText.getText())) {
            phoneEditText.setError("Please enter password");
            return false;
        }else if (TextUtils.isEmpty(descriptionEditText.getText())) {
            descriptionEditText.setError("Please enter password");
            return false;
        }else {
            return true;
        }
    }

    private void casting(){
        productEditText = findViewById(R.id.edit_item_product_name);
        stockEditText = findViewById(R.id.edit_item_no_of_stock);
        priceEditText = findViewById(R.id.edit_item_price);
        phoneEditText = findViewById(R.id.edit_item_phone_number);
        descriptionEditText = findViewById(R.id.edit_item_description);
        imageView = findViewById(R.id.edit_item_image);
        locationImageView = findViewById(R.id.edit_item_imageView);
        locationTextView = findViewById(R.id.edit_item_location_textView);
        updateButton = findViewById(R.id.edit_item_add_button);
    }

    private void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void redirect() {
        finish();
        Intent i = new Intent(this, DashboardActivity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}
