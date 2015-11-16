package com.mallapp.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.FavCentersConstants;
import com.mallapp.Constants.GlobelProfile;
import com.mallapp.Model.FavouriteCenters;
import com.mallapp.utils.Log;



public class SharedPreferenceFavouriteCenters {
	
	private static final String TAG = "SharedPreferenceFavCenters";
	private static SharedPreferences favCentersPrefrence = null;
	
	private static void initPrefrence(Context mContext) {
		try {
			SharedPreferenceFavouriteCenters.favCentersPrefrence = mContext.getSharedPreferences(
																	AppConstants.PREF_CENTER_KEY, Context.MODE_PRIVATE);
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
	}

	
	public static void SaveFavCenters(FavouriteCenters favCenters, Context context) {
		try {
			if (SharedPreferenceFavouriteCenters.favCentersPrefrence == null) {
				SharedPreferenceFavouriteCenters.initPrefrence(context);
			}
			if (SharedPreferenceFavouriteCenters.favCentersPrefrence != null) {
				final Editor editor = SharedPreferenceFavouriteCenters.favCentersPrefrence.edit();
				
				editor.putInt(FavCentersConstants.ID, 	favCenters.getId());
				editor.putString(FavCentersConstants.CENTER_TITLE, 	 favCenters.getCenter_title());
				editor.putString(FavCentersConstants.CENTER_CITY, 	 favCenters.getCenter_city());
				editor.putString(FavCentersConstants.CENTER_LOCATION,favCenters.getCenter_location());
				editor.putString(FavCentersConstants.CENTER_LOGO, 	 favCenters.getCenter_logo());
				editor.putString(FavCentersConstants.CENTER_IMAGE, 	 favCenters.getCenter_image());
				editor.putBoolean(FavCentersConstants.CENTER_FAV, 	 favCenters.isIsfav());
				editor.apply();
			}
			
		} catch (final Exception exception) {
			Log.e(SharedPreferenceFavouriteCenters.TAG, "" + exception);
		}
	}

	public static FavouriteCenters getFavCenters(Context context) {
		try {
			if (SharedPreferenceFavouriteCenters.favCentersPrefrence == null) {
				SharedPreferenceFavouriteCenters.initPrefrence(context);
			}
			FavouriteCenters fav_centers= new FavouriteCenters();
			
			fav_centers.setId(favCentersPrefrence.getInt(FavCentersConstants.ID, 0));
			fav_centers.setCenter_title(favCentersPrefrence.getString(FavCentersConstants.CENTER_TITLE, null));
			fav_centers.setCenter_city(favCentersPrefrence.getString(FavCentersConstants.CENTER_CITY, null));
			fav_centers.setCenter_location(favCentersPrefrence.getString(FavCentersConstants.CENTER_LOCATION, null));
			fav_centers.setCenter_logo(favCentersPrefrence.getString(FavCentersConstants.CENTER_LOGO, null));
			fav_centers.setCenter_image(favCentersPrefrence.getString(FavCentersConstants.CENTER_IMAGE, null));
			
			
			return fav_centers;
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
		return null;
	}
	
	public static void DeleteFavCenters(Context context) {

		try {
			if (SharedPreferenceFavouriteCenters.favCentersPrefrence == null) {
				SharedPreferenceFavouriteCenters.initPrefrence(context);
			}
			if (SharedPreferenceFavouriteCenters.favCentersPrefrence != null) {
				
				final Editor editor = SharedPreferenceFavouriteCenters.favCentersPrefrence.edit();
				
				editor.putString(GlobelProfile.name, 	null);
				editor.putString(GlobelProfile.b_day, 	null);
				editor.putString(GlobelProfile.location,null);
				editor.putString(GlobelProfile.education,null);
				editor.putString(GlobelProfile.gender, 	null);
				editor.putString(GlobelProfile.country_name, null);
		 		editor.putString(GlobelProfile.country_code, null);
		 		editor.putString(GlobelProfile.mobile_no, null);
		 		editor.putBoolean(GlobelProfile.push_notification, true);
				editor.putString(GlobelProfile.profile_image, null);
				editor.apply();
			}
		} catch (final Exception exception) {
			Log.e(SharedPreferenceFavouriteCenters.TAG, "" + exception);
		}
	}
}
