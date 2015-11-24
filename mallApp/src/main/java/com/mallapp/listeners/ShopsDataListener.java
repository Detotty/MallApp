package com.mallapp.listeners;

import com.mallapp.Model.ShopsModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 11/24/2015.
 */
public interface ShopsDataListener {
    public void onDataReceived(ArrayList<ShopsModel> shopsModelArrayList);
    public void OnError();

}
