package com.List.Adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.foound.widget.AmazingAdapter;
import com.j256.ormlite.dao.Dao;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.Shops;
import com.mallapp.Model.ShopsModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;
import com.mallapp.View.ShopDetailActivity;
import com.mallapp.View.ShopMainMenuActivity;
import com.mallapp.cache.ShopCacheManager;
import com.mallapp.globel.GlobelShops;
import com.mallapp.imagecapture.ImageLoader;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;


@SuppressLint("InflateParams")
public class ShopAdapter extends AmazingAdapter {
    private Context context;
    HashMap<String, ArrayList<ShopsModel>> shops_all_audience;
    private ArrayList<String> _listDataHeader;
    private Activity activity;
    String audience_type;
    private ShopsModel shop_obj;
    public ImageLoader imageLoader;
    Dao<ShopsModel, Integer> shopsDao;
    String url;
    String UserId;
    VolleyNetworkUtil volleyNetworkUtil;




    public ShopAdapter(Context context, Activity activity,
                       HashMap<String, ArrayList<ShopsModel>> shops_all_audience,
                       ArrayList<String> header,
                       String audience_type,Dao<ShopsModel, Integer> shopsDao) {

        super();
        this.context = context;
        this.shops_all_audience = shops_all_audience;
        this._listDataHeader = header;
        this.activity = activity;
        this.audience_type = audience_type;
        imageLoader = new ImageLoader(activity.getApplicationContext());
        this.shopsDao = shopsDao;
        UserId = SharedPreferenceUserProfile.getUserId(context);
        volleyNetworkUtil = new VolleyNetworkUtil(context);

    }

    public String getAudience_type() {
        return audience_type;
    }

    public void setAudience_type(String audience_type) {
        this.audience_type = audience_type;
        //Log.e("audience type", audience_type);
    }

    public ShopAdapter() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {//this.shops_all_audience.get(_listDataHeader.get(groupPosition)).size();
        int res = 0;
        for (int i = 0; i < shops_all_audience.size(); i++) {
            res += shops_all_audience.get(_listDataHeader.get(i)).size();
        }
        return res;
        //return 0;
    }

    @Override
    public ShopsModel getItem(int position) {
        //Shops shops= shops_all_audience.get(_listDataHeader.get(groupPosition)).get(childPosititon);
        int c = 0;
        for (int i = 0; i < shops_all_audience.size(); i++) {
            if (position >= c && position < c + shops_all_audience.get(_listDataHeader.get(i)).size()) {
                //return shops_all_audience.get(i).second.get(position - c);
                return shops_all_audience.get(_listDataHeader.get(i)).get(position - c);//.second.get(position - c);
            }
            c += shops_all_audience.get(_listDataHeader.get(i)).size();
        }
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void onNextPageRequested(int page) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
//		Log.e(TAG, "displaySectionHeader = "+displaySectionHeader);
        if (displaySectionHeader) {
            view.findViewById(R.id.section_header).setVisibility(View.VISIBLE);
            TextView lSectionTitle = (TextView) view.findViewById(R.id.section_header);
            lSectionTitle.setText("" + getSections()[getSectionForPosition(position)]);
        } else {
            view.findViewById(R.id.section_header).setVisibility(View.GONE);
        }
    }


    static class ViewHolder {
        TextView title, decs, floor_no;
        ImageButton is_fav;
        ImageView back_image;
        //RelativeLayout r1;
    }


    @Override
    public View getAmazingView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final ViewHolder holder;

        if (convertView == null) {
            //LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = activity.getLayoutInflater().inflate(R.layout.list_item_shop_sectionheader, parent, false);//mInflater.inflate(R.layout.list_item_shop_sectionheader ,null);

            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.decs = (TextView) view.findViewById(R.id.decs);
            holder.floor_no = (TextView) view.findViewById(R.id.floor_no);
            holder.is_fav = (ImageButton) view.findViewById(R.id.fav_center);
            holder.back_image = (ImageView) view.findViewById(R.id.center_image);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        shop_obj = getItem(position);
        holder.title.setText(shop_obj.getStoreName());
        holder.decs.setText(shop_obj.getBriefText());
        holder.floor_no.setText(shop_obj.getFloor());
        Picasso.with(context).load(shop_obj.getLogoURL()).into(holder.back_image);


        final boolean fav = shop_obj.isFav();
        if (fav)
            holder.is_fav.setImageResource(R.drawable.offer_fav_p);
        else
            holder.is_fav.setImageResource(R.drawable.offer_fav);


        holder.is_fav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                shop_obj = getItem(position);
                if (!shop_obj.isFav()) {
                    holder.is_fav.setImageResource(R.drawable.offer_fav_p);
                    shop_obj.setFav(true);
                    updateShops(shop_obj);
                    url = ApiConstants.POST_FAV_SHOP_URL_KEY+UserId+"&EntityId="+shop_obj.getMallStoreId()+"&IsShop=true"+"&IsDeleted=false";
                    volleyNetworkUtil.PostFavShop(url);

//                    ShopCacheManager.updateShops(context, shop_obj, "", position);
                } else {
                    holder.is_fav.setImageResource(R.drawable.offer_fav);
                    shop_obj.setFav(false);
                    updateShops(shop_obj);
                    url = ApiConstants.POST_FAV_SHOP_URL_KEY+UserId+"&EntityId="+shop_obj.getMallStoreId()+"&IsShop=true"+"&IsDeleted=true";
                    volleyNetworkUtil.PostFavShop(url);

//                    ShopCacheManager.updateShops(context, shop_obj, "", position);
                }
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                shop_obj = getItem(position);
                GlobelShops.shopModel_obj = shop_obj;
//				GlobelShops.shop_obj= shop_obj;
                Intent intent = new Intent(activity, ShopDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("MallStoreId", shop_obj.getMallStoreId());
                activity.getApplication().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        TextView lSectionHeader = (TextView) header;
        lSectionHeader.setText("" + getSections()[getSectionForPosition(position)]);
        //lSectionHeader.setBackgroundColor(alpha << 24 | (0xbbffbb));
        //lSectionHeader.setTextColor(alpha << 24 | (0x000000));
    }

    @Override
    public int getPositionForSection(int section) {

        if (section < 0) section = 0;
        if (section >= shops_all_audience.size()) section = shops_all_audience.size() - 1;
        int c = 0;
        for (int i = 0; i < shops_all_audience.size(); i++) {
            if (section == i) {
                return c;
            }//shops_all_audience.get(_listDataHeader.get(i)).size();
            c += shops_all_audience.get(_listDataHeader.get(i)).size();
        }
        return 0;
    }


    @Override
    public int getSectionForPosition(int position) {
        int c = 0;
        for (int i = 0; i < shops_all_audience.size(); i++) {
            if (position >= c && position < c + shops_all_audience.get(_listDataHeader.get(i)).size()) {
                return i;
            }
            c += shops_all_audience.get(_listDataHeader.get(i)).size();
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        String[] res = new String[shops_all_audience.size()];
        for (int i = 0; i < shops_all_audience.size(); i++) {
            res[i] = this._listDataHeader.get(i);
        }
        return res;
    }
    public void updateShops(ShopsModel fav){
        try {
            shopsDao.createOrUpdate(fav);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
