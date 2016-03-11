package com.List.Adapter;

import java.sql.SQLException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.Shops;
import com.mallapp.Model.ShopsModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;
import com.mallapp.View.ShopDetailActivity;
import com.mallapp.View.ShopMainMenuActivity;
import com.mallapp.cache.ShopCacheManager;
import com.mallapp.globel.GlobelShops;
import com.mallapp.imagecapture.ImageLoader;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;

public class ShopSearchAdapter extends ArrayAdapter<ShopsModel>{

	//private static final String TAG = PhoneContactsArrayAdapter.class.getSimpleName();
	private ArrayList<ShopsModel> shop_search;
	Context context;
	Activity activity;
	public ImageLoader imageLoader;
	Dao<ShopsModel, Integer> shopsDao;
	String url;
	String UserId;
	VolleyNetworkUtil volleyNetworkUtil;
	
	public ShopSearchAdapter(Context context, Activity act, int textViewResourceId, ArrayList<ShopsModel> objects,Dao<ShopsModel, Integer> shopsDao) {
		super(context, textViewResourceId, objects);
		shop_search= objects;
		this.context= context;
		this.activity= act;
		imageLoader	= new ImageLoader(context);
		this.shopsDao = shopsDao;
		UserId = SharedPreferenceUserProfile.getUserId(context);
		volleyNetworkUtil = new VolleyNetworkUtil(context);
	}
	
	public ArrayList<ShopsModel> getShop_search() {
		return shop_search;
	}

	public void setShop_search(ArrayList<ShopsModel> shop_search) {
		this.shop_search = shop_search;
		//Log.e("", shop_search.size()+" setShop_search = shop_search");
	}

	@Override
	public int getCount() {
		return shop_search.size();
	}

	@Override
	public ShopsModel getItem(int position) {
		return this.shop_search.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public int getPosition(ShopsModel item) {
		return super.getPosition(item);
	}
	
	static class ViewHolder {
		TextView title, decs, floor_no;
		ImageButton is_fav;
		ImageView back_image;
		//RelativeLayout r1;
	}

	ShopsModel shop_obj;
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
    	
    	View view= convertView;
    	final ViewHolder holder;
    	
        if (convertView == null) {
			//LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	view = activity.getLayoutInflater().inflate(R.layout.list_item_shop, parent, false);
        	LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	view = infalInflater.inflate(R.layout.list_item_shop, parent, false);
        	holder = new ViewHolder();
			holder.title 		= (TextView) view.findViewById(R.id.title);
			holder.decs 		= (TextView) view.findViewById(R.id.decs);
			holder.floor_no 	= (TextView) view.findViewById(R.id.floor_no);
			holder.is_fav		= (ImageButton)view.findViewById(R.id.fav_center);
			holder.back_image	= (ImageView) view.findViewById(R.id.center_image);
			view.setTag(holder);
		}else {
			holder 	= (ViewHolder) view.getTag();
		}
		
        shop_obj	= getItem(position);
		
        holder.title.setText(shop_obj.getStoreName());
		holder.decs	.setText(shop_obj.getBriefText());
		holder.floor_no.setText(shop_obj.getFloor());
		Picasso.with(context).load(shop_obj.getLogoURL()).into(holder.back_image);
//		imageLoader.DisplayImage(AppConstants.PREF_URI_KEY, holder.back_image);
		
		final boolean fav	= shop_obj.isFav();
		if(fav)
			holder.is_fav.setImageResource(R.drawable.offer_fav_p);
		else
			holder.is_fav.setImageResource(R.drawable.offer_fav);
		
		
		holder.is_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				shop_obj= getItem(position);
				if(!shop_obj.isFav()){
					holder.is_fav.setImageResource(R.drawable.offer_fav_p);
					shop_obj.setFav(true);
//					updateShops(shop_obj);
					url = ApiConstants.POST_FAV_SHOP_URL_KEY+UserId+"&EntityId="+shop_obj.getMallStoreId()+"&IsShop=true"+"&IsDeleted=false";
					volleyNetworkUtil.PostFavShop(url);
				}else{
					holder.is_fav.setImageResource(R.drawable.offer_fav);
					shop_obj.setFav(false);
//					updateShops(shop_obj);
					url = ApiConstants.POST_FAV_SHOP_URL_KEY+UserId+"&EntityId="+shop_obj.getMallStoreId()+"&IsShop=true"+"&IsDeleted=true";
					volleyNetworkUtil.PostFavShop(url);
				}
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				shop_obj= getItem(position);
				GlobelShops.shopModel_obj= shop_obj;
				Intent intent= new Intent(activity, ShopDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				activity.getApplication().startActivity(intent);

			}
		});
        return view;
    }

	public void updateShops(ShopsModel fav){
		try {
			shopsDao.createOrUpdate(fav);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}