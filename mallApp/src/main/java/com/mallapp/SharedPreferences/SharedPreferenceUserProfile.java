package com.mallapp.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.GlobelProfile;
import com.mallapp.Model.UserProfile;
import com.mallapp.utils.Log;



public class SharedPreferenceUserProfile {
	
	private static final String TAG = "SharedPreferenceUserProfile";
	private static SharedPreferences usrPrefrence = null;
	
	private static void initPrefrence(Context mContext) {
		try {
			SharedPreferenceUserProfile.usrPrefrence = mContext.getSharedPreferences
						(AppConstants.PREF_KEY, Context.MODE_PRIVATE);
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
	}

	public static void SaveUserProfile(UserProfile userProfile, Context context) {
		try {
			if (SharedPreferenceUserProfile.usrPrefrence == null) {
				SharedPreferenceUserProfile.initPrefrence(context);
			}
			if (SharedPreferenceUserProfile.usrPrefrence != null) {
				
				final Editor editor = SharedPreferenceUserProfile.usrPrefrence.edit();
				
				editor.putString(GlobelProfile.name, 	userProfile.getName());
				editor.putString(GlobelProfile.b_day, 	userProfile.getDate_birth());
				editor.putString(GlobelProfile.location,userProfile.getLocation());
				editor.putString(GlobelProfile.education,userProfile.getEducation());
				editor.putString(GlobelProfile.gender, 	userProfile.getGender());
				//editor.putString(GlobelProfile.profile_image, userProfile.getPicture_path());
				editor.apply();
			}
		} catch (final Exception exception) {
			Log.e(SharedPreferenceUserProfile.TAG, "" + exception);
		}
	}

	public static UserProfile getUserProfile(Context context) {
		try {
			if (SharedPreferenceUserProfile.usrPrefrence == null) {
				SharedPreferenceUserProfile.initPrefrence(context);
			}
			UserProfile userProfile= new UserProfile();
			
			userProfile.setName		(usrPrefrence.getString(GlobelProfile.name, null));
			userProfile.setEducation(usrPrefrence.getString(GlobelProfile.education, null));
			userProfile.setLocation	(usrPrefrence.getString(GlobelProfile.location, null));
			userProfile.setGender	(usrPrefrence.getString(GlobelProfile.gender, null));
			userProfile.setDate_birth(usrPrefrence.getString(GlobelProfile.b_day, null));
			userProfile.setPicture_path(usrPrefrence.getString(GlobelProfile.profile_image, null));
			
			return userProfile;
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
		return null;
	}
	
	public static void DeleteUserProfile(Context context) {

		try {
			if (SharedPreferenceUserProfile.usrPrefrence == null) {
				SharedPreferenceUserProfile.initPrefrence(context);
			}
			if (SharedPreferenceUserProfile.usrPrefrence != null) {
				
				final Editor editor = SharedPreferenceUserProfile.usrPrefrence.edit();
				
				editor.putString(GlobelProfile.name, 	null);
				editor.putString(GlobelProfile.b_day, 	null);
				editor.putString(GlobelProfile.location,null);
				editor.putString(GlobelProfile.education,null);
				editor.putString(GlobelProfile.gender, 	null);
				editor.putString(GlobelProfile.country_name, null);
		 		editor.putString(GlobelProfile.country_code, null);
		 		editor.putString(GlobelProfile.phone_no, null);
		 		editor.putBoolean(GlobelProfile.push_notification, true);
				editor.putString(GlobelProfile.profile_image, null);
				editor.apply();
			}
		} catch (final Exception exception) {
			Log.e(SharedPreferenceUserProfile.TAG, "" + exception);
		}
	}
	
	
	public static void SaveUserCountry(UserProfile userProfile, Context context) {
		try {
			if (SharedPreferenceUserProfile.usrPrefrence == null) {
				SharedPreferenceUserProfile.initPrefrence(context);
			}
			if (SharedPreferenceUserProfile.usrPrefrence != null) {
				final Editor editor = SharedPreferenceUserProfile.usrPrefrence.edit();
				editor.putString(GlobelProfile.country_name, userProfile.getCountry());
		 		editor.putString(GlobelProfile.country_code, userProfile.getCountryCode());
		 		editor.putString(GlobelProfile.phone_no, 	 userProfile.getPhone_no());
		 		editor.putBoolean(GlobelProfile.push_notification, userProfile.isPush_notification());
		 		editor.apply();
			}
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
	}

	public static UserProfile getUserCountry(Context context) {
		try {
			if (SharedPreferenceUserProfile.usrPrefrence == null) {
				SharedPreferenceUserProfile.initPrefrence(context);
			}
			UserProfile userLoction= new UserProfile();
			userLoction.setCountry(		usrPrefrence.getString(GlobelProfile.country_name, null));
			userLoction.setCountryCode(	usrPrefrence.getString(GlobelProfile.country_code, null));
			userLoction.setPhone_no(	usrPrefrence.getString(GlobelProfile.phone_no, null));
			return userLoction;
			//return SharedPreferenceUserProfile.usrPrefrence.getString(key, null);
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
		return null;
	}
	
	
	public void name() {
		
	}

}
