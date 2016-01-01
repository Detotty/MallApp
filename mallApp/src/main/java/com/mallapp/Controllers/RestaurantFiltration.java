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
import com.mallapp.Model.RestaurantCategoriesModel;
import com.mallapp.Model.RestaurantMenu;
import com.mallapp.Model.RestaurantModel;
import com.mallapp.Model.ShopsModel;
import com.mallapp.View.R;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.utils.Log;

public class RestaurantFiltration {
	
	
	public static HashMap<String, ArrayList<RestaurantModel>> filterFavouriteRestaurantAlphabetically(String favShopFilter,
														ArrayList<RestaurantModel> favourite_shop_List) {
		
		HashMap<String, ArrayList<RestaurantModel>> mainDictionary  	= new HashMap<String, ArrayList<RestaurantModel>>();
		ArrayList<String> 				mainSectionArray		= new ArrayList<String>();
		String current_section_header	= null;
		ArrayList<RestaurantModel> rest_list = null;
		
		
		favourite_shop_List = sortRestaurantList(favourite_shop_List);
		for(int i=0; i< favourite_shop_List.size() ; i++){

			RestaurantModel rest_obj = favourite_shop_List.get(i);
			
			if(rest_obj != null){
				String rest_name= rest_obj.getRestaurantName();
				
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
						rest_list = new ArrayList<RestaurantModel>();
						rest_list.add(rest_obj);
						mainSectionArray.add(fisrt_char);
						current_section_header= fisrt_char;
						
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, rest_list);
						}
					
					
					}else{
						rest_list = new ArrayList<RestaurantModel>();
						rest_list.add(rest_obj);
						mainSectionArray.add(fisrt_char);
					}
				}
			}
		}
		GlobelRestaurants.header_section_alphabetics= mainSectionArray;
		return mainDictionary;
	}

	
	public static HashMap<String, ArrayList<RestaurantModel>>  filterFavouriteRestaurantCategory(String favShopFilter,
				ArrayList<RestaurantModel> favourite_shop_List) {

		HashMap<String, ArrayList<RestaurantModel>> mainDictionary
											= new HashMap<String, ArrayList<RestaurantModel>>();
		ArrayList<String> mainSectionArray 	= new ArrayList<String>();
		String current_section_header		= null;
		ArrayList<RestaurantModel> shop_list 	= null;
		
		
		favourite_shop_List = sortRestaurantListCategory(favourite_shop_List);
		for(int i=0; i< favourite_shop_List.size() ; i++){
			
			RestaurantModel rest_obj 		= favourite_shop_List.get(i);
			if(rest_obj != null){

				RestaurantCategoriesModel[] shop_category= rest_obj.getRestaurantCategories();

				for (RestaurantCategoriesModel shopCat:
						shop_category) {
					if(shopCat != null && shopCat.getCategoryName().length()>0){

						if(current_section_header == null || current_section_header.length()== 0)
							current_section_header= shopCat.getCategoryName();

						if(mainSectionArray.size()>0
								&& mainSectionArray.contains(shopCat.getCategoryName())
								&& current_section_header.equals(shopCat.getCategoryName())){

							shop_list.add(rest_obj);
							if(i+1==favourite_shop_List.size() ){
								Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
								mainDictionary.put(current_section_header, shop_list);

							}

						}else if(!current_section_header.equals(shopCat.getCategoryName())){
//							Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
							mainDictionary.put(current_section_header, shop_list);

							shop_list = new ArrayList<RestaurantModel>();
							shop_list.add(rest_obj);
							mainSectionArray.add(shopCat.getCategoryName());
							current_section_header= shopCat.getCategoryName();

							if(i+1==favourite_shop_List.size() ){
								Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
								mainDictionary.put(current_section_header, shop_list);
							}
						}else{
							shop_list = new ArrayList<RestaurantModel>();
							shop_list.add(rest_obj);
							mainSectionArray.add(shopCat.getCategoryName());
						}
					}

				}

			}
		}
		GlobelRestaurants.header_section_category= mainSectionArray;
		return mainDictionary;
	}

	
	public static HashMap<String, ArrayList<RestaurantModel>>  filterFavouriteRestaurantFloor(String favShopFilter,
			ArrayList<RestaurantModel> favourite_shop_List) {

		HashMap<String, ArrayList<RestaurantModel>> mainDictionary	= new HashMap<String, ArrayList<RestaurantModel>>();
		ArrayList<String> mainSectionArray					= new ArrayList<String>();
		String current_section_header= null;
		ArrayList<RestaurantModel> shop_list = null;
		
		
		favourite_shop_List = sortRestaurantListFloor(favourite_shop_List);
		
		for(int i=0; i< favourite_shop_List.size() ; i++){
			RestaurantModel rest_obj = favourite_shop_List.get(i);
			
			if(rest_obj != null){
			
				String shop_category= rest_obj.getFloor();
				
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
						
						shop_list = new ArrayList<RestaurantModel>();
						shop_list.add(rest_obj);
						mainSectionArray.add(shop_category);
						current_section_header= shop_category;
	
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
					}else{
						shop_list = new ArrayList<RestaurantModel>();
						shop_list.add(rest_obj);
						mainSectionArray.add(shop_category);
					}
				}
			}
		}
		GlobelRestaurants.header_section_floor= mainSectionArray;
		return mainDictionary;
	}
	
	
	
	
	private static ArrayList<RestaurantModel> sortRestaurantList(ArrayList<RestaurantModel> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<RestaurantModel>() {
			@Override
			public int compare(RestaurantModel lhs, RestaurantModel rhs) {
				return lhs.getRestaurantName().compareTo(rhs.getRestaurantName());
			}
		});
		return favourite_shop_List;
	}
	
	private static ArrayList<RestaurantModel> sortRestaurantListCategory(ArrayList<RestaurantModel> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<RestaurantModel>() {
			@Override
			public int compare(RestaurantModel lhs, RestaurantModel rhs) {
				return lhs.getRestaurantName().compareTo(rhs.getRestaurantName());
			}
		});
		return favourite_shop_List;
	}
	
	private static ArrayList<RestaurantModel> sortRestaurantListFloor(ArrayList<RestaurantModel> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<RestaurantModel>() {
			@Override
			public int compare(RestaurantModel lhs, RestaurantModel rhs) {
				return lhs.getFloor().compareTo(rhs.getFloor());
			}
		});
		return favourite_shop_List;
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

	public static HashMap<String, ArrayList<RestaurantModel>>  filterFavouriteRestCategory(String favShopFilter,
																							ArrayList<RestaurantModel> favourite_shop_List) {

		HashMap<String, ArrayList<RestaurantModel>> mainDictionary
				= new HashMap<String, ArrayList<RestaurantModel>>();
		ArrayList<String> mainSectionArray 	= new ArrayList<String>();
		String current_section_header		= null;
		ArrayList<RestaurantModel> shop_list 	= null;


		favourite_shop_List = sortRestaurantListCategorys(favourite_shop_List);
		for(int i=0; i< favourite_shop_List.size() ; i++){

			RestaurantModel rest_obj 		= favourite_shop_List.get(i);
			if(rest_obj != null){

				String shop_category= rest_obj.getCat();

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

						shop_list = new ArrayList<RestaurantModel>();
						shop_list.add(rest_obj);
						mainSectionArray.add(shop_category);
						current_section_header= shop_category;

						if(i+1==favourite_shop_List.size() ){
							Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
							mainDictionary.put(current_section_header, shop_list);
						}
					}else{
						shop_list = new ArrayList<RestaurantModel>();
						shop_list.add(rest_obj);
						mainSectionArray.add(shop_category);
					}
				}
			}
		}
		GlobelRestaurants.header_section_category= mainSectionArray;
		return mainDictionary;
	}

	private static ArrayList<RestaurantModel> sortRestaurantListCategorys(ArrayList<RestaurantModel> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<RestaurantModel>() {
			@Override
			public int compare(RestaurantModel lhs, RestaurantModel rhs) {
				return lhs.getCat().compareTo(rhs.getCat());
			}
		});
		return favourite_shop_List;
	}
}
