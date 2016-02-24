package com.mallapp.View;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;

import com.chatdbserver.xmpp.IMManager;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.GlobelProfile;
import com.mallapp.Constants.GlobelWebURLs;
import com.mallapp.Model.UserProfile;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.utils.CE_Controller;
import com.mallapp.utils.Log;


public class SplashScreen extends Activity {

	long m_dwSplashTime = 3000;
	boolean m_bPaused = false;
	boolean m_bSplashActive = true;
	private String country_name, phone_no, name,  profile_image;
	IMManager imManager;


	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_splash_screen);
		//ActionBar actionBar = getActionBar();
		//actionBar.hide();
		imManager=IMManager.getIMManager(getApplicationContext());
		getPreferenes();
		Thread splashTimer = new Thread() {
			public void run() {
				try {
					long ms = 0;
					while (m_bSplashActive && ms < m_dwSplashTime) {
						sleep(50);
						if (!m_bPaused)
							ms += 100;
					}
					if(	name!=null 	&& name.length()>0){
						CE_Controller.getInstance().getAllUserContactAndUpdateOnServer();
						UserProfileModel user_profile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);
						imManager.InitIMManager(GlobelWebURLs.IM_SERVER,GlobelWebURLs.ce_user+user_profile.getUserId(),user_profile.getUserId());
						Intent tabIntent = new Intent(SplashScreen.this, DashboardTabFragmentActivity.class);
						startActivity(tabIntent);
					}else{
						Log.e("", "elsee case");
						Intent mainIntent = new Intent(SplashScreen.this, RegistrationActivity.class);
						SplashScreen.this.startActivity(mainIntent);
					}
				} catch (Exception e) {
				} finally {
					finish();
				}
			}
		};
		splashTimer.start();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		m_bSplashActive = false;
		m_bPaused = false;
		return super.onKeyDown(keyCode, event);
	}

	protected void onPause() {
		super.onPause();
		m_bPaused = false;
	}

	protected void onResume() {
		super.onResume();
		m_bPaused = false;
	}

	public void getPreferenes(){
		UserProfileModel userProfile = SharedPreferenceUserProfile.getUserProfile(this);
		name 			= userProfile.getFullName();
		//Log.e("", ""+ country_code+""+ name+""+ mobile_no+""+ birthday);
	}
}