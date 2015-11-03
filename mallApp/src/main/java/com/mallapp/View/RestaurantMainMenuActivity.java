package com.mallapp.View;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Controllers.RestaurantFiltration;
import com.mallapp.Controllers.RestaurantList;
import com.mallapp.Controllers.ShopFiltration;
import com.mallapp.Model.Restaurant;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.layouts.SegmentedRadioGroup;
import com.mallapp.utils.InputHandler;


public class RestaurantMainMenuActivity extends Activity 
										implements 	OnCheckedChangeListener,
													OnClickListener {
	
	String TAG = getClass().getCanonicalName();
	//public static Handler uihandler;
	//static Context context;
	SegmentedRadioGroup segmentText;
	private ImageButton open_navigation;
	
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
	static ArrayList <Restaurant>  restaurant_read_audience, searchResults,search_array;
	static HashMap<String, ArrayList<Restaurant>>  all_audience ;
	static HashMap<String, ArrayList<Restaurant>> category_audience,  floor_audience;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		readRestaurantsList();
		setContentView(R.layout.shop_main_menu);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		init();
		filterRestaurants();
		displayIndex();
		initSectionHeaderList();
		initExpandableList();
		
		adapter_search= new RestaurantSearchAdapter(getApplicationContext(),this, R.layout.list_item_shop, searchResults);
		list_view_search.setAdapter(adapter_search);
		
		search_feild.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int start, int before, int count) {
			
				String searchString = cs.toString().trim();
				int textLength = searchString.length();
				
				if(textLength>0){
					
					cancel_search.setTextColor(getResources().getColor(R.color.purple));
					list_view1.setVisibility(View.GONE);
					side_index_scroll.setVisibility(View.GONE);
					search_array = GlobelRestaurants.restaurant_array;
					if(search_array== null || search_array.size()==0){
						readRestaurantsList();
					}
					searchResults = new ArrayList<Restaurant>();
					
					for(int i= 0; i< search_array.size(); i++){
						
						String rest_name=search_array.get(i).getName().toString();
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
		cancel_search.setOnClickListener(this);
	}

	private void init() {
		open_navigation		= (ImageButton) 		findViewById(R.id.navigation);
		segmentText 		= (SegmentedRadioGroup) findViewById(R.id.segment_text);
		search_feild		= (EditText) 			findViewById(R.id.search_feild);
		heading				= (TextView) 			findViewById(R.id.heading);
		cancel_search		= (Button) 				findViewById(R.id.cancel_search);
		list_view			= (AmazingListView) 	findViewById(R.id.shop_list);
		list_view_search	= (ListView) 			findViewById(R.id.search_list_view);
		side_index_layout	= (LinearLayout) 		findViewById(R.id.side_index);
		side_index_scroll 	= (LinearLayout) 		findViewById(R.id.scroll_side_index);
		list_view1 			= (ExpandableListView) 	findViewById(R.id.expandableListView);
		heading.setText("RESTAURANTS");
	}

	private void readRestaurantsList() {
		restaurant_read_audience= GlobelRestaurants.restaurant_array;
		
		if(restaurant_read_audience == null || restaurant_read_audience.size() == 0){
			RestaurantList.saveRestaurantData(getApplicationContext());
			restaurant_read_audience= RestaurantList.readRestaurantList(getApplicationContext());
			
			GlobelRestaurants.restaurant_array= restaurant_read_audience;
			search_array= restaurant_read_audience;
			searchResults= restaurant_read_audience;
		}
		search_array	=  GlobelRestaurants.restaurant_array;
		searchResults	=  GlobelRestaurants.restaurant_array;
	}

	
	
	private static int prev = -1;
	
	
	private void initExpandableList() {
		list_view1.setHeaderDividersEnabled(true);
		list_view1.setSelector(android.R.color.white);
		list_view1.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				if(prev!=-1){
					list_view1.collapseGroup(prev);    
				}
				prev=groupPosition;
			}
		});
		
		list_view1.setGroupIndicator(getResources().getDrawable(R.drawable.my_group_statelist));
	}

	
	private void initSectionHeaderList() {
		list_view.setPinnedHeaderView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item_shop_header, list_view, false));
		
		adapter	= new RestaurantAdapter(getApplicationContext(), RestaurantMainMenuActivity.this, all_audience, 
							GlobelRestaurants.header_section_alphabetics, audienceFilter);
		
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
		all_audience 		= new HashMap<String, ArrayList<Restaurant>>();
		category_audience	= new HashMap<String, ArrayList<Restaurant>>();
		floor_audience		= new HashMap<String, ArrayList<Restaurant>>();
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
			
			adapter	= new RestaurantAdapter(getApplicationContext(), this, all_audience, GlobelRestaurants.header_section_alphabetics, audienceFilter);
			list_view.	setAdapter(adapter);
			list_view1.	setVisibility(View.GONE);
			side_index_scroll.	setVisibility(View.VISIBLE);
			
		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_CATEGORY)){
			
			invisibleIndexList();
			adapter1 = new RestaurantExpandableAdapter(getApplicationContext(),this, category_audience, GlobelRestaurants.header_section_category);
			list_view1.setAdapter(adapter1);
			
		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_FLOOR)){
			
			invisibleIndexList();
			adapter1 = new RestaurantExpandableAdapter(getApplicationContext(),this, floor_audience, GlobelRestaurants.header_section_floor);
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
		
		if(open_navigation.getId()== v.getId()){
			//DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
			finish();
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
}