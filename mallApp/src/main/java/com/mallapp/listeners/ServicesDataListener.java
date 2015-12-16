package com.mallapp.listeners;

import com.mallapp.Model.ServicesModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 12/15/2015.
 */
public interface ServicesDataListener {
    public void onDataReceived(ArrayList<ServicesModel> servicesModels);
    public void OnError();
}
