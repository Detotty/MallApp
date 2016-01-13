package com.mallapp.Application;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.mallapp.Controllers.OffersNewsList;
import com.mallapp.Model.Offers_News;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.View.R;
import com.mallapp.cache.AppCacheManager;
import com.mallapp.utils.LruBitmapCache;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sharjeel on 11/5/2015.
 */

@ReportsCrashes(formKey = "", mailTo = "aysha@zap-itsolutions.com",
        mode = ReportingInteractionMode.SILENT, resToastText = R.string.app_name)
public class MallApplication extends Application implements Serializable {

    private static final long serialVersionUID = -6734652616751863916L;
    public static String TAG = MallApplication.class.getSimpleName();
    public static ArrayList<Offers_News> offer_objects =  new ArrayList<Offers_News>();


    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static MallApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        //GetCurrentDate.changeBirthdayDateFormat("2015-08-12T09:10:30.9900000");
        // The following line triggers the initialization of ACRA

        ACRA.init(this);
        mInstance = this;
        new DataHandler(this);
//        readOffersNews();
        readShops();
        readRetuarant();
        readServices();
        // ACRA.getErrorReporter().handleException(null);

    }


    public static synchronized MallApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private void readServices() {
        // TODO Auto-generated method stub

    }

    private void readRetuarant() {
        // TODO Auto-generated method stub

    }

    private void readShops() {
        // TODO Auto-generated method stub
    }

    /*public void readOffersNews() {
        try {
            offer_objects = AppCacheManager.readObjectList(getApplicationContext());
            if(offer_objects== null || offer_objects.size()==0){
                // its mean array is null. and call class for entring data
                OffersNewsList.saveOffersNewsData(getApplicationContext());
            }else{
                offer_objects= OffersNewsList.readOffersNews(getApplicationContext());
            }
        } catch (ClassNotFoundException e) {
            //
            e.printStackTrace();
        } catch (IOException e) {
            //Log.e("mall app", "mall app IOException call");
            OffersNewsList.saveOffersNewsData(getApplicationContext());
            e.printStackTrace();
        }
    }*/

}
