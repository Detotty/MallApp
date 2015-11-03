package com.List.Adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mallapp.Model.Interst_Selection;
import com.mallapp.View.R;
import com.mallapp.cache.InterestCacheManager;

@SuppressLint("InflateParams") 
public class InterestAdapter extends ArrayAdapter<Interst_Selection>{

	//private static final String TAG = PhoneContactsArrayAdapter.class.getSimpleName();
	private ArrayList<Interst_Selection> interst_;
	Context context;
	
	public InterestAdapter(Context context, int textViewResourceId, 
						ArrayList<Interst_Selection> objects) {
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
	public Interst_Selection getItem(int position) {
		return this.interst_.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public int getPosition(Interst_Selection item) {
		return super.getPosition(item);
	}
	
	static class ViewHolder {
		TextView interst_title;
		ImageView is_interest;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View view = convertView;
		
		if (view == null) {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mInflater.inflate(R.layout.list_item_interest, null);
			holder = new ViewHolder();
			holder.interst_title 	= (TextView) 	view.findViewById(R.id.interst_title);
			holder.is_interest		= (ImageView) 	view.findViewById(R.id.is_interst);
			view.setTag(holder);
			
		}else 
			holder = (ViewHolder) view.getTag();
		
		
		Interst_Selection interst_obj= getItem(position);
		holder.interst_title.setText(interst_obj.getInterest_title());
		boolean is_interts= interst_obj.isInterested();
		
		if(is_interts){
			holder.is_interest.setImageResource(R.drawable.interest_p);
			holder.interst_title.setTextColor(context.getResources().getColor(R.color.purple));
		}else{
			holder.is_interest.setImageResource(R.drawable.interest);
			holder.interst_title.setTextColor(context.getResources().getColor(R.color.search_area));
		}
		
		
		holder.is_interest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Interst_Selection fav_obj= getItem(position);
				if(fav_obj.isInterested()){
					fav_obj.setInterested(false);
					InterestCacheManager.updateCenters(context, fav_obj);
					holder.is_interest.setImageResource(R.drawable.interest);
					holder.interst_title.setTextColor(context.getResources().getColor(R.color.search_area));
				}else{
					fav_obj.setInterested(true);
					InterestCacheManager.updateCenters(context, fav_obj);
					holder.is_interest.setImageResource(R.drawable.interest_p);
					holder.interst_title.setTextColor(context.getResources().getColor(R.color.purple));
				}
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Interst_Selection fav_obj= getItem(position);
				if(fav_obj.isInterested()){
					fav_obj.setInterested(false);
					InterestCacheManager.updateCenters(context, fav_obj);
					holder.is_interest.setImageResource(R.drawable.interest);
					holder.interst_title.setTextColor(context.getResources().getColor(R.color.search_area));
				}else{
					fav_obj.setInterested(true);
					InterestCacheManager.updateCenters(context, fav_obj);
					holder.is_interest.setImageResource(R.drawable.interest_p);
					holder.interst_title.setTextColor(context.getResources().getColor(R.color.purple));
				}
			
			}
		});
		
		
		return view;
	}
}