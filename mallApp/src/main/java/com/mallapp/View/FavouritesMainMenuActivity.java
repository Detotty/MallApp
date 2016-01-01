package com.mallapp.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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

import com.List.Adapter.OfferNewsSearchAdapter;
import com.List.Adapter.OffersNewsExpandableAdapter;
import com.List.Adapter.Offers_News_Adapter;
import com.List.Adapter.RestaurantExpandableAdapter;
import com.List.Adapter.RestaurantSearchAdapter;
import com.List.Adapter.ShopAdapter;
import com.List.Adapter.ShopExpandableAdapter;
import com.List.Adapter.ShopSearchAdapter;
import com.foound.widget.AmazingListView;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.FavouriteCentersFiltration;
import com.mallapp.Controllers.RestaurantFiltration;
import com.mallapp.Controllers.ShopFiltration;
import com.mallapp.Model.FavoritesModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.RestaurantModel;
import com.mallapp.Model.ShopsModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.globel.GlobelShops;
import com.mallapp.layouts.SegmentedRadioGroup;
import com.mallapp.listeners.FavoritesDataListener;
import com.mallapp.utils.AppUtils;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.Log;
import com.mallapp.utils.VolleyNetworkUtil;

public class FavouritesMainMenuActivity extends Activity  
										implements 	OnCheckedChangeListener, OnClickListener, FavoritesDataListener {

	
	String TAG = getClass().getCanonicalName();
	public static Handler uihandler;
	SegmentedRadioGroup segmentText;
	private ImageButton open_navigation;
	
	private ExpandableListView list_view1;
	private ListView list_view;

	private ListView 			list_view_search;
	
	private EditText 	search_feild;
	private Button 		cancel_search;
	private TextView	heading;
	
//	ShopAdapter 		adapter;
	
	ShopExpandableAdapter adapter_S;
	RestaurantExpandableAdapter adapter_R;
	OffersNewsExpandableAdapter adapter_O;
	Offers_News_Adapter adapter;


	ShopSearchAdapter 		adapter_search_S;
	RestaurantSearchAdapter adapter_search_R;
	OfferNewsSearchAdapter 	adapter_search_O;
	
	
	LinearLayout 		side_index_layout;
	//LinearLayout 		side_index_scroll;

	String audienceFilter = Offers_News_Constants.AUDIENCE_FILTER_OFFERS;
	static ArrayList <ShopsModel>  shops_read_audience, searchResults, search_array;
	//static HashMap<String, ArrayList<Shops>>  shops_all_audience ;
	//static HashMap<String, ArrayList<Shops>> shops_category_audience,  shops_floor_audience;
	
	static ArrayList <RestaurantModel>  restaurant_read_audience, r_searchResults,r_search_array;
	
	static ArrayList <MallActivitiesModel> offers_read_audience,  o_searchResults,o_search_array;
	static ArrayList <MallActivitiesModel> news_read_audience,  n_searchResults,n_search_array;

	static HashMap<String, ArrayList<RestaurantModel>> 	r_category_audience ;
	static HashMap<String, ArrayList<ShopsModel>> 		shops_category_audience;
	static HashMap<String, ArrayList<MallActivitiesModel>> 	offer_category_audience, news_category_audience ;

	VolleyNetworkUtil volleyNetworkUtil;
	String UserID, URL;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		
		setContentView(R.layout.favourite_main_menu);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		uihandler= MainMenuConstants.uiHandler;
		UserID  = SharedPreferenceUserProfile.getUserId(this);
		URL = ApiConstants.GET_USER_FAV_URL_KEY+ UserID;
		volleyNetworkUtil = new VolleyNetworkUtil(this);
		volleyNetworkUtil.GetUserFav(URL, this);
		init();
		initArrays();
		


		
		
		
		

		
		
		search_feild.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int start, int before, int count) {
				
				String searchString = cs.toString().trim();
				Log.e(TAG, ""+searchString);
				int textLength = searchString.length();
				
				if(textLength>0){
					
					Log.e(TAG, "hide existing lists = "+audienceFilter);
					cancel_search.setTextColor(getResources().getColor(R.color.purple));
					list_view1.	setVisibility(View.GONE);
					
					if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)){
						
						/*o_search_array = GlobelOffersNews.centered_filter_array;
						o_searchResults= GlobelOffersNews.centered_filter_array;*/
						
						if(o_search_array== null || o_search_array.size()==0)
							readOffersNewsList();
						o_searchResults = new ArrayList<MallActivitiesModel>();
						
						for(int i= 0; i< o_search_array.size(); i++){
							if(o_search_array.get(i).getEntityType().toString().equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)){
								String name=o_search_array.get(i).getActivityTextTitle().toString();
								
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
							list_view1.	setVisibility(View.VISIBLE);
						}
						
					}else if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)){
						
						/*o_search_array = GlobelOffersNews.centered_filter_array;
						o_searchResults= GlobelOffersNews.centered_filter_array;*/
						
						if(o_search_array== null || o_search_array.size()==0)
							readOffersNewsList();
						o_searchResults = new ArrayList<MallActivitiesModel>();
						
						for(int i= 0; i< o_search_array.size(); i++){
							if(o_search_array.get(i).getEntityType().toString().equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)){
								String name=o_search_array.get(i).getActivityTextTitle().toString();
								
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
							list_view1.	setVisibility(View.VISIBLE);
						}
						
					}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_SHOPS)){
					
						/*search_array= GlobelShops.shop_array;
						if(search_array== null || search_array.size()==0){
							readShopList();
						}*/
						searchResults = new ArrayList<ShopsModel>();
						
						for(int i= 0; i< search_array.size(); i++){
							//Log.e(TAG, "shop_name = .....get");
							String shop_name=search_array.get(i).getStoreName().toString();
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
							list_view1.	setVisibility(View.VISIBLE);
						}
					}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS)){
						
						/*r_search_array = GlobelRestaurants.restaurant_array;
						if(r_search_array== null || r_search_array.size()==0){
							readRestaurantsList();
						}*/
						r_search_array = new ArrayList<RestaurantModel>();
						
						for(int i= 0; i< r_search_array.size(); i++){
								
							String rest_name = r_search_array.get(i).getRestaurantName().toString();
							if(textLength <= rest_name.length()){
								if(searchString.equalsIgnoreCase(rest_name.substring(0,textLength)))
									r_searchResults.add(r_search_array.get(i));
							}
						}
						if(r_searchResults!=null && r_searchResults.size()>0){
//							adapter_search_R= new RestaurantSearchAdapter(getApplicationContext(),FavouritesMainMenuActivity.this, R.layout.list_item_shop, r_searchResults);
							list_view_search.setAdapter(adapter_search_R);
							list_view_search.setVisibility(View.VISIBLE);
							
						}else{
							list_view1.	setVisibility(View.VISIBLE);
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
					list_view1.	setVisibility(View.VISIBLE);
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
		list_view1 = (ExpandableListView) 	findViewById(R.id.expandableListView);
		list_view = (ListView) findViewById(R.id.shop_list);
		heading.setText(getResources().getString(R.string.heading_favourite));
	}

	private void readShopList() {
		/*shops_read_audience= GlobelShops.shop_array;
		
		if(shops_read_audience == null || shops_read_audience.size() == 0){
			ShopList.saveOffersNewsData(getApplicationContext());
			shops_read_audience= ShopList.readShopsList(getApplicationContext());
			GlobelShops.shop_array= shops_read_audience;
			search_array= shops_read_audience;
			searchResults= shops_read_audience;
		}
		search_array	=  GlobelShops.shop_array;
		searchResults	=  GlobelShops.shop_array;*/
	}
	
	private void readRestaurantsList() {
		/*restaurant_read_audience= GlobelRestaurants.restaurant_array;
		
		if(restaurant_read_audience == null || restaurant_read_audience.size() == 0){
			RestaurantList.saveRestaurantData(getApplicationContext());
			restaurant_read_audience= RestaurantList.readRestaurantList(getApplicationContext());
			
			GlobelRestaurants.restaurant_array= restaurant_read_audience;
			r_search_array= restaurant_read_audience;
			r_searchResults= restaurant_read_audience;
		}
		r_search_array	=  GlobelRestaurants.restaurant_array;
		r_searchResults	=  GlobelRestaurants.restaurant_array;*/
	}

	
	private void readOffersNewsList() {
		
		/*offers_read_audience = GlobelOffersNews.centered_filter_array;
		if( offers_read_audience == null 
				||  offers_read_audience.size() == 0){
			
			offers_read_audience = OffersNewsList.readOffersNewsCentered(getApplicationContext());
			GlobelOffersNews.centered_filter_array= offers_read_audience;
		}
		o_search_array = GlobelOffersNews.centered_filter_array;
		o_searchResults= GlobelOffersNews.centered_filter_array;*/
		//Log.e("offers_read_audience ,...", "offers_read_audience ......"+ offers_read_audience.size());
	}


	private static int prev = -1;


	private void initExpandableList() {
		list_view1.setHeaderDividersEnabled(true);
		list_view1.setSelector(android.R.color.white);
		list_view1.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				if (prev != -1) {
					list_view1.collapseGroup(prev);
				}
				prev = groupPosition;
			}
		});
		
		list_view1.setGroupIndicator(getResources().getDrawable(R.drawable.my_group_statelist));
	}


	private void filterShops() {
		if (shops_read_audience.size()>0)
		shops_category_audience= ShopFiltration.filterFavouriteShopsCat(MainMenuConstants.AUDIENCE_FILTER_CATEGORY, shops_read_audience);
	}

	private void filterRestaurants() {
		if (restaurant_read_audience.size()>0)
		r_category_audience	= RestaurantFiltration.filterFavouriteRestCategory(MainMenuConstants.AUDIENCE_FILTER_CATEGORY, restaurant_read_audience);
	}
	
	private void filterOffers() {
		if (offers_read_audience.size()>0)
		offer_category_audience= FavouriteCentersFiltration.filterFavouriteOffersCategory(offers_read_audience);
	}
	
	private void filterNews() {
		if (offers_read_audience.size()>0)
		news_category_audience = FavouriteCentersFiltration.filterFavouriteNewsCategory(offers_read_audience);
	}
	
	
	

	private void initArrays() {
		r_category_audience 	= new HashMap<String, ArrayList<RestaurantModel>>();
		shops_category_audience	= new HashMap<String, ArrayList<ShopsModel>>();
		offer_category_audience	= new HashMap<String, ArrayList<MallActivitiesModel>>();
		news_category_audience	= new HashMap<String, ArrayList<MallActivitiesModel>>();
		offers_read_audience = new ArrayList<>();
		news_read_audience = new ArrayList<>();
		shops_read_audience = new ArrayList<>();
		restaurant_read_audience = new ArrayList<>();

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
			} else if (checkedId == R.id.button_four) {
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

		/*readShopList();
		readRestaurantsList();
		readOffersNewsList();*/
		
		filterShops();
		filterRestaurants();
		filterOffers();
		filterNews();
		
		invisibleSearch();
		list_view1.setVisibility(View.VISIBLE);
		
		if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)){
			/*adapter_O= new OffersNewsExpandableAdapter(getApplicationContext(),this, offer_category_audience, GlobelOffersNews.header_section_offer);
			list_view1.setAdapter(adapter_O);*/
			adapter = new Offers_News_Adapter(getApplicationContext(), this, R.layout.list_item_offers_new,
					offers_read_audience, audienceFilter, null
			);
			list_view.setAdapter(adapter);
			list_view1.setVisibility(View.GONE);
			list_view.setVisibility(View.VISIBLE);

		}else if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)){
			adapter = new Offers_News_Adapter(getApplicationContext(), this, R.layout.list_item_offers_new,
					news_read_audience, audienceFilter, null
			);
			list_view.setAdapter(adapter);
			list_view1.setVisibility(View.GONE);
			list_view.setVisibility(View.VISIBLE);


		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_SHOPS)){
			adapter_S = new ShopExpandableAdapter(getApplicationContext(),this, shops_category_audience, GlobelShops.header_section_category,null);
			list_view1.setAdapter(adapter_S);
			list_view1.setVisibility(View.VISIBLE);
			list_view.setVisibility(View.GONE);

		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS)){
			adapter_R = new RestaurantExpandableAdapter(getApplicationContext(),this, r_category_audience, GlobelRestaurants.header_section_category,null);
			list_view1.setAdapter(adapter_R);
			list_view1.setVisibility(View.VISIBLE);
			list_view.setVisibility(View.GONE);

		}
	}


	private void invisibleSearch() {
		list_view_search.setVisibility(View.GONE);
		cancel_search.setTextColor(getResources().getColor(R.color.grey));
		search_feild.setText("");
	}


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

	private void initSectionHeaderList() {
//		list_view.setPinnedHeaderView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item_shop_header, list_view, false));
		/*adapter = new Offers_News_Adapter(getApplicationContext(), RestaurantMainMenuActivity.this, shops_all, GlobelShops.header_section_alphabetics, audienceFilter, shopsDao);
		list_view.setAdapter(adapter);*/
		adapter = new Offers_News_Adapter(getApplicationContext(), this, R.layout.list_item_offers_new,
				offers_read_audience, audienceFilter, null
		);
		list_view.setAdapter(adapter);
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
				
				list_view1.	setVisibility(View.VISIBLE);
				
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


	@Override
	public void onDataReceived(ArrayList<FavoritesModel> favoritesModels) {
		/*readOffersNewsList();
		readShopList();
		readRestaurantsList();

		filterShops();
		filterRestaurants();
		filterOffers();
		filterNews();*/

		for (FavoritesModel fav: favoritesModels
			 ) {
			if (fav.getEntityType().equals(Offers_News_Constants.ENTITY_TYPE_OFFER) ){
					offers_read_audience.add(AppUtils.CastToMallActivities(fav));
			}else if (  fav.getEntityType().equals(Offers_News_Constants.ENTITY_TYPE_NEWS)){
					news_read_audience.add(AppUtils.CastToMallActivities(fav));
			}else if (fav.getEntityType().equals(Offers_News_Constants.ENTITY_TYPE_SHOP)){
					shops_read_audience.add(AppUtils.CastToShopModel(fav));
			}else if (fav.getEntityType().equals(Offers_News_Constants.ENTITY_TYPE_RESTAURANT)){
					restaurant_read_audience.add(AppUtils.CastToRestaurantModel(fav));
			}else {

			}
		}
		search_array	=  shops_read_audience;
		searchResults	=  shops_read_audience;
		r_search_array= restaurant_read_audience;
		r_searchResults= restaurant_read_audience;
		o_search_array = offers_read_audience;
		o_searchResults= offers_read_audience;
		filterShops();
		filterRestaurants();
		filterOffers();
		filterNews();
//		displayIndex();
		initSectionHeaderList();
		initExpandableList();

		/*adapter_O		= new OffersNewsExpandableAdapter(getApplicationContext(),this, offer_category_audience, GlobelOffersNews.header_section_offer);
		list_view1.setAdapter(adapter_O);*/
		adapter_search_O= new OfferNewsSearchAdapter(getApplicationContext(),this, R.layout.list_item_shop, o_searchResults);
		list_view_search.setAdapter(adapter_search_O);
	}

	@Override
	public void OnError() {

	}
}
