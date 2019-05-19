package www.sumanmyon.com.ecommerceappdemo.UserPaurse;

import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import www.sumanmyon.com.ecommerceappdemo.Adapter.DashBoardRecyclerViewAdapter;
import www.sumanmyon.com.ecommerceappdemo.Adapter.UserPaurseRecyclerViewAdapter;
import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.DataBase.ProductDataBase;
import www.sumanmyon.com.ecommerceappdemo.MainActivity;
import www.sumanmyon.com.ecommerceappdemo.R;

public class User {

    MainActivity activity;

    ProductDataBase productDataBase;
    ArrayList<CacheDataBase> cacheDataBaseArrayList;
    CacheDataBase cacheDataBase;
    UserPaurseRecyclerViewAdapter recyclerViewAdapter;

    public User(MainActivity mainActivity) {
        this.activity = mainActivity;
        productDataBase = new ProductDataBase(activity);
    }

    public void getAllDataFromDataBase(){
        Cursor cursor = productDataBase.getAllData();
        if(cursor.getCount() == 0){  //TODO there is no data in database
            //showMessage("There is no data in database");
            activity.showMessage("There is no data in database");
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

        showDataInRecyclerView();
    }

     public void getAllDataFromDataBaseSearch(ArrayList<CacheDataBase> cacheDataBaseArrayListSearch) {
        this.cacheDataBaseArrayList = cacheDataBaseArrayListSearch;
        showDataInRecyclerView();
    }


    private void showDataInRecyclerView() {
        //show in recyclerView
        recyclerViewAdapter = new UserPaurseRecyclerViewAdapter(activity,cacheDataBaseArrayList);

        RecyclerView recyclerView = recyclerView = (RecyclerView)activity.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public ArrayList<CacheDataBase> getCacheDataBaseArrayList() {
        return cacheDataBaseArrayList;
    }

    public ArrayList<CacheDataBase> getCardDataBaseArrayList() {
        return recyclerViewAdapter.getAddToCardDataBase();
    }

}
