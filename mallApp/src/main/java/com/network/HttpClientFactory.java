/**
 * 
 */
package com.network;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;



/**
 * @author Muhammad Babar Naveed
 * @Date   Jul 12, 2013
 * @Email  <babar@appstertech.com>
 * 
 */
public class HttpClientFactory {

	public static DefaultHttpClient getThreadSafeClient() {

		DefaultHttpClient client = Applications.getmHttpClient();
		ClientConnectionManager mgr = client.getConnectionManager();
		HttpParams params = client.getParams();

		client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,
				mgr.getSchemeRegistry()), params);

		return client;
	}
}
