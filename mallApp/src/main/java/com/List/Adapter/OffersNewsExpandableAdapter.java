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

import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.View.OffersDetailActivity;
import com.mallapp.View.R;
import com.mallapp.cache.AppCacheManager;
import com.mallapp.utils.GlobelOffersNews;

public class OffersNewsExpandableAdapter extends BaseExpandableListAdapter {

	private Context 			_context;
	private Activity			activity;
	private MallActivitiesModel offer_obj;
	//public ImageLoader 			imageLoader;
	private HashMap<String, ArrayList<MallActivitiesModel>>  offer_all_audience;
	private ArrayList<String> _listDataHeader;
	
	
	
    public OffersNewsExpandableAdapter(Context context, Activity	activity,
    		HashMap<String, ArrayList<MallActivitiesModel>> listChildData,
    		ArrayList<String> header ) {
    	this._context 	= context;
    	this.activity	= activity;
    	this.offer_all_audience	= listChildData;
    	this._listDataHeader	= header;
    	//imageLoader				= new ImageLoader(activity.getApplicationContext());
    }

    
    
    
    @Override
    public MallActivitiesModel getChild(int groupPosition, int childPosititon) {
    	MallActivitiesModel rest= offer_all_audience.get(_listDataHeader.get(groupPosition)).get(childPosititon);
    	if(rest!= null)
    		return rest;
    	
		return null;
    }

    
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    
    static class ViewHolder {
		TextView title, decs, center_name, shome_name;
		ImageButton is_fav;
		ImageView back_image;
	}
    
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
            	boolean isLastChild, View convertView, ViewGroup parent) {
    	
    	View view= convertView;
    	final ViewHolder holder;
    	
        if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	view = infalInflater.inflate(R.layout.list_item_offers, parent, false);
        	holder = new ViewHolder();
			holder.title 		= (TextView) view.findViewById(R.id.title);
			holder.decs 		= (TextView) view.findViewById(R.id.center_city);
			holder.center_name 	= (TextView) view.findViewById(R.id.center_name);
			holder.shome_name 	= (TextView) view.findViewById(R.id.shop_name);
			holder.is_fav		= (ImageButton) view.findViewById(R.id.fav_center);
			holder.back_image	= (ImageView) view.findViewById(R.id.center_image);

			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
        offer_obj	= getChild(groupPosition, childPosition);
        
        holder.title.setText(offer_obj.getActivityName());
		holder.decs.setText(offer_obj.getBriefText());
		holder.center_name.setText(offer_obj.getStartDate());
		holder.shome_name.setText(offer_obj.getMallName());
		
		//imageLoader.DisplayImage(AppConstants.PREF_URI_KEY, holder.back_image);
		
		final boolean fav	= offer_obj.isFav();
		if(fav)
			holder.is_fav.setImageResource(R.drawable.offer_fav_p);
		else
			holder.is_fav.setImageResource(R.drawable.offer_fav_r);
		
		
		holder.is_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				offer_obj= getChild(groupPosition, childPosition);
				if(!offer_obj.isFav()){
					holder.is_fav.setImageResource(R.drawable.offer_fav_p);
					offer_obj.setFav(true);
					AppCacheManager.updateOffersNews(_context, offer_obj,childPosition);
				}else{
					holder.is_fav.setImageResource(R.drawable.offer_fav_r);
					offer_obj.setFav(false);
					AppCacheManager.updateOffersNews(_context, offer_obj,childPosition);
				}
			}
		});
		
		
		
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				
				offer_obj= getChild(groupPosition, childPosition);
				GlobelOffersNews.offer_obj_mall= offer_obj;
				Intent intent= new Intent(activity, OffersDetailActivity.class);
				intent.putExtra(Offers_News_Constants.MALL_OBJECT,offer_obj);
				intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);                     
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				activity.getApplication().startActivity(intent);
			}
		});
		
        
        return view;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.offer_all_audience.get(_listDataHeader.get(groupPosition)).size();//(this._listDataHeader.get(groupPosition).getTitle()).size();
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
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_shop_expand_header, parent, false);
        }
    	
    	TextView header = (TextView) convertView.findViewById(R.id.heading);
    	TextView count 	= (TextView) convertView.findViewById(R.id.count);
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

