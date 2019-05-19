package www.sumanmyon.com.ecommerceappdemo.Admin;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import www.sumanmyon.com.ecommerceappdemo.Adapter.DashBoardRecyclerViewAdapter;
import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.DataBase.ProductDataBase;
import www.sumanmyon.com.ecommerceappdemo.MainActivity;
import www.sumanmyon.com.ecommerceappdemo.R;

public class DashboardActivity extends AppCompatActivity {
    ProductDataBase productDataBase;
    ArrayList<CacheDataBase> cacheDataBaseArrayList;
    CacheDataBase cacheDataBase;
    DashBoardRecyclerViewAdapter recyclerViewAdapter;

    FloatingActionButton fab;

    String uid, redirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //get user id
        Bundle bundle = getIntent().getExtras();
        if(bundle.equals("")){
            //return;
        }
        uid = bundle.getString("uid");
        redirect = bundle.getString("activity");
        //casting
        casting();

        productDataBase = new ProductDataBase(this);
        getAllDataFromDataBase();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reDirectToAddItemActivity();
            }
        });
    }

    private void casting() {
        fab = findViewById(R.id.fab);
    }

    private void getAllDataFromDataBase() {
        Cursor cursor = productDataBase.getAllData(uid);
        if(cursor.getCount() == 0){  //TODO there is no data in database
            //showMessage("There is no data in database");
            showMessage("There is no data in database");
            return;
        }

        //TODO if cursor is at first of row or not
        if(!cursor.isFirst()){
            cursor.moveToFirst();
        }

        cacheDataBaseArrayList = new ArrayList<>();
        do{
            String id = cursor.getString(0);
            String productName = cursor.getString(1);
            String stock = cursor.getString(2);
            String price = cursor.getString(3);
            String phone = cursor.getString(4);
            String image = cursor.getString(5);
            String description = cursor.getString(6);
            String location = cursor.getString(7);
            String uid = cursor.getString(8);
            cacheDataBase = new CacheDataBase(id,productName,stock,price,phone,image,description,location,uid);

            cacheDataBaseArrayList.add(cacheDataBase);
        }while (cursor.moveToNext());

        //show in recyclerView
        recyclerViewAdapter = new DashBoardRecyclerViewAdapter(this,cacheDataBaseArrayList);

        RecyclerView recyclerView = recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void reDirectToAddItemActivity(){
        finish();
        Intent i = new Intent(this, AddItemActivity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(redirect.equals("login")){
            finish();
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("uid",uid);
            startActivity(i);
        }
    }
}
