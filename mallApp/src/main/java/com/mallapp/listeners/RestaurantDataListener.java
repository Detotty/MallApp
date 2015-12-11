package com.mallapp.listeners;

import com.mallapp.Model.RestaurantDetailModel;
import com.mallapp.Model.RestaurantModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 12/9/2015.
 */
public interface RestaurantDataListener {
    public void onDataReceived(ArrayList<RestaurantModel> restaurantModelArrayList);
    public void onRestaurantDetailReceived(RestaurantDetailModel restaurantDetailModel);
    public void OnError();

}
