package com.mallapp.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.List.Adapter.OfferNewsSearchAdapter;
import com.List.Adapter.OffersNewsExpandableAdapter;
import com.List.Adapter.RestaurantExpandableAdapter;
import com.List.Adapter.RestaurantSearchAdapter;
import com.List.Adapter.ShopExpandableAdapter;
import com.List.Adapter.ShopSearchAdapter;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.FavouriteCentersFiltration;
import com.mallapp.Controllers.OffersNewsList;
import com.mallapp.Controllers.RestaurantFiltration;
import com.mallapp.Controllers.RestaurantList;
import com.mallapp.Controllers.ShopFiltration;
import com.mallapp.Controllers.ShopList;
import com.mallapp.Model.Offers_News;
import com.mallapp.Model.Restaurant;
import com.mallapp.Model.Shops;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.globel.GlobelShops;
import com.mallapp.layouts.SegmentedRadioGroup;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.Log;

public class FavouritesMainMenuActivity extends Activity  
										implements 	OnCheckedChangeListener, OnClickListener {

	
	String TAG = getClass().getCanonicalName();
	public static Handler uihandler;
	SegmentedRadioGroup segmentText;
	private ImageButton open_navigation;
	
	private ExpandableListView 	list_view;
	private ListView 			list_view_search;
	
	private EditText 	search_feild;
	private Button 		cancel_search;
	private TextView	heading;
	
//	ShopAdapter 		adapter;
	
	ShopExpandableAdapter adapter_S;
	RestaurantExpandableAdapter adapter_R;
	OffersNewsExpandableAdapter adapter_O;
	
	
	ShopSearchAdapter 		adapter_search_S;
	RestaurantSearchAdapter adapter_search_R;
	OfferNewsSearchAdapter 	adapter_search_O;
	
	
	LinearLayout 		side_index_layout;
	//LinearLayout 		side_index_scroll;

	String audienceFilter = Offers_News_Constants.AUDIENCE_FILTER_OFFERS;
	static ArrayList <Shops>  shops_read_audience, searchResults, search_array;
	//static HashMap<String, ArrayList<Shops>>  shops_all_audience ;
	//static HashMap<String, ArrayList<Shops>> shops_category_audience,  shops_floor_audience;
	
	static ArrayList <Restaurant>  restaurant_read_audience, r_searchResults,r_search_array;
	
	static ArrayList <Offers_News> offers_read_audience,  o_searchResults,o_search_array;
	
	static HashMap<String, ArrayList<Restaurant>> 	r_category_audience ;
	static HashMap<String, ArrayList<Shops>> 		shops_category_audience;
	static HashMap<String, ArrayList<Offers_News>> 	offer_category_audience, news_category_audience ;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		readOffersNewsList();
		readShopList();
		readRestaurantsList();
		
		setContentView(R.layout.favourite_main_menu);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		uihandler= MainMenuConstants.uiHandler;
		
		init();
		initArrays();
		
		filterShops();
		filterRestaurants();
		filterOffers();
		filterNews();
		
		initExpandableList();
		
//		adapter_O		= new OffersNewsExpandableAdapter(getApplicationContext(),this, offer_category_audience, GlobelOffersNews.header_section_offer);
		list_view.setAdapter(adapter_O);

		
		
		
		
//		adapter_search_O= new OfferNewsSearchAdapter(getApplicationContext(),this, R.layout.list_item_shop, o_searchResults);
		list_view_search.setAdapter(adapter_search_O);
		
		
		search_feild.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int start, int before, int count) {
				
				String searchString = cs.toString().trim();
				Log.e(TAG, ""+searchString);
				int textLength = searchString.length();
				
				if(textLength>0){
					
					Log.e(TAG, "hide existing lists = "+audienceFilter);
					cancel_search.setTextColor(getResources().getColor(R.color.purple));
					list_view.	setVisibility(View.GONE);
					
					
					
					
					
					if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)){
						
						o_search_array = GlobelOffersNews.centered_filter_array;
						o_searchResults= GlobelOffersNews.centered_filter_array;
						
						if(o_search_array== null || o_search_array.size()==0)
							readOffersNewsList();
						o_searchResults = new ArrayList<Offers_News>();
						
						for(int i= 0; i< o_search_array.size(); i++){
							if(o_search_array.get(i).getType().toString().equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)){
								String name=o_search_array.get(i).getTitle().toString();
								
								if(textLength <= name.length()){
									if(searchString.equalsIgnoreCase(name.substring(0,textLength)))
										o_searchResults.add(o_search_array.get(i));
								}
							}
						}
						
						if(o_searchResults!=null && o_searchResults.size()>0) {
//							adapter_search_O= new OfferNewsSearchAdapter(getApplicationContext(),FavouritesMainMenuActivity.this, R.layout.list_item_shop, o_searchResults);
							list_view_search.setAdapter(adapter_search_O);
							list_view_search.setVisibility(View.VISIBLE);
						
						}else{
							list_view.	setVisibility(View.VISIBLE);
						}
						
					}else if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)){
						
						o_search_array = GlobelOffersNews.centered_filter_array;
						o_searchResults= GlobelOffersNews.centered_filter_array;
						
						if(o_search_array== null || o_search_array.size()==0)
							readOffersNewsList();
						o_searchResults = new ArrayList<Offers_News>();
						
						for(int i= 0; i< o_search_array.size(); i++){
							if(o_search_array.get(i).getType().toString().equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)){
								String name=o_search_array.get(i).getTitle().toString();
								
								if(textLength <= name.length()){
									if(searchString.equalsIgnoreCase(name.substring(0,textLength)))
										o_searchResults.add(o_search_array.get(i));
								}
							}
						}
						
						if(o_searchResults!=null && o_searchResults.size()>0) {
//							adapter_search_O= new OfferNewsSearchAdapter(getApplicationContext(),FavouritesMainMenuActivity.this, R.layout.list_item_shop, o_searchResults);
							list_view_search.setAdapter(adapter_search_O);
							list_view_search.setVisibility(View.VISIBLE);
						
						}else{
							list_view.	setVisibility(View.VISIBLE);
						}
						
					}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_SHOPS)){
					
						search_array= GlobelShops.shop_array;
						if(search_array== null || search_array.size()==0){
							readShopList();
						}
						searchResults = new ArrayList<Shops>();
						
						for(int i= 0; i< search_array.size(); i++){
							//Log.e(TAG, "shop_name = .....get");
							String shop_name=search_array.get(i).getName().toString();
							//Log.e(TAG, "shop_name = ...."+ shop_name);
							if(textLength <= shop_name.length()){
								if(searchString.equalsIgnoreCase(shop_name.substring(0,textLength)))
									searchResults.add(search_array.get(i));
							}
						}
						
						if(searchResults!=null && searchResults.size()>0) {
//							adapter_search_S= new ShopSearchAdapter(getApplicationContext(),FavouritesMainMenuActivity.this, R.layout.list_item_shop, searchResults);
							list_view_search.setAdapter(adapter_search_S);
							list_view_search.setVisibility(View.VISIBLE);
						
						}else{
							list_view.	setVisibility(View.VISIBLE);
						}
					}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS)){
						
						r_search_array = GlobelRestaurants.restaurant_array;
						if(r_search_array== null || r_search_array.size()==0){
							readRestaurantsList();
						}
						r_search_array = new ArrayList<Restaurant>();
						
						for(int i= 0; i< r_search_array.size(); i++){
								
							String rest_name = r_search_array.get(i).getName().toString();
							if(textLength <= rest_name.length()){
								if(searchString.equalsIgnoreCase(rest_name.substring(0,textLength)))
									r_searchResults.add(r_search_array.get(i));
							}
						}
						if(r_searchResults!=null && r_searchResults.size()>0){
							adapter_search_R= new RestaurantSearchAdapter(getApplicationContext(),FavouritesMainMenuActivity.this, R.layout.list_item_shop, r_searchResults);
							list_view_search.setAdapter(adapter_search_R);
							list_view_search.setVisibility(View.VISIBLE);
							
						}else{
							list_view.	setVisibility(View.VISIBLE);
						}
					}
					
					
					
					
					
					
					
					
					
					//Log.e(TAG, "search_array = "+ GlobelShops.shop_array.size());
					//Log.e(TAG, "search_array = "+ search_array.size());
					
