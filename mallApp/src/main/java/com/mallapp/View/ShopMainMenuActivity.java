package com.mallapp.View;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.List.Adapter.ShopAdapter;
import com.List.Adapter.ShopExpandableAdapter;
import com.List.Adapter.ShopSearchAdapter;
import com.foound.widget.AmazingListView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.ShopFiltration;
import com.mallapp.Controllers.ShopList;
import com.mallapp.Model.ShopDetailModel;
import com.mallapp.Model.Shops;
import com.mallapp.Model.ShopsModel;
import com.mallapp.cache.ShopCacheManager;
import com.mallapp.db.DatabaseHelper;
import com.mallapp.globel.GlobelShops;
import com.mallapp.layouts.SegmentedRadioGroup;
import com.mallapp.listeners.ShopsDataListener;
import com.mallapp.utils.Log;
import com.mallapp.utils.VolleyNetworkUtil;


public class ShopMainMenuActivity extends SlidingDrawerActivity
        implements ShopsDataListener, OnCheckedChangeListener,
        OnClickListener {

    String TAG = getClass().getCanonicalName();
    public static Handler uihandler;
    SegmentedRadioGroup segmentText;
    private ImageButton open_navigation, open_drawer;
    private TextView heading;

    private AmazingListView list_view;
    private ExpandableListView list_view1;
    private ListView list_view_search;

    private EditText search_feild;
    private Button cancel_search;
    ShopAdapter adapter;
    ShopExpandableAdapter adapter1;
    ShopSearchAdapter adapter_search;
    LinearLayout side_index_layout;
    LinearLayout side_index_scroll;

    String audienceFilter = MainMenuConstants.AUDIENCE_FILTER_ALL;
    public static String mallPlaceId;

    static ArrayList<ShopsModel> shopModel_read_audience, shopsearchResults, shopsearch_array;
    ArrayList<ShopsModel> dbList = new ArrayList<>();

    static HashMap<String, ArrayList<ShopsModel>> shops_all;
    static HashMap<String, ArrayList<ShopsModel>> shops_category, shops_floor;

    VolleyNetworkUtil volleyNetworkUtil;
    // Reference of DatabaseHelper class to access its DAOs and other components
    private DatabaseHelper databaseHelper = null;
    Dao<ShopsModel, Integer> shopsDao;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_main_menu);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
        shopModel_read_audience = new ArrayList<ShopsModel>();
        mallPlaceId = getIntent().getStringExtra(Offers_News_Constants.MALL_PLACE_ID);
        String url = ApiConstants.GET_SHOPS_URL_KEY + mallPlaceId;
        volleyNetworkUtil = new VolleyNetworkUtil(this);
        volleyNetworkUtil.GetShops(url, this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        uihandler = MainMenuConstants.uiHandler;
        init();


        search_feild.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {

                String searchString = cs.toString().trim();
                Log.e(TAG, "" + searchString);
                int textLength = searchString.length();
                //Log.e(TAG, ""+textLength);

                if (textLength > 0) {

                    Log.e(TAG, "hide existing lists = " + audienceFilter);
                    cancel_search.setTextColor(getResources().getColor(R.color.purple));
                    list_view1.setVisibility(View.GONE);
                    side_index_scroll.setVisibility(View.GONE);
//                    shopsearch_array = GlobelShops.shopModel_array;

                    if (shopsearch_array == null || shopsearch_array.size() == 0) {
                        readShopList();
                    }
//					Log.e(TAG, "shopsearch_array = "+ GlobelShops.shop_array.size());
//					Log.e(TAG, "shopsearch_array = "+ shopsearch_array.size());
                    shopsearchResults = new ArrayList<ShopsModel>();

                    for (int i = 0; i < shopsearch_array.size(); i++) {
                        //Log.e(TAG, "shop_name = .....get");
                        String shop_name = shopsearch_array.get(i).getStoreName().toString();
                        //Log.e(TAG, "shop_name = ...."+ shop_name);
                        if (textLength <= shop_name.length()) {

                            if (searchString.equalsIgnoreCase(shop_name.substring(0, textLength)))
                                shopsearchResults.add(shopsearch_array.get(i));
                        }
                    }
                    Log.e(TAG, "shopsearch_array = " + shopsearch_array.size());
                    Log.e(TAG, "search results = " + shopsearchResults.size());

                    if (shopsearchResults != null && shopsearchResults.size() > 0) {
                        adapter_search.setShop_search(shopsearchResults);
                        list_view_search.setVisibility(View.VISIBLE);
                        adapter_search.notifyDataSetChanged();

                    } else {

                        if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)) {
                            side_index_scroll.setVisibility(View.VISIBLE);
                        } else if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)
                                || audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)) {

                            list_view1.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    cancel_search.setTextColor(getResources().getColor(R.color.grey));
                    Log.e(TAG, "view existing lists");
                    if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)) {
                        side_index_scroll.setVisibility(View.VISIBLE);
                    } else if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)
                            || audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)) {

                        list_view1.setVisibility(View.VISIBLE);
                        list_view_search.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        segmentText.setOnCheckedChangeListener(this);
            open_navigation.setOnClickListener(this);
            open_drawer.setOnClickListener(this);
            open_navigation.setVisibility(View.GONE);
            open_drawer.setVisibility(View.VISIBLE);
        cancel_search.setOnClickListener(this);

    }

    private void init() {
        open_navigation = (ImageButton) findViewById(R.id.navigation);
        open_drawer = (ImageButton) findViewById(R.id.navigation_drawer);
        segmentText = (SegmentedRadioGroup) findViewById(R.id.segment_text);
        heading				= (TextView) 			findViewById(R.id.heading);
        search_feild = (EditText) findViewById(R.id.search_feild);
        cancel_search = (Button) findViewById(R.id.cancel_search);
        list_view = (AmazingListView) findViewById(R.id.shop_list);
        list_view_search = (ListView) findViewById(R.id.search_list_view);
        side_index_layout = (LinearLayout) findViewById(R.id.side_index);
        side_index_scroll = (LinearLayout) findViewById(R.id.scroll_side_index);
        list_view1 = (ExpandableListView) findViewById(R.id.expandableListView);
        heading.setText("SHOPS");

        try {
            // This is how, a reference of DAO object can be done
            shopsDao = getHelper().getShopsDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private void readShopList() {
        /*shops_read_audience = GlobelShops.shop_array;
        if (shops_read_audience == null || shops_read_audience.size() == 0) {
            ShopList.saveOffersNewsData(getApplicationContext());
            shops_read_audience = ShopList.readShopsList(getApplicationContext());
            GlobelShops.shop_array = shops_read_audience;
            shopsearch_array = shops_read_audience;
            shopsearchResults = shops_read_audience;
        }

        shopsearch_array = GlobelShops.shop_array;
        shopsearchResults = GlobelShops.shop_array;*/
        //	Log.e(TAG, "read shopp list = "+ GlobelShops.shop_array.size());
    }


    private static int prev = -1;
    private static int collapsed = -1;


    private void initExpandableList() {

        list_view1.setHeaderDividersEnabled(true);
        list_view1.setSelector(android.R.color.white);
        list_view1.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (prev != -1) {
                    if (prev != collapsed)
                        list_view1.collapseGroup(prev);
                }
                prev = groupPosition;
            }
        });
        list_view1.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                collapsed = groupPosition;
            }
        });

        list_view1.setGroupIndicator(getResources().getDrawable(R.drawable.my_group_statelist));
    }

    private void initSectionHeaderList() {
        list_view.setPinnedHeaderView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item_shop_header, list_view, false));
        adapter = new ShopAdapter(getApplicationContext(), ShopMainMenuActivity.this, shops_all, GlobelShops.header_section_alphabetics, audienceFilter, shopsDao);
        list_view.setAdapter(adapter);
    }


    private void filterShops() {
        initArrays();
        if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)) {
            shops_all = ShopFiltration.filterFavouriteShopsModelAlphabetically(audienceFilter, shopModel_read_audience);
        } else if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)) {
            shops_category = ShopFiltration.filterFavouriteShopsModelCategory(audienceFilter, shopModel_read_audience);
        } else if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)) {
            shops_floor = ShopFiltration.filterFavouriteShopsModelFloor(audienceFilter, shopModel_read_audience);
        }
        //all_audience_images=ShopFiltration.getShopsImagesList(getActivity().getApplicationContext(), shops_all_audience);
    }


    private void initArrays() {
        shops_all = new HashMap<String, ArrayList<ShopsModel>>();
        shops_category = new HashMap<String, ArrayList<ShopsModel>>();
        shops_floor = new HashMap<String, ArrayList<ShopsModel>>();

    }

    Map<String, Integer> mapIndex;

    private void displayIndex() {
        mapIndex = ShopFiltration.getIndexList(getResources());
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(R.layout.list_item_side_index, side_index_layout, false);
            textView.setText(index);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickIndex(v);
                }
            });
            side_index_layout.addView(textView);
        }
    }

    private void onClickIndex(View view) {
        TextView selectedIndex = (TextView) view;
        list_view.setSelection(mapIndex.get(selectedIndex.getText()));
        Toast.makeText(getApplicationContext(), "" + selectedIndex.getText(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        /*
         * You'll need this in your class to release the helper when done.
		 */
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }


    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == segmentText) {
            if (checkedId == R.id.button_one) {
                audienceFilter = MainMenuConstants.AUDIENCE_FILTER_ALL;
            } else if (checkedId == R.id.button_two) {
                audienceFilter = MainMenuConstants.AUDIENCE_FILTER_CATEGORY;
            } else if (checkedId == R.id.button_three) {
                audienceFilter = MainMenuConstants.AUDIENCE_FILTER_FLOOR;
            }
            callInOnResume();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
        finish();
    }

    private void invisibleIndexList() {
        //list_view.setVisibility(View.GONE);
        list_view1.setVisibility(View.VISIBLE);
        side_index_scroll.setVisibility(View.GONE);
    }

    private void callInOnResume() {

        readShopList();
        filterShops();
        invisibleSearch();
        if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)) {
            adapter = new ShopAdapter(getApplicationContext(), this,
                    shops_all, GlobelShops.header_section_alphabetics, audienceFilter, shopsDao);

            list_view.setAdapter(adapter);
            list_view1.setVisibility(View.GONE);
            side_index_scroll.setVisibility(View.VISIBLE);

        } else if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)) {

            invisibleIndexList();
            adapter1 = new ShopExpandableAdapter(getApplicationContext(), this,
                    shops_category, GlobelShops.header_section_category,shopsDao);
            list_view1.setAdapter(adapter1);

        } else if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)) {

            invisibleIndexList();
            adapter1 = new ShopExpandableAdapter(getApplicationContext(), this,
                    shops_floor, GlobelShops.header_section_floor,shopsDao);
            list_view1.setAdapter(adapter1);
            list_view1.setVisibility(View.VISIBLE);
        }
    }


    private void invisibleSearch() {
        list_view_search.setVisibility(View.GONE);
        cancel_search.setTextColor(getResources().getColor(R.color.grey));
        search_feild.setText("");
    }

    @Override
    public void onClick(View v) {

        if (open_navigation.getId() == v.getId()) {
            DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
            finish();

        } else if(open_drawer.getId() == v.getId()){
            SlidingDrawerActivity.uiHandler.sendEmptyMessage(1);
        }
        else if (cancel_search.getId() == v.getId()) {

            list_view_search.setVisibility(View.GONE);

            if (search_feild.getText().toString().length() > 0) {

                cancel_search.setTextColor(getResources().getColor(R.color.grey));
                search_feild.setText("");

                if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)) {

                    side_index_scroll.setVisibility(View.VISIBLE);

                } else if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)
                        || audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)) {

                    list_view1.setVisibility(View.VISIBLE);
                }
            } else {

            }
        }
    }


    @Override
    public void onDataReceived(ArrayList<ShopsModel> shopsModelArrayList) {
        try {
            /*if (shopsModelArrayList.size()>0)
            shopModel_read_audience = readShopsList(ShopMainMenuActivity.this);
            else*/
            getDBShops();
//            shopModel_read_audience = shopsModelArrayList;

            if (dbList != null) {
                for (ShopsModel shop : dbList
                        ) {
                    for (int i = 0; i < shopsModelArrayList.size(); i++) {
                        ShopsModel sh = shopsModelArrayList.get(i);
                        if (sh.getMallStoreId().equals(shop.getMallStoreId())) {
                            if (shop.isFav()) {
                                sh.setFav(true);
                                shopsModelArrayList.set(i, sh);
                            }
                        }
                    }
                }
            }
            shopModel_read_audience = shopsModelArrayList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        shopsearchResults = shopsModelArrayList;
        shopsearch_array = shopsModelArrayList;

        adapter_search = new ShopSearchAdapter(getApplicationContext(), this, R.layout.list_item_shop, shopsearchResults,shopsDao);
        list_view_search.setAdapter(adapter_search);
        filterShops();
        displayIndex();
        initSectionHeaderList();
        initExpandableList();
    }

    @Override
    public void onShopDetailReceived(ShopDetailModel shopDetail) {

    }

    @Override
    public void OnError() {
         Toast.makeText(context, "No shops found for this mall", Toast.LENGTH_SHORT).show();
         String serverError = context.getResources().getString(R.string.shop_error_message);
         Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
    }

    /*public static void writeShopsList(Context context, ArrayList<ShopsModel> offer_objects) {
        try {
            ShopCacheManager.writeObjectList(context, offer_objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.w(MallApp_Application.TAG, "write:" + offer_objects.size());
    }

    public static ArrayList<ShopsModel> readShopsList(Context context) {
        try {
            shopModel_read_audience = ShopCacheManager.readObjectList(context, MainMenuConstants.SELECTED_CENTER_NAME);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shopModel_read_audience;
    }*/

    public void getDBShops() {
        try {
            // This is how, a reference of DAO object can be done
            Dao<ShopsModel, Integer> studentDao = getHelper().getShopsDao();
            // Get our query builder from the DAO
            final QueryBuilder<ShopsModel, Integer> queryBuilder = studentDao.queryBuilder();
            // We need only Students who are associated with the selected Teacher, so build the query by "Where" clause
            // Prepare our SQL statement
            final PreparedQuery<ShopsModel> preparedQuery = queryBuilder.prepare();
            // Fetch the list from Database by queryingit
            final Iterator<ShopsModel> studentsIt = studentDao.queryForAll().iterator();
            // Iterate through the StudentDetails object iterator and populate the comma separated String
            while (studentsIt.hasNext()) {
                final ShopsModel sDetails = studentsIt.next();
                dbList.add(sDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}