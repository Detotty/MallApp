package com.List.Adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Fragments.OffersTabFragment;
import com.mallapp.Model.NavDrawerItem;
import com.mallapp.View.DashboardTabFragmentActivity;
import com.mallapp.View.DiscountCalculatorActivity;
import com.mallapp.View.FavouritesMainMenuActivity;
import com.mallapp.View.MallDetailActivity;
import com.mallapp.View.R;
import com.mallapp.View.RestaurantMainMenuActivity;
import com.mallapp.View.ServicesMainMenuActivity;
import com.mallapp.View.ShopMainMenuActivity;
import com.mallapp.View.SlidingDrawerActivity;
import com.mallapp.utils.AppUtils;

public class NavDrawerListAdapter extends BaseAdapter{

	private Context context;
	Activity activity;
    private ArrayList<NavDrawerItem> navDrawerItems;
	
    public NavDrawerListAdapter(Context context, Activity act,ArrayList<NavDrawerItem> navDrawerItems){
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
    	
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

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(position);
            }
        });*/
        
        return convertView;
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        //fragment= null;
        switch (position) {
            case 1:{

                MainMenuConstants.uiHandler= SlidingDrawerActivity.uiHandler;
                DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                //finish();
                Intent activity= new Intent(context, DashboardTabFragmentActivity.class);
                activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //Bundle b = new Bundle();
                //b.putString("favourite", subcatagory);
                //activity.putExtras(b);
                activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.activity.getApplication().startActivity(activity);
                this.activity.finish();
            }
            break;

            case 2:

                if(	MainMenuConstants.SELECTED_CENTER_NAME!=null
                        && MainMenuConstants.SELECTED_CENTER_NAME.length()>0
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals("all")
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){

                    MainMenuConstants.uiHandler= SlidingDrawerActivity.uiHandler;
                    Intent activity = new Intent(context, ShopMainMenuActivity.class);
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.putExtra(Offers_News_Constants.MALL_PLACE_ID, AppUtils.MallIdSelection(context, OffersTabFragment.pos));
                    activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.activity.getApplication().startActivity(activity);
                    this.activity.finish();
                } else
//                    showdailog();
                break;

            case 3:

                if(	MainMenuConstants.SELECTED_CENTER_NAME!=null
                        && MainMenuConstants.SELECTED_CENTER_NAME.length()>0
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals("all")
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){

                    MainMenuConstants.uiHandler= SlidingDrawerActivity.uiHandler;
                    DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                    Intent activity= new Intent(context, RestaurantMainMenuActivity.class);
                    activity.putExtra(Offers_News_Constants.MALL_PLACE_ID, AppUtils.MallIdSelection(context, OffersTabFragment.pos));
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.activity.getApplication().startActivity(activity);
                    this.activity.finish();
                } else
//                    showdailog();
                break;


            case 4:
                if(	MainMenuConstants.SELECTED_CENTER_NAME!=null
                        && MainMenuConstants.SELECTED_CENTER_NAME.length()>0
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals("all")
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){

                    MainMenuConstants.uiHandler= SlidingDrawerActivity.uiHandler;
                    DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                    Intent activity= new Intent(context, ServicesMainMenuActivity.class);
                    activity.putExtra(Offers_News_Constants.MALL_PLACE_ID, AppUtils.MallIdSelection(context, OffersTabFragment.pos));
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.activity.getApplication().startActivity(activity);
                    this.activity.finish();
                } else
//                    showdailog();
                break;

            case 5:
                if(	MainMenuConstants.SELECTED_CENTER_NAME!=null
                        && MainMenuConstants.SELECTED_CENTER_NAME.length()>0
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals("all")
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){
                    MainMenuConstants.uiHandler= SlidingDrawerActivity.uiHandler;
                    DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                    Intent activity = new Intent(context, MallDetailActivity.class);
                    activity.putExtra(Offers_News_Constants.MALL_PLACE_ID, AppUtils.MallIdSelection(context, OffersTabFragment.pos));
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.activity.getApplication().startActivity(activity);
                    this.activity.finish();
                } else
//                    showdailog();
                break;


            case 6: {

                MainMenuConstants.uiHandler = SlidingDrawerActivity.uiHandler;
                DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                Intent activity = new Intent(context, DiscountCalculatorActivity.class);
                activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.activity.getApplication().startActivity(activity);
            }
            break;


            case 7:{

                MainMenuConstants.uiHandler= SlidingDrawerActivity.uiHandler;
                DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                Intent activity= new Intent(context, FavouritesMainMenuActivity.class);
                activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.activity.getApplication().startActivity(activity);
                this.activity.finish();
            }
            break;

            default:
                break;
        }
    }

}