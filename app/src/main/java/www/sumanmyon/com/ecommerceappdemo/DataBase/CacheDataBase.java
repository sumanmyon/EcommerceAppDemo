package www.sumanmyon.com.ecommerceappdemo.DataBase;

import android.os.Parcelable;

public class CacheDataBase {
    private String id;
    private String uid;
    private String productName;
    private String stock;
    private String price;
    private String phoneNo;
    private String image;
    private String location;
    private String description;

    private String remainingStock;

    public CacheDataBase(String id,
                         String productName, String stock, String price,
                         String phone, String image, String description,
                         String location, String uid) {
        this.id = id;
        this.uid = uid;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.phoneNo = phone;
        this.image = image;
        this.location = location;
        this.description = description;
    }

    public CacheDataBase(String id,
                         String productName, String stock, String price,
                         String phone, String image, String description,
                         String location, String uid, String remainingStock) {
        this.id = id;
        this.uid = uid;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.phoneNo = phone;
        this.image = image;
        this.location = location;
        this.description = description;

        this.remainingStock = remainingStock;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getProductName() {
        return productName;
    }

    public String getStock() {
        return stock;
    }

    public String getPrice() {
        return price;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getRemainingStock() {
        return remainingStock;
    }
}
