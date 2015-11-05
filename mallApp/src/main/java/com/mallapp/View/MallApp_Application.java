package com.mallapp.View;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

import com.mallapp.Controllers.OffersNewsList;
import com.mallapp.Model.Offers_News;
import com.mallapp.cache.AppCacheManager;


@ReportsCrashes(formKey = "", mailTo = "aysha@zap-itsolutions.com", 
					mode = ReportingInteractionMode.SILENT, resToastText = R.string.app_name)

public class MallApp_Application extends Application implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6734652616751863916L;
	public static String TAG = MallApp_Application.class.getSimpleName();
	public static ArrayList<Offers_News> offer_objects =  new ArrayList<Offers_News>();

	@Override
    public void onCreate() {
        super.onCreate();
        // The following line triggers the initialization of ACRA
        ACRA.init(this);

        readOffersNews();
        readShops();
        readRetuarant();
        readServices();
        
       
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

	public void readOffersNews() {
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
	}
}