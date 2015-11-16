package com.mallapp.listeners;

import com.mallapp.Model.UserProfile;
import com.mallapp.Model.UserProfileModel;

/**
 * Created by Sharjeel on 11/6/2015.
 */
public interface RegistrationUserListener {

    public void onDataReceived(UserProfileModel userProfile);
    public void onConnectionError();

}
