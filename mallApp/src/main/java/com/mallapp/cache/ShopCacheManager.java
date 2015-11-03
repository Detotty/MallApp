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

import com.mallapp.Model.Shops;
import com.mallapp.utils.Log;



public class ShopCacheManager {

	//private static final String TAG = AppCacheManager.class.getSimpleName();
	private static final String CACHE_FILE_NAME = "shop_objects_file";
	private static CacheManager mCacheManager;
	
	
	
	public static void writeObjectList(Context context, ArrayList<Shops> listAccount) throws IOException {
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

	public static ArrayList<Shops> readObjectList(Context context, String center_name) 
													throws 	ClassNotFoundException, 
															IOException {
		
		ArrayList<Shops> list 	= new ArrayList<Shops>();
		FileInputStream fis 	= new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		ObjectInputStream obj 	= new ObjectInputStream(fis);
		
		try {
			if(center_name ==null || center_name.length()==0){
				
				while (fis.available() != -1) {
					// Read object from file
					Shops acc = (Shops) obj.readObject();
					//if(acc.getCenter_name().trim().equals(center_name))
					list.add(acc);
				}
			
			
			}else{
				while (fis.available() != -1) {
					// Read object from file
					Shops acc = (Shops) obj.readObject();
					if(acc.getCenter_name().trim().equals(center_name))
						list.add(acc);
				}
			}
			obj.close();
			fis.close();
		} catch (EOFException ex) {
			
		}
		Log.w("read", "offers list size "+ list.size());
		return list;
	}

	
	public static void updateShops(Context context, Shops obj, String center_name) {
		ArrayList<Shops> all_shops= getAllShopsList(context, center_name);
		Log.w("update centers", all_shops.size()+ "....read centers list completed "+ obj.getId()+"......"+ obj.isFav());
		
		if (all_shops != null) {
			all_shops.set(obj.getId(), obj);
			saveShops(context, all_shops);
		}
	}
	
	public static void saveShops(Context context, ArrayList<Shops> listAccount) {
		try {
			writeObjectList(context, listAccount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Shops> getAllShopsList(Context context, String center_name) {
		ArrayList<Shops> allentersList = null;
		try {
			allentersList = readObjectList(context, center_name);
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