package com.mallapp.listeners;

import com.mallapp.Model.FavouriteCentersModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel Haider on 11/18/2015.
 */
public interface MallDataListener {
    public void onDataReceived(ArrayList<FavouriteCentersModel> favouriteCentersModels);
}
