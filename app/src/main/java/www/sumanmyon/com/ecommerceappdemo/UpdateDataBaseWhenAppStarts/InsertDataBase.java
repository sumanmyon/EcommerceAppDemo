package www.sumanmyon.com.ecommerceappdemo.UpdateDataBaseWhenAppStarts;

import android.app.Activity;
import android.widget.Toast;

import www.sumanmyon.com.ecommerceappdemo.DataBase.SharePrefForDataBaseUpdate;
import www.sumanmyon.com.ecommerceappdemo.DataBase.UpdateDataBaseInFuture;
import www.sumanmyon.com.ecommerceappdemo.UserPaurse.PaurseProduct;

public class InsertDataBase {
    Activity activity;
    UpdateDataBaseInFuture updateDataBaseInFuture;

    public InsertDataBase(PaurseProduct mainActivity) {
        this.activity = mainActivity;
        updateDataBaseInFuture = new UpdateDataBaseInFuture(mainActivity);
    }

    public void setForUpdate(){
        //put data in share preference
        new SharePrefForDataBaseUpdate(activity)
                .setCredential("true");

    }

    public void insert(String id, String productName, String stock, String price,
                       String phone, String image, String description,
                       String location, String uid){
        updateDataBaseInFuture = new UpdateDataBaseInFuture(activity);
        boolean isInserted = updateDataBaseInFuture.insert(id,productName,stock,price,phone,image,description,location,uid);
        if(isInserted) {
           //showMessage("Data Inserted Successfully");
        }else {
            showMessage("Data Inserted failed");
        }
    }

    public void showMessage(String message) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
}
