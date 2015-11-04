package com.mallapp.View;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;

import com.mallapp.Constants.GlobelProfile;
import com.mallapp.utils.Log;


public class SplashScreen extends Activity {

	long m_dwSplashTime = 3000;
	boolean m_bPaused = false;
	boolean m_bSplashActive = true;
	private String country_name, phone_no, name,  profile_image;
	

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_splash_screen);
		//ActionBar actionBar = getActionBar();
		//actionBar.hide();
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
					if(country_name!= null 		&& country_name.length()>0 && 
							phone_no.length()>0 && phone_no!= null &&
							name!=null 			&& name.length()>0 &&
							profile_image!=null && profile_image.length()>0){
						
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
		SharedPreferences prefs = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
		country_name 	= prefs.getString("country_name", null);
		phone_no 		= prefs.getString("phone_no", null);
		name 			= prefs.getString("name", null);
		profile_image 	= prefs.getString(GlobelProfile.profile_image, null);
		//Log.e("", ""+ country_code+""+ name+""+ phone_no+""+ birthday);
	}
}