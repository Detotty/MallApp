package com.List.Adapter;

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

import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.Restaurant;
import com.mallapp.View.R;
import com.mallapp.View.RestaurantDetailActivity;
import com.mallapp.cache.RestaurantCacheManager;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.imagecapture.ImageLoader;

public class RestaurantSearchAdapter extends ArrayAdapter<Restaurant>{

	//private static final String TAG = PhoneContactsArrayAdapter.class.getSimpleName();
	private ArrayList<Restaurant> rest_search;
	Context context;
	Activity activity;
	public ImageLoader imageLoader;
	
	public RestaurantSearchAdapter(Context context, Activity act, int textViewResourceId, ArrayList<Restaurant> objects) {
		super(context, textViewResourceId, objects);
		rest_search= objects;
		this.context= context;
		this.activity= act;
		imageLoader	= new ImageLoader(context);
	}
	
	
	
	
	public ArrayList<Restaurant> getRest_search() {
		return rest_search;
	}

	public void setRest_search(ArrayList<Restaurant> rest_search) {
		this.rest_search = rest_search;
		//Log.e("", rest_search.size()+" setrest_search = rest_search");
	}

	@Override
	public int getCount() {
		return rest_search.size();
	}

	@Override
	public Restaurant getItem(int position) {
		return this.rest_search.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public int getPosition(Restaurant item) {
		return super.getPosition(item);
	}
	
	static class ViewHolder {
		TextView title, decs, floor_no;
		ImageButton is_fav;
		ImageView back_image;
		//RelativeLayout r1;
	}

	Restaurant rest_obj;
	
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
		
        rest_obj	= getItem(position);
        holder.title.setText(rest_obj.getName());
		holder.decs	.setText(rest_obj.getDescription());
		holder.floor_no.setText(rest_obj.getFloor_no());
		
		imageLoader.DisplayImage(AppConstants.PREF_URI_KEY, holder.back_image);
		
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
					RestaurantCacheManager.updateRestaurant(context, rest_obj, "");
				}else{
					holder.is_fav.setImageResource(R.drawable.offer_fav);
					rest_obj.setFav(false);
					RestaurantCacheManager.updateRestaurant(context, rest_obj, "");
				}
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				rest_obj= getItem(position);
				GlobelRestaurants.rest_obj= rest_obj;
				Intent intent= new Intent(activity, RestaurantDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				activity.getApplication().startActivity(intent);

			}
		});
        return view;
    }
}
