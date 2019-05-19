package www.sumanmyon.com.ecommerceappdemo.NavigationDrawer;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import www.sumanmyon.com.ecommerceappdemo.DataBase.SharePreferenceForUserCredential;
import www.sumanmyon.com.ecommerceappdemo.R;

import www.sumanmyon.com.ecommerceappdemo.MainActivity;
import www.sumanmyon.com.ecommerceappdemo.RecommendationAndFeedBack.FeedBack;
import www.sumanmyon.com.ecommerceappdemo.RecommendationAndFeedBack.ShareSocialMedia;

public class Navigation implements NavigationView.OnNavigationItemSelectedListener {
    MainActivity mainActivity;
    NavigationView nv;

    public Navigation(MainActivity mainActivity, NavigationView nv) {
        this.mainActivity = mainActivity;
        this.nv = nv;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.dashboard:
                //mainActivity.showMessage("Add Item, Comming Soon");
                mainActivity.checklogin("nav");
                break;
            case R.id.social_media:
                new ShareSocialMedia(mainActivity).share();
                //mainActivity.showMessage("Social Media Sharing, Comming Soon");
                break;
            case R.id.feedback:
                Intent i = new Intent(mainActivity, FeedBack.class);
                mainActivity.startActivity(i);
                //mainActivity.showMessage("FeedBack, Comming Soon");
                break;
            case R.id.logout:
                nv.getMenu().setGroupVisible(R.id.group_after_login, false);
                new SharePreferenceForUserCredential(mainActivity).logoutCredential("","","");
                mainActivity.userNameTextView.setText("username");
                mainActivity.userProfileImageView.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.images));
                mainActivity.showMessage("Logout Successfully");
                break;
            default:
                return true;
        }

        menuItem.setChecked(true);
        mainActivity.drawerLayout.closeDrawers();
        return true;
    }
}
