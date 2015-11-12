package com.network;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
/**
 * 
 * @author Muhammad Babar Naveed
 *    Date Feb 26, 2013
 *
 */

public interface IHttpApi {
	
	public String doHttpRequest(HttpRequestBase httpRequest)throws IllegalArgumentException, 
				ConnectException, IOException;
	public HttpPost createHttpPost(String url, List<NameValuePair> requestParams);
	public HttpGet createHttpGet(String url, String params);
}
