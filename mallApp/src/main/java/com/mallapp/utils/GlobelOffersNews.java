package com.mallapp.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.mallapp.Fragments.OfferPagerTabFragment;
import com.mallapp.Model.FavouriteCenters;
import com.mallapp.Model.Offers_News;

public class GlobelOffersNews {
	
	
	public static ArrayList<Bitmap> bitmap_images;
	public static Offers_News offer_obj;
	public static Offers_News related_offer_obj;
	public static Offers_News offer_obj_chat;
	public static String offers_click_type;

//	public static boolean new_endorsement_created;
	public static String endorsement_favourite_title;

	public static ArrayList<Offers_News> centered_filter_array;
	public static ArrayList<Offers_News> endorsement_array;
	public static ArrayList<Offers_News> all_audience;
	public static ArrayList<Offers_News> offers_audience;
	public static ArrayList<Offers_News> news_audience;

	public static ArrayList<Drawable> all_audience_images;
	public static ArrayList<Drawable> offers_audience_images;
	public static ArrayList<Drawable> news_audience_images;
	
	public static List<OfferPagerTabFragment> fragment_instences;
	public static ArrayList<FavouriteCenters> TITLES_centers;
	public static ArrayList<String> TITLES;
	
	
	public static ArrayList<String> header_section_offer;
	public static ArrayList<String> header_section_news;
	

}
