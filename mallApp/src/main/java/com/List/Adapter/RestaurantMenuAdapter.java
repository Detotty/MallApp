package com.List.Adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mallapp.Model.RestaurantMenu;
import com.mallapp.View.R;

public class RestaurantMenuAdapter extends BaseAdapter{

	//private Context context;
	Activity activity;
    private ArrayList<RestaurantMenu> navDrawerItems;
	
    public RestaurantMenuAdapter(Context context, Activity act, ArrayList<RestaurantMenu> navDrawerItems){
        //this.context = context;
        this.activity= act;
        this.navDrawerItems = navDrawerItems;
    }
	
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }
 
    @Override
    public Object getItem(int position) {       
        return navDrawerItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 

    static class ViewHolder{
    	
    	TextView title, price;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    
    	final ViewHolder holder;
    	
    	if (convertView == null) {
    		convertView = activity.getLayoutInflater().inflate(R.layout.list_item_restaurant_menu, parent, false);
        	holder = new ViewHolder();
			holder.title	= (TextView) convertView.findViewById(R.id.menu_title);
			holder.price	= (TextView) convertView.findViewById(R.id.menu_price);
			
			convertView.setTag(holder);
        }else {
			holder = (ViewHolder) convertView.getTag();
		}
    	
    	holder.title.setText(navDrawerItems.get(position).getTitle());
        holder.price.setText(navDrawerItems.get(position).getPrice());
        return convertView;
    }
}
