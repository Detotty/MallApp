package com.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.net.ssl.HostnameVerifier;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import org.apache.http.conn.scheme.SocketFactory;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

/**
 * 
 * @author Muhammad Babar Naveed Date Feb 26, 2013
 * 
 */
public class AndroidHTTP extends Thread {

	public AndroidHTTP(Context context) {
		super();
		this.context = context;
	}

	HttpClient httpclient;
	String url;
	String parameters;
	AndroidHttpListener listener;
	String packet;
	List<NameValuePair> postParameters;
	int statusCode;
	private volatile Looper mMyLooper;
	ClientConnectionManager conMgr;
	BufferedReader bufferedReader = null;
	Context context;
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public List<NameValuePair> getPostParameters() {
		return postParameters;
	}

	public void setPostParameters(List<NameValuePair> postParameters) {
		this.postParameters = postParameters;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	private HttpRequestType httpTypee;

	public AndroidHttpListener getListener() {
		return listener;
	}

	public void setListener(AndroidHttpListener listener) {
		this.listener = listener;
	}



	public void setUrl(String url) {
		this.url = url;
	}

	public void setParameters(String params) {
		this.parameters = params;
	}


	public HttpParams getHTTPParams() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpProtocolParams.setUserAgent(params, "MeneameAndroid/0.1.6");
		// Make pool
		ConnPerRoute connPerRoute = new ConnPerRouteBean(12);
		ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);
		ConnManagerParams.setMaxTotalConnections(params, 20);
		// Set timeout
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		// Some client params
		HttpClientParams.setRedirecting(params, false);

		return params;
	}

	public void setHTTPType(HttpRequestType reqType) {
		httpTypee = reqType;
	}

	/**
	 * 
	 * @param reqType
	 */
	public void submitRequest(HttpRequestType reqType,Context context) throws Exception
	{

		if(reqType == HttpRequestType.HTTPGet)
		{
			HttpGet httpGet = null;
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));

			@SuppressWarnings("unused")
			SocketFactory trustAll;
			schReg.register(new Scheme("https", new FakeSocketFactory(), 443));

			HttpParams pp = getHTTPParams();
			conMgr = new ThreadSafeClientConnManager(pp, schReg);

			this.httpclient = new DefaultHttpClient(conMgr,pp);
			if(parameters == null || parameters.equals(""))
			{
				//Log.e("Httpp ---------------------URL", url);
				httpGet = new HttpGet(url.trim());
			}
			else
			{
				//Log.e("Httpp ---------------------URL", url+"?"+parameters);
				httpGet= new HttpGet(url+"?"+parameters);
			}

			ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>()
					{ 
				public byte[] handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException 
						{ 
					HttpEntity entity = response.getEntity(); 
					if (entity != null)
					{
						return EntityUtils.toByteArray(entity);
					}else
					{
						return null;
					}

						}
					};

					byte data[]	= httpclient.execute(httpGet, handler);

					if(data != null)
					{
						if(listener!=null)
							listener.dataRecieved(data);
					}

		}
		else if (reqType==HttpRequestType.HTTPSGet)
		{
			HttpGet httpGet = null;

			this.httpclient = new ExtendedHTTPClient(context);
			if(parameters == null || parameters.equals(""))
			{
				//Log.e("Httpp ---------------------URL", url);
				httpGet = new HttpGet(url.trim());
			}
			else
			{
				//Log.e("Httpp ---------------------URL", url);
				httpGet= new HttpGet(url+"?"+parameters);
			}

			ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>()
					{ 
				public byte[] handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException 
						{ 
					HttpEntity entity = response.getEntity(); 
					if (entity != null)
					{
						return EntityUtils.toByteArray(entity);
					}else
					{
						return null;
					}

						}
					};
					byte data[]	= httpclient.execute(httpGet, handler);

					if(data != null)
					{
						if(listener!=null)
							listener.dataRecieved(data);
					}
		}
		else if(reqType==HttpRequestType.HTTPPost)
		{
			this.httpclient  = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);

			//Log.e("Httpp ---------------------URL", url);
			if(postParameters!=null)
			{
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
				request.setEntity(entity);
				HttpResponse response= this.httpclient .execute(request);

				bufferedReader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));
				StringBuffer stringBuffer = new StringBuffer("");
				String line = "";
				String LineSeparator = System.getProperty("line.separator");
				while ((line = bufferedReader.readLine()) != null) {
					stringBuffer.append(line + LineSeparator); 
				}
				bufferedReader.close();

				byte data[]=stringBuffer.toString().getBytes();
				if(data != null)
				{
					//String str = new String(data);
					if(listener!=null)
						listener.dataRecieved(data);
					//LogStlealth.printI("HTTPClient---DataRecieved------", str);

				}
			}



		}
		else if(reqType==HttpRequestType.HTTPSPost)
		{
//			HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			this.httpclient = new ExtendedHTTPClient(context);

			HttpPost request = new HttpPost(url);
			//Log.e("Httpps ---------------------URL", url);

			if(postParameters!=null)
			{
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
				request.setEntity(entity);
				HttpResponse response = httpclient.execute(request);

				 bufferedReader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));
				StringBuffer stringBuffer = new StringBuffer("");
				String line = "";
				String LineSeparator = System.getProperty("line.separator");
				while ((line = bufferedReader.readLine()) != null) {
					stringBuffer.append(line + LineSeparator); 
				}
				bufferedReader.close();

				byte data[]=stringBuffer.toString().getBytes();
				if(data != null)
				{
					if(listener!=null)
						listener.dataRecieved(data);
				}
			}




		}

	}

	public void run() {
		Looper.prepare();
		mMyLooper=Looper.myLooper();
		try {
			submitRequest(httpTypee,context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Looper.loop();
	}

	public void killMe(){
		if(mMyLooper!=null)
			mMyLooper.quit();
	}
}
