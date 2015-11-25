package com.mallapp.listeners;

import com.mallapp.Model.ShopDetailModel;
import com.mallapp.Model.ShopsModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 11/24/2015.
 */
public interface ShopsDataListener {
    public void onDataReceived(ArrayList<ShopsModel> shopsModelArrayList);
    public void onShopDetailReceived(ShopDetailModel shopDetail);
    public void OnError();

}
