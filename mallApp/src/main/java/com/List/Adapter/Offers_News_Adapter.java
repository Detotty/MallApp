package com.List.Adapter;

import java.util.ArrayList;

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

import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.View.OffersDetailActivity;
import com.mallapp.View.R;
import com.mallapp.cache.AppCacheManager;
import com.mallapp.utils.GlobelOffersNews;
import com.squareup.picasso.Picasso;


public class Offers_News_Adapter extends ArrayAdapter<MallActivitiesModel> {

    private static final String TAG = Offers_News_Adapter.class.getSimpleName();

    private Context context;
    private Activity activity;
    String audience_type;
    private MallActivitiesModel offer_obj;


    ArrayList<MallActivitiesModel> mallActivities_All,
            mallActivities_Offers,
            mallActivities_News;
    String mall_clicked_type;

    public Offers_News_Adapter(Context context, Activity activti, int textViewResourceId,

                               ArrayList<MallActivitiesModel> mallActivities_All, String audience_type) {

        super(context, textViewResourceId);
        this.context = context;
        this.activity = activti;
        this.mallActivities_All = new ArrayList<>();
        if (MainMenuConstants.SELECTED_CENTER_NAME.equals("All")) {
            this.mallActivities_All = mallActivities_All;
        } else {
            for (MallActivitiesModel mam : mallActivities_All
                    ) {
                if (mam.getMallName().equals(MainMenuConstants.SELECTED_CENTER_NAME)) {
                    this.mallActivities_All.add(mam);
                }
            }
        }
        this.audience_type = audience_type;
        FilteredOffersNewsList(mallActivities_All);
    }

    public String getAudience_type() {
        return audience_type;
    }

    public void setAudience_type(String audience_type) {
        this.audience_type = audience_type;

    }

    @Override
    public int getCount() {
        if (audience_type.equals(Offers_News_Constants.AUDIENCE_FILTER_ALL))
            return mallActivities_All.size();
        else if (audience_type.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS))
            return mallActivities_Offers.size();
        else if (audience_type.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS))
            return mallActivities_News.size();
        else
            return mallActivities_All.size();
    }

    @Override
    public MallActivitiesModel getItem(int position) {
        if (audience_type.equals(Offers_News_Constants.AUDIENCE_FILTER_ALL))
            return mallActivities_All.get(position);
        else if (audience_type.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS))
            return mallActivities_Offers.get(position);
        else if (audience_type.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS))
            return mallActivities_News.get(position);
        else
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
        TextView title, decs, center_name, shome_name;
        ImageButton is_fav;
        ImageView back_image, entity_logo;
        //RelativeLayout r1;
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
            holder.is_fav = (ImageButton) view.findViewById(R.id.fav_center);
            holder.back_image = (ImageView) view.findViewById(R.id.center_image);
            holder.entity_logo = (ImageView) view.findViewById(R.id.entity_logo);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
///////////////////////////////////////////////////////////////////////////////////////////		
        offer_obj = getItem(position);
        Drawable d = null;

		/*if(this.audience_type.equals(Offers_News_Constants.AUDIENCE_FILTER_ALL)){
            endorsement_images_list= endorsement_all_images_list;
		}else if(this.audience_type.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)){
			endorsement_images_list= endorsement_contacts_images_list;
		}else if(this.audience_type.equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)){
			endorsement_images_list= endorsement_trusted_images_list;
		}else{
			endorsement_images_list= endorsement_all_images_list;
		}
		
		if(endorsement_images_list!=null && endorsement_images_list.size()>0){
			d = endorsement_images_list.get(position);
			holder.back_image.setBackground(d);
		}*/

        holder.title.setText(offer_obj.getActivityTextTitle());
        holder.decs.setText(offer_obj.getDetailText());
        holder.center_name.setText(offer_obj.getMallName());
        holder.shome_name.setText(offer_obj.getPlaceName());
        Picasso.with(context).load(offer_obj.getImageURL()).into(holder.back_image);
        Picasso.with(context).load(offer_obj.getEntityLogo()).into(holder.entity_logo);
		/*final boolean fav	= offer_obj.isFav();
		if(fav)
			holder.is_fav.setImageResource(R.drawable.offer_fav_p);
		else
			holder.is_fav.setImageResource(R.drawable.offer_fav);
		
		
		holder.is_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				offer_obj= getItem(position);
				if(!offer_obj.isFav()){
					holder.is_fav.setImageResource(R.drawable.offer_fav_p);
					offer_obj.setFav(true);
					AppCacheManager.updateOffersNews(context, offer_obj);
				}else{
					holder.is_fav.setImageResource(R.drawable.offer_fav);
					offer_obj.setFav(false);
					AppCacheManager.updateOffersNews(context, offer_obj);
				}
			}
		});*/

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                offer_obj = getItem(position);
//				GlobelOffersNews.offer_obj= offer_obj;
                Intent intent = new Intent(activity, OffersDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getApplication().startActivity(intent);

//				if(endorsement_clicked_type!=null && 
//						endorsement_clicked_type.equals(
//								Offers_News_Constants.ENDORSEMENT_CLICK_TYPE)){
//					end_obj= getItem(position);
//					Globel_Endorsement.end_obj_chat= end_obj;
//					Globel_Endorsement.endorsement_click_type= null;
//					Intent resultIntent = new Intent();
//					resultIntent.putExtra("result","some result");
//					activity.setResult(Endorsement_Detail_Chat.REQUEST_CODE_FOR_ENDORSEMENT, resultIntent);
//					activity.finish();
//					
//				}else{
//					end_obj= getItem(position);//endorsement_filter.get(position);
//					Globel_Endorsement.end_obj= end_obj;
//					Intent intent= new Intent(activity, Endorsement_Overview_Detail.class);
//					intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);                     
//					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					activity.getApplication().startActivity(intent);
//				}
            }
        });
        return view;
    }


    private void FilteredOffersNewsList(ArrayList<MallActivitiesModel> all) {

        mallActivities_Offers = new ArrayList<>();
        mallActivities_News = new ArrayList<>();
        for (MallActivitiesModel ma : all) {
            if (ma.getActivityName().equals("News")) {
                if (MainMenuConstants.SELECTED_CENTER_NAME.equals("All")) {
                    mallActivities_News.add(ma);
                } else {
                    if (ma.getMallName().equals(MainMenuConstants.SELECTED_CENTER_NAME)) {
                        this.mallActivities_News.add(ma);
                    }
                }
            } else {
                if (MainMenuConstants.SELECTED_CENTER_NAME.equals("All")) {
                    mallActivities_Offers.add(ma);
                } else {
                    if (ma.getMallName().equals(MainMenuConstants.SELECTED_CENTER_NAME)) {
                        mallActivities_Offers.add(ma);
                    }
                }
            }
        }
    }

}