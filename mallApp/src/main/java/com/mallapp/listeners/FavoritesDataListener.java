package com.mallapp.listeners;

import com.mallapp.Model.FavoritesModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 12/22/2015.
 */
public interface FavoritesDataListener {
    public void onDataReceived(ArrayList<FavoritesModel> favoritesModels);
    public void OnError();
}
