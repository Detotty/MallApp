package com.List.Adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mallapp.Model.NavDrawerItem;
import com.mallapp.View.R;

public class NavDrawerListAdapter extends BaseAdapter{

	//private Context context;
	Activity activity;
    private ArrayList<NavDrawerItem> navDrawerItems;
	
    public NavDrawerListAdapter(Context context, Activity act,ArrayList<NavDrawerItem> navDrawerItems){
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
    	ImageView nav_icon ;
    	TextView nav_title;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	final ViewHolder holder;
    	if (convertView == null) {
    		convertView = activity.getLayoutInflater().inflate(R.layout.drawer_list_item, parent, false);
        	//LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            //convertView = mInflater.inflate(R.layout.drawer_list_item, parent, false);
            holder = new ViewHolder();
			holder.nav_title 	= (TextView) convertView.findViewById(R.id.nav_title);
			holder.nav_icon		= (ImageView) convertView.findViewById(R.id.nav_icon);
			
			convertView.setTag(holder);
        }else {
			holder = (ViewHolder) convertView.getTag();
		}
    	holder.nav_title.setText(navDrawerItems.get(position).getNavigation_title());
        holder.nav_icon.setImageResource(navDrawerItems.get(position).getNavigation_image()); 
        
        
        return convertView;
    }
}