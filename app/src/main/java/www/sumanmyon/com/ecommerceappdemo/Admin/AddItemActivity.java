package www.sumanmyon.com.ecommerceappdemo.Admin;

import android.content.Intent;
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

import www.sumanmyon.com.ecommerceappdemo.DataBase.ProductDataBase;
import www.sumanmyon.com.ecommerceappdemo.ImageCaptureAndStore.CaptureImage;
import www.sumanmyon.com.ecommerceappdemo.R;

import static www.sumanmyon.com.ecommerceappdemo.ImageCaptureAndStore.CaptureImage.CAPTURE_IMAGE_REQUEST;

public class AddItemActivity extends AppCompatActivity {

    EditText productEditText, stockEditText, priceEditText, phoneEditText, descriptionEditText;
    public ImageView imageView, locationImageView;
    TextView locationTextView;
    Button addButton;

    String uid;
    ProductDataBase productDataBase;
    CaptureImage captureImage;

    int PLACE_PICKER_REQUEST = 2;
    String locationPick = "";

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //casting
        casting();

        //get user id
        Bundle bundle = getIntent().getExtras();
        if(bundle.equals("")){
            //return;
        }
        uid = bundle.getString("uid");

        captureImage = new CaptureImage(this,imageView, "mainActivity");
        imageView.setOnClickListener(captureImage);
        locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try{
                    startActivityForResult(builder.build(AddItemActivity.this),PLACE_PICKER_REQUEST);
                }catch (Exception e){
                    e.getMessage();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isTrue = checkingEditTextFieldsAreNotNull();
                if(isTrue){
                    productDataBase = new ProductDataBase(AddItemActivity.this);
                    boolean isInserted = productDataBase.insert(
                            productEditText.getText().toString(),
                            stockEditText.getText().toString(),
                            priceEditText.getText().toString(),
                            phoneEditText.getText().toString(),
                            captureImage.getProductImageURl(),
                            descriptionEditText.getText().toString(),
                            locationPick,
                            uid);
                    if(isInserted) {
                        showMessage("Data Inserted Successfully");
                        //redirect to page
                        redirect();
                    }else {
                        showMessage("Data Inserted failed");
                    }
                }
            }
        });

    }

    private void casting() {
        productEditText = findViewById(R.id.add_item_product_name);
        stockEditText = findViewById(R.id.add_item_no_of_stock);
        priceEditText = findViewById(R.id.add_item_price);
        phoneEditText = findViewById(R.id.add_item_phone_number);
        descriptionEditText = findViewById(R.id.add_item_description);
        imageView = findViewById(R.id.add_item_imageView);
        locationImageView = findViewById(R.id.add_location_imageView);
        locationTextView = findViewById(R.id.add_item_location_textView);
        addButton = findViewById(R.id.add_item_add_button);
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
        }else if(locationPick==""){
            showMessage("Please select your location");
            return false;
        } else {
            return true;
        }
    }

    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void redirect() {
        finish();
        Intent i = new Intent(this, DashboardActivity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(CAPTURE_IMAGE_REQUEST == requestCode)
            captureImage.onResult(requestCode, resultCode,data);
        else if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,AddItemActivity.this);
                String lat = String.valueOf(place.getLatLng().latitude);
                String lng = String.valueOf(place.getLatLng().longitude);
                locationTextView.setText("location: "+ lat +", "+lng);
                locationPick = lat+"::"+lng;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
      captureImage.onPermission(requestCode,permissions,grantResults);
    }
}
