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
import com.mallapp.Model.Shops;
import com.mallapp.View.R;
import com.mallapp.View.ShopDetailActivity;
import com.mallapp.cache.ShopCacheManager;
import com.mallapp.globel.GlobelShops;
import com.mallapp.imagecapture.ImageLoader;

public class ShopSearchAdapter extends ArrayAdapter<Shops>{

	//private static final String TAG = PhoneContactsArrayAdapter.class.getSimpleName();
	private ArrayList<Shops> shop_search;
	Context context;
	Activity activity;
	public ImageLoader imageLoader;
	
	public ShopSearchAdapter(Context context, Activity act, int textViewResourceId, ArrayList<Shops> objects) {
		super(context, textViewResourceId, objects);
		shop_search= objects;
		this.context= context;
		this.activity= act;
		imageLoader	= new ImageLoader(context);
	}
	
	public ArrayList<Shops> getShop_search() {
		return shop_search;
	}

	public void setShop_search(ArrayList<Shops> shop_search) {
		this.shop_search = shop_search;
		//Log.e("", shop_search.size()+" setShop_search = shop_search");
	}

	@Override
	public int getCount() {
		return shop_search.size();
	}

	@Override
	public Shops getItem(int position) {
		return this.shop_search.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public int getPosition(Shops item) {
		return super.getPosition(item);
	}
	
	static class ViewHolder {
		TextView title, decs, floor_no;
		ImageButton is_fav;
		ImageView back_image;
		//RelativeLayout r1;
	}

	Shops shop_obj;
	
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
		
        holder.title.setText(shop_obj.getName());
		holder.decs	.setText(shop_obj.getDescription());
		holder.floor_no.setText(shop_obj.getFloor_no());
		
		imageLoader.DisplayImage(AppConstants.PREF_URI_KEY, holder.back_image);
		
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
					ShopCacheManager.updateShops(context, shop_obj, "");
				}else{
					holder.is_fav.setImageResource(R.drawable.offer_fav);
					shop_obj.setFav(false);
					ShopCacheManager.updateShops(context, shop_obj, "");
				}
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				shop_obj= getItem(position);
				GlobelShops.shop_obj= shop_obj;
				Intent intent= new Intent(activity, ShopDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				activity.getApplication().startActivity(intent);

			}
		});
        return view;
    }
}