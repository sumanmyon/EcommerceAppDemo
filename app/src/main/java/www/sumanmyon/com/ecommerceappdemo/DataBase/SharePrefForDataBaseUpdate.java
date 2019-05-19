package www.sumanmyon.com.ecommerceappdemo.DataBase;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharePrefForDataBaseUpdate {
    Activity activity;
    private static final String MY_PREFS_NAME = "DataBaseUpdate";
    SharedPreferences prefs;

    public SharePrefForDataBaseUpdate(Activity activity){
        this.activity = activity;
        prefs = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    //Setting values in Preference
    public void setCredential(String str){
        SharedPreferences.Editor editor= prefs.edit();
        // String data = "username="+username+"::password="+password;
        editor.putString("update", str);
        editor.apply();
    }

    //Retrieve data from preference
    public String getCredential(){
        String getUserData = null;
        String update = prefs.getString("update", "No name defined");//"No name defined" is the default value.
        getUserData =update;
        //activity.showMessage(getUserData);
        return getUserData;
    }
}
