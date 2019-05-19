package www.sumanmyon.com.ecommerceappdemo.RecommendationAndFeedBack;

import android.app.Activity;
import android.content.Intent;

public class ShareSocialMedia {
    String message = "This is great App, you should try it out!";
    Intent shareIntent;
    Activity activity;

    public ShareSocialMedia(Activity activity) {
        this.activity = activity;
    }

    public void share(){
        shareIntent = new Intent(Intent.ACTION_SEND);   //explicit intent
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Ecommerce App");
        shareIntent.putExtra(Intent.EXTRA_TEXT,message);
        activity.startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
