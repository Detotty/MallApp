package com.mallapp.cache;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.mallapp.Model.FavouriteCenters;
import com.mallapp.Model.FavouriteCentersModel;

public class CentersCacheManager {

	//private static final String TAG = AppCacheManager.class.getSimpleName();
	private static final String CACHE_FILE_NAME = "fav_center_file";
	private static CacheManager mCacheManager;
	
	
	
	private static void writeObjectList(Context context, ArrayList<FavouriteCentersModel> centers_list) throws IOException {
		// Create FileOutputStream to write file
		FileOutputStream fos = new FileOutputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		// Create ObjectOutputStream to write object
		ObjectOutputStream objOutputStream = new ObjectOutputStream(fos);
		// Write object to file
		for (Object obj : centers_list) {

			objOutputStream.writeObject(obj);
			objOutputStream.reset();
		}
		Log.w("write centers", "write centers list completed");
		objOutputStream.close();
	}

	private static ArrayList<FavouriteCentersModel> readObjectList(Context context) throws ClassNotFoundException, IOException {
		ArrayList<FavouriteCentersModel> list = new ArrayList<FavouriteCentersModel>();
		// Create new FileInputStream object to read file
		FileInputStream fis = new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		// Create new ObjectInputStream object to read object from file
		ObjectInputStream obj = new ObjectInputStream(fis);
		try {
			while (fis.available() != -1) {
				// Read object from file
				FavouriteCentersModel acc = (FavouriteCentersModel) obj.readObject();
				list.add(acc);
			}
		} catch (EOFException ex) {
			//Log.e("offers news list class", "mall app apllication excepton");
			//ex.printStackTrace();
		}finally{
	         // releases any associated system files with this stream
			if(fis!=null)
				fis.close();
			if(obj!=null)
				obj.close();
		} 
		
		
		Log.w("read centers", "read centers list completed " + list.size());
		return list;
	}
	
	
	private static int readCountObjectList(Context context) throws ClassNotFoundException, IOException {
		ArrayList<FavouriteCentersModel> list = new ArrayList<FavouriteCentersModel>();
		
		FileInputStream fis = new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		
		ObjectInputStream obj = new ObjectInputStream(fis);
		try {
			while (fis.available() != -1) {
				FavouriteCentersModel acc = (FavouriteCentersModel) obj.readObject();
				if(acc.isIsfav())
					list.add(acc);
			}
		} catch (EOFException ex) {
			ex.printStackTrace();
		}finally{
	         // releases any associated system files with this stream
			if(fis!=null)
				fis.close();
			if(obj!=null)
				obj.close();
		}
		Log.w("read centers", "read centers list completed "+ list.size());
		return list.size();
	}
	
	public static void updateCenters(Context context, FavouriteCentersModel obj,int pos) {
		Log.w("update centers", "read centers list completed "+ obj.getMallPlaceId()+"......"+ obj.isIsfav());
		ArrayList<FavouriteCentersModel> allcentersList= getAllCenters(context);
		if (allcentersList != null) {
			allcentersList.set(pos, obj);
//			allcentersList.add(obj);
			saveFavorites(context, allcentersList);
		}
	}
	
	public static void saveFavorites(Context context, ArrayList<FavouriteCentersModel> allcentersList) {
		try {
			writeObjectList(context, allcentersList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int countCenters(Context context) {
		int count=0;
		try {
			count = readCountObjectList(context);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static ArrayList<FavouriteCentersModel> getAllCenters(Context context) {
		ArrayList<FavouriteCentersModel> allentersList = null;
		try {
			allentersList = readObjectList(context);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allentersList;
	}

	public static void clearCache(Context context, String fileName) {
		mCacheManager = CacheManager.getInstance(context);
		mCacheManager.deleteFile(fileName);
	}

	public static ArrayList<FavouriteCentersModel> readSelectedObjectList(Context context) throws ClassNotFoundException, IOException {
		ArrayList<FavouriteCentersModel> list = new ArrayList<FavouriteCentersModel>();

		FileInputStream fis = new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);

		ObjectInputStream obj = new ObjectInputStream(fis);
		try {
			while (fis.available() != -1) {
				FavouriteCentersModel acc = (FavouriteCentersModel) obj.readObject();
				if(acc.isIsfav())
					list.add(acc);
			}
		} catch (EOFException ex) {
			ex.printStackTrace();
		}finally{
			// releases any associated system files with this stream
			if(fis!=null)
				fis.close();
			if(obj!=null)
				obj.close();
		}
		Log.w("read centers", "read centers list completed " + list.size());
		return list;
	}
}