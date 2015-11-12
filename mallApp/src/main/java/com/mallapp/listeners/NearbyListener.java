package com.mallapp.listeners;

import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.InterestSelectionModel;

import java.util.ArrayList;

/**
 * Created by hpp on 11/12/2015.
 */
public interface NearbyListener {
    public void onMallDataReceived(ArrayList<FavouriteCentersModel> mallList);

}
