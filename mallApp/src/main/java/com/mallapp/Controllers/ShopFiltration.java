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
import android.util.Pair;

import com.mallapp.Model.ShopCategoriesModel;
import com.mallapp.Model.Shops;
import com.mallapp.Model.ShopsModel;
import com.mallapp.View.R;
import com.mallapp.globel.GlobelShops;
import com.mallapp.utils.Log;

public class ShopFiltration {
	
	
	public static HashMap<String, ArrayList<Shops>> filterFavouriteShopsAlphabetically(String favShopFilter, ArrayList<Shops> favourite_shop_List) {
		//ArrayList<Shops> favourite_shop_Filter_List = new ArrayList<Shops>();
		
		HashMap<String, ArrayList<Shops>> mainDictionary  	= new HashMap<String, ArrayList<Shops>>();
		ArrayList<String> 				mainSectionArray	= new ArrayList<String>();
		//Pair<String, ArrayList<Shops>> pair_shop_object;
		String current_section_header= null;
		ArrayList<Shops> shop_list = null;
		
		
		favourite_shop_List = sortShopsList(favourite_shop_List);
		
		for(int i=0; i< favourite_shop_List.size() ; i++){
			Shops shop_obj = favourite_shop_List.get(i);
			
			if(shop_obj != null){
				String shop_name= shop_obj.getName();
				
				if(shop_name != null && shop_name.length()>0){
					String fisrt_char = shop_name.substring(0, 1).toUpperCase(Locale.getDefault());
					
					if(current_section_header == null || current_section_header.length()== 0)
						current_section_header= fisrt_char;
					
					
					if(mainSectionArray.size()>0 
							&& mainSectionArray.contains(fisrt_char)
							&& current_section_header.equals(fisrt_char)){
						shop_list.add(shop_obj);
						
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
					}else if(!current_section_header.equals(fisrt_char)){
						mainDictionary.put(current_section_header, shop_list);
						
						shop_list = new ArrayList<Shops>();
						shop_list.add(shop_obj);
						mainSectionArray.add(fisrt_char);
						current_section_header= fisrt_char;
						
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
					}else{
						shop_list = new ArrayList<Shops>();
						shop_list.add(shop_obj);
						mainSectionArray.add(fisrt_char);
					}
				}
			}
		}
		GlobelShops.header_section_alphabetics= mainSectionArray;
		return mainDictionary;
	}


	public static HashMap<String, ArrayList<ShopsModel>> filterFavouriteShopsModelAlphabetically(String favShopFilter, ArrayList<ShopsModel> favourite_shop_List) {
		//ArrayList<Shops> favourite_shop_Filter_List = new ArrayList<Shops>();

		HashMap<String, ArrayList<ShopsModel>> mainDictionary  	= new HashMap<String, ArrayList<ShopsModel>>();
		ArrayList<String> 				mainSectionArray	= new ArrayList<String>();
		//Pair<String, ArrayList<Shops>> pair_shop_object;
		String current_section_header= null;
		ArrayList<ShopsModel> shop_list = null;


		favourite_shop_List = sortShopsModelList(favourite_shop_List);

		for(int i=0; i< favourite_shop_List.size() ; i++){
			ShopsModel shop_obj = favourite_shop_List.get(i);

			if(shop_obj != null){
				String shop_name= shop_obj.getStoreName();

				if(shop_name != null && shop_name.length()>0){
					String fisrt_char = shop_name.substring(0, 1).toUpperCase(Locale.getDefault());

					if(current_section_header == null || current_section_header.length()== 0)
						current_section_header= fisrt_char;


					if(mainSectionArray.size()>0
							&& mainSectionArray.contains(fisrt_char)
							&& current_section_header.equals(fisrt_char)){
						shop_list.add(shop_obj);

						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
					}else if(!current_section_header.equals(fisrt_char)){
						mainDictionary.put(current_section_header, shop_list);

						shop_list = new ArrayList<ShopsModel>();
						shop_list.add(shop_obj);
						mainSectionArray.add(fisrt_char);
						current_section_header= fisrt_char;

						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
					}else{
						shop_list = new ArrayList<ShopsModel>();
						shop_list.add(shop_obj);
						mainSectionArray.add(fisrt_char);
					}
				}
			}
		}
		GlobelShops.header_section_alphabetics= mainSectionArray;
		return mainDictionary;
	}

