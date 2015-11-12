/**
 * 
 */
package com.network;

import org.apache.http.HttpVersion;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.app.Application;

/**
 * @author Muhammad Babar Naveed
 * @Date   Jul 3, 2013
 * @Email  <babar@appstertech.com>
 * 
 */
public class Applications extends Application {
	
	static public DefaultHttpClient mDefaultHttpClient;

	@Override
	public void onCreate() {
		super.onCreate();
		mDefaultHttpClient = getmHttpClient();
	}

	static public DefaultHttpClient getmHttpClient() {
		
		if (Applications.mDefaultHttpClient == null) {
			
			/** connection*/
			int timeoutConnection = 9000; 
			/** socket */
			
			int timeoutSocket = 9000; 
			HttpParams mHttpParams = new BasicHttpParams();

			HttpProtocolParams.setVersion(mHttpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(mHttpParams, "utf-8");
			mHttpParams.setBooleanParameter("http.protocol.expect-continue",
					false);
			Applications.mDefaultHttpClient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(mHttpParams,
					timeoutConnection);
			HttpConnectionParams.setSoTimeout(mHttpParams, timeoutSocket);
			Applications.mDefaultHttpClient.setParams(mHttpParams);

		} else {
			
			mDefaultHttpClient = new DefaultHttpClient();
		}
		
		return mDefaultHttpClient;
	}
}
