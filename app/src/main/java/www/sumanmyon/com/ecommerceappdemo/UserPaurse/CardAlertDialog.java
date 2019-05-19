package www.sumanmyon.com.ecommerceappdemo.UserPaurse;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.MainActivity;
import www.sumanmyon.com.ecommerceappdemo.R;
import www.sumanmyon.com.ecommerceappdemo.UpdateDataBaseWhenAppStarts.InsertDataBase;

public class CardAlertDialog implements View.OnClickListener {

    MainActivity activity;
    CacheDataBase dataBase, addToCardDB;

    String noOfItem = "";
    String calculateValue = "";

    ArrayList<CacheDataBase> addToCardDataBase;
    ArrayList<CacheDataBase> addToCardDataBase2;

    public CardAlertDialog(MainActivity activity, CacheDataBase dataBase, ArrayList<CacheDataBase> toCardDataBase, ArrayList<CacheDataBase> addToCardDataBase) {
        this.activity = activity;
        this.dataBase = dataBase;
        this.addToCardDataBase = addToCardDataBase;
        this.addToCardDataBase2 = toCardDataBase;
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View mView = activity.getLayoutInflater().inflate(R.layout.add_to_card_layout,null);
        TextView productText = mView.findViewById(R.id.card_product_textView);
        EditText stockText = mView.findViewById(R.id.card_stock);
        TextView remainingText = mView.findViewById(R.id.card_remaining_stock);
        Button cardDone = mView.findViewById(R.id.card_done_button);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        productText.setText(dataBase.getProductName());
        stockText.setText("");
        remainingText.setText(dataBase.getStock());

        //todo on textChanged
        stockText.addTextChangedListener(new TextChange(dataBase.getStock(),stockText,remainingText));

        cardDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showMessage("Added");
                if(noOfItem == ""){
                    dialog.cancel();
                }else {
                    //Add to Card
                    addToCardDB = new CacheDataBase(dataBase.getId(),dataBase.getProductName(),noOfItem,
                            dataBase.getPrice(),dataBase.getPhoneNo(),dataBase.getImage(),
                            dataBase.getDescription(),dataBase.getLocation(),dataBase.getUid(),
                            calculateValue);

                    addToCardDataBase2.add(addToCardDB);
                    activity.setShoppingCardTextViewItems(String.valueOf(addToCardDataBase2.size()));
                    showMessage(String.valueOf(addToCardDataBase2.size()));
                    dialog.cancel();
                }
            }
        });
    }



    public ArrayList<CacheDataBase> getAddToCardDataBase() {
        return addToCardDataBase2;
    }

    public void showMessage(String message) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }

    public class TextChange implements TextWatcher{

        Integer stockNo;
        EditText stockText;
        TextView remainingText;



        public TextChange(String stock, EditText stockText, TextView remainingText) {
            stockNo = Integer.parseInt(stock.trim());
            this.stockText = stockText;
            this.remainingText = remainingText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                if (!TextUtils.isEmpty(stockText.getText()) || stockText.getText().toString() != "") {
                    int calculate = stockNo - Integer.parseInt(stockText.getText().toString());
                    if (calculate >= 0) {
                        remainingText.setText(String.valueOf(calculate));
                        noOfItem = stockText.getText().toString();
                        calculateValue = String.valueOf(calculate);
                    } else {
                        remainingText.setText("Limit exceed");
                        noOfItem = "";
                    }
                } else {
                    remainingText.setText(String.valueOf(stockNo));
                    noOfItem = "";
                }
            }catch (Exception e){
                remainingText.setText(String.valueOf(stockNo));
                noOfItem = "";
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    }
}
