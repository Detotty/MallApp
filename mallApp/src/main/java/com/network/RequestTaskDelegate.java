/**
 * 
 */
package com.network;

import org.json.JSONObject;

/**
 * @author Muhammad Babar Naveed
 * @Date   Jul 3, 2013
 * @Email  <babar@appstertech.com>
 * 
 */
public interface RequestTaskDelegate {
	
	/**
	 * To HTTp request result
	 * 
	 * @param mRequestCode for request
	 * @param mJSONString : raw response in String 
	 */
	void RequestResult(int mRequestCode, String mJSONString, int statusCode);
	
	/**
	 * For HTTP request result
	 * 
	 * @param mResultFlag : flag to show is request success or fail, true means request Success
	 * @param mRequestCode : int number which shows which request result it is
	 * @param mObject : Object with getter/setter 
	 * @param mJSONObject : raw Object for JSON response
	 * @param mJSONString : raw response in String 
	 */
	void RequestResult(boolean mResultFlag, int mRequestCode, Object mObject, JSONObject mJSONObject, String mJSONString, int statusCode);
	
	/**
	 * 
	 * Request response to handle error
	 * @param mResultFlag : flag to show is request success or fail, true means request Success
	 * @param mRequestCode : int number which shows which request result it is
	 * @param mResponseMessage : error message
	 */
	void RequestResponse(boolean mResultFlag, int mRequestCode, String mResponseMessage, int statusCode);
	

}