//					Log.e(TAG, "search_array = "+ search_array.size());
//					Log.e(TAG, "search results = "+searchResults.size());
//				
					
				
				}else{
					cancel_search.setTextColor(getResources().getColor(R.color.grey));
					Log.e(TAG, "view existing lists");
					list_view.	setVisibility(View.VISIBLE);
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
		
		list_view_search	= (ListView) 			findViewById(R.id.search_list_view);
		side_index_layout	= (LinearLayout) 		findViewById(R.id.side_index);
		list_view 			= (ExpandableListView) 	findViewById(R.id.expandableListView);
		heading.setText(getResources().getString(R.string.heading_favourite));
	}

	private void readShopList() {
		shops_read_audience= GlobelShops.shop_array;
		
		if(shops_read_audience == null || shops_read_audience.size() == 0){
			ShopList.saveOffersNewsData(getApplicationContext());
			shops_read_audience= ShopList.readShopsList(getApplicationContext());
			GlobelShops.shop_array= shops_read_audience;
			search_array= shops_read_audience;
			searchResults= shops_read_audience;
		}
		search_array	=  GlobelShops.shop_array;
		searchResults	=  GlobelShops.shop_array;
	}
	
	private void readRestaurantsList() {
		restaurant_read_audience= GlobelRestaurants.restaurant_array;
		
		if(restaurant_read_audience == null || restaurant_read_audience.size() == 0){
			RestaurantList.saveRestaurantData(getApplicationContext());
			restaurant_read_audience= RestaurantList.readRestaurantList(getApplicationContext());
			
			GlobelRestaurants.restaurant_array= restaurant_read_audience;
			r_search_array= restaurant_read_audience;
			r_searchResults= restaurant_read_audience;
		}
		r_search_array	=  GlobelRestaurants.restaurant_array;
		r_searchResults	=  GlobelRestaurants.restaurant_array;
	}

	
	private void readOffersNewsList() {
		
		offers_read_audience = GlobelOffersNews.centered_filter_array;
		if( offers_read_audience == null 
				||  offers_read_audience.size() == 0){
			
			offers_read_audience = OffersNewsList.readOffersNewsCentered(getApplicationContext());
			GlobelOffersNews.centered_filter_array= offers_read_audience;
		}
		o_search_array = GlobelOffersNews.centered_filter_array;
		o_searchResults= GlobelOffersNews.centered_filter_array;
		//Log.e("offers_read_audience ,...", "offers_read_audience ......"+ offers_read_audience.size());
	}


	private static int prev = -1;


	private void initExpandableList() {
		list_view.setHeaderDividersEnabled(true);
		list_view.setSelector(android.R.color.white);
		list_view.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				if(prev!=-1){
					list_view.collapseGroup(prev);    
				}
				prev=groupPosition;
			}
		});
		
		list_view.setGroupIndicator(getResources().getDrawable(R.drawable.my_group_statelist));
	}


	private void filterShops() {
		shops_category_audience= ShopFiltration.filterFavouriteShopsCategory(MainMenuConstants.AUDIENCE_FILTER_CATEGORY, shops_read_audience);
	}

	private void filterRestaurants() {
		r_category_audience	= RestaurantFiltration.filterFavouriteRestaurantCategory(MainMenuConstants.AUDIENCE_FILTER_CATEGORY, restaurant_read_audience);
	}
	
	private void filterOffers() {
		offer_category_audience= FavouriteCentersFiltration.filterFavouriteOffersCategory(offers_read_audience);
	}
	
	private void filterNews() {
		news_category_audience = FavouriteCentersFiltration.filterFavouriteNewsCategory(offers_read_audience);
	}
	
	
	

	private void initArrays() {
		r_category_audience 	= new HashMap<String, ArrayList<Restaurant>>();
		shops_category_audience	= new HashMap<String, ArrayList<Shops>>();
		offer_category_audience	= new HashMap<String, ArrayList<Offers_News>>();
		news_category_audience	= new HashMap<String, ArrayList<Offers_News>>();
	}

	Map<String, Integer> mapIndex;

	
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
	// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group == segmentText) {
			if (checkedId == R.id.button_one) {
				audienceFilter= Offers_News_Constants.AUDIENCE_FILTER_OFFERS;
			} else if (checkedId == R.id.button_two) {
				audienceFilter= Offers_News_Constants.AUDIENCE_FILTER_NEWS;
			} else if (checkedId == R.id.button_three) {
				audienceFilter= MainMenuConstants.AUDIENCE_FILTER_SHOPS;
			} else if (checkedId == R.id.button_three) {
				audienceFilter= MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS;
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
	

	private void callInOnResume() {

		readShopList();
		readRestaurantsList();
		readOffersNewsList();
		
		filterShops();
		filterRestaurants();
		filterOffers();
		filterNews();
		
		invisibleSearch();
		list_view.setVisibility(View.VISIBLE);
		
		if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)){
		
//			adapter_O= new OffersNewsExpandableAdapter(getApplicationContext(),this, offer_category_audience, GlobelOffersNews.header_section_offer);
			list_view.setAdapter(adapter_O);
			
		}else if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)){
		
//			adapter_O= new OffersNewsExpandableAdapter(getApplicationContext(),this, news_category_audience, GlobelOffersNews.header_section_news);
			list_view.setAdapter(adapter_O);
			
		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_SHOPS)){
		
//			adapter_S = new ShopExpandableAdapter(getApplicationContext(),this, shops_category_audience, GlobelShops.header_section_category);
			list_view.setAdapter(adapter_S);
			
		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS)){
		
			adapter_R = new RestaurantExpandableAdapter(getApplicationContext(),this, r_category_audience, GlobelRestaurants.header_section_category);
			list_view.setAdapter(adapter_R);
			
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
			DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
			finish();
	
		}else if(cancel_search.getId() == v.getId()){
			list_view_search.setVisibility(View.GONE);
			if(search_feild.getText().toString().length()>0){
			
				cancel_search.setTextColor(getResources().getColor(R.color.grey));
				search_feild.setText("");
				
				list_view.	setVisibility(View.VISIBLE);
				
//				if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)){
//					
//				}else if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)){
//					
//				}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_SHOPS)){
//				
//					
//				}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS)){
//					
//				}
			}else{
				
			}
		}
	}


}
