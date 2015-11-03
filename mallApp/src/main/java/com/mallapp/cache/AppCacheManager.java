package com.mallapp.cache;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.mallapp.Model.Offers_News;



public class AppCacheManager {

	//private static final String TAG = AppCacheManager.class.getSimpleName();
	private static final String CACHE_FILE_NAME = "offer_objects_file";
	private static CacheManager mCacheManager;
	
	
	
	public static void writeObjectList(Context context, ArrayList<Offers_News> listAccount) throws IOException {
		// Create FileOutputStream to write file
		FileOutputStream fos = new FileOutputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		// Create ObjectOutputStream to write object
		ObjectOutputStream objOutputStream = new ObjectOutputStream(fos);
		// Write object to file
		for (Object obj : listAccount) {

			objOutputStream.writeObject(obj);
			objOutputStream.reset();
		}
		objOutputStream.close();
		Log.w("write", "write offers list ");
	}

	public static ArrayList<Offers_News> readObjectList(Context context) throws ClassNotFoundException, IOException {
		ArrayList<Offers_News> list = new ArrayList<Offers_News>();
		// Create new FileInputStream object to read file
		FileInputStream fis = new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		// Create new ObjectInputStream object to read object from file
		ObjectInputStream obj = new ObjectInputStream(fis);
		try {
			while (fis.available() != -1) {
				// Read object from file
				Offers_News acc = (Offers_News) obj.readObject();
				list.add(acc);
			}
			obj.close();
			fis.close();
			
		} catch (EOFException ex) {
			
			//Log.e("offers news list class", "mall app apllication excepton");
			//ex.printStackTrace();
		}
		
		Log.w("read", "offers list size "+ list.size());
		return list;
	}
	
	
	
	public static ArrayList<Offers_News> readObjectListCentered(Context context, String center_name) 
															throws ClassNotFoundException, IOException {
	
		ArrayList<Offers_News> list = new ArrayList<Offers_News>();
		// Create new FileInputStream object to read file
		FileInputStream fis = new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		// Create new ObjectInputStream object to read object from file
		ObjectInputStream obj = new ObjectInputStream(fis);
		
		try {
			if(center_name ==null || center_name.length()==0){
				while (fis.available() != -1) {
					// Read object from file
					Offers_News acc = (Offers_News) obj.readObject();
					//if(acc.getCenter_name().trim().equals(center_name))
					list.add(acc);
				}
			}else{
				while (fis.available() != -1) {
					// Read object from file
					Offers_News acc = (Offers_News) obj.readObject();
					if(acc.getCenter_name().trim().equals(center_name))
						list.add(acc);
				}
			}
			obj.close();
			fis.close();
		} catch (EOFException ex) {
			
		}
		//Log.w("read centered list", "offers list size centered.... "+ list.size());
		return list;
	}

	
	public static void updateOffersNews(Context context, Offers_News obj) {
		Log.w("update centers", "read centers list completed "+ obj.getId()+"......"+ obj.isFav());
		ArrayList<Offers_News> allcentersList= getAllOffersNews(context);
		if (allcentersList != null) {
			allcentersList.set(obj.getId(), obj);
			saveOffersNews(context, allcentersList);
		}
	}
	
	public static void saveOffersNews(Context context, ArrayList<Offers_News> listAccount) {
		try {
			writeObjectList(context, listAccount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Offers_News> getAllOffersNews(Context context) {
		ArrayList<Offers_News> allentersList = null;
		try {
			allentersList = readObjectList(context);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allentersList;
	}

	
	
	public static void clearCache(Context context, String fileName) {
		mCacheManager = CacheManager.getInstance(context);
		mCacheManager.deleteFile(fileName);
	}

}