	public static HashMap<String, ArrayList<Shops>>  filterFavouriteShopsCategory(String favShopFilter,
				ArrayList<Shops> favourite_shop_List) {

		HashMap<String, ArrayList<Shops>> mainDictionary
											= new HashMap<String, ArrayList<Shops>>();
		ArrayList<String> mainSectionArray 	= new ArrayList<String>();
		String current_section_header		= null;
		ArrayList<Shops> shop_list 			= null;
		
		
		favourite_shop_List = sortShopsListCategory(favourite_shop_List);
		
		for(int i=0; i< favourite_shop_List.size() ; i++){
			Shops shop_obj 		= favourite_shop_List.get(i);
			
			if(shop_obj != null){
				
				String shop_category= shop_obj.getCatagory();
				
				if(shop_category != null && shop_category.length()>0){
					
					if(current_section_header == null || current_section_header.length()== 0)
						current_section_header= shop_category;
					
					if(mainSectionArray.size()>0 
							&& mainSectionArray.contains(shop_category)
							&& current_section_header.equals(shop_category)){
						
						shop_list.add(shop_obj);
						if(i+1==favourite_shop_List.size() ){
							Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
							mainDictionary.put(current_section_header, shop_list);
							
						}
		
					}else if(!current_section_header.equals(shop_category)){
						Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
						mainDictionary.put(current_section_header, shop_list);
						
						shop_list = new ArrayList<Shops>();
						shop_list.add(shop_obj);
						mainSectionArray.add(shop_category);
						current_section_header= shop_category;

						if(i+1==favourite_shop_List.size() ){
							Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
							mainDictionary.put(current_section_header, shop_list);
						}
					}else{
						shop_list = new ArrayList<Shops>();
						shop_list.add(shop_obj);
						mainSectionArray.add(shop_category);
					}
				}
			}
		}
		GlobelShops.header_section_category= mainSectionArray;
		return mainDictionary;
	}



	public static HashMap<String, ArrayList<ShopsModel>>  filterFavouriteShopsModelCategory(String favShopFilter,
																				  ArrayList<ShopsModel> favourite_shop_List) {

		HashMap<String, ArrayList<ShopsModel>> mainDictionary
				= new HashMap<String, ArrayList<ShopsModel>>();
		ArrayList<String> mainSectionArray 	= new ArrayList<String>();
		String current_section_header		= null;
		ArrayList<ShopsModel> shop_list 			= null;


		favourite_shop_List = sortShopsModelListCategory(favourite_shop_List);

		for(int i=0; i< favourite_shop_List.size() ; i++){
			ShopsModel shop_obj 		= favourite_shop_List.get(i);

			if(shop_obj != null){

				ShopCategoriesModel[] shop_category= shop_obj.getShopCategories();

				for (ShopCategoriesModel shopCat:
						shop_category) {
					if(shopCat != null && shopCat.getCategoryName().length()>0){

						if(current_section_header == null || current_section_header.length()== 0)
							current_section_header= shopCat.getCategoryName();

						if(mainSectionArray.size()>0
								&& mainSectionArray.contains(shopCat)
								&& current_section_header.equals(shopCat)){

							shop_list.add(shop_obj);
							if(i+1==favourite_shop_List.size() ){
								Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
								mainDictionary.put(current_section_header, shop_list);

							}

						}else if(!current_section_header.equals(shop_category)){
//							Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
							mainDictionary.put(current_section_header, shop_list);

							shop_list = new ArrayList<ShopsModel>();
							shop_list.add(shop_obj);
							mainSectionArray.add(shopCat.getCategoryName());
							current_section_header= shopCat.getCategoryName();

							if(i+1==favourite_shop_List.size() ){
								Log.e("", "shop_list of "+current_section_header+" = "+shop_list.size());
								mainDictionary.put(current_section_header, shop_list);
							}
						}else{
							shop_list = new ArrayList<ShopsModel>();
							shop_list.add(shop_obj);
							mainSectionArray.add(shopCat.getCategoryName());
						}
					}
				}

			}
		}
		GlobelShops.header_section_category= mainSectionArray;
		return mainDictionary;
	}

	
	public static HashMap<String, ArrayList<Shops>>  filterFavouriteShopsFloor(String favShopFilter, 
			ArrayList<Shops> favourite_shop_List) {

		HashMap<String, ArrayList<Shops>> mainDictionary	= new HashMap<String, ArrayList<Shops>>();
		ArrayList<String> mainSectionArray					= new ArrayList<String>();
		String current_section_header= null;
		ArrayList<Shops> shop_list = null;
		
		
		favourite_shop_List = sortShopsListFloor(favourite_shop_List);
		
		for(int i=0; i< favourite_shop_List.size() ; i++){
			Shops shop_obj = favourite_shop_List.get(i);
			
			if(shop_obj != null){
			
				String shop_category= shop_obj.getFloor_no();
				
				if(shop_category != null && shop_category.length()>0){
					//String fisrt_char = shop_category.substring(0, 1).toUpperCase(Locale.getDefault());
					if(current_section_header == null || current_section_header.length()== 0)
						current_section_header= shop_category;
					
					if(mainSectionArray.size()>0 
							&& mainSectionArray.contains(shop_category)
							&& current_section_header.equals(shop_category)){
						
						shop_list.add(shop_obj);
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
						
					}
					
					else if(!current_section_header.equals(shop_category)){
						
						mainDictionary.put(current_section_header, shop_list);
						
						shop_list = new ArrayList<Shops>();
						shop_list.add(shop_obj);
						mainSectionArray.add(shop_category);
						current_section_header= shop_category;
	
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
					}else{
						shop_list = new ArrayList<Shops>();
						shop_list.add(shop_obj);
						mainSectionArray.add(shop_category);
					}
				}
			}
		}
		GlobelShops.header_section_floor= mainSectionArray;
		return mainDictionary;
	}

