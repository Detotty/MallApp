package com.mallapp.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.List.Adapter.ServiceAdapter;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.ShopFiltration;
import com.mallapp.Model.ServicesModel;
import com.mallapp.globel.GlobelMainMenu;
import com.mallapp.listeners.ServicesDataListener;
import com.mallapp.utils.InputHandler;
import com.mallapp.utils.VolleyNetworkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicesMainMenuActivity extends SlidingDrawerActivity implements OnClickListener,  ServicesDataListener {

    //region Data Members
    String TAG = getClass().getCanonicalName();
    private ImageButton open_navigation, open_drawer;

    private ListView list_view;
    private ListView list_view_search;

    private EditText search_feild;
    private TextView heading;
    private Button cancel_search;

    ServiceAdapter adapter, adapter_search;
    private LinearLayout side_index_layout;
    private LinearLayout scroll_side_index;

    static ArrayList<ServicesModel> services_read, searchResults, search_array;
    String url, mallPlaceId;
    VolleyNetworkUtil volleyNetworkUtil;
    Map<String, Integer> mapIndex;
    //endregion


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_main_menu);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        mallPlaceId = getIntent().getStringExtra(Offers_News_Constants.MALL_PLACE_ID);
        url = ApiConstants.GET_MALL_SERVICES_URL_KEY + mallPlaceId;
        volleyNetworkUtil = new VolleyNetworkUtil(this);
        volleyNetworkUtil.GetServices(url, this);

        //region Search Implementation
        search_feild.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                String searchString = cs.toString().trim();
                int textLength = searchString.length();

                if (textLength > 0) {
                    cancel_search.setTextColor(getResources().getColor(R.color.purple));

                    scroll_side_index.setVisibility(View.GONE);
//					s_search_array = GlobelMainMenu.services_array;
                    if (search_array == null || search_array.size() == 0) {
//                        readServicesList();
                    }
                    searchResults = new ArrayList<ServicesModel>();

                    for (int i = 0; i < search_array.size(); i++) {

                        String rest_name = search_array.get(i).getFacilityType().toString();
                        if (textLength <= rest_name.length()) {
                            if (searchString.equalsIgnoreCase(rest_name.substring(0, textLength)))
                                searchResults.add(search_array.get(i));
                        }
                    }

                    if (searchResults != null && searchResults.size() > 0) {
                        adapter_search.setService_search(searchResults);
                        list_view_search.setVisibility(View.VISIBLE);
                        adapter_search.notifyDataSetChanged();
                    } else {
                        list_view.setVisibility(View.VISIBLE);
                    }
                } else {
                    cancel_search.setTextColor(getResources().getColor(R.color.grey));
                    list_view.setVisibility(View.VISIBLE);
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
        //endregion

        open_navigation.setOnClickListener(this);
        open_drawer.setOnClickListener(this);
        open_navigation.setVisibility(View.GONE);
        open_drawer.setVisibility(View.VISIBLE);
        cancel_search.setOnClickListener(this);

        list_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicesModel s1 = (ServicesModel) adapter.getItem(GlobelMainMenu.selected_index_services);
                s1.setOpened(false);
                services_read.set(GlobelMainMenu.selected_index_services, s1);


                ServicesModel s = (ServicesModel) adapter.getItem(position);
                if (s.isOpened()) {
                    s.setOpened(false);
                } else {
                    s.setOpened(true);
                }
                GlobelMainMenu.selected_index_services = position;
                services_read.set(position, s);
                adapter.setService_search(services_read);
                adapter.notifyDataSetChanged();
            }
        });
    }

    //region Data Initializations
    private void init() {
        open_navigation = (ImageButton) findViewById(R.id.navigation);
        open_drawer = (ImageButton) findViewById(R.id.navigation_drawer);
        heading = (TextView) findViewById(R.id.heading);
        heading.setText(getResources().getString(R.string.heading_services));

        search_feild = (EditText) findViewById(R.id.search_feild);
        cancel_search = (Button) findViewById(R.id.cancel_search);

        list_view = (ListView) findViewById(R.id.list);
        list_view_search = (ListView) findViewById(R.id.search_list_view);
        side_index_layout = (LinearLayout) findViewById(R.id.side_index);
        scroll_side_index = (LinearLayout) findViewById(R.id.scroll_side_index);

    }
    //endregion

    //region Fast Scroll Index Display
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
    //endregion

    //region Activity Life Cycle Functions
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
        finish();
    }
    //endregion

    //region Click Events Handling
    @Override
    public void onClick(View v) {

        if (open_navigation.getId() == v.getId()) {
            //DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
            finish();
        } else if (open_drawer.getId() == v.getId()) {
            SlidingDrawerActivity.uiHandler.sendEmptyMessage(1);
        } else if (cancel_search.getId() == v.getId()) {
            list_view_search.setVisibility(View.GONE);
            scroll_side_index.setVisibility(View.VISIBLE);

            if (search_feild.getText().toString().length() > 0) {
                cancel_search.setTextColor(getResources().getColor(R.color.grey));
                search_feild.setText("");
                InputHandler.hideSoftKeyboard(this);
            }
        }
    }
    //endregion



    /*//region Expandable List Management
    @Override
    public void onItemClick(AdapterView<?> adapter, View row, int position, long index) {


        ServicesModel s1 = (ServicesModel) adapter.getItemAtPosition(GlobelMainMenu.selected_index_services);
        s1.setOpened(false);
        services_read.set(GlobelMainMenu.selected_index_services, s1);


        ServicesModel s = (ServicesModel) adapter.getItemAtPosition(position);
        if (s.isOpened()) {
            s.setOpened(false);
        } else {
            s.setOpened(true);
        }
        GlobelMainMenu.selected_index_services = position;
        services_read.set(position, s);
        this.adapter.setService_search(services_read);
        this.adapter.notifyDataSetChanged();

        Log.e("", ".....on item click listener in adapter... position = " + position + ".....id......" + s.getId() + "......... " + s.isOpened() + "  .... title ......" + s.getFacilityType());
        Log.e("", ".....on item click listener in adapter... GlobelMainMenu.selected_index_services = " + GlobelMainMenu.selected_index_services + ".....id......" + s.getId() + "......... " + s.isOpened() + "  .... title ......" + s.getFacilityType());
    }
    //endregion*/

    @Override
    public void onDataReceived(ArrayList<ServicesModel> servicesModels) {
        services_read = servicesModels;
        search_array = services_read;
        searchResults = services_read;
        displayIndex();

        adapter = new ServiceAdapter(getApplicationContext(), this, R.layout.list_item, services_read);
        list_view.setAdapter(adapter);
//        list_view.setOnItemClickListener(this);

        adapter_search = new ServiceAdapter(getApplicationContext(), this, R.layout.list_item_shop, searchResults);
        list_view_search.setAdapter(adapter_search);
        list_view_search.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> ad, View row, int position, long index) {

                Log.e("", ".....on item click listener in adapter... position = " + position + "..... index ........" + index);
                ServicesModel s = (ServicesModel) ad.getItemAtPosition(position);
                if (s.isOpened()) {
                    s.setOpened(false);
                } else {
                    s.setOpened(true);
                }
                searchResults.set(position, s);
                adapter_search.setService_search(searchResults);
                adapter_search.notifyDataSetChanged();
                //".....id......"+s.getId()+"......... "+ s.isOpened()+"  .... title ......"+ s.getTitle());

            }
        });

    }

    @Override
    public void OnError() {

    }
}