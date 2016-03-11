package com.List.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mallapp.Application.MallApplication;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.View.R;
import com.mallapp.cache.CentersCacheManager;
import com.squareup.picasso.Picasso;

public class FavouriteCenterAdapter extends ArrayAdapter<FavouriteCentersModel>{

	//private static final String TAG = PhoneContactsArrayAdapter.class.getSimpleName();
	private ArrayList<FavouriteCentersModel> interst_;
	Context context;
	ImageLoader imageLoader = MallApplication.getInstance().getImageLoader();
	ImageView thumbNail;
	
	public FavouriteCenterAdapter(Context context, int textViewResourceId, 
						ArrayList<FavouriteCentersModel> objects) {
		super(context, textViewResourceId, objects);
		interst_= objects;
		this.context= context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getCount() {
		return interst_.size();
	}

	@Override
	public FavouriteCentersModel getItem(int position) {
		return this.interst_.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public int getPosition(FavouriteCentersModel item) {
		return super.getPosition(item);
	}
	
	static class ViewHolder {
		TextView center_title, center_city, center_add;
		ImageView is_center, center_image;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View view = convertView;
		
		if (view == null) {
		
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mInflater.inflate(R.layout.list_item_favourite ,parent, false);
			
			holder = new ViewHolder();
			holder.center_title = (TextView) view.findViewById(R.id.center_name);
			holder.center_add = (TextView) view.findViewById(R.id.center_add);
			holder.center_city 	= (TextView) view.findViewById(R.id.center_city);
			if (imageLoader == null)
				imageLoader = MallApplication.getInstance().getImageLoader();
			thumbNail = (ImageView) view.findViewById(R.id.center_image);
			holder.is_center	= (ImageView) view.findViewById(R.id.fav_center);
//			holder.center_image	= (ImageView) view.findViewById(R.id.center_image);
			view.setTag(holder);
			
		}else
			holder = (ViewHolder) view.getTag();
		
		
		FavouriteCentersModel fav_obj= getItem(position);
		holder.center_title.setText(fav_obj.getName());
		holder.center_add.setText(fav_obj.getPlaceName());
		holder.center_city.setText(fav_obj.getCityName());
		Picasso.with(context).load(fav_obj.getLogoUrl()).into(thumbNail);
//		int res = context.getResources().getIdentifier(image_nam, "drawable", context.getPackageName());
//		holder.center_image.setImageResource(res);
		
		boolean is_interts = fav_obj.isIsfav();
		if(is_interts){
			holder.is_center.setImageResource(R.drawable.offer_fav_p);
			holder.center_title.setTextColor(context.getResources().getColor(R.color.purple));
			holder.center_add.setTextColor(context.getResources().getColor(R.color.purple));
			holder.center_city.setTextColor(context.getResources().getColor(R.color.purple));
		}else{
			holder.is_center.setImageResource(R.drawable.offer_fav_r);
			holder.center_title.setTextColor(context.getResources().getColor(R.color.search_area));
			holder.center_add.setTextColor(context.getResources().getColor(R.color.search_area));
			holder.center_city.setTextColor(context.getResources().getColor(R.color.search_area));
		}

		if(fav_obj.isIsfav()){
			holder.is_center.setImageResource(R.drawable.offer_fav_p);
//			CentersCacheManager.updateCenters(context, fav_obj, position);
			holder.center_title.setTextColor(context.getResources().getColor(R.color.purple));
			holder.center_city.setTextColor(context.getResources().getColor(R.color.purple));
		}

		// set selected item
		LinearLayout ActiveItem = (LinearLayout) view;
		/*if (position == selectedItem)
		{
			ActiveItem
					.setBackgroundResource(R.drawable.abc_btn_borderless_material);

			// for focus on it
			int top = (ActiveItem == null) ? 0 : ActiveItem.getTop();
			((ListView) parent).setSelectionFromTop(position, top);
			fav_obj= getItem(position);
			if(fav_obj.isIsfav()){
//			fav_obj= getItem(position);
				fav_obj.setIsfav(true);
				holder.is_center.setImageResource(R.drawable.interest_p);
				CentersCacheManager.updateCenters(context, fav_obj,position);
				holder.center_title.setTextColor(context.getResources().getColor(R.color.purple));
				holder.center_city.setTextColor(context.getResources().getColor(R.color.purple));
//			}
		}*/
		
		holder.is_center.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FavouriteCentersModel fav_obj= getItem(position);
				if(fav_obj.isIsfav()){
					fav_obj.setIsfav(false);
					holder.is_center.setImageResource(R.drawable.offer_fav_r);
					holder.center_title.setTextColor(context.getResources().getColor(R.color.search_area));
					holder.center_city.setTextColor(context.getResources().getColor(R.color.search_area));
//					CentersCacheManager.updateCenters(context, fav_obj,position);
				}else{
					fav_obj.setIsfav(true);
					holder.is_center.setImageResource(R.drawable.offer_fav_p);
//					CentersCacheManager.updateCenters(context, fav_obj,position);
					holder.center_title.setTextColor(context.getResources().getColor(R.color.purple));
					holder.center_city.setTextColor(context.getResources().getColor(R.color.purple));
				}
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FavouriteCentersModel fav_obj= getItem(position);
				if(fav_obj.isIsfav()){
					fav_obj.setIsfav(false);
					holder.is_center.setImageResource(R.drawable.offer_fav_r);
					holder.center_title.setTextColor(context.getResources().getColor(R.color.search_area));
					holder.center_city.setTextColor(context.getResources().getColor(R.color.search_area));
//					CentersCacheManager.updateCenters(context, fav_obj,position);
				}else{
					fav_obj.setIsfav(true);
					holder.is_center.setImageResource(R.drawable.offer_fav_p);
//					CentersCacheManager.updateCenters(context, fav_obj,position);
					
					holder.center_title.setTextColor(context.getResources().getColor(R.color.purple));
					holder.center_city.setTextColor(context.getResources().getColor(R.color.purple));
				}
			}
		});
		
		
		return view;
	}
	private int selectedItem;

	public void setSelectedItem(int position) {
		selectedItem = position;
	}
}