package com.mallapp.Controllers;

import java.util.ArrayList;

import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.Offers_News;
import com.mallapp.SharedPreferences.SharedPreference;
import com.mallapp.View.R;
import com.mallapp.utils.GlobelOffersNews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class OffersNewsFiltration {
	
	public static void readOffersNews(Context context) {
		ArrayList<Offers_News> offerNews_list_filter = new ArrayList<Offers_News>();
		SharedPreference s_p= new SharedPreference();
		offerNews_list_filter= s_p.getOffersNews(context);
		GlobelOffersNews.endorsement_array= offerNews_list_filter;
	}

	public static ArrayList<Offers_News> filterFavouriteCenters(String favouriteCenterFilter, 
												ArrayList<Offers_News> favourite_center_List) {
		
		ArrayList<Offers_News> favourite_center_Filter_List = new ArrayList<Offers_News>();
		if(favouriteCenterFilter.equalsIgnoreCase(Offers_News_Constants.AUDIENCE_FILTER_ALL)){
			favourite_center_Filter_List= favourite_center_List;
		}else{
			for(Offers_News offer: favourite_center_List){
				if(favouriteCenterFilter.equals(offer.getCenter_name().trim())){
					favourite_center_Filter_List.add(offer);
				}
			}
		}
		return favourite_center_Filter_List;
	}
	
	public static ArrayList<Offers_News>  getAllAudience(ArrayList<Offers_News> favouriteList) {
		ArrayList<Offers_News> all_audience= favouriteList;
		//all_audience_images= getOffersImagesList(all_audience);
		return all_audience;
	}
	
	
	public static ArrayList<Drawable> getOffersImagesList(Context context, ArrayList<Offers_News> endorsement_list_filter){
		ArrayList<Drawable> endorsement_images_list = new ArrayList<Drawable>();
		for(Offers_News object : endorsement_list_filter) {
			String image_nam= object.getImage();
			
			int imageResource = context.getResources().getIdentifier(image_nam, "drawable", context.getPackageName());
			Drawable d = context.getResources().getDrawable(imageResource);
			Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
			
			int mDstWidth 	= context.getResources().getDimensionPixelSize(R.dimen.createview_destination_width);
	        int mDstHeight 	= context.getResources().getDimensionPixelSize(R.dimen.createview_destination_height);
			
	        d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth,mDstHeight, true));
			// Set your new, scaled drawable "d"
			endorsement_images_list.add( d);
		}
		return endorsement_images_list;
	}
	
	public static void getContactsAudience(ArrayList<Offers_News> favouriteList) {
//		offers_audience= new ArrayList<Offers_News>();
//		
//		for(Offers_News endorsement: favouriteList){
//			String audience= endorsement.getType();
//			
//			if(audience.trim().equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS))
//				offers_audience.add(endorsement);
//		}
//		//Log.e("", "filter data endorsement_contacts_audience"+ endorsement_contacts_audience.size());
//		offers_audience_images= getOffersImagesList(offers_audience);
	}
	
	public static void getTrustedAudience(ArrayList<Offers_News> favouriteList) {
//		news_audience= favouriteList;
//		news_audience_images= getOffersImagesList(news_audience);
//		news_audience= new ArrayList<Offers_News>();
//		
//		for(Offers_News endorsement: favouriteList){
//			String audience= endorsement.getType();
//			
//			if(audience.trim().equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS))
//				news_audience.add(endorsement);
//		}
//		//Log.e("", "filter data endorsement_contacts_audience"+ endorsement_contacts_audience.size());
//		news_audience_images= getOffersImagesList(news_audience);
		
	}
	
	
	
}
