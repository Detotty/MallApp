package com.mallapp.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.Toast;

import com.List.Adapter.OffersNewsExpandableAdapter;
import com.List.Adapter.Offers_News_Adapter;
import com.List.Adapter.RestaurantExpandableAdapter;
import com.List.Adapter.RestaurantSearchAdapter;
import com.List.Adapter.ShopExpandableAdapter;
import com.List.Adapter.ShopSearchAdapter;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
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
import com.mallapp.db.DatabaseHelper;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.globel.GlobelShops;
import com.mallapp.layouts.SegmentedRadioGroup;
import com.mallapp.listeners.FavoritesDataListener;
import com.mallapp.utils.AppUtils;
import com.mallapp.utils.Log;
import com.mallapp.utils.VolleyNetworkUtil;

public class FavouritesMainMenuActivity extends SlidingDrawerActivity
										implements 	OnCheckedChangeListener, OnClickListener, FavoritesDataListener {

	
	String TAG = getClass().getCanonicalName();
	public static Handler uihandler;
	SegmentedRadioGroup segmentText;
	private ImageButton open_navigation,open_drawer;
	
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
	Offers_News_Adapter 	adapter_search_O;
	
	
	LinearLayout 		side_index_layout;
	//LinearLayout 		side_index_scroll;

	String audienceFilter = Offers_News_Constants.AUDIENCE_FILTER_OFFERS;
	static ArrayList <ShopsModel>  shops_read_audience, s_searchResults, s_search_array;
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

	private DatabaseHelper databaseHelper = null;
	Dao<ShopsModel, Integer> shopsDao;
	Dao<RestaurantModel, Integer> restaurantsDao;
	Dao<MallActivitiesModel, Integer> mallActivitiesDao;

	LinearLayout error_layout, rootLayout;


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
		init();
		initArrays();
		volleyNetworkUtil = new VolleyNetworkUtil(this);
		volleyNetworkUtil.GetUserFav(URL, this);


		segmentText.setOnCheckedChangeListener(this);
		open_navigation.setOnClickListener(this);
		open_drawer.setOnClickListener(this);
		open_navigation.setVisibility(View.GONE);
		open_drawer.setVisibility(View.VISIBLE);
		cancel_search.setOnClickListener(this);
	}


	private void init() {

		error_layout	= (LinearLayout) findViewById(R.id.error_layout);
		rootLayout	= (LinearLayout) findViewById(R.id.layout_rootList);

		open_navigation		= (ImageButton) 		findViewById(R.id.back);
		open_drawer = (ImageButton) findViewById(R.id.navigation_drawer);
		segmentText 		= (SegmentedRadioGroup) findViewById(R.id.segment_text);
		search_feild		= (EditText) 			findViewById(R.id.search_feild);
		heading				= (TextView) 			findViewById(R.id.heading);
		cancel_search		= (Button) 				findViewById(R.id.cancel_search);
		
		list_view_search	= (ListView) 			findViewById(R.id.search_list_view);
		side_index_layout	= (LinearLayout) 		findViewById(R.id.side_index);
		list_view1 = (ExpandableListView) 	findViewById(R.id.expandableListView);
		list_view = (ListView) findViewById(R.id.shop_list);
		heading.setText(getResources().getString(R.string.heading_favourite));

		try {
			// This is how, a reference of DAO object can be done
			shopsDao = getHelper().getShopsDao();
			restaurantsDao = getHelper().getRestaurantsDao();
			mallActivitiesDao = getHelper().getMallActivitiesDao();

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
					offers_read_audience, audienceFilter, mallActivitiesDao
			);
			list_view.setAdapter(adapter);
			list_view1.setVisibility(View.GONE);
			list_view.setVisibility(View.VISIBLE);

		}else if(audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)){
			adapter = new Offers_News_Adapter(getApplicationContext(), this, R.layout.list_item_offers_new,
					news_read_audience, audienceFilter, mallActivitiesDao
			);
			list_view.setAdapter(adapter);
			list_view1.setVisibility(View.GONE);
			list_view.setVisibility(View.VISIBLE);


		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_SHOPS)){
			if (shops_category_audience.size()>0)
			adapter_S = new ShopExpandableAdapter(getApplicationContext(),this, shops_category_audience, GlobelShops.header_section_category,shopsDao);
			list_view1.setAdapter(adapter_S);
			list_view1.setVisibility(View.VISIBLE);
			list_view.setVisibility(View.GONE);

		}else if(audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS)){
			if (r_category_audience.size()>0)
				adapter_R = new RestaurantExpandableAdapter(getApplicationContext(),this, r_category_audience, GlobelRestaurants.header_section_category,restaurantsDao);
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
				offers_read_audience, audienceFilter, mallActivitiesDao
		);
		list_view.setAdapter(adapter);
	}
	
	
	@Override
	public void onClick(View v) {
	
		if(open_navigation.getId()== v.getId()){
			DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
			finish();
	
		}else if (open_drawer.getId() == v.getId()) {
			SlidingDrawerActivity.uiHandler.sendEmptyMessage(1);
		}else if(cancel_search.getId() == v.getId()){
			list_view_search.setVisibility(View.GONE);
			if(search_feild.getText().toString().length()>0){
			
				cancel_search.setTextColor(getResources().getColor(R.color.grey));
				search_feild.setText("");

				Log.e(TAG, "view existing lists");
				if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS) || audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_SHOPS))
					list_view1.	setVisibility(View.VISIBLE);
				if (audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS) || audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS))
					list_view.	setVisibility(View.VISIBLE);
				list_view_search.setVisibility(View.GONE);

			}else{
				
			}
		}
	}


	@Override
	public void onDataReceived(ArrayList<FavoritesModel> favoritesModels) {

		if (favoritesModels.size()>0){
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
			s_search_array =  shops_read_audience;
			s_searchResults =  shops_read_audience;
			r_search_array= restaurant_read_audience;
			r_searchResults= restaurant_read_audience;
			o_search_array = offers_read_audience;
			o_searchResults= offers_read_audience;
			n_search_array = news_read_audience;
			n_searchResults= news_read_audience;
			filterShops();
			filterRestaurants();
			filterOffers();
			filterNews();
//		displayIndex();
			initSectionHeaderList();
			initExpandableList();

		/*adapter_O		= new OffersNewsExpandableAdapter(getApplicationContext(),this, offer_category_audience, GlobelOffersNews.header_section_offer);
		list_view1.setAdapter(adapter_O);*/
			adapter_search_O= new Offers_News_Adapter(getApplicationContext(), FavouritesMainMenuActivity.this, R.layout.list_item_offers_new,
					o_searchResults, audienceFilter, mallActivitiesDao
			);
			list_view_search.setAdapter(adapter_search_O);
			searchFunc();
		}else{
			String serverError = context.getResources().getString(R.string.fav_error_message);
			Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void OnError() {
		error_layout.setVisibility(View.VISIBLE);
		rootLayout.setVisibility(View.GONE);
	}

	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}

	void searchFunc(){
		search_feild.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int start, int before, int count) {

				String searchString = cs.toString().trim();
				Log.e(TAG, "" + searchString);
				int textLength = searchString.length();

				if (textLength > 0) {

					Log.e(TAG, "hide existing lists = " + audienceFilter);
					cancel_search.setTextColor(getResources().getColor(R.color.purple));
					list_view1.setVisibility(View.GONE);

					if (audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)) {

						/*o_search_array = GlobelOffersNews.centered_filter_array;
						o_searchResults= GlobelOffersNews.centered_filter_array;*/

						/*if(o_search_array== null || o_search_array.size()==0)
							readOffersNewsList();*/
						o_searchResults = new ArrayList<MallActivitiesModel>();

						for (int i = 0; i < o_search_array.size(); i++) {
							if (o_search_array.get(i).getEntityType().toString().equals(Offers_News_Constants.ENTITY_TYPE_OFFER)) {
								String name = o_search_array.get(i).getActivityTextTitle().toString();

								if (textLength <= name.length()) {
									if (searchString.equalsIgnoreCase(name.substring(0, textLength)))
										o_searchResults.add(o_search_array.get(i));
								}
							}
						}

						if (o_searchResults != null && o_searchResults.size() > 0) {
							adapter_search_O = new Offers_News_Adapter(getApplicationContext(), FavouritesMainMenuActivity.this, R.layout.list_item_offers_new,
									o_searchResults, audienceFilter, mallActivitiesDao
							);
							list_view_search.setAdapter(adapter_search_O);
							list_view_search.setVisibility(View.VISIBLE);
							list_view.setVisibility(View.GONE);


						} else {
							list_view.setVisibility(View.VISIBLE);
						}

					} else if (audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)) {

						/*o_search_array = GlobelOffersNews.centered_filter_array;
						o_searchResults= GlobelOffersNews.centered_filter_array;*/

						/*if(o_search_array== null || o_search_array.size()==0)
							readOffersNewsList();*/
						n_searchResults = new ArrayList<MallActivitiesModel>();

						for (int i = 0; i < n_search_array.size(); i++) {
							if (n_search_array.get(i).getEntityType().toString().equals(Offers_News_Constants.ENTITY_TYPE_NEWS)) {
								String name = n_search_array.get(i).getActivityTextTitle().toString();

								if (textLength <= name.length()) {
									if (searchString.equalsIgnoreCase(name.substring(0, textLength)))
										n_searchResults.add(n_search_array.get(i));
								}
							}
						}

						if (n_searchResults != null && n_searchResults.size() > 0) {
							adapter_search_O = new Offers_News_Adapter(getApplicationContext(), FavouritesMainMenuActivity.this, R.layout.list_item_offers_new,
									n_searchResults, audienceFilter, mallActivitiesDao
							);
							list_view_search.setAdapter(adapter_search_O);
							list_view_search.setVisibility(View.VISIBLE);
							list_view.setVisibility(View.GONE);

						} else {
							list_view.setVisibility(View.VISIBLE);
						}

					} else if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_SHOPS)) {

						/*s_search_array= GlobelShops.shop_array;
						if(s_search_array== null || s_search_array.size()==0){
							readShopList();
						}*/
						s_searchResults = new ArrayList<ShopsModel>();

						for (int i = 0; i < s_search_array.size(); i++) {
							//Log.e(TAG, "shop_name = .....get");
							String shop_name = s_search_array.get(i).getStoreName().toString();
							//Log.e(TAG, "shop_name = ...."+ shop_name);
							if (textLength <= shop_name.length()) {
								if (searchString.equalsIgnoreCase(shop_name.substring(0, textLength)))
									s_searchResults.add(s_search_array.get(i));
							}
						}

						if (s_searchResults != null && s_searchResults.size() > 0) {
							adapter_search_S = new ShopSearchAdapter(getApplicationContext(), FavouritesMainMenuActivity.this, R.layout.list_item_shop, s_searchResults, shopsDao);
							list_view_search.setAdapter(adapter_search_S);
							list_view_search.setVisibility(View.VISIBLE);

						} else {
							list_view1.setVisibility(View.VISIBLE);
						}
					} else if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS)) {

						/*r_search_array = GlobelRestaurants.restaurant_array;
						if(r_search_array== null || r_search_array.size()==0){
							readRestaurantsList();
						}*/
						r_searchResults = new ArrayList<RestaurantModel>();

						for (int i = 0; i < r_search_array.size(); i++) {

							String rest_name = r_search_array.get(i).getRestaurantName().toString();
							if (textLength <= rest_name.length()) {
								if (searchString.equalsIgnoreCase(rest_name.substring(0, textLength)))
									r_searchResults.add(r_search_array.get(i));
							}
						}
						if (r_searchResults != null && r_searchResults.size() > 0) {
							adapter_search_R = new RestaurantSearchAdapter(getApplicationContext(), FavouritesMainMenuActivity.this, R.layout.list_item_shop, r_searchResults, restaurantsDao);
							list_view_search.setAdapter(adapter_search_R);
							list_view_search.setVisibility(View.VISIBLE);
						} else {
							list_view1.setVisibility(View.VISIBLE);
						}
					}


					//Log.e(TAG, "s_search_array = "+ GlobelShops.shop_array.size());
					//Log.e(TAG, "s_search_array = "+ s_search_array.size());

//					Log.e(TAG, "s_search_array = "+ s_search_array.size());
//					Log.e(TAG, "search results = "+s_searchResults.size());
//

				} else {
					cancel_search.setTextColor(getResources().getColor(R.color.grey));
					Log.e(TAG, "view existing lists");
					if (audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_RESTUARANTS) || audienceFilter.equals(MainMenuConstants.AUDIENCE_FILTER_SHOPS))
						list_view1.setVisibility(View.VISIBLE);
					if (audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS) || audienceFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS))
						list_view.setVisibility(View.VISIBLE);
					list_view_search.setVisibility(View.GONE);

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
	}


}
