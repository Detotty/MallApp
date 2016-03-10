package com.mallapp.View;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.utils.VolleyNetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationActivity extends Activity {

    TextView heading;
    Switch switch_notification;
    VolleyNetworkUtil volleyNetworkUtil;
    UserProfileModel user_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        volleyNetworkUtil = new VolleyNetworkUtil(this);
        ImageButton button_back = (ImageButton) findViewById(R.id.back);
        heading = (TextView) findViewById(R.id.heading);
        heading.setText(getResources().getString(R.string.notifications));
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*overridePendingTransition(R.anim.right_out,
                        R.anim.right_in);*/

            }
        });
        user_profile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", SharedPreferenceUserProfile.getUserId(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch_notification = (Switch) findViewById(R.id.switch_notification);
        switch_notification.setChecked(DataHandler.getBooleanPreferences(AppConstants.PREF_NOT_KEY));
        switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    try {
                        jsonObject.put("IsEnabled",1);
                        volleyNetworkUtil.PostNotSet(ApiConstants.POST_MALL_NOTIFICATION_SETTING,jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    DataHandler.updatePreferences(AppConstants.PREF_NOT_KEY,true);
                } else {
                    try {
                        jsonObject.put("IsEnabled",0);
                        volleyNetworkUtil.PostNotSet(ApiConstants.POST_MALL_NOTIFICATION_SETTING, jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    DataHandler.updatePreferences(AppConstants.PREF_NOT_KEY,false);
                }

            }
        });


    }


}
