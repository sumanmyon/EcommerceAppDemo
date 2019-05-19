package www.sumanmyon.com.ecommerceappdemo.UserPaurse;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.R;
import www.sumanmyon.com.ecommerceappdemo.UpdateDataBaseWhenAppStarts.InsertDataBase;

import static www.sumanmyon.com.ecommerceappdemo.MainActivity.user;

public class PaurseProduct extends AppCompatActivity {

    Button button;

    ArrayList<CacheDataBase> dataBases = user.getCardDataBaseArrayList();
    Double total = 0.0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paurse_product);

        button = findViewById(R.id.paurse_button);
        creatingTableLayoutToshowData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataBases != null) {
                    if (dataBases.size() >= 0 && total>0.0) {
                        try {
                            for (int i = 0; i < dataBases.size(); i++) {
                                CacheDataBase dataBase = dataBases.get(i);
                                update(dataBase.getId(), dataBase.getProductName(), dataBase.getRemainingStock(), dataBase.getPrice(), dataBase.getPhoneNo(), dataBase.getImage(), dataBase.getDescription(), dataBase.getLocation(), dataBase.getUid());
                            }

                            showMessage("Paurse Successfully");
                        }catch (Exception e){
                            showMessage("You haven't paurse any thing");
                        }
                    }else {
                        showMessage("You haven't paurse any thing");
                    }
                }else {
                    showMessage("You haven't paurse any thing");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void creatingTableLayoutToshowData() {
        TableLayout tableLayout;
        tableLayout = findViewById(R.id.SaleOrderLines);
        tableRow(tableLayout,"Product","no. of items","Price",getColor(R.color.colorPrimary));

        if(dataBases != null) {
            if(dataBases.size()>=0) {
                for (int i = 0; i < dataBases.size(); i++) {
                    CacheDataBase dataBase = dataBases.get(i);

                    double totalCal = Double.parseDouble(dataBase.getStock()) * Double.parseDouble(dataBase.getPrice());
                    total = total +totalCal;
                    tableRow(tableLayout,dataBase.getProductName(), dataBase.getStock(),String.valueOf(totalCal), getColor(R.color.black));
                }

                if(total == 0){
                    showMessage("You haven't paurse any thing");
                }
            }
        }else {
            showMessage("You haven't paurse any thing");
        }

        tableRow(tableLayout,"","","", getColor(R.color.black));
        tableRow(tableLayout,"","total: ", String.valueOf(total), getColor(R.color.black));
    }

    private void tableRow(TableLayout tableLayout, String productName, String stock, String value, int color) {
        TableRow tableRow = new TableRow(this);

        TableLayout.LayoutParams tableRowParams= new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

        int leftMargin=10;
        int topMargin=2;
        int rightMargin=10;
        int bottomMargin=2;

        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        tableRow.setLayoutParams(tableRowParams);

        TextView textView = new TextView(this);
        TextView textView1 = new TextView(this);
        TextView textView2 = new TextView(this);

        textView.setText(productName);
        textView.setTextColor(color);
        textView1.setText(stock);
        textView1.setTextColor(color);
        textView2.setText(value);
        textView2.setTextColor(color);

        tableRow.addView(textView);
        tableRow.addView(textView1);
        tableRow.addView(textView2);
        tableLayout.setStretchAllColumns(true);
        tableLayout.addView(tableRow);
    }


    public void update(String id, String productName, String noOfItem,
                       String price, String phoneNo, String image,
                       String description, String location, String uid){
        InsertDataBase dataBase = new InsertDataBase(PaurseProduct.this);
        dataBase.setForUpdate();
        dataBase.insert(id,productName,noOfItem,price,
                phoneNo,image,description,location,uid);
    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}
