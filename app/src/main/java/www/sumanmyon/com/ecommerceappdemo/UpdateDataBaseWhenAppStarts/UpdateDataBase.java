package www.sumanmyon.com.ecommerceappdemo.UpdateDataBaseWhenAppStarts;

import android.database.Cursor;

import java.util.ArrayList;

import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.DataBase.ProductDataBase;
import www.sumanmyon.com.ecommerceappdemo.DataBase.SharePrefForDataBaseUpdate;
import www.sumanmyon.com.ecommerceappdemo.DataBase.UpdateDataBaseInFuture;
import www.sumanmyon.com.ecommerceappdemo.MainActivity;

public class UpdateDataBase {
    MainActivity mainActivity;
    UpdateDataBaseInFuture updateDataBaseInFuture;
    ProductDataBase productDataBase;

    public UpdateDataBase(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        updateDataBaseInFuture = new UpdateDataBaseInFuture(mainActivity);
        productDataBase = new ProductDataBase(mainActivity);
    }


    public void update() {
        Cursor cursor = updateDataBaseInFuture.getAllData();
        if(cursor.getCount() == 0){  //TODO there is no data in database
            //showMessage("There is no data in database");
            mainActivity.showMessage("There is no data in database");
            return;
        }

        //TODO if cursor is at first of row or not
        if(!cursor.isFirst()){
            cursor.moveToFirst();
        }

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

            updateInProductDataBase(id,productName,stock,price,phone,image,description,location,uid);

        }while (cursor.moveToNext());

    }

    boolean isUpdated;

    private void updateInProductDataBase(String id, String productName, String stock,
                                         String price, String phone, String image,
                                         String description, String location, String uid) {
        int s = Integer.parseInt(stock);
        if(s==0){
            productDataBase.delete(id,uid);
        }else {
            isUpdated = productDataBase.update(id, productName, stock, price, phone, image, description, location, uid);
        }

        //delete update database table
        updateDataBaseInFuture.delete(id,uid);

    }

    public void setUpdateToFalse(){
        if (isUpdated) {
            mainActivity.showMessage("Data Updated Successfully");
        } else {
            mainActivity.showMessage("Data Updating failed");
        }

        //set share pref for database uptade to false
        new SharePrefForDataBaseUpdate(mainActivity).setCredential("false");
    }


}
