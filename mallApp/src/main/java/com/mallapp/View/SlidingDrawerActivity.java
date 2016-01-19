package com.mallapp.View;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.List.Adapter.NavDrawerListAdapter;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Fragments.CardTabFragments;
import com.mallapp.Fragments.MessagesTabFragments;
import com.mallapp.Fragments.OffersTabFragment;
import com.mallapp.Fragments.ProfileTabFragment;
import com.mallapp.Fragments.RewardsTabFragments;
import com.mallapp.Model.NavDrawerItem;
import com.mallapp.utils.AppUtils;
import com.mallapp.utils.Log;
import com.squareup.picasso.Picasso;

@SuppressLint("InflateParams")
public class SlidingDrawerActivity extends FragmentActivity implements OnItemClickListener {

    public static DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    FragmentTabHost mTabHost;

    // nav drawer title
    //private CharSequence mDrawerTitle;

    // used to store app title
    //private CharSequence mTitle;

    // slide menu items
    public String[] navMenuTitles;
    public String[] navMenuIcons;

    public ArrayList<NavDrawerItem> navDrawerItems;
    public NavDrawerListAdapter adapter;
    Fragment fragment = null;
    public static ImageView drawer_logo;
    public static TextView drawer_text;
    public static Context context;




    @Override
    protected void onResume() {
        super.onResume();
        context = this;
        initDrawer();
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("InflateParams")
    private void initDrawer() {
        navDrawerItems= new ArrayList<NavDrawerItem>();
        getNavList();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        View view1 = getLayoutInflater().inflate(R.layout.drawer_header_list_item, null);
        View view2 = getLayoutInflater().inflate(R.layout.drawer_footer_list_item, null);

        drawer_logo = (ImageView) view1.findViewById(R.id.selected_center_logo);
        drawer_text = (TextView) view1.findViewById(R.id.side_menu_header);

        if (mDrawerList.getHeaderViewsCount() == 0){
            mDrawerList.addHeaderView(view1);
            mDrawerList.addFooterView(view2);
        }

        adapter = new NavDrawerListAdapter(this,this, navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);
        mDrawerLayout.setDrawerListener(mDrawerListener);

        // first position
//        fragment = new DashBoardFragmentTabs(uiHandler);
//		getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
//		mDrawerLayout.closeDrawer(mDrawerList);

    }

    private void getNavList() {
        navMenuTitles =getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().getStringArray(R.array.nav_drawer_icons);
        for(int i=0; i<navMenuTitles.length; i++){
            String image_nam= navMenuIcons[i];
            int res = getResources().getIdentifier(image_nam, "drawable", getPackageName());
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], res));
        }
    }


    private DrawerListener mDrawerListener = new DrawerListener() {
        @Override
        public void onDrawerStateChanged(int status) {
            drawerLogoCondtions();
        }
        @Override
        public void onDrawerSlide(View view, float slideArg) {

        }
        @Override
        public void onDrawerOpened(View view) {
            mDrawerLayout.openDrawer(mDrawerList);

        }
        @Override
        public void onDrawerClosed(View view) {
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        displayView(position);
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        //fragment= null;
        switch (position) {
            case 1:{

                    MainMenuConstants.uiHandler= uiHandler;
                    DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                    //finish();
                    Intent activity= new Intent(SlidingDrawerActivity.this, DashboardTabFragmentActivity.class);
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //Bundle b = new Bundle();
                    //b.putString("favourite", subcatagory);
                    //activity.putExtras(b);
                    startActivity(activity);
                    finish();
                }
                break;

            case 2:

                if(	MainMenuConstants.SELECTED_CENTER_NAME!=null
                        && MainMenuConstants.SELECTED_CENTER_NAME.length()>0
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals("all")
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){

                    MainMenuConstants.uiHandler= uiHandler;
                    Intent activity = new Intent(SlidingDrawerActivity.this, ShopMainMenuActivity.class);
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.putExtra(Offers_News_Constants.MALL_PLACE_ID, AppUtils.MallIdSelection(context, OffersTabFragment.pos));
                    startActivity(activity);
                    finish();
                } else
                    showdailog();
                break;

            case 3:

                if(	MainMenuConstants.SELECTED_CENTER_NAME!=null
                        && MainMenuConstants.SELECTED_CENTER_NAME.length()>0
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals("all")
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){

                    MainMenuConstants.uiHandler= uiHandler;
                    DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                    Intent activity= new Intent(SlidingDrawerActivity.this, RestaurantMainMenuActivity.class);
                    activity.putExtra(Offers_News_Constants.MALL_PLACE_ID, AppUtils.MallIdSelection(context, OffersTabFragment.pos));
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity);
                    finish();
                } else
                    showdailog();
                break;


            case 4:
                if(	MainMenuConstants.SELECTED_CENTER_NAME!=null
                        && MainMenuConstants.SELECTED_CENTER_NAME.length()>0
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals("all")
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){

                    MainMenuConstants.uiHandler= uiHandler;
                    DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                    Intent activity= new Intent(SlidingDrawerActivity.this, ServicesMainMenuActivity.class);
                    activity.putExtra(Offers_News_Constants.MALL_PLACE_ID, AppUtils.MallIdSelection(context, OffersTabFragment.pos));
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity);
                    finish();
                } else
                    showdailog();
                break;

            case 5:
                if(	MainMenuConstants.SELECTED_CENTER_NAME!=null
                        && MainMenuConstants.SELECTED_CENTER_NAME.length()>0
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals("all")
                        && !MainMenuConstants.SELECTED_CENTER_NAME.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){
                    MainMenuConstants.uiHandler= uiHandler;
                    DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                    Intent activity = new Intent(SlidingDrawerActivity.this, MallDetailActivity.class);
                    activity.putExtra(Offers_News_Constants.MALL_PLACE_ID, AppUtils.MallIdSelection(context, OffersTabFragment.pos));
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity);
                    finish();
                } else
                    showdailog();
                break;


            case 6: {

                    MainMenuConstants.uiHandler = uiHandler;
                    DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                    Intent activity = new Intent(SlidingDrawerActivity.this, DiscountCalculatorActivity.class);
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity);
                }
                break;


            case 7:{

                    MainMenuConstants.uiHandler= uiHandler;
                    DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
                    Intent activity= new Intent(SlidingDrawerActivity.this, FavouritesMainMenuActivity.class);
                    activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity);
                    finish();
                }
                break;

            default:
                break;
        }
    }




    private void showdailog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(getResources().getString(R.string.center_select_title));

        alertDialogBuilder.setMessage(getResources().getString(R.string.center_select_message));

        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @SuppressLint("HandlerLeak")
    public static Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("handler", "Handler works");
                    Log.e("", "	MainMenuConstants.SELECTED_CENTER_NAME = "+	MainMenuConstants.SELECTED_CENTER_NAME);
                    if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(mDrawerList);
                    } else {

                        drawerLogoCondtions();


                        mDrawerLayout.openDrawer(mDrawerList);
                    }
                    break;
            }};

    };






    public static void setCenter_logo(String center_logo, Context context) {
		
		/*int mDstWidth 	= context.getResources().getDimensionPixelSize(R.dimen.center_logo_width);
        int mDstHeight 	= context.getResources().getDimensionPixelSize(R.dimen.center_logo_height);
		
		int imageResource 	= context.getResources().getIdentifier(center_logo, "drawable", context.getPackageName());
		Drawable d 			= context.getResources().getDrawable(imageResource);
		Bitmap bitmap 		= ((BitmapDrawable) d).getBitmap();
		
		d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth,mDstHeight, true));
		drawer_logo.setImageDrawable(d);*/
        Picasso.with(context).load(center_logo).placeholder(R.drawable.mallapp_placeholder).error(R.drawable.mallapp_placeholder).into(drawer_logo);

    }

    static void drawerLogoCondtions(){
        if(	MainMenuConstants.SELECTED_CENTER_NAME!=null
                && MainMenuConstants.SELECTED_CENTER_NAME.length()>0
                && !MainMenuConstants.SELECTED_CENTER_NAME.equals("all")
                && !MainMenuConstants.SELECTED_CENTER_NAME.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){

            drawer_text.setVisibility(View.GONE);
            drawer_logo.setVisibility(View.VISIBLE);
            setCenter_logo(MainMenuConstants.SELECTED_CENTER_LOGO, context);

        } else{
            drawer_text.setVisibility(View.VISIBLE);
            drawer_logo.setVisibility(View.GONE);
        }
    }






}
