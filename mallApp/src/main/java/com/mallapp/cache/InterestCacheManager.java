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

import com.mallapp.Model.InterestSelectionModel;
import com.mallapp.Model.Interst_Selection;

public class InterestCacheManager {

	//private static final String TAG = AppCacheManager.class.getSimpleName();
	private static final String CACHE_FILE_NAME = "interest_file";
	private static CacheManager mCacheManager;
	
	

	private static void writeObjectList(Context context, ArrayList<InterestSelectionModel> centers_list) throws IOException {
		// Create FileOutputStream to write file
		FileOutputStream fos = new FileOutputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		// Create ObjectOutputStream to write object
		ObjectOutputStream objOutputStream = new ObjectOutputStream(fos);
		
		// Write object to file
		for (Object obj : centers_list) {
			objOutputStream.writeObject(obj);
			objOutputStream.reset();
		}
		objOutputStream.close();
	}

	private static ArrayList<InterestSelectionModel> readObjectList(Context context) throws ClassNotFoundException, IOException {
		ArrayList<InterestSelectionModel> list = new ArrayList<InterestSelectionModel>();
		// Create new FileInputStream object to read file
		FileInputStream fis = new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		// Create new ObjectInputStream object to read object from file
		ObjectInputStream obj = new ObjectInputStream(fis);
		try {
			while (fis.available() != -1) {
				// Read object from file
				InterestSelectionModel acc = (InterestSelectionModel) obj.readObject();
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
		return list;
	}
	
	
	private static int readCountObjectList(Context context) throws ClassNotFoundException, IOException {
		ArrayList<InterestSelectionModel> list = new ArrayList<InterestSelectionModel>();
		
		FileInputStream fis = new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);
		
		ObjectInputStream obj = new ObjectInputStream(fis);
		try {
			while (fis.available() != -1) {
				InterestSelectionModel acc = (InterestSelectionModel) obj.readObject();
				if(acc.isInterested())
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

	
	public static void updateCenters(Context context, InterestSelectionModel obj, int pos) {
		
		ArrayList<InterestSelectionModel> allcentersList= getAllCenters(context);
		if (allcentersList != null) {
			allcentersList.set(pos, obj);
//			allcentersList.add(obj);
			saveFavorites(context, allcentersList);
		}
	}
	
	
	public static void saveFavorites(Context context, ArrayList<InterestSelectionModel> allcentersList) {
		try {
			writeObjectList(context, allcentersList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<InterestSelectionModel> getAllCenters(Context context) {
		ArrayList<InterestSelectionModel> allentersList = null;
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

	public static int countInterests(Context context) {
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
	

	public static void clearCache(Context context, String fileName) {
		mCacheManager = CacheManager.getInstance(context);
		mCacheManager.deleteFile(fileName);
	}

	public static ArrayList<InterestSelectionModel> readSelectedObjectList(Context context) throws ClassNotFoundException, IOException {
		ArrayList<InterestSelectionModel> list = new ArrayList<InterestSelectionModel>();

		FileInputStream fis = new FileInputStream(context.getCacheDir() + File.separator + CACHE_FILE_NAME);

		ObjectInputStream obj = new ObjectInputStream(fis);
		try {
			while (fis.available() != -1) {
				InterestSelectionModel acc = (InterestSelectionModel) obj.readObject();
				if(acc.isInterested())
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
