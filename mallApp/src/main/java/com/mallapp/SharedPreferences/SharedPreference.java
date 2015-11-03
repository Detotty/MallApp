package com.mallapp.SharedPreferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mallapp.Controllers.OffersNewsList;
import com.mallapp.Model.Offers_News;

public class SharedPreference {

	public static final String PREFS_NAME = "OFFERSS";
	public static final String FAVORITES = "CREATE_ENDORSEMENT";
	
	public SharedPreference() {
		super();
	}
	// This four methods are used for maintaining favorites.
	public void saveFavorites(Context context, ArrayList<Offers_News> obj) {
//		Log.e("save", "saveFavorites..."+obj.toString());
//		Editor editor;
//		SharedPreferences settings;
//		settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
//		editor = settings.edit();
//		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//		System.out.println(gson.toJson(obj));
//		String jsonEndorsement = gson.toJson(obj);
//		editor.putString(FAVORITES, jsonEndorsement);
//		editor.commit();
//		Log.e("save", "jsonEndorsement..."+jsonEndorsement);
		
		
		//MallApp_Application.writeEndorsments(context, obj);
	}

	public void addEndrosement(Context context, Offers_News obj) {
		
		ArrayList<Offers_News> endorsement_list = getOffersNews(context);
		if (endorsement_list == null){
			endorsement_list = new ArrayList<Offers_News>();
			obj.setId(0);
		}else if(endorsement_list.size() > 2){
			
			endorsement_list = new ArrayList<Offers_News>();
			obj.setId(0);
			
			SharedPreferences prefs = context.getSharedPreferences("Favourites", Context.MODE_PRIVATE);		
			String tiles_Arr 		= prefs.getString("arr_favourites", null);
			ArrayList<String> TITLES= new ArrayList<String>();
			
			if(tiles_Arr != null){
				tiles_Arr= tiles_Arr.replace("[", "");
				tiles_Arr= tiles_Arr.replace("]", "");
				TITLES = new ArrayList<String>(Arrays.asList(tiles_Arr.split(",")));
				
				if(TITLES.size()>1){
					TITLES= new ArrayList<String>();
					TITLES.add("All");
					tiles_Arr = TITLES.toString();
					tiles_Arr= tiles_Arr.replace("[", "");
					tiles_Arr= tiles_Arr.replace("]", "");
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("arr_favourites", tiles_Arr);
					editor.apply();
				}
			}
		}else
			obj.setId(endorsement_list.size());
			
		//endorsement_list.set(arg0, arg1);
		endorsement_list.add(obj);
		saveFavorites(context, endorsement_list);
	}

	public void removeEndrosement(Context context, Offers_News product) {
		ArrayList<Offers_News> favorites = getOffersNews(context);
		if (favorites != null) {
			favorites.remove(product);
			saveFavorites(context, favorites);
		}
	}
	
	
	public void updateEndrosement(Context context, Offers_News obj) {
		ArrayList<Offers_News> allEndorsementList = getOffersNews(context);
		if (allEndorsementList != null) {
			//Log.e("", "update endorsement_id..."+ obj.getId());
			//Log.e("fav", ""+ obj.isFavourite());
			allEndorsementList.set(obj.getId(), obj);
			Log.w("allEndorsementList.size() ",""+allEndorsementList.size());
			saveFavorites(context, allEndorsementList);
		}
	}

	public ArrayList<Offers_News> getOffersNews(Context context) {
		//return null;
		
//		SharedPreferences settings;
//		List<Endorsement_object> favorites;
//		settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
//
//		if (settings.contains(FAVORITES)) {
//			
//			String jsonFavorites = settings.getString(FAVORITES, null);
//			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//			Endorsement_object[] favoriteItems = gson.fromJson(jsonFavorites, Endorsement_object[].class);
//			favorites = Arrays.asList(favoriteItems);
//			favorites = new ArrayList<Endorsement_object>(favorites);
//			Log.e("get", "favoriteItems..."+favoriteItems.toString());
//		
//		} else
//			return null;
//		return (ArrayList<Endorsement_object>) favorites;
		
		return OffersNewsList.readOffersNews(context);
	}


	class ExceptionSerializer implements JsonSerializer<Exception>{
		@Override
		public JsonElement serialize(Exception src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("cause", new JsonPrimitive(String.valueOf(src.getCause())));
			jsonObject.add("message", new JsonPrimitive(src.getMessage()));
			return jsonObject;
		}
	}
}