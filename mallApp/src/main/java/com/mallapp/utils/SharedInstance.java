package com.mallapp.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hpp on 11/4/2015.
 */
public class SharedInstance {

    private static SharedInstance instance;

    private HashMap<String,Object> sharedHashMap;


    public SharedInstance(){

        sharedHashMap = new HashMap<String,Object>();
    }

    public static SharedInstance getInstance(){

        if(instance == null)
            instance = new SharedInstance();

        return  instance;

    }

    public HashMap getSharedHashMap(){
        return sharedHashMap;
    }



}
