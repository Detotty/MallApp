package com.mallapp.globel;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.mallapp.Fragments.RestaurantPagerFragment;
import com.mallapp.Model.Restaurant;
import com.mallapp.Model.RestaurantMenu;

public class GlobelRestaurants {

	public static ArrayList<Bitmap> bitmap_images;
	public static Restaurant rest_obj;
	
	public static Restaurant related_rest_obj;
	public static Restaurant rest_obj_chat;

	public static String endorsement_favourite_title;
	
	public static ArrayList<Restaurant> restaurant_array;
	
	public static ArrayList<Restaurant> alphabetic_audience;
	public static ArrayList<Restaurant> category_audience;
	public static ArrayList<Restaurant> floor_audience;

	public static ArrayList<Drawable> alphabetic_audience_images;
	public static ArrayList<Drawable> category_audience_images;
	public static ArrayList<Drawable> floor_audience_images;
	
	
	public static ArrayList<String> header_section_alphabetics;
	public static ArrayList<String> header_section_category;
	public static ArrayList<String> header_section_floor;
	
	
	public static List<RestaurantPagerFragment> fragment_instences;
	public static ArrayList<RestaurantMenu> menu_array;
	public static ArrayList<String> TITLES;


}
