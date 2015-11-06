package com.mallapp.listeners;

import com.mallapp.Model.UserProfile;

/**
 * Created by Sharjeel on 11/6/2015.
 */
public interface RegistrationUserListener {

    public void onDataReceived(UserProfile userProfile);
    public void onConnectionError();

}
