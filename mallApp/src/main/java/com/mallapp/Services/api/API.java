package com.mallapp.Services.api;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

public class API {
	
	// private final String URL =Global.domain+"/b2c.php/pScripts/%s";
	private final String URL = "http://smpp5.routesms.com:8080/bulksms/%s";//resyncOrderPayments
	private final int CONNECTION_TIMEOUT = 2000;
	Context context;

	
	public API(Context context) {
		this.context = context;
	}

	public APIResponse call(String method, Map<String, String> params,
			HttpMethods httpMethod) {
		HttpURLConnection urlConnection = null;
		APIResponse response = null;
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			String loc = generateUrl(method, params);

			// if("syncSalesTransaction".equalsIgnoreCase(method))
			// save(loc);

			Log.i("Url", loc);

			URL url = new URL(loc);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(httpMethod.getName());
			urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);

			try {
				response = new APIResponse(urlConnection.getResponseCode());
			} catch (Exception e) {
				e.printStackTrace();
			}

			String responseBody;

			if (response.getCode() == ResponseCodes.OK.getCode())
				responseBody = getResponse(urlConnection, false);
			else
				responseBody = getResponse(urlConnection, true);

			response.setBody(responseBody);
			Log.i("Response.....", responseBody);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

		Runtime.getRuntime().gc();
		return response;

	}

	public void save(String data) {
		String filename = "jsone.txt";
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		FileOutputStream fos;
		byte[] data1 = new String(data).getBytes();

		try {
			fos = new FileOutputStream(file);
			fos.write(data1);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// handle exception
		} catch (IOException e) {
			// handle exception
		}
	}

	public InputStream getInputStream(String method,
			Map<String, String> params, HttpMethods httpMethod) {

		HttpURLConnection urlConnection = null;
		InputStream instream = null;

		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			String loc = generateUrl(method, params);
			Log.e("Url", loc);

			URL url = new URL(loc);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(httpMethod.getName());
			urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);

			if (urlConnection.getResponseCode() == ResponseCodes.OK.getCode())
				instream = urlConnection.getInputStream();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
		Runtime.getRuntime().gc();
		return instream;
	}

	/**
	 * Calculate the url of a REST call to the API
	 * 
	 * @param method
	 * @param params
	 * @return String the URL
	 */

	private String generateUrl(String method, Map<String, String> params)
			throws Exception {

		String loc = String.format(URL, method);
		String query = generateParams(params);
		loc += query;
		
		// escape it
		try {
			URI uri = new URI(loc);
			loc = uri.toURL().toString();
		} catch (URISyntaxException e) {
			throw new Exception("calcRestUrl:cannot escape URI:" + loc);
		}
		return loc;
	}

	private String generateParams(Map<String, String> params) {

		String query = "";

		// handle map of params as query params or headers
		if (params != null) {
			for (String key : params.keySet()) {
				String value = params.get(key);

				try {
					value = URLEncoder.encode(value, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				// ensure we are not adding null strings as they will actual say
				// null on query
				if (value == null)
					value = "";

				if (query.length() == 0)
					query += "?" + key + "=" + value;
				else
					query += "&" + key + "=" + value;
			}
		}
		return query;
	}

	public String getResponse(HttpURLConnection urlConnection,
			boolean checkError) {

		try {

			InputStream instream = checkError ? urlConnection.getErrorStream()
					: urlConnection.getInputStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					instream));
			String inputLine;
			StringBuffer responseString = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				responseString.append(inputLine);
			}

			if (in != null)
				in.close();

			if (instream != null)
				instream.close();

			return responseString.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Runtime.getRuntime().gc();
		return null;
	}

	public static boolean isNetworkAvailable(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager
					.getActiveNetworkInfo();
			return activeNetworkInfo != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
	
	static final String DIR = "POS";
	public static final String FILE = "response.txt";

	public static void writeResponseToFile(InputStream in) {
		try {
			File root = new File(Environment.getExternalStorageDirectory(), DIR);
			if (!root.exists())
				root.mkdirs();

			File file = new File(root, FILE);

			OutputStream stream = new BufferedOutputStream(
					new FileOutputStream(file));
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				stream.write(buffer, 0, len);
			}

			if (stream != null)
				stream.close();

			if (in != null)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Runtime.getRuntime().gc();
	}

	public static String readResponseFromFile() {
		try {

			File root = new File(Environment.getExternalStorageDirectory(), DIR);
			if (!root.exists())
				return "0";

			File file = new File(root, FILE);
			if (!file.exists())
				return "0";

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			String response = "";

			while ((line = br.readLine()) != null) {
				response += line;
			}

			if (br != null)
				br.close();

			return response;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Runtime.getRuntime().gc();

		return "0";
	}

	

	
	
}
