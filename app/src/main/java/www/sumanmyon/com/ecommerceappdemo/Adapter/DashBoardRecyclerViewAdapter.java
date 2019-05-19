package www.sumanmyon.com.ecommerceappdemo.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import www.sumanmyon.com.ecommerceappdemo.Admin.DashboardActivity;
import www.sumanmyon.com.ecommerceappdemo.Admin.EditActivity;
import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.DataBase.ProductDataBase;
import www.sumanmyon.com.ecommerceappdemo.GoogleMap.ShowGoogleMap;
import www.sumanmyon.com.ecommerceappdemo.R;

public class DashBoardRecyclerViewAdapter extends RecyclerView.Adapter<DashBoardRecyclerViewAdapter.ViewHolder> {
    Activity activity;
    ArrayList<CacheDataBase> cacheDataBaseArrayList;
    //LayoutInflater inflater;

    public DashBoardRecyclerViewAdapter(Activity activity, ArrayList<CacheDataBase> cacheDataBaseArrayList) {
        this.activity = activity;
        this.cacheDataBaseArrayList = cacheDataBaseArrayList;
       // inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.show_item_lists,null);
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

        try {
//        Uri uri = Uri.fromFile(new File(dataBase.getImage()));
//        Picasso.get().load(uri).into(holder.imageView);
            Bitmap myBitmap = BitmapFactory.decodeFile(dataBase.getImage());
            int nh = (int) (myBitmap.getHeight() * (512.0 / myBitmap.getWidth()));
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

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reDirectToEditActivity(dataBase);
            }
        });

        holder.deteleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDataFromDataBase(dataBase.getId(), dataBase.getUid());
            }
        });
    }


    @Override
    public int getItemCount() {
        return cacheDataBaseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productTextView, descriptionTextView, stockTextView, priceTextView;
        ImageView imageView,locationTextView;
        Button editButton, deteleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            productTextView = itemView.findViewById(R.id.main_list_product_textView);
            descriptionTextView = itemView.findViewById(R.id.main_list_description_textView);
            stockTextView = itemView.findViewById(R.id.main_list_stock_textView);
            locationTextView = itemView.findViewById(R.id.main_list_location_textView);
            priceTextView = itemView.findViewById(R.id.main_list_price_textView);
            imageView = itemView.findViewById(R.id.main_list_imageView);

            editButton = itemView.findViewById(R.id.edit_button);
            deteleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

    private void reDirectToEditActivity(CacheDataBase dataBase){
        activity.finish();
        Intent i = new Intent(activity, EditActivity.class);
        i.putExtra("uid",  dataBase.getUid());
        i.putExtra("id",  dataBase.getId());
        i.putExtra("productName",  dataBase.getProductName());
        i.putExtra("stock",  dataBase.getStock());
        i.putExtra("price",  dataBase.getPrice());
        i.putExtra("phoneNo",  dataBase.getPhoneNo());
        i.putExtra("image",  dataBase.getImage());
        i.putExtra("description",  dataBase.getDescription());
        i.putExtra("location",  dataBase.getLocation());
        activity.startActivity(i);
    }

    private void deleteDataFromDataBase(final String id, final String uid){
         AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle("Delete Item/Product")
                .setMessage("Do you want to Delete this Item ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProductDataBase productDataBase = new ProductDataBase(activity);
                        Integer isDeleted = productDataBase.delete(id,uid);
                        if(isDeleted > 0){
                            showMessage("Data Deleted Successfully");
                            restart(uid);
                        }else {
                            showMessage("Data Deleting failed");
                            dialogInterface.cancel();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         dialogInterface.cancel();
                     }
                }).show();
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
}
