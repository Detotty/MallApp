package com.mallapp.listeners;

import com.mallapp.Model.FloorOverViewModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 1/25/2016.
 */
public interface FloorsDataListener {
    public void onDataReceived(ArrayList<FloorOverViewModel> floorOverViewModels);
    public void OnError();
}
