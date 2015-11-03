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
import com.mallapp.Model.Services;
import com.mallapp.utils.Log;

public class ServicesCacheManager {

	private static final String CACHE_FILE_NAME = "services_objects_file";
	private static CacheManager mCacheManager;
	
	private static void writeObjectList(Context context, ArrayList<Services> listAccount) throws IOException {
		
		FileOutputStream fos = new FileOutputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		ObjectOutputStream objOutputStream = new ObjectOutputStream(fos);
		
		for (Object obj : listAccount) {
			objOutputStream.writeObject(obj);
			objOutputStream.reset();
		}
		objOutputStream.close();
		Log.w("write", "write services list ");
	}

	
	public static ArrayList<Services> readObjectList(Context context, String center_name) 
													throws 	ClassNotFoundException, 
															IOException {
		
		ArrayList<Services> list 	= new ArrayList<Services>();
		FileInputStream fis 	= new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		ObjectInputStream obj 	= new ObjectInputStream(fis);
		
		try {
//			if(center_name ==null || center_name.length()==0){
				while (fis.available() != -1) {
					// Read object from file
					Services acc = (Services) obj.readObject();
					//if(acc.getCenter_name().trim().equals(center_name))
					list.add(acc);
				}
//			}else{
//				while (fis.available() != -1) {
//					// Read object from file
//					Services acc = (Services) obj.readObject();
//					if(acc.getCenter_name().trim().equals(center_name))
//						list.add(acc);
//				}
//			}
			obj.close();
			fis.close();
		} catch (EOFException ex) {
			
		}
		Log.w("read", "offers list size "+ list.size());
		return list;
	}

	
	public static void updateServices(Context context, Services obj, String center_name) {
		ArrayList<Services> all_shops= getAllServicesList(context, center_name);
		Log.w("update centers", all_shops.size()+ "....read centers list completed "+ obj.getId()+"......");
		if (all_shops != null) {
			all_shops.set(obj.getId(), obj);
			saveServices(context, all_shops);
		}
	}
	
	public static void saveServices(Context context, ArrayList<Services> listAccount) {
		try {
			writeObjectList(context, listAccount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Services> getAllServicesList(Context context, String center_name) {
		ArrayList<Services> allentersList = null;
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
