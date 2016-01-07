package com.mallapp.socialsharing;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mallapp.Constants.SocialSharingConstants;
import com.mallapp.Model.UserProfile;
import com.mallapp.utils.Log;


public class Facebook_Login {
	
	/*Context context;
	Activity activity;
	TextView user_name, user_education, user_location, user_dob, text_;
	RadioButton male, female;
	UserProfile userProfile;
	private static String FB_APP_ID;
//	private Facebook facebook;
	//private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	boolean connection_type= false;// false mean  get user fprofile, true mean post on fb



	public Facebook_Login(Context context, Activity active_activity, TextView name, TextView text,boolean connection_type) {
		super();
		this.context = context;
		this.activity=active_activity;
		user_name	= name;
		text_= text;
		this.connection_type=connection_type;
		FB_APP_ID= SocialSharingConstants.FB_APP_ID;
		*//*facebook = new Facebook(FB_APP_ID);
		SessionStore.restore(facebook, context);*//*
		
		
		
		
		//mAsyncRunner = new AsyncFacebookRunner(facebook);
	}

	public Facebook_Login(Context context, Activity activity,
			TextView user_name, TextView user_education,
			TextView user_location, TextView user_dob, RadioButton male, RadioButton female, boolean connection_type) {
		super();
		this.context = context;
		this.activity = activity;
		this.user_name = user_name;
		this.user_education = user_education;
		this.user_location = user_location;
		this.user_dob = user_dob;
		this.male= male;
		this.female= female;
		this.connection_type=connection_type;
		FB_APP_ID= SocialSharingConstants.FB_APP_ID;
//		facebook = new Facebook(FB_APP_ID);
		//mAsyncRunner = new AsyncFacebookRunner(facebook);
	}

	public Facebook_Login(Context context, Activity activity,
			boolean connection_type) {
		super();
		this.context = context;
		this.activity = activity;
		this.connection_type = connection_type;
		FB_APP_ID= SocialSharingConstants.FB_APP_ID;
		facebook = new Facebook(FB_APP_ID);
		
		
		
		
		Log.e("profile json", "contructor");
		//mAsyncRunner = new AsyncFacebookRunner(facebook);
	}
	
	
	
	public final class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			try {
				//The user has logged in, so now you can query and use their Facebook info
	    		JSONObject json = Util.parseJson(facebook.request("me"));
				String facebookID = json.getString("id");
				String firstName = json.getString("first_name");
				String lastName = json.getString("last_name");
				Toast.makeText(context, "Thank you for Logging In, " + firstName + " " + lastName + "!", Toast.LENGTH_SHORT).show();
				SessionStore.save(facebook,context);
				}
			catch( Exception error ) {
				Toast.makeText( context, error.toString(), Toast.LENGTH_SHORT).show();
				}
			catch( FacebookError error ) {
				Toast.makeText( context, error.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		
		public void onFacebookError(FacebookError error) {
			Toast.makeText( context, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		    }
		
		public void onError(DialogError error) {
			Toast.makeText( context, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
			}
		 
        public void onCancel() {
        	Toast.makeText( context, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
			}
		}
	
	
	
	
	public  OnClickListener loginButtonListener = new OnClickListener() {
    	public void onClick( View v ) {
    		if( !facebook.isSessionValid() ) {
    			Toast.makeText(context, "Authorizing", Toast.LENGTH_SHORT).show();
    			facebook.authorize(activity, new String[] { "" }, new LoginDialogListener());
    			}
    		else {
    			Toast.makeText( context, "Has valid session", Toast.LENGTH_SHORT).show();
    			try {
	    			JSONObject json = Util.parseJson(facebook.request("me"));
	    			String facebookID = json.getString("id");
	    			String firstName = json.getString("first_name");
	    			String lastName = json.getString("last_name");
	    			Toast.makeText(context, "You already have a valid session, " + firstName + " " + lastName + ". No need to re-authorize.", Toast.LENGTH_SHORT).show();
	    			}
    			catch( Exception error ) {
    				Toast.makeText( context, error.toString(), Toast.LENGTH_SHORT).show();
    				}
    			catch( FacebookError error ) {
    				Toast.makeText( context, error.toString(), Toast.LENGTH_SHORT).show();
    				}
    			}
    		}
    	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void loginToFacebook() {
		
		mPrefs =  activity.getPreferences(Context.MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		
		if (access_token != null) {
			facebook.setAccessToken(access_token);
			if(connection_type)
				postOnMyWall();
			else ;
				//getProfileInformation();
		}
		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}
		
		if (!facebook.isSessionValid()) {
			Log.e("profile json", "isSessionValid");
			facebook.authorize(activity, new String[] {"email", "publish_actions"
					//, "user_birthday", "user_education_history", "user_location"
					}, new DialogListener() {
				@Override
				public void onCancel() {
					// Function to handle cancel event
				}
				@Override
				public void onComplete(Bundle values) {
					
					Log.e("profile json", "onComplete");
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token", facebook.getAccessToken());
					editor.putLong("access_expires", facebook.getAccessExpires());
					editor.commit();
					if(connection_type)
						postOnMyWall();
					else {}
						//getProfileInformation();
				}
				@Override
				public void onError(DialogError error) {
					// Function to handle error
					Log.e("profile json", "onError");
				}
				@Override
				public void onFacebookError(FacebookError fberror) {
					Log.e("profile json", "onFacebookError");
					// Function to handle Facebook errors
				}
			});
		}
	}

	*//**
	 * Get Profile information by making request to Facebook Graph API
	 * *//*
//	protected void getProfileInformation() {
//		Log.e("profile json", "priofile");
//		mAsyncRunner.request("me", new RequestListener() {
//			@Override
//			public void onComplete(String response, Object state) {
//				String json = response;
//				Log.e("profile json", "priofile");
//				try {
//						
//					Log.e("profile json", json);
//					
//					// Facebook Profile JSON data
//					JSONObject profile = new JSONObject(json);
//					
//					String user_name = profile.getString("name");
//					String gender_s = profile.getString("gender");
//					String date_birth = profile.getString("birthday");
//					final JSONObject address = profile.getJSONObject("location");
//					String locat = address.getString("name");
//					JSONArray education_arr = profile.getJSONArray("education");
//					JSONObject uni = education_arr.getJSONObject(0).getJSONObject("school");
//					String edu = uni.getString("name");
//					
//					userProfile= new UserProfile(user_name, date_birth, edu, locat,gender_s );
//					activity.runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							set_user_profile();
//							// stuff that updates ui
//						}
//					});
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//			@Override
//			public void onIOException(IOException e, Object state) {
//			}
//			
//			@Override
//			public void onFileNotFoundException(FileNotFoundException e, Object state) {
//			}
//			
//			@Override
//			public void onMalformedURLException(MalformedURLException e, Object state) {
//			}
//			
//			@Override
//			public void onFacebookError(FacebookError e, Object state) {
//			}
//			
//		});
//	}

	protected void set_user_profile() {
		
		if(user_name != null)
		user_name.setText("" + userProfile.getName());
		if(user_education != null)
			user_education.setText("" + userProfile.getEducation());
		if(user_location != null)
			user_location.setText("" + userProfile.getLocation());
		if(user_dob != null)
			user_dob.setText("" + userProfile.getDate_birth());
		
		if (userProfile.getGender() != null 
				&& female !=null
				&& male !=null){
			
			if(userProfile.getGender().equals("female")){
				female.setChecked(true);
				male.setChecked(false);
			}else if(userProfile.getGender().equals("Female")){
				female.setChecked(false);
				male.setChecked(true);
			}
		}
	}

	
	public void postOnMyWall() {
		facebook.dialog(activity, "feed", new DialogListener() {
			@Override
			public void onFacebookError(FacebookError e) {
				
			}
			@Override
			public void onError(DialogError e) {
				
			}
			@Override
			public void onComplete(Bundle values) {
				
			}
			@Override
			public void onCancel() {
				
			}
		});
	}*/

}