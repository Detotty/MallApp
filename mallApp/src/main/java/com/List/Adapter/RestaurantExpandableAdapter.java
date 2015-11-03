package com.List.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
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
import com.mallapp.utils.Log;

public class RestaurantExpandableAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private Activity	activity;
	Restaurant rest_obj;
	public ImageLoader imageLoader;
	private HashMap<String, ArrayList<Restaurant>> rest_all_audience;
	private ArrayList<String> _listDataHeader;
	
	
	
    public RestaurantExpandableAdapter(Context context, Activity	activity,
    		HashMap<String, ArrayList<Restaurant>> listChildData,
    		ArrayList<String> header ) {
    	this._context 	= context;
    	this.activity	= activity;
    	this.rest_all_audience	= listChildData;
    	this._listDataHeader	= header;
    	imageLoader				= new ImageLoader(activity.getApplicationContext());
    }

    
    
    
    @Override
    public Restaurant getChild(int groupPosition, int childPosititon) {
    	Restaurant rest= rest_all_audience.get(_listDataHeader.get(groupPosition)).get(childPosititon);
    	if(rest!= null)
    		return rest;
    	
		return null;
    }

    
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    
    static class ViewHolder {
		TextView title, decs, floor_no;
		ImageButton is_fav;
		ImageView back_image;
		//RelativeLayout r1;
	}
    
    
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
            	boolean isLastChild, View convertView, ViewGroup parent) {
    	
    	View view= convertView;
    	final ViewHolder holder;
    	
        if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		
        rest_obj	= getChild(groupPosition, childPosition);
		
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
				rest_obj= getChild(groupPosition, childPosition);
				if(!rest_obj.isFav()){
					holder.is_fav.setImageResource(R.drawable.offer_fav_p);
					rest_obj.setFav(true);
					RestaurantCacheManager.updateRestaurant(_context, rest_obj, "");
				}else{
					holder.is_fav.setImageResource(R.drawable.offer_fav);
					rest_obj.setFav(false);
					RestaurantCacheManager.updateRestaurant(_context, rest_obj, "");
				}
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				rest_obj= getChild(groupPosition, childPosition);
				GlobelRestaurants.rest_obj= rest_obj;
				Intent intent= new Intent(activity, RestaurantDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				activity.getApplication().startActivity(intent);
			}
		});
		
        
        return view;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
    	Log.e("", "shops_all_audience   = " +groupPosition);
        return this.rest_all_audience.get(_listDataHeader.get(groupPosition)).size();//(this._listDataHeader.get(groupPosition).getTitle()).size();
    }
 
    @Override
    public String getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    
    	String headerTitle = (String) getGroup(groupPosition);
    	int count_ = getGroupCount();
    	if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater)
            		this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_shop_expand_header, parent, false);
        }
    	
    	TextView header = (TextView) convertView.findViewById(R.id.heading);
    	TextView count 	= (TextView) convertView.findViewById(R.id.count);
    	//lblListHeader.setTypeface(null, Typeface.BOLD);
    	header.setText(headerTitle);
    	count.setText(count_+" Shops");
    	
    	return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
