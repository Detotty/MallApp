package com.List.Adapter;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.Model.ShopsModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.OffersDetailActivity;
import com.mallapp.View.R;
import com.mallapp.cache.AppCacheManager;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.StringUtils;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;


public class Offers_News_Adapter extends ArrayAdapter<MallActivitiesModel> {

    private static final String TAG = Offers_News_Adapter.class.getSimpleName();

    private Context context;
    private Activity activity;
    String audience_type;
    String UserId;
    String url;
    private MallActivitiesModel offer_obj;
    Dao<MallActivitiesModel, Integer> mallActivitiesModelIntegerDaol;
    VolleyNetworkUtil volleyNetworkUtil;

    ArrayList<MallActivitiesModel> mallActivities_All;

    public Offers_News_Adapter(Context context, Activity activti, int textViewResourceId,

                               ArrayList<MallActivitiesModel> mallActivities_All, String audience_type,    Dao<MallActivitiesModel, Integer> mallActivitiesModelIntegerDaol
    ) {

        super(context, textViewResourceId);
        this.context = context;
        this.activity = activti;
        this.mallActivities_All = new ArrayList<>();
        this.mallActivitiesModelIntegerDaol = mallActivitiesModelIntegerDaol;
        volleyNetworkUtil = new VolleyNetworkUtil(context);
        this.mallActivities_All = mallActivities_All;

        this.audience_type = audience_type;
        UserId = SharedPreferenceUserProfile.getUserId(context);
    }

    public String getAudience_type() {
        return audience_type;
    }

    public void setAudience_type(String audience_type) {
        this.audience_type = audience_type;

    }

    @Override
    public int getCount() {
            return mallActivities_All.size();
    }

    @Override
    public MallActivitiesModel getItem(int position) {
            return mallActivities_All.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getPosition(MallActivitiesModel item) {
        return super.getPosition(item);
    }

    static class ViewHolder {
        TextView title, decs, center_name, shome_name, new_offer_news;
        ImageButton is_fav;
        ImageView back_image, entity_logo, activity_type;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View view = convertView;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.list_item_offers_new, null);

            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.decs = (TextView) view.findViewById(R.id.center_city);
            holder.center_name = (TextView) view.findViewById(R.id.center_name);
            holder.shome_name = (TextView) view.findViewById(R.id.shop_name);
            holder.new_offer_news = (TextView) view.findViewById(R.id.tv_new_offer_news);
            holder.is_fav = (ImageButton) view.findViewById(R.id.fav_center);
            holder.back_image = (ImageView) view.findViewById(R.id.center_image);
            holder.entity_logo = (ImageView) view.findViewById(R.id.entity_logo);
            holder.activity_type = (ImageView) view.findViewById(R.id.iv_activity_type);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
///////////////////////////////////////////////////////////////////////////////////////////		
        offer_obj = getItem(position);
        Drawable d = null;

        String offerTime;
        String UTCDate = StringUtils.changeDateFormat(offer_obj.getCreatedUTCDateTime());
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        if (UTCDate.equals(date)){
            holder.new_offer_news.setVisibility(View.VISIBLE);
        }
        else
            holder.new_offer_news.setVisibility(View.GONE);

        if (offer_obj.getActivityName().equals("Offer")){
            offerTime  = "Offer Starts "+offer_obj.getStartDate().substring(0,offer_obj.getStartDate().indexOf("T"))+" Ends "+offer_obj.getEndDate().substring(0,offer_obj.getEndDate().indexOf("T"));
            holder.activity_type.setImageResource(R.drawable.offer_activity);
        }else {
            offerTime  = offer_obj.getStartDate().substring(0,offer_obj.getStartDate().indexOf("T"));
            holder.activity_type.setImageResource(R.drawable.news_activity);
        }
        holder.title.setText(offer_obj.getActivityTextTitle());
        holder.decs.setText(offer_obj.getBriefText());
        holder.center_name.setText(offerTime);
        holder.shome_name.setText(offer_obj.getEntityName()+","+offer_obj.getPlaceName());
        Picasso.with(context).load(offer_obj.getImageURL()).fit().into(holder.back_image);
        Picasso.with(context).load(offer_obj.getEntityLogoSquare()).placeholder(R.drawable.listview_logo_placeholder).fit().into(holder.entity_logo);
		final boolean fav	= offer_obj.isFav();
		if(fav)
			holder.is_fav.setImageResource(R.drawable.offer_fav_p);
		else
			holder.is_fav.setImageResource(R.drawable.offer_fav);
		
		
		holder.is_fav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                offer_obj = getItem(position);
                if (!offer_obj.isFav()) {
                    holder.is_fav.setImageResource(R.drawable.offer_fav_p);
                    offer_obj.setFav(true);
                    url = ApiConstants.POST_FAV_OFFERS_URL_KEY+UserId+"&ActivityId="+offer_obj.getActivityId()+"&isDeleted=false";
                    volleyNetworkUtil.PostFavNnO(url);
                    url = "";
//                    updateMalls(offer_obj);
                } else {
                    holder.is_fav.setImageResource(R.drawable.offer_fav);
                    offer_obj.setFav(false);
                    url = ApiConstants.POST_FAV_OFFERS_URL_KEY+UserId+"&ActivityId="+offer_obj.getActivityId()+"&isDeleted=true";
                    volleyNetworkUtil.PostFavNnO(url);
                    url = "";
//                    updateMalls(offer_obj);
                }
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                offer_obj = getItem(position);
//				GlobelOffersNews.offer_obj= offer_obj;
                Intent intent = new Intent(activity, OffersDetailActivity.class);
                intent.putExtra(Offers_News_Constants.MALL_OBJECT,offer_obj);
                intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getApplication().startActivity(intent);
            }
        });
        return view;
    }

    public void updateMalls(MallActivitiesModel fav){
        try {
            mallActivitiesModelIntegerDaol.createOrUpdate(fav);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}