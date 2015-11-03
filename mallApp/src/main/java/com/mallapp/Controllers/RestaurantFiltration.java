package com.mallapp.Controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mallapp.Model.Restaurant;
import com.mallapp.Model.RestaurantMenu;
import com.mallapp.View.R;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.utils.Log;

public class RestaurantFiltration {
	
	
	public static HashMap<String, ArrayList<Restaurant>> filterFavouriteRestaurantAlphabetically(String favShopFilter, 
														ArrayList<Restaurant> favourite_shop_List) {
		
		HashMap<String, ArrayList<Restaurant>> mainDictionary  	= new HashMap<String, ArrayList<Restaurant>>();
		ArrayList<String> 				mainSectionArray		= new ArrayList<String>();
		String current_section_header	= null;
		ArrayList<Restaurant> rest_list = null;
		
		
		favourite_shop_List = sortRestaurantList(favourite_shop_List);
		for(int i=0; i< favourite_shop_List.size() ; i++){
		
			Restaurant rest_obj = favourite_shop_List.get(i);
			
			if(rest_obj != null){
				String rest_name= rest_obj.getName();
				
				if(rest_name != null && rest_name.length()>0){
					
					String fisrt_char = rest_name.substring(0, 1).toUpperCase(Locale.getDefault());
					if(current_section_header == null || current_section_header.length()== 0)
						current_section_header= fisrt_char;
					
					if(mainSectionArray.size()>0 
							&& mainSectionArray.contains(fisrt_char)
							&& current_section_header.equals(fisrt_char)){
						
						rest_list.add(rest_obj);
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, rest_list);
						}
					}else if(!current_section_header.equals(fisrt_char)){
						
						
						mainDictionary.put(current_section_header, rest_list);
						rest_list = new ArrayList<Restaurant>();
						rest_list.add(rest_obj);
						mainSectionArray.add(fisrt_char);
						current_section_header= fisrt_char;
						
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, rest_list);
						}
					
					
					}else{
						rest_list = new ArrayList<Restaurant>();
						rest_list.add(rest_obj);
						mainSectionArray.add(fisrt_char);
					}
				}
			}
		}
		GlobelRestaurants.header_section_alphabetics= mainSectionArray;
		return mainDictionary;
	}

	
	public static HashMap<String, ArrayList<Restaurant>>  filterFavouriteRestaurantCategory(String favShopFilter, 
				ArrayList<Restaurant> favourite_shop_List) {

		HashMap<String, ArrayList<Restaurant>> mainDictionary  
											= new HashMap<String, ArrayList<Restaurant>>();
		ArrayList<String> mainSectionArray 	= new ArrayList<String>();
		String current_section_header		= null;
		ArrayList<Restaurant> shop_list 	= null;
		
		
		favourite_shop_List = sortRestaurantListCategory(favourite_shop_List);
		for(int i=0; i< favourite_shop_List.size() ; i++){
			
			Restaurant rest_obj 		= favourite_shop_List.get(i);
			if(rest_obj != null){
				
				String shop_category= rest_obj.getCatagory();
				
				if(shop_category != null && shop_category.length()>0){
					
					if(current_section_header == null || current_section_header.length()== 0)
						current_section_header= shop_category;
					
					if(mainSectionArray.size()>0 
							&& mainSectionArray.contains(shop_category)
							&& current_section_header.equals(shop_category)){
						
						shop_list.add(rest_obj);
						if(i+1==favourite_shop_List.size() ){
							Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
							mainDictionary.put(current_section_header, shop_list);
							
						}
		
					}else if(!current_section_header.equals(shop_category)){
						Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
						mainDictionary.put(current_section_header, shop_list);
						
						shop_list = new ArrayList<Restaurant>();
						shop_list.add(rest_obj);
						mainSectionArray.add(shop_category);
						current_section_header= shop_category;

						if(i+1==favourite_shop_List.size() ){
							Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
							mainDictionary.put(current_section_header, shop_list);
						}
					}else{
						shop_list = new ArrayList<Restaurant>();
						shop_list.add(rest_obj);
						mainSectionArray.add(shop_category);
					}
				}
			}
		}
		GlobelRestaurants.header_section_category= mainSectionArray;
		return mainDictionary;
	}

	
	public static HashMap<String, ArrayList<Restaurant>>  filterFavouriteRestaurantFloor(String favShopFilter, 
			ArrayList<Restaurant> favourite_shop_List) {

		HashMap<String, ArrayList<Restaurant>> mainDictionary	= new HashMap<String, ArrayList<Restaurant>>();
		ArrayList<String> mainSectionArray					= new ArrayList<String>();
		String current_section_header= null;
		ArrayList<Restaurant> shop_list = null;
		
		
		favourite_shop_List = sortRestaurantListFloor(favourite_shop_List);
		
		for(int i=0; i< favourite_shop_List.size() ; i++){
			Restaurant rest_obj = favourite_shop_List.get(i);
			
			if(rest_obj != null){
			
				String shop_category= rest_obj.getFloor_no();
				
				if(shop_category != null && shop_category.length()>0){
					//String fisrt_char = shop_category.substring(0, 1).toUpperCase(Locale.getDefault());
					if(current_section_header == null || current_section_header.length()== 0)
						current_section_header= shop_category;
					
					if(mainSectionArray.size()>0 
							&& mainSectionArray.contains(shop_category)
							&& current_section_header.equals(shop_category)){
						
						shop_list.add(rest_obj);
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
					}
					
					else if(!current_section_header.equals(shop_category)){
						
						mainDictionary.put(current_section_header, shop_list);
						
						shop_list = new ArrayList<Restaurant>();
						shop_list.add(rest_obj);
						mainSectionArray.add(shop_category);
						current_section_header= shop_category;
	
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
					}else{
						shop_list = new ArrayList<Restaurant>();
						shop_list.add(rest_obj);
						mainSectionArray.add(shop_category);
					}
				}
			}
		}
		GlobelRestaurants.header_section_floor= mainSectionArray;
		return mainDictionary;
	}
	
	
	
	
	private static ArrayList<Restaurant> sortRestaurantList(ArrayList<Restaurant> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<Restaurant>() {
			@Override
			public int compare(Restaurant lhs, Restaurant rhs) {
				return lhs.getName().compareTo(rhs.getName());
			}
		});
		return favourite_shop_List;
	}
	
	private static ArrayList<Restaurant> sortRestaurantListCategory(ArrayList<Restaurant> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<Restaurant>() {
			@Override
			public int compare(Restaurant lhs, Restaurant rhs) {
				return lhs.getCatagory().compareTo(rhs.getCatagory());
			}
		});
		return favourite_shop_List;
	}
	
	private static ArrayList<Restaurant> sortRestaurantListFloor(ArrayList<Restaurant> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<Restaurant>() {
			@Override
			public int compare(Restaurant lhs, Restaurant rhs) {
				return lhs.getFloor_no().compareTo(rhs.getFloor_no());
			}
		});
		return favourite_shop_List;
	}



	
	public static LinkedHashMap<String, Integer> getIndexList(Resources res) {
		String[] alphabetic= res.getStringArray(R.array.alphabetic_list);
		
		LinkedHashMap<String, Integer> mapIndex = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < alphabetic.length; i++) {
			String fruit = alphabetic[i];
			String index = fruit.substring(0, 1);

			if (mapIndex.get(index) == null)
				mapIndex.put(index, i);
		}
		return mapIndex;
	}
	

	public static Drawable getShopLogo(Context context,String image_nam) {
		int imageResource = context.getResources().getIdentifier(image_nam, "drawable", context.getPackageName());
		Drawable d = context.getResources().getDrawable(imageResource);
		
		
		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
		int mDstWidth 	= context.getResources().getDimensionPixelSize(R.dimen.createview_destination_width);
        int mDstHeight 	= context.getResources().getDimensionPixelSize(R.dimen.createview_destination_height);
		
        return d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth,mDstHeight, true));
	}
	
	public static Drawable getShopLogo(Context context,int imageResource) {
		//int imageResource = context.getResources().getIdentifier(image_nam, "drawable", context.getPackageName());
		Drawable d = context.getResources().getDrawable(imageResource);
		
		
		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
		int mDstWidth 	= context.getResources().getDimensionPixelSize(R.dimen.createview_destination_width);
        int mDstHeight 	= context.getResources().getDimensionPixelSize(R.dimen.createview_destination_height);
		
        return d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth,mDstHeight, true));
	}
	
	
	
	public static ArrayList<RestaurantMenu> filterRestaurantMenu(String favourite, 
			ArrayList<RestaurantMenu> favourite_center_List) {

		ArrayList<RestaurantMenu> favourite_center_Filter_List = new ArrayList<RestaurantMenu>();
		for(RestaurantMenu menu: favourite_center_List){
				if(favourite.equals(menu.getType().trim())){
					favourite_center_Filter_List.add(menu);
				}
			}
		return favourite_center_Filter_List;
	}
}
