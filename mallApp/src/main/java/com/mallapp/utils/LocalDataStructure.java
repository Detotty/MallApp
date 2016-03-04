/**
 * 
 */
package com.mallapp.utils;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author VAIO Fit14 E
 *
 */
public class LocalDataStructure {

	
	
	public static String TAG = LocalDataStructure.class.getCanonicalName();
	public static LocalDataStructure localDataStructure=null;
	
	
	public Map<String, String> accessNumberMap = new HashMap<String, String>();
	public List<String> accessNumberList = new ArrayList<String>();
	
	
	private String userBalance="00.00";
	private ImageLoader imageLoader;
	
	private Map<String, RegisterContact> contactRegisterMap = new HashMap<String, RegisterContact>();
	private Map<String, RegisterContact> contactWithSipRegisterMap = new HashMap<String, RegisterContact>();
	

	
	
	/**
	 * 
	 */
	public LocalDataStructure() {
	}
	
	/**
	 * 
	 * @return
	 */
	public static LocalDataStructure getInstance(){
		   
		   if(localDataStructure==null)
			   localDataStructure = new LocalDataStructure();
		return localDataStructure;
	}
	
	
	
	/**
	 * @return the accessNumberMap
	 */
	public Map<String, String> getAccessNumberMap() {
		return accessNumberMap;
	}
	/**
	 * @param accessNumberMap the accessNumberMap to set
	 */
	public void setAccessNumberMap(Map<String, String> accessNumberMap) {
		this.accessNumberMap = accessNumberMap;
	}

	/**
	 * @return the tAG
	 */
	public static String getTAG() {
		return TAG;
	}

	/**
	 * @param tAG the tAG to set
	 */
	public static void setTAG(String tAG) {
		TAG = tAG;
	}

	/**
	 * @return the localDataStructure
	 */
	public static LocalDataStructure getLocalDataStructure() {
		return localDataStructure;
	}

	/**
	 * @param localDataStructure the localDataStructure to set
	 */
	public static void setLocalDataStructure(LocalDataStructure localDataStructure) {
		LocalDataStructure.localDataStructure = localDataStructure;
	}

	/**
	 * @return the userBalance
	 */
	public String getUserBalance() {
		return userBalance;
	}

	/**
	 * @param userBalance the userBalance to set
	 */
	public void setUserBalance(String userBalance) {
		this.userBalance = userBalance;
	}

	/**
	 * @return the imageLoader
	 */
	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	/**
	 * @param imageLoader the imageLoader to set
	 */
	public void setImageLoader(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}

	/**
	 * @return the contactRegisterMap
	 */
	public Map<String, RegisterContact> getContactRegisterMap() {
		return contactRegisterMap;
	}

	/**
	 * @param contactRegisterMap the contactRegisterMap to set
	 */
	public void setContactRegisterMap(
			Map<String, RegisterContact> contactRegisterMap) {
		this.contactRegisterMap = contactRegisterMap;
	}

	/**
	 * @return the accessNumberList
	 */
	public List<String> getAccessNumberList() {
		return accessNumberList;
	}

	/**
	 * @param accessNumberList the accessNumberList to set
	 */
	public void setAccessNumberList(List<String> accessNumberList) {
		this.accessNumberList = accessNumberList;
	}

	/**
	 * @return the contactWithSipRegisterMap
	 */
	public Map<String, RegisterContact> getContactWithSipRegisterMap() {
		return contactWithSipRegisterMap;
	}

	/**
	 * @param contactWithSipRegisterMap the contactWithSipRegisterMap to set
	 */
	public void setContactWithSipRegisterMap(
			Map<String, RegisterContact> contactWithSipRegisterMap) {
		this.contactWithSipRegisterMap = contactWithSipRegisterMap;
	}


	
	
	
}
