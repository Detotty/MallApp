package com.List.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mallapp.Model.FavouriteCenters;
import com.mallapp.View.R;
import com.mallapp.cache.CentersCacheManager;

public class FavouriteCenterAdapter extends ArrayAdapter<FavouriteCenters>{

	//private static final String TAG = PhoneContactsArrayAdapter.class.getSimpleName();
	private ArrayList<FavouriteCenters> interst_;
	Context context;
	
	public FavouriteCenterAdapter(Context context, int textViewResourceId, 
						ArrayList<FavouriteCenters> objects) {
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
	public FavouriteCenters getItem(int position) {
		return this.interst_.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public int getPosition(FavouriteCenters item) {
		return super.getPosition(item);
	}
	
	static class ViewHolder {
		TextView center_title, center_city;
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
			holder.center_city 	= (TextView) view.findViewById(R.id.center_city);
			holder.is_center	= (ImageView) view.findViewById(R.id.fav_center);
			holder.center_image	= (ImageView) view.findViewById(R.id.center_image);
			view.setTag(holder);
			
		}else
			holder = (ViewHolder) view.getTag();
		
		
		FavouriteCenters fav_obj= getItem(position);
		holder.center_title.setText(fav_obj.getCenter_title());
		holder.center_city.setText(fav_obj.getCenter_city());
		
		String image_nam = fav_obj.getCenter_image();
		int res = context.getResources().getIdentifier(image_nam, "drawable", context.getPackageName());
		holder.center_image.setImageResource(res);
		
		boolean is_interts = fav_obj.isIsfav();
		if(is_interts){
			holder.is_center.setImageResource(R.drawable.interest_p);
			holder.center_title.setTextColor(context.getResources().getColor(R.color.purple));
			holder.center_city.setTextColor(context.getResources().getColor(R.color.purple));
		}else{
			holder.is_center.setImageResource(R.drawable.interest);
			holder.center_title.setTextColor(context.getResources().getColor(R.color.search_area));
			holder.center_city.setTextColor(context.getResources().getColor(R.color.search_area));
		}
		
		holder.is_center.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FavouriteCenters fav_obj= getItem(position);
				if(fav_obj.isIsfav()){
					fav_obj.setIsfav(false);
					holder.is_center.setImageResource(R.drawable.interest);
					holder.center_title.setTextColor(context.getResources().getColor(R.color.search_area));
					holder.center_city.setTextColor(context.getResources().getColor(R.color.search_area));
					CentersCacheManager.updateCenters(context, fav_obj);
				}else{
					fav_obj.setIsfav(true);
					holder.is_center.setImageResource(R.drawable.interest_p);
					CentersCacheManager.updateCenters(context, fav_obj);
					
					holder.center_title.setTextColor(context.getResources().getColor(R.color.purple));
					holder.center_city.setTextColor(context.getResources().getColor(R.color.purple));
				}
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FavouriteCenters fav_obj= getItem(position);
				if(fav_obj.isIsfav()){
					fav_obj.setIsfav(false);
					holder.is_center.setImageResource(R.drawable.interest);
					holder.center_title.setTextColor(context.getResources().getColor(R.color.search_area));
					holder.center_city.setTextColor(context.getResources().getColor(R.color.search_area));
					CentersCacheManager.updateCenters(context, fav_obj);
				}else{
					fav_obj.setIsfav(true);
					holder.is_center.setImageResource(R.drawable.interest_p);
					CentersCacheManager.updateCenters(context, fav_obj);
					
					holder.center_title.setTextColor(context.getResources().getColor(R.color.purple));
					holder.center_city.setTextColor(context.getResources().getColor(R.color.purple));
				}
			}
		});
		
		
		return view;
	}
}