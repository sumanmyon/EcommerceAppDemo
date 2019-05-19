package www.sumanmyon.com.ecommerceappdemo.Search;

import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import www.sumanmyon.com.ecommerceappdemo.DataBase.CacheDataBase;
import www.sumanmyon.com.ecommerceappdemo.DataBase.ProductDataBase;
import www.sumanmyon.com.ecommerceappdemo.MainActivity;

public class SearchByProductName implements SearchView.OnQueryTextListener {
    MainActivity mainActivity;
    ArrayList<String> list;
    ArrayAdapter<String> adapter = null;
    ArrayList<CacheDataBase> cacheDataBaseArrayList;

    ArrayList<CacheDataBase> cacheDataBaseArrayListSearch;
    CacheDataBase cacheDataBase;

    public SearchByProductName(MainActivity mainActivity, ArrayList<CacheDataBase> cacheDataBaseArrayList) {
        this.mainActivity = mainActivity;
        this.cacheDataBaseArrayList = cacheDataBaseArrayList;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(adapter==null) {
           search(newText);
        }else {
            search(newText);
        }
        return true;
    }

    private void search(String newText) {
        if (!newText.equals("")) {
            list = new ArrayList<>();
            cacheDataBaseArrayListSearch = new ArrayList<>();

            for (int i = 0; i < cacheDataBaseArrayList.size(); i++) {
                String text = cacheDataBaseArrayList.get(i).getProductName();
                String regex = ".*" + newText + ".*";
                boolean matches = Pattern.matches(regex, text);
                //System.out.println("matches = " + matches);
                if (matches) {
                    //for list View
//                    list.add(cacheDataBaseArrayList.get(i).getProductName());

                    //for product show
                    cacheDataBase = new CacheDataBase(
                            cacheDataBaseArrayList.get(i).getId(),
                            cacheDataBaseArrayList.get(i).getProductName(),
                            cacheDataBaseArrayList.get(i).getStock(),
                            cacheDataBaseArrayList.get(i).getPrice(),
                            cacheDataBaseArrayList.get(i).getPhoneNo(),
                            cacheDataBaseArrayList.get(i).getImage(),
                            cacheDataBaseArrayList.get(i).getDescription(),
                            cacheDataBaseArrayList.get(i).getLocation(),
                            cacheDataBaseArrayList.get(i).getUid()
                    );

                    cacheDataBaseArrayListSearch.add(cacheDataBase);
                }
            }

            try {
//                adapter = new ArrayAdapter<>(mainActivity, android.R.layout.simple_list_item_1, list);
//                mainActivity.listView.setAdapter(adapter);
                suffel();
            } catch (Exception e) {
                cacheDataBaseArrayListSearch = null;
                suffel();
            }
        }else {
            cacheDataBaseArrayListSearch = null;
            suffel();
        }
    }

    private void suffel(){
        mainActivity.refresh("search", cacheDataBaseArrayListSearch);
    }
}
