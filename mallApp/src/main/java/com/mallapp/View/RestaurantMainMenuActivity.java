package com.mallapp.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.List.Adapter.RestaurantAdapter;
import com.List.Adapter.RestaurantExpandableAdapter;
import com.List.Adapter.RestaurantSearchAdapter;
import com.foound.widget.AmazingListView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Controllers.RestaurantFiltration;
import com.mallapp.Controllers.RestaurantList;
import com.mallapp.Controllers.ShopFiltration;
import com.mallapp.Model.Restaurant;
import com.mallapp.Model.RestaurantDetailModel;
import com.mallapp.Model.RestaurantModel;
import com.mallapp.Model.ShopsModel;
import com.mallapp.db.DatabaseHelper;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.layouts.SegmentedRadioGroup;
import com.mallapp.listeners.RestaurantDataListener;
import com.mallapp.utils.InputHandler;
import com.mallapp.utils.VolleyNetworkUtil;


public class RestaurantMainMenuActivity extends SlidingDrawerActivity
										implements 	OnCheckedChangeListener,RestaurantDataListener,
													OnClickListener {
	
	String TAG = getClass().getCanonicalName();
	//public static Handler uihandler;
	//static Context context;
	SegmentedRadioGroup segmentText;
	private ImageButton open_navigation,open_drawer;
	
	private AmazingListView 	list_view;
	private ExpandableListView 	list_view1;
	private ListView 			list_view_search;
	
	private EditText 	search_feild;
	private TextView	heading;
	private Button 		cancel_search;
	
	RestaurantAdapter 			adapter;
	RestaurantExpandableAdapter adapter1;
	RestaurantSearchAdapter 	adapter_search;
	
	private LinearLayout 	side_index_layout;
	private LinearLayout 	side_index_scroll;
	
	String audienceFilter = MainMenuConstants.AUDIENCE_FILTER_ALL;
	static ArrayList <RestaurantModel>  restaurant_read_audience, searchResults,search_array;
	static HashMap<String, ArrayList<RestaurantModel>>  all_audience ;
	static HashMap<String, ArrayList<RestaurantModel>> category_audience,  floor_audience;

	public static String mallPlaceId;
	VolleyNetworkUtil volleyNetworkUtil;
	private DatabaseHelper databaseHelper = null;
	Dao<RestaurantModel, Integer> restaurantDao;
	ArrayList<RestaurantModel> dbList = new ArrayList<>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		readRestaurantsList();
		setContentView(R.layout.shop_main_menu);
		mallPlaceId = getIntent().getStringExtra("MallPlaceId");
		String url = ApiConstants.GET_RESTAURANT_URL_KEY + mallPlaceId;
		volleyNetworkUtil = new VolleyNetworkUtil(this);
		volleyNetworkUtil.GetRestaurant(url, this);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		init();
		/*filterRestaurants();
		displayIndex();
		initSectionHeaderList();
		initExpandableList();*/
		
		/*adapter_search= new RestaurantSearchAdapter(getApplicationContext(),this, R.layout.list_item_shop, s_searchResults);
		list_view_search.setAdapter(adapter_search);*/
		
		search_feild.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int start, int before, int count) {
			
				String searchString = cs.toString().trim();
				int textLength = searchString.length();
				
				if(textLength>0){
					
					cancel_search.setTextColor(getResources().getColor(R.color.purple));
					list_view1.setVisibility(View.GONE);
					side_index_scroll.setVisibility(View.GONE);
//					s_search_array = GlobelRestaurants.restaurant_array;
					if(search_array== null || search_array.size()==0){
						readRestaurantsList();
					}
					searchResults = new ArrayList<RestaurantModel>();
					
					for(int i= 0; i< search_array.size(); i++){
						
						String rest_name=search_array.get(i).getRestaurantName().toString();
						if(textLength <= rest_name.length()){
							if(searchString.equalsIgnoreCase(rest_name.substring(0,textLength)))
								searchResults.add(search_array.get(i));
						}
					}
					if(searchResults!=null && searchResults.size()>0){
						adapter_search.setRest_search(searchResults);
						list_view_search.setVisibility(View.VISIBLE);
						adapter_search.notifyDataSetChanged();
					
					}else{
						if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){
							side_index_scroll.	setVisibility(View.VISIBLE);
						}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)
								|| audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)){
							list_view1.	setVisibility(View.VISIBLE);
						}
					}
				}else{
					//cancel_search.setTextColor(getResources().getColor(R.color.grey));
					if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){
						side_index_scroll.	setVisibility(View.VISIBLE);
					}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)
							|| audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)){

						list_view1.	setVisibility(View.VISIBLE);
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
		open_navigation		= (ImageButton) 		findViewById(R.id.navigation);
		open_drawer		= (ImageButton) 		findViewById(R.id.navigation_drawer);
		segmentText 		= (SegmentedRadioGroup) findViewById(R.id.segment_text);
		search_feild		= (EditText) 			findViewById(R.id.search_feild);
		search_feild.setHint(R.string.restaurant_search_hint);
		heading				= (TextView) 			findViewById(R.id.heading);
		cancel_search		= (Button) 				findViewById(R.id.cancel_search);
		list_view			= (AmazingListView) 	findViewById(R.id.shop_list);
		list_view_search	= (ListView) 			findViewById(R.id.search_list_view);
		side_index_layout	= (LinearLayout) 		findViewById(R.id.side_index);
		side_index_scroll 	= (LinearLayout) 		findViewById(R.id.scroll_side_index);
		list_view1 			= (ExpandableListView) 	findViewById(R.id.expandableListView);
		heading.setText("RESTAURANTS");

		try {
			// This is how, a reference of DAO object can be done
			restaurantDao = getHelper().getRestaurantsDao();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}

	private void readRestaurantsList() {
		/*restaurant_read_audience= GlobelRestaurants.restaurant_array;
		
		if(restaurant_read_audience == null || restaurant_read_audience.size() == 0){
			RestaurantList.saveRestaurantData(getApplicationContext());
			restaurant_read_audience= RestaurantList.readRestaurantList(getApplicationContext());
			
			GlobelRestaurants.restaurant_array= restaurant_read_audience;
			s_search_array= restaurant_read_audience;
			s_searchResults= restaurant_read_audience;
		}
		s_search_array	=  GlobelRestaurants.restaurant_array;
		s_searchResults	=  GlobelRestaurants.restaurant_array;*/
	}

	
	
	private static int prev = -1;
	
	
	private void initExpandableList() {
		list_view1.setHeaderDividersEnabled(true);
		list_view1.setSelector(android.R.color.white);
		list_view1.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				/*if(prev!=-1){
					list_view1.collapseGroup(prev);    
				}
				prev=groupPosition;*/
			}
		});
		
		list_view1.setGroupIndicator(getResources().getDrawable(R.drawable.my_group_statelist));
	}

	
	private void initSectionHeaderList() {
		list_view.setPinnedHeaderView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item_shop_header, list_view, false));
		
		adapter	= new RestaurantAdapter(getApplicationContext(), RestaurantMainMenuActivity.this, all_audience, 
							GlobelRestaurants.header_section_alphabetics, audienceFilter,restaurantDao);
		
		list_view.setAdapter(adapter);
	}

	
	private void filterRestaurants() {
		initArrays();
		if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){
			all_audience 		= RestaurantFiltration.filterFavouriteRestaurantAlphabetically(audienceFilter, restaurant_read_audience);
		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)){
			category_audience	= RestaurantFiltration.filterFavouriteRestaurantCategory(audienceFilter, restaurant_read_audience);
		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)){
			floor_audience		= RestaurantFiltration.filterFavouriteRestaurantFloor(audienceFilter, restaurant_read_audience);
		}
	}

	
	private void initArrays() {
		all_audience 		= new HashMap<String, ArrayList<RestaurantModel>>();
		category_audience	= new HashMap<String, ArrayList<RestaurantModel>>();
		floor_audience		= new HashMap<String, ArrayList<RestaurantModel>>();
	}
	
	Map<String, Integer> mapIndex;
	
	private void displayIndex() {
		mapIndex= ShopFiltration.getIndexList(getResources());
		TextView textView;
		List<String> indexList = new ArrayList<String>(mapIndex.keySet());
		for (String index : indexList) {
			textView = (TextView) getLayoutInflater().inflate( R.layout.list_item_side_index, side_index_layout, false);
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
		Toast.makeText(getApplicationContext(), ""+selectedIndex.getText(), Toast.LENGTH_SHORT).show();
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
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
		super.onResume();
	}

	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group == segmentText) {
			if (checkedId == R.id.button_one) {
				audienceFilter= MainMenuConstants.AUDIENCE_FILTER_ALL;
			} else if (checkedId == R.id.button_two) {
				audienceFilter= MainMenuConstants.AUDIENCE_FILTER_CATEGORY;
			} else if (checkedId == R.id.button_three) {
				audienceFilter= MainMenuConstants.AUDIENCE_FILTER_FLOOR;
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
		readRestaurantsList();
		filterRestaurants();
		invisibleSearch();
		
		if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){
			
			adapter	= new RestaurantAdapter(getApplicationContext(), this, all_audience, GlobelRestaurants.header_section_alphabetics, audienceFilter,restaurantDao);
			list_view.	setAdapter(adapter);
			list_view1.	setVisibility(View.GONE);
			side_index_scroll.	setVisibility(View.VISIBLE);
			
		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)){
			
			invisibleIndexList();
			adapter1 = new RestaurantExpandableAdapter(getApplicationContext(),this, category_audience, GlobelRestaurants.header_section_category,restaurantDao);
			list_view1.setAdapter(adapter1);
			
		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)){
			
			invisibleIndexList();
			adapter1 = new RestaurantExpandableAdapter(getApplicationContext(),this, floor_audience, GlobelRestaurants.header_section_floor,restaurantDao);
			list_view1.setAdapter(adapter1);
		}
	}

	
	private void invisibleSearch() {
		list_view_search.setVisibility(View.GONE);
		cancel_search.setTextColor(getResources().getColor(R.color.grey));
		search_feild.setText("");
	}

	
	
	@Override
	public void onClick(View v) {
		
		if(open_navigation.getId()== v.getId()){
			//DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
			finish();
		}else if(open_drawer.getId() == v.getId()){
			SlidingDrawerActivity.uiHandler.sendEmptyMessage(1);
		}else if(cancel_search.getId() == v.getId()){

			list_view_search.setVisibility(View.GONE);
			if(search_feild.getText().toString().length()>0){
				
				cancel_search.setTextColor(getResources().getColor(R.color.grey));
				search_feild.setText("");
				InputHandler.hideSoftKeyboard(this);
				
				if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){
					side_index_scroll.setVisibility(View.VISIBLE);
				
				}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)
						|| audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)){
					
					list_view1.	setVisibility(View.VISIBLE);
				}
			}else{
				
			}
		}
	}

	@Override
	public void onDataReceived(ArrayList<RestaurantModel> restaurantModelArrayList) {
		try {
            /*if (shopsModelArrayList.size()>0)
            shopModel_read_audience = readShopsList(ShopMainMenuActivity.this);
            else*/
			getDBRestaurants();
//            shopModel_read_audience = shopsModelArrayList;

			if (dbList != null) {
				for (RestaurantModel rest : dbList
						) {
					for (int i = 0; i < restaurantModelArrayList.size(); i++) {
						RestaurantModel sh = restaurantModelArrayList.get(i);
						if (sh.getMallResturantId().equals(rest.getMallResturantId())) {
							if (rest.isFav()) {
								sh.setFav(true);
								restaurantModelArrayList.set(i, sh);
							}
						}
					}
				}
			}
			restaurant_read_audience = restaurantModelArrayList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}


		searchResults = restaurantModelArrayList;
		search_array = restaurantModelArrayList;

		adapter_search = new RestaurantSearchAdapter(getApplicationContext(), this, R.layout.list_item_shop, searchResults,restaurantDao);
		list_view_search.setAdapter(adapter_search);
		filterRestaurants();
		displayIndex();
		initSectionHeaderList();
		initExpandableList();
	}

	@Override
	public void onRestaurantDetailReceived(RestaurantDetailModel restaurantDetailModel) {

	}

	@Override
	public void OnError() {

	}

	public void getDBRestaurants() {
		try {
			// This is how, a reference of DAO object can be done
			Dao<RestaurantModel, Integer> studentDao = getHelper().getRestaurantsDao();
			// Get our query builder from the DAO
			final QueryBuilder<RestaurantModel, Integer> queryBuilder = studentDao.queryBuilder();
			// We need only Students who are associated with the selected Teacher, so build the query by "Where" clause
			// Prepare our SQL statement
			final PreparedQuery<RestaurantModel> preparedQuery = queryBuilder.prepare();
			// Fetch the list from Database by queryingit
			final Iterator<RestaurantModel> studentsIt = studentDao.queryForAll().iterator();
			// Iterate through the StudentDetails object iterator and populate the comma separated String
			while (studentsIt.hasNext()) {
				final RestaurantModel sDetails = studentsIt.next();
				dbList.add(sDetails);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}