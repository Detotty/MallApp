package com.mallapp.Controllers;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.mallapp.Model.UserProfile;
import com.mallapp.ServicesApi.MySqlConnection;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.globel.GlobelServices;
import com.mallapp.utils.Log;

public class SendVerificationCode {
	
	
	public static void sendVerificationCode(Context context, String phone_N, boolean push_notification, String countryName, String CountryZipCode) {
		
		UserProfile user_location= new UserProfile(countryName, CountryZipCode, phone_N, push_notification);
		//String message= ""+R.string.access_code_country;
		JSONObject obj = new JSONObject();
		JSONObject jsonObj = null ;
//		phone_N= "+"+phone_N;
		try {
			obj.put("PhoneNumber", phone_N);
			obj.put("LanguageID", "1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.e("", "JSONObject = "+obj.toString());
		
		try {

			String url= GlobelServices.SEND_SMS_URL_KEY+phone_N;
			String result= MySqlConnection.executeHttpGet(url);
			jsonObj  = new JSONObject(result);
			
			boolean success= jsonObj.getBoolean("Success");
			if(success){
				
				SharedPreferenceUserProfile.SaveUserCountry(user_location, context);
				//AlertMessages.show_alert(context, ""+R.string.app_name1, message, "OK");
			}
			Log.e("", ""+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static boolean verifyAccessCode(Context context, String phone_N, String access_code) {
		
		JSONObject obj = new JSONObject();
		JSONObject jsonObj = null ;
//		phone_N= "+"+phone_N;
		
		try {
			obj.put("mobileNo", phone_N);
			obj.put("code", access_code);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.e("", "JSONObject = "+obj.toString());
		
		try {

			String url= GlobelServices.VERIFY_SMS_URL_KEY;
			String result= MySqlConnection.executeHttpPost(url, obj);
			jsonObj  = new JSONObject(result);
			
			boolean success= jsonObj.getBoolean("Success");
			
			if(success)
				saveToken(jsonObj,context);
				return success;
			
//			Log.e("", ""+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}

	public static void saveToken(JSONObject jsonObj, Context context){

		try {
			boolean success = jsonObj.getBoolean("Success");

                            /*
                             * {"Success":true,"User_Id":"3def09ea-9850-46dc-9831-a8d6b1f44b00",
                             * "Auth_Token":"d1bbadcb-1118-4c4a-84e4-38221f7dc37b",
                             * "Message":"","httpStatusCode":200}
                             */

			if(success) {
				String profileID = jsonObj.getString("User_Id");
				String security_token = jsonObj.getString("Auth_Token");

				SharedPreferenceUserProfile.saveUserToken(security_token, profileID, context);



			}else if (jsonObj.has("Message")) {
				String message = jsonObj.getString("Message");

				if(message!=null && message!= "")
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}

		}
		catch(JSONException e) {
			e.printStackTrace();
		}
	}

	public static void GetMallList(String url){
		try {
			JSONObject obj = new JSONObject();
			JSONObject jsonObj = null ;
			String result= MySqlConnection.executeHttpGet(url);
			jsonObj  = new JSONObject(result);

			boolean success= jsonObj.getBoolean("Success");
			if(success){

				//AlertMessages.show_alert(context, ""+R.string.app_name1, message, "OK");
			}
			Log.e("", ""+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
}