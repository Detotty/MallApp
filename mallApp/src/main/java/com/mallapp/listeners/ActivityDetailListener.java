package com.mallapp.listeners;

import com.mallapp.Model.MallActivitiesModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 1/28/2016.
 */
public interface ActivityDetailListener {
    public void onDataReceived(MallActivitiesModel mallActivitiesModels);
    public void OnError();
}
