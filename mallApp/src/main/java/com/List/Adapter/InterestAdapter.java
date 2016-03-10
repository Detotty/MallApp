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

import com.mallapp.Model.InterestSelectionModel;
import com.mallapp.Model.Interst_Selection;
import com.mallapp.View.R;
import com.mallapp.cache.InterestCacheManager;

@SuppressLint("InflateParams") 
public class InterestAdapter extends ArrayAdapter<InterestSelectionModel>{

	//private static final String TAG = PhoneContactsArrayAdapter.class.getSimpleName();
	private ArrayList<InterestSelectionModel> interst_;
	Context context;
	private ImageView is_interest_select_all;
	
	public InterestAdapter(Context context, int textViewResourceId, 
						ArrayList<InterestSelectionModel> objects,ImageView is_interest_select_all) {
		super(context, textViewResourceId, objects);
		interst_= objects;
		this.context= context;
		this.is_interest_select_all = is_interest_select_all;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getCount() {
		return interst_.size();
	}

	@Override
	public InterestSelectionModel getItem(int position) {
		return this.interst_.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public int getPosition(InterestSelectionModel item) {
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
		
		
		InterestSelectionModel interst_obj= getItem(position);
		holder.interst_title.setText(interst_obj.getCategoryText());
		boolean is_interts= interst_obj.isInterested();
		
		if(is_interts){
			holder.is_interest.setImageResource(R.drawable.offer_fav_p);
			holder.interst_title.setTextColor(context.getResources().getColor(R.color.purple));
		}else{
			holder.is_interest.setImageResource(R.drawable.offer_fav_r);
			holder.interst_title.setTextColor(context.getResources().getColor(R.color.search_area));
		}

		if(interst_obj.isInterested()){
			InterestCacheManager.updateCenters(context, interst_obj, position);
			holder.is_interest.setImageResource(R.drawable.offer_fav_p);
			holder.interst_title.setTextColor(context.getResources().getColor(R.color.purple));
		}

		
		
		/*holder.is_interest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InterestSelectionModel fav_obj= getItem(position);
				if(fav_obj.isInterested()){
					fav_obj.setInterested(false);
					InterestCacheManager.updateCenters(context, fav_obj, position);
					holder.is_interest.setImageResource(R.drawable.offer_fav_r);
					is_interest_select_all.setImageResource(R.drawable.offer_fav_r);
					holder.interst_title.setTextColor(context.getResources().getColor(R.color.search_area));
				}else{
					fav_obj.setInterested(true);
					InterestCacheManager.updateCenters(context, fav_obj, position);
					holder.is_interest.setImageResource(R.drawable.offer_fav_p);
					holder.interst_title.setTextColor(context.getResources().getColor(R.color.purple));
				}
			}
		});*/
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				InterestSelectionModel fav_obj= getItem(position);
				if(fav_obj.isInterested()){
					fav_obj.setInterested(false);
					InterestCacheManager.updateCenters(context, fav_obj, position);
					holder.is_interest.setImageResource(R.drawable.offer_fav_r);
					is_interest_select_all.setImageResource(R.drawable.offer_fav_r);
					holder.interst_title.setTextColor(context.getResources().getColor(R.color.search_area));
				}else{
					fav_obj.setInterested(true);
					InterestCacheManager.updateCenters(context, fav_obj, position);
					holder.is_interest.setImageResource(R.drawable.offer_fav_p);
					holder.interst_title.setTextColor(context.getResources().getColor(R.color.purple));
				}
			
			}
		});
		
		
		return view;
	}

	private String selectedItem;
	public void setSelectedItem(String position) {
		selectedItem = position;
	}

	public interface OnDataChangeListener{
		public void onDataChanged(int size);
	}
}