package com.List.Adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.foound.widget.AmazingAdapter;
import com.j256.ormlite.dao.Dao;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.Restaurant;
import com.mallapp.Model.RestaurantModel;
import com.mallapp.Model.ShopsModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;
import com.mallapp.View.RestaurantDetailActivity;
import com.mallapp.cache.RestaurantCacheManager;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.imagecapture.ImageLoader;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;

public class RestaurantAdapter extends AmazingAdapter {
	
	private Context context;
	HashMap<String, ArrayList<RestaurantModel>>  rest_all_audience;
	private ArrayList<String> _listDataHeader;
	private Activity	activity;
	String 				audience_type;
	private RestaurantModel 	rest_obj;
	public ImageLoader 	imageLoader;
	Dao<RestaurantModel, Integer> restaurantsDao;
	String url;
	String UserId;
	VolleyNetworkUtil volleyNetworkUtil;
	
	
	

	public RestaurantAdapter(Context context,Activity activity, 
			HashMap<String, ArrayList<RestaurantModel>> shops_all_audience,  ArrayList<String> header,
			String audience_type,Dao<RestaurantModel, Integer> restaurantsDao) {
		
		super();
		this.context 	= context;
		this.rest_all_audience = shops_all_audience;
		this._listDataHeader= header;
		this.activity 	= activity;
		this.audience_type = audience_type;
		imageLoader		= new ImageLoader(activity.getApplicationContext());
		this.restaurantsDao = restaurantsDao;
		UserId = SharedPreferenceUserProfile.getUserId(context);
		volleyNetworkUtil = new VolleyNetworkUtil(context);
	}

	public String getAudience_type() {
		return audience_type;
	}

	public void setAudience_type(String audience_type) {
		this.audience_type = audience_type;
		//Log.e("audience type", audience_type);
	}
	
	public RestaurantAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		int res = 0;
		for (int i = 0; i < rest_all_audience.size(); i++) {
			res += rest_all_audience.get(_listDataHeader.get(i)).size();
		}
		return res;
		//return 0;
	}

	@Override
	public RestaurantModel getItem(int position) {
		int c = 0;
		for (int i = 0; i < rest_all_audience.size(); i++) {
			if (position >= c && position < c + rest_all_audience.get(_listDataHeader.get(i)).size()) {
				return rest_all_audience.get(_listDataHeader.get(i)).get(position - c);//.second.get(position - c);
			}
			c += rest_all_audience.get(_listDataHeader.get(i)).size();
		}
		return null;
	}

	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	protected void onNextPageRequested(int page) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
		if (displaySectionHeader) {
			view.findViewById(R.id.section_header).setVisibility(View.VISIBLE);
			TextView lSectionTitle = (TextView) view.findViewById(R.id.section_header);
			lSectionTitle.setText(""+getSections()[getSectionForPosition(position)]);
		} else {
			view.findViewById(R.id.section_header).setVisibility(View.GONE);
		}
	}
	
	
	
	static class ViewHolder {
		TextView title, decs, floor_no;
		ImageButton is_fav;
		ImageView back_image;
		//RelativeLayout r1;
	}
	

	@Override
	public View getAmazingView(final int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		final ViewHolder holder;
		
		if (convertView == null) {
			view = activity.getLayoutInflater().inflate(R.layout.list_item_shop_sectionheader, parent, false );
			holder = new ViewHolder();
			holder.title 		= (TextView) view.findViewById(R.id.title);
			holder.decs 		= (TextView) view.findViewById(R.id.decs);
			holder.floor_no 	= (TextView) view.findViewById(R.id.floor_no);
			holder.is_fav		= (ImageButton)view.findViewById(R.id.fav_center);
			holder.back_image	= (ImageView) view.findViewById(R.id.center_image);

			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		
		rest_obj = getItem(position);
		holder.title.setText(rest_obj.getRestaurantName());
		holder.decs.setText(rest_obj.getBriefText());
		holder.floor_no.setText(rest_obj.getFloor());
		Picasso.with(context).load(rest_obj.getLogoURL()).into(holder.back_image);




		final boolean fav	= rest_obj.isFav();
		if(fav)
			holder.is_fav.setImageResource(R.drawable.offer_fav_p);
		else
			holder.is_fav.setImageResource(R.drawable.offer_fav);
		
		
		holder.is_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				rest_obj= getItem(position);
				if(!rest_obj.isFav()){
					holder.is_fav.setImageResource(R.drawable.offer_fav_p);
					rest_obj.setFav(true);
					updateRestaurants(rest_obj);
					url = ApiConstants.POST_FAV_RESTAURANT_URL_KEY+UserId+"&EntityId="+rest_obj.getMallResturantId()+"&IsRestaurant=true"+"&IsDeleted=false";
					volleyNetworkUtil.PostFavRestaurant(url);
//					RestaurantCacheManager.updateRestaurant(context, rest_obj, "");
				}else{
					holder.is_fav.setImageResource(R.drawable.offer_fav);
					rest_obj.setFav(false);
					updateRestaurants(rest_obj);
					url = ApiConstants.POST_FAV_RESTAURANT_URL_KEY+UserId+"&EntityId="+rest_obj.getMallResturantId()+"&IsRestaurant=true"+"&IsDeleted=true";
					volleyNetworkUtil.PostFavRestaurant(url);
//					RestaurantCacheManager.updateRestaurant(context, rest_obj, "");
				}
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				rest_obj= getItem(position);
				GlobelRestaurants.rest_obj_model= rest_obj;
				Intent intent= new Intent(activity, RestaurantDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("MallStoreId", rest_obj.getMallResturantId());
				activity.getApplication().startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public void configurePinnedHeader(View header, int position, int alpha) {
		TextView lSectionHeader = (TextView)header;
		lSectionHeader.setText("" + getSections()[getSectionForPosition(position)]);
		//lSectionHeader.setBackgroundColor(alpha << 24 | (0xbbffbb));
		lSectionHeader.setTextColor(alpha << 24 | (0x000000));
	}

	@Override
	public int getPositionForSection(int section) {
		if (section < 0) section = 0;
		if (section >= rest_all_audience.size()) section = rest_all_audience.size() - 1;
		int c = 0;
		for (int i = 0; i < rest_all_audience.size(); i++) {
			if (section == i) {
				return c;
			}
			c += rest_all_audience.get(_listDataHeader.get(i)).size();
		}
		return 0;
	}


	@Override
	public int getSectionForPosition(int position) {
		int c = 0;
		for (int i = 0; i < rest_all_audience.size(); i++) {
			if (position >= c && position < c + rest_all_audience.get(_listDataHeader.get(i)).size()) {
				return i;
			}
			c += rest_all_audience.get(_listDataHeader.get(i)).size();
		}
		return -1;
	}

	@Override
	public Object[] getSections() {
		String[] res = new String[rest_all_audience.size()];
		for (int i	 = 0; i < rest_all_audience.size(); i++) {
			res[i]	 = this._listDataHeader.get(i);
		}
		return res;
	}


	public void updateRestaurants(RestaurantModel fav){
		try {
			restaurantsDao.createOrUpdate(fav);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
