package www.sumanmyon.com.ecommerceappdemo.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ProductDataBase extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "ecommerce2.db";   //not case sensetive
    public static final String TABLE_NAME = "product_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "PRODUCT_NAME";
    public static final String COL_3 = "STOCK";
    public static final String COL_4 = "PRICE";
    public static final String COL_5 = "PHONE";
    public static final String COL_6 = "IMAGE";
    public static final String COL_7 = "DESCRIPTION";
    public static final String COL_8 = "LOCATION";
    public static final String COL_9 = "UID";

    public ProductDataBase(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create db
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, PRODUCT_NAME TEXT, STOCK TEXT, PRICE TEXT, PHONE TEXT, IMAGE TEXT, DESCRIPTION TEXT, LOCATION TEXT, UID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //TODO Insert data in database
    public boolean insert(String productName, String stock, String price,
                          String phone, String image, String description,
                          String location, String uid){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, productName);
        contentValues.put(COL_3, stock);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, phone);
        contentValues.put(COL_6, image);
        contentValues.put(COL_7, description);
        contentValues.put(COL_8, location);
        contentValues.put(COL_9, uid);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //TODO Show/Get data from database
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME , null);
        //Cursor cursor = db.rawQuery("select * from "+TABLE_NAME, null);
        return cursor;
    }

    //TODO Show/Get data from database for specific user
    public Cursor getAllData(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where uid=" + uid, null);
        //Cursor cursor = db.rawQuery("select * from "+TABLE_NAME, null);
        return cursor;
    }

    //TODO Update data in database
    public boolean update(String id,String productName, String stock, String price,
                          String phone, String image, String description,
                          String location, String uid){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, productName);
        contentValues.put(COL_3, stock);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, phone);
        contentValues.put(COL_6, image);
        contentValues.put(COL_7, description);
        contentValues.put(COL_8, location);

        db.update(TABLE_NAME,contentValues," ID = ? and UID = ?",new String[]{id,uid});

        return true;
    }

    //TODO Delete data in database
    public Integer delete(String id, String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME," ID = " + id + " and UID = " + uid, null);
        //db.delete(TABLE_NAME," ID = ?",new String[]{id});
    }
}
