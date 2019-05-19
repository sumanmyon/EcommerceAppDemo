package www.sumanmyon.com.ecommerceappdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import www.sumanmyon.com.ecommerceappdemo.Admin.AddItemActivity;
import www.sumanmyon.com.ecommerceappdemo.Admin.DashboardActivity;
import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.DataBase.LoginAndRegisterDBHelper;
import www.sumanmyon.com.ecommerceappdemo.DataBase.SharePrefForDataBaseUpdate;
import www.sumanmyon.com.ecommerceappdemo.DataBase.SharePreferenceForUserCredential;
import www.sumanmyon.com.ecommerceappdemo.ImageCaptureAndStore.CaptureImage;
import www.sumanmyon.com.ecommerceappdemo.LoginAndRegister.LoginActivity;
import www.sumanmyon.com.ecommerceappdemo.NavigationDrawer.Navigation;
import www.sumanmyon.com.ecommerceappdemo.Search.SearchByProductName;
import www.sumanmyon.com.ecommerceappdemo.UpdateDataBaseWhenAppStarts.InsertDataBase;
import www.sumanmyon.com.ecommerceappdemo.UpdateDataBaseWhenAppStarts.UpdateDataBase;
import www.sumanmyon.com.ecommerceappdemo.UserPaurse.PaurseProduct;
import www.sumanmyon.com.ecommerceappdemo.UserPaurse.User;

import static www.sumanmyon.com.ecommerceappdemo.ImageCaptureAndStore.CaptureImage.CAPTURE_IMAGE_REQUEST;

public class MainActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nv;
    Toolbar toolbar;
    public TextView shoppingCardTextView;

    public ImageView userProfileImageView;
    public TextView userNameTextView;
    CaptureImage captureImage;


    LoginAndRegisterDBHelper helper;
    String username;
    String uid;

    public static User user;

    public SearchView searchView;
    public ListView listView;

    @Override
    protected void onRestart() {
        super.onRestart();
        //updateDataBaseWhenAppStarts();
        user.getAllDataFromDataBase();
        setShoppingCardTextViewItems("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new LoginAndRegisterDBHelper(this);

        //
        updateDataBaseWhenAppStarts();

        //casting
        casting();

        //get user name
        checklogin("main");



        user = new User(this);
        user.getAllDataFromDataBase();

        searchView.setOnQueryTextListener(new SearchByProductName(this,user.getCacheDataBaseArrayList()));

        shoppingCardTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PaurseProduct.class);
                startActivity(i);
            }
        });

        captureImage = new CaptureImage(MainActivity.this,userProfileImageView,"mainActivity");
        userProfileImageView.setOnClickListener(captureImage);
    }


    private void casting(){
        //navigation and drawer
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        nv = (NavigationView)findViewById(R.id.navigation);

        //toolbar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);

        shoppingCardTextView = findViewById(R.id.main_card_shopping_basket);

        drawerLayout();
        navigationListener();

        userProfileImageView = nv.getHeaderView(0).findViewById(R.id.nav_user_image);
        userNameTextView = nv.getHeaderView(0).findViewById(R.id.nav_user_name);
    }

    private void drawerLayout(){
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void navigationListener(){
        nv.setNavigationItemSelectedListener(new Navigation(this,nv));
    }

    private void updateDataBaseWhenAppStarts() {
        //checking if database contain updated data sets
        String isUpdate = new SharePrefForDataBaseUpdate(MainActivity.this).getCredential();
        UpdateDataBase updateDataBase = new UpdateDataBase(this);

        if (isUpdate.equals("true")) {
            //update Database
            try {
                updateDataBase.update();
                updateDataBase.setUpdateToFalse();
            }catch (Exception e){
                updateDataBase.setUpdateToFalse();
            }
        }

    }

    public void checklogin(String str) {
        //checking login credientials
        String credential = new SharePreferenceForUserCredential(MainActivity.this)
                .getCredential();

        if(credential.equals("::::")){
            if(str.equals("nav")) {
                login();
            }
        }else {
            String[] data = credential.split("::");
            boolean isHaved = helper.check(data[0],data[1]);
            if(isHaved == true){
                username = data[0];
                uid = data[2];
                if(str.equals("main")){
                    nv.getMenu().setGroupVisible(R.id.group_after_login, true);
                    //set username in naviagtion
                    userNameTextView.setText(username);

                    //get user profile
                    String userProfile = new SharePreferenceForUserCredential(MainActivity.this)
                            .getUserProfile();
                    try {
                        Bitmap myBitmap = BitmapFactory.decodeFile(userProfile);
                        int nh = (int) (myBitmap.getHeight() * (512.0 / myBitmap.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);
                        userProfileImageView.setImageBitmap(scaled);
                    }catch (Exception e){

                    }

                }else if(str.equals("nav")){
                    addItem();
                }
            }else {
                login();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(CAPTURE_IMAGE_REQUEST == requestCode)
            captureImage.onResult(requestCode, resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        captureImage.onPermission(requestCode,permissions,grantResults);
    }

    public void setShoppingCardTextViewItems(String noOfItems){
        shoppingCardTextView.setText(noOfItems);
    }

    private void login(){
        finish();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void addItem(){
        Intent i = new Intent(MainActivity.this, DashboardActivity.class);
        i.putExtra("uid",uid);
        i.putExtra("activity","main");
        startActivity(i);
    }

    public void showMessage(String message) {
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }

    public void refresh(String str, ArrayList<CacheDataBase> cacheDataBaseArrayListSearch) {
        if(str.equals("search")){
            if(cacheDataBaseArrayListSearch == null){
                user.getAllDataFromDataBase();
            }else {
                user.getAllDataFromDataBaseSearch(cacheDataBaseArrayListSearch);
            }
        }
    }
}
