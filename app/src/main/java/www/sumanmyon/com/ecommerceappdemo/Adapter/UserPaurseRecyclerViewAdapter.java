package www.sumanmyon.com.ecommerceappdemo.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import www.sumanmyon.com.ecommerceappdemo.Admin.DashboardActivity;
import www.sumanmyon.com.ecommerceappdemo.Admin.EditActivity;
import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.DataBase.ProductDataBase;
import www.sumanmyon.com.ecommerceappdemo.GoogleMap.ShowGoogleMap;
import www.sumanmyon.com.ecommerceappdemo.MainActivity;
import www.sumanmyon.com.ecommerceappdemo.R;
import www.sumanmyon.com.ecommerceappdemo.UserPaurse.CardAlertDialog;

public class UserPaurseRecyclerViewAdapter extends RecyclerView.Adapter<UserPaurseRecyclerViewAdapter.ViewHolder> {
    MainActivity activity;
    ArrayList<CacheDataBase> cacheDataBaseArrayList;
    ArrayList<CacheDataBase> addToCardDataBase2 = null;
    ArrayList<CacheDataBase> addToCardDataBase;
    //LayoutInflater inflater;
    CardAlertDialog cardAlertDialog;

    public UserPaurseRecyclerViewAdapter(MainActivity activity, ArrayList<CacheDataBase> cacheDataBaseArrayList) {
        this.activity = activity;
        this.cacheDataBaseArrayList = cacheDataBaseArrayList;
        addToCardDataBase = new ArrayList<>();
        addToCardDataBase2 = new ArrayList<>();
        // inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.user_item_list_layout,null);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final CacheDataBase dataBase = cacheDataBaseArrayList.get(position);
        holder.productTextView.setText(dataBase.getProductName());
        holder.descriptionTextView.setText(dataBase.getDescription());
        holder.stockTextView.setText("Stock: " + dataBase.getStock());
        //holder.locationTextView.setText("location: " +dataBase.getLocation());
        holder.priceTextView.setText("Price: "+dataBase.getPrice());

        try{
            Bitmap myBitmap = BitmapFactory.decodeFile(dataBase.getImage());
            int nh = (int) ( myBitmap.getHeight() * (512.0 / myBitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);
            holder.imageView.setImageBitmap(scaled);
        }catch (Exception e){

        }

        holder.locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ShowGoogleMap.class);
                i.putExtra("location",dataBase.getLocation());
                activity.startActivity(i);
            }
        });
        cardAlertDialog = new CardAlertDialog(activity, dataBase,addToCardDataBase,addToCardDataBase2);
        holder.paurseButton.setOnClickListener(cardAlertDialog);
    }


    @Override
    public int getItemCount() {
        return cacheDataBaseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productTextView, descriptionTextView, stockTextView,  priceTextView;
        ImageView locationTextView, imageView;
        Button paurseButton;

        public ViewHolder(View itemView) {
            super(itemView);
            productTextView = itemView.findViewById(R.id.user_list_product_textView);
            descriptionTextView = itemView.findViewById(R.id.user_list_description_textView);
            stockTextView = itemView.findViewById(R.id.user_list_stock_textView);
            locationTextView = itemView.findViewById(R.id.user_list_location_textView);
            priceTextView = itemView.findViewById(R.id.user_list_price_textView);
            imageView = itemView.findViewById(R.id.user_list_imageView);

            paurseButton = itemView.findViewById(R.id.paurse_button);
        }
    }

    public void showMessage(String message) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }

    public void restart(String uid){
        Intent i = new Intent(activity, DashboardActivity.class);
        i.putExtra("uid",uid);
        activity.startActivity(i);
        activity.finish();
    }

    public ArrayList<CacheDataBase> getAddToCardDataBase() {
        return cardAlertDialog.getAddToCardDataBase();
    }
}