	public static HashMap<String, ArrayList<ShopsModel>>  filterFavouriteShopsModelFloor(String favShopFilter,
			ArrayList<ShopsModel> favourite_shop_List) {

		HashMap<String, ArrayList<ShopsModel>> mainDictionary	= new HashMap<String, ArrayList<ShopsModel>>();
		ArrayList<String> mainSectionArray					= new ArrayList<String>();
		String current_section_header= null;
		ArrayList<ShopsModel> shop_list = null;


		favourite_shop_List = sortShopsModelListFloor(favourite_shop_List);

		for(int i=0; i< favourite_shop_List.size() ; i++){
			ShopsModel shop_obj = favourite_shop_List.get(i);

			if(shop_obj != null){

				String shop_category= shop_obj.getFloor();

				if(shop_category != null && shop_category.length()>0){
					//String fisrt_char = shop_category.substring(0, 1).toUpperCase(Locale.getDefault());
					if(current_section_header == null || current_section_header.length()== 0)
						current_section_header= shop_category;

					if(mainSectionArray.size()>0
							&& mainSectionArray.contains(shop_category)
							&& current_section_header.equals(shop_category)){

						shop_list.add(shop_obj);
						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}

					}

					else if(!current_section_header.equals(shop_category)){

						mainDictionary.put(current_section_header, shop_list);

						shop_list = new ArrayList<ShopsModel>();
						shop_list.add(shop_obj);
						mainSectionArray.add(shop_category);
						current_section_header= shop_category;

						if(i+1==favourite_shop_List.size() ){
							mainDictionary.put(current_section_header, shop_list);
						}
					}else{
						shop_list = new ArrayList<ShopsModel>();
						shop_list.add(shop_obj);
						mainSectionArray.add(shop_category);
					}
				}
			}
		}
		GlobelShops.header_section_floor= mainSectionArray;
		return mainDictionary;
	}
	
	
	
	
	private static ArrayList<Shops> sortShopsList(ArrayList<Shops> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<Shops>() {
			@Override
			public int compare(Shops lhs, Shops rhs) {
				return lhs.getName().compareTo(rhs.getName());
			}
		});
		return favourite_shop_List;
	}

	private static ArrayList<ShopsModel> sortShopsModelList(ArrayList<ShopsModel> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<ShopsModel>() {
			@Override
			public int compare(ShopsModel lhs, ShopsModel rhs) {
				return lhs.getStoreName().compareTo(rhs.getStoreName());
			}
		});
		return favourite_shop_List;
	}
	
	private static ArrayList<Shops> sortShopsListCategory(ArrayList<Shops> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<Shops>() {
			@Override
			public int compare(Shops lhs, Shops rhs) {
				return lhs.getCatagory().compareTo(rhs.getCatagory());
			}
		});
		return favourite_shop_List;
	}


	private static ArrayList<ShopsModel> sortShopsModelListCategory(ArrayList<ShopsModel> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<ShopsModel>() {
			@Override
			public int compare(ShopsModel lhs, ShopsModel rhs) {

				return lhs.getStoreName().compareTo(rhs.getStoreName());
			}
		});
		return favourite_shop_List;
	}

	
	private static ArrayList<Shops> sortShopsListFloor(ArrayList<Shops> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<Shops>() {
			@Override
			public int compare(Shops lhs, Shops rhs) {
				return lhs.getFloor_no().compareTo(rhs.getFloor_no());
			}
		});
		return favourite_shop_List;
	}

	private static ArrayList<ShopsModel> sortShopsModelListFloor(ArrayList<ShopsModel> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<ShopsModel>() {
			@Override
			public int compare(ShopsModel lhs, ShopsModel rhs) {
				return lhs.getFloor().compareTo(rhs.getFloor());
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
	
	
	
	
	
	
	
	
	
	public static ArrayList<Pair<String, ArrayList<Shops>>> filterFavouriteShopsAlphabetically1(String favShopFilter, 
			ArrayList<Shops> favourite_shop_List) {
		
		//ArrayList<Shops> favourite_shop_Filter_List = new ArrayList<Shops>();

		ArrayList<Pair<String, ArrayList<Shops>>> mainDictionary  	= new ArrayList<Pair<String, ArrayList<Shops>>>();
		ArrayList<String> 					mainSectionArray  		= new ArrayList<String>();
		Pair<String, ArrayList<Shops>> pair_shop_object;
		String current_section_header= null;
		ArrayList<Shops> shop_list = null;
		
		
		favourite_shop_List = sortShopsList(favourite_shop_List);
		//Log.e("filter array", "shops_all_audience : " + favourite_shop_List.size());
		
		for(int i=0; i< favourite_shop_List.size() ; i++){
			Shops shop_obj = favourite_shop_List.get(i);
			
			if(shop_obj != null){
				String shop_name= shop_obj.getName();
				//Log.e("filter array", "shop name = "+shop_name);

				if(shop_name != null && shop_name.length()>0){
					String fisrt_char = shop_name.substring(0, 1).toUpperCase(Locale.getDefault());
					
					if(current_section_header == null || current_section_header.length()== 0)
						current_section_header= fisrt_char;
					
					//Log.e("filter array", "shop first alphabetic = "+fisrt_char);
					
					if(mainSectionArray.size()>0 
							&& mainSectionArray.contains(fisrt_char)
							&& current_section_header.equals(fisrt_char)){
						shop_list.add(shop_obj);

					}else if(!current_section_header.equals(fisrt_char)){

						pair_shop_object= new Pair<String, ArrayList<Shops>>(current_section_header, shop_list);
						mainDictionary.add(pair_shop_object);
						//Log.e("filter array", "current_section_header   = "+current_section_header);
						//Log.e("filter array", "shop_list			    = "+shop_list.size());
						//Log.e("filter array", "mainDictionary size 		= "+mainDictionary.size());
						//Log.e("filter array", "mainSectionArray size 	= "+mainSectionArray.size());
						//Log.e("filter array", "index  size 	= "+i);

						shop_list = new ArrayList<Shops>();
						shop_list.add(shop_obj);
						mainSectionArray.add(fisrt_char);
						current_section_header= fisrt_char;

						if(i+1==favourite_shop_List.size() ){
							pair_shop_object= new Pair<String, ArrayList<Shops>>(current_section_header, shop_list);
							mainDictionary.add(pair_shop_object);
							//Log.e("filter array", "current_section_header   = "+current_section_header);
							//Log.e("filter array", "shop_list			    = "+shop_list.size());
							//Log.e("filter array", "mainDictionary size 		= "+mainDictionary.size());
							//Log.e("filter array", "mainSectionArray size 	= "+mainSectionArray.size());
							//Log.e("filter array", "index  size 	= "+i);
						}
					}else{
						shop_list = new ArrayList<Shops>();
						shop_list.add(shop_obj);
						mainSectionArray.add(fisrt_char);
						//createNewAlphabeticArray(shop_obj, fisrt_char, shop_list, mainSectionArray);
					}
				}
			}
		}
		//Log.e("filter array", "mainDictionary size = "+mainDictionary.size());
		return mainDictionary;
	}
	
	
	public static ArrayList<Pair<String, ArrayList<Drawable>>> getShopsImagesList(Context context, 
														ArrayList<Pair<String, ArrayList<Shops>>>  shops_list_filter){
		
		ArrayList<Pair<String, ArrayList<Drawable>>> shops_images_list = new ArrayList<Pair<String, ArrayList<Drawable>>>();
//		for(int i=0; i< shops_list_filter.size(); i++){
//			Pair<String, ArrayList<Shops>> pair_obj= shops_list_filter.get(i);
//			
//		}
//		for(Shops object : shops_list_filter) {
//			String image_nam= object.getLogo_image();
//			Drawable d= getShopLogo(context, image_nam);
//			
//			// Set your new, scaled drawable "d"
//			shops_images_list.add(d);
//		}
		
		return shops_images_list;
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
	
	

}
