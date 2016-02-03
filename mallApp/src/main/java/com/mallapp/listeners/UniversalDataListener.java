package com.mallapp.listeners;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Sharjeel on 2/2/2016.
 */
public interface UniversalDataListener {

    public void onDataReceived(JSONObject jsonObject, JSONArray jsonArray);
    public void OnError();


}
