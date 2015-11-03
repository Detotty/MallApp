package com.mallapp.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

import com.mallapp.Constants.AppConstants;
import com.mallapp.utils.Log;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceFavourites {

	/** The Constant TAG. */
	private static final String TAG = "SharedPreferenceFavourites";
	/**
	 * key of array
	 */
	public static final String KEY = "FAV_LIST";
	/** The usr preference. */
	private static SharedPreferences favPrefrence = null;

	/**
	 * Inits the preference.
	 * 
	 * @param mContext
	 *            The context
	 */
	private static void initPrefrence(Context mContext) {
		try {
			SharedPreferenceFavourites.favPrefrence = mContext.
					getSharedPreferences(AppConstants.PREF_FAV_KEY, Context.MODE_PRIVATE);
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
	}

	public static void	saveFavouritesArray(Context context,String value) {
		
		ArrayList<String> TITLES= getFavouritesList(context);
		if(TITLES == null  || TITLES.size() ==0){
			TITLES= new ArrayList<String>();
			TITLES.add(value);
		}else
			TITLES.add(value);
		
		String tiles_Arr = TITLES.toString();
		saveArrayList(context, KEY, tiles_Arr);
	}
	
	/**
	 * Save value.
	 * 
	 * @param context
	 *            the context
	 * @param value
	 *            the value
	 * @param key
	 *            the key
	 */
	private static void saveArrayList(Context context, String key, String value) {
		try {
			if (SharedPreferenceFavourites.favPrefrence == null) {
				SharedPreferenceFavourites.initPrefrence(context);
			}
			if (SharedPreferenceFavourites.favPrefrence != null) {
				final Editor editing = SharedPreferenceFavourites.favPrefrence.edit();
				try {
					editing.remove(key);// whole array remove
				} catch (final Exception exception) {
					Log.e(SharedPreferenceFavourites.TAG, "" + exception);
				}
				editing.putString(key, value);
				editing.commit();
			}
		} catch (final Exception exception) {
			Log.e(SharedPreferenceFavourites.TAG, "" + exception);
		}
	}

	/**
	 * Gets the value.
	 * 
	 * @param context
	 *            the context
	 * @param key
	 *            the key
	 * @return the value
	 */
	private static String getArrayList(Context context, String key) {
		try {
			if (SharedPreferenceFavourites.favPrefrence == null) {
				SharedPreferenceFavourites.initPrefrence(context);
			}
			return SharedPreferenceFavourites.favPrefrence.getString(key, null);
		} catch (final Exception exception) {
			Log.e(SharedPreferenceFavourites.TAG, "" + exception);
		}
		
		return null;
	}	
	
	
	public static ArrayList<String> getFavouritesList(Context context) {
		String jsonFavorites = getArrayList(context, KEY);
		if(jsonFavorites == null)
			return null;
		
		jsonFavorites= jsonFavorites.replace("[", "");
		jsonFavorites= jsonFavorites.replace("]", "");
		ArrayList<String> TITLES = new ArrayList<String>(Arrays.asList(jsonFavorites.split(",")));
		if(TITLES.size()>0)
			return  TITLES;
		return null;
	}
}