package com.mallapp.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.GlobelProfile;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.utils.Log;



public class SharedPreferenceUserProfile {

	private static final String TAG = "SharedPreferenceUserProfile";
	private static final String tokenTag = "EndorsementUserToken";
	private static SharedPreferences usrPrefrence = null;
	private static SharedPreferences tokenPreferences = null;


	private static void initPrefrence(Context mContext) {
		try {
			SharedPreferenceUserProfile.usrPrefrence = mContext.getSharedPreferences
						(AppConstants.PREF_KEY, Context.MODE_PRIVATE);
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
	}

	public static void SaveUserProfile(UserProfileModel userProfile, Context context) {
		try {
			if (SharedPreferenceUserProfile.usrPrefrence == null) {
				SharedPreferenceUserProfile.initPrefrence(context);
			}
			if (SharedPreferenceUserProfile.usrPrefrence != null) {
				
				final Editor editor = SharedPreferenceUserProfile.usrPrefrence.edit();
				
				editor.putString(GlobelProfile.name, 	userProfile.getFullName());
				editor.putString(GlobelProfile.b_day, 	userProfile.getDOB());
				editor.putString(GlobelProfile.location,userProfile.getCityName());
				editor.putString(GlobelProfile.gender, 	userProfile.getGender());
				//editor.putString(GlobelProfile.profile_image, userProfile.getPicture_path());
				editor.apply();
			}
		} catch (final Exception exception) {
			Log.e(SharedPreferenceUserProfile.TAG, "" + exception);
		}
	}

	public static UserProfileModel getUserProfile(Context context) {
		try {
			if (SharedPreferenceUserProfile.usrPrefrence == null) {
				SharedPreferenceUserProfile.initPrefrence(context);
			}
			UserProfileModel userProfile= new UserProfileModel();
			
			userProfile.setFullName(usrPrefrence.getString(GlobelProfile.name, null));
//			userProfile.setEducation(usrPrefrence.getString(GlobelProfile.education, null));
//			userProfile.setLocation	(usrPrefrence.getString(GlobelProfile.location, null));
			userProfile.setGender(usrPrefrence.getString(GlobelProfile.gender, null));
			userProfile.setDOB(usrPrefrence.getString(GlobelProfile.b_day, null));
//			userProfile.setPicture_path(usrPrefrence.getString(GlobelProfile.profile_image, null));
			
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
		 		editor.putString(GlobelProfile.mobile_no, null);
		 		editor.putBoolean(GlobelProfile.push_notification, true);
				editor.putString(GlobelProfile.profile_image, null);
				editor.apply();
			}
		} catch (final Exception exception) {
			Log.e(SharedPreferenceUserProfile.TAG, "" + exception);
		}
	}
	
	
	public static void SaveUserCountry(UserProfileModel userProfile, Context context) {
		try {
			if (SharedPreferenceUserProfile.usrPrefrence == null) {
				SharedPreferenceUserProfile.initPrefrence(context);
			}
			if (SharedPreferenceUserProfile.usrPrefrence != null) {
				final Editor editor = SharedPreferenceUserProfile.usrPrefrence.edit();
//				editor.putString(GlobelProfile.country_name, userProfile.getCountry());
		 		editor.putString(GlobelProfile.country_code, userProfile.getCountryCode());
		 		editor.putString(GlobelProfile.mobile_no, userProfile.getMobilePhone());
		 		editor.putBoolean(GlobelProfile.push_notification, userProfile.isPush_notification());
		 		editor.apply();
			}
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
	}

	public static UserProfileModel getUserCountry(Context context) {
		try {
			if (SharedPreferenceUserProfile.usrPrefrence == null) {
				SharedPreferenceUserProfile.initPrefrence(context);
			}
			UserProfileModel userLoction= new UserProfileModel();
//			userLoction.setCountry(		usrPrefrence.getString(GlobelProfile.country_name, null));
			userLoction.setCountryCode(usrPrefrence.getString(GlobelProfile.country_code, null));
			userLoction.setMobilePhone(usrPrefrence.getString(GlobelProfile.mobile_no, null));
			return userLoction;
			//return SharedPreferenceUserProfile.usrPrefrence.getString(key, null);
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
		return null;
	}
	
	
	public void name() {
		
	}
	private static void initTokenPreference(Context context){
		try {
			SharedPreferenceUserProfile.tokenPreferences = context.getSharedPreferences
					(AppConstants.USER_TOKEN, Context.MODE_PRIVATE);

		} catch (final Exception exception) {
			Log.e(tokenTag, "" + exception);
		}
	}

	public static String getUserToken(Context context) {

		try{
			if (SharedPreferenceUserProfile.tokenPreferences == null) {
				SharedPreferenceUserProfile.initTokenPreference(context);
			}
//            String userToken  = tokenPreferences.getString(GlobelProfile.profileID, 	null);

			String userToken = tokenPreferences.getString(GlobelProfile.securityToken, null);


			return userToken;

		} catch (final Exception exception) {
			exception.printStackTrace();
			Log.e(tokenTag, "" + exception);
		}
		return null;
	}

	public static void saveUserToken(String token,String userId, Context context) {

		try {
			if (SharedPreferenceUserProfile.tokenPreferences == null) {
				SharedPreferenceUserProfile.initTokenPreference(context);
			}
			if (SharedPreferenceUserProfile.tokenPreferences != null) {
				Editor editor = SharedPreferenceUserProfile.tokenPreferences.edit();

				editor.putString(GlobelProfile.profileID, userId);
				editor.putString(GlobelProfile.securityToken, token);
				editor.apply();
			}
		} catch (final Exception exception) {
			exception.printStackTrace();
			Log.e(TAG, "" + exception);
		}
	}

	public static String getUserId(Context context) {

		try{
			if (SharedPreferenceUserProfile.tokenPreferences == null) {
				SharedPreferenceUserProfile.initTokenPreference(context);
			}
			String userId  = tokenPreferences.getString(GlobelProfile.profileID, null);

//            String userId = usrPrefrence.getString(GlobelProfile.securityToken, null);


			return userId;

		} catch (Exception exception) {
			exception.printStackTrace();
			Log.e(tokenTag, "UserId: " + exception);
		}
		return null;
	}

}
