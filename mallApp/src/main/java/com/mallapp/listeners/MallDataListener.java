package com.mallapp.listeners;

import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.MallDetailModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel Haider on 11/18/2015.
 */
public interface MallDataListener {
    public void onDataReceived(ArrayList<MallActivitiesModel> mallActivitiesModels);
    public void onMallDetailReceived(MallDetailModel mallDetailModel);
    public void OnError();
}
