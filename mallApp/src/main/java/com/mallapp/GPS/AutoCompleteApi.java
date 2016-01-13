package com.mallapp.GPS;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mallapp.Model.PlaceAutoCompleteModel;
import com.mallapp.Model.UserLocationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Sharjeel on 11/6/2015.
 */
public class AutoCompleteApi {

    private static final String LOG_TAG = "ExampleApp";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_NEAYBYPLACE = "/nearbysearch";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyDo3jXU0q6nI9VwUE5JO3dqnJFIBaaOaEg";
//										   AIzaSyBDI_nkm_I3UuQRAXjQWQx550zD87Rfc4M
//	private static final String API_KEY = "AIzaSyCp6TKbZckem3_I7hDOmOEueJ3Qf6WVS40";

//	public static ArrayList<String> autocomplete(String input,String category) {
//
//		//ArrayList<HashMap<String, String>> resultList = null;
//	    ArrayList<String> resultList = null;
//		HttpURLConnection conn = null;
//
//		StringBuilder jsonResults = new StringBuilder();
//
//	    try {
//	        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
//	        sb.append("?input =" + URLEncoder.encode(input, "utf8"));
//	        sb.append("&sensor=false&radius=5000");
//            sb.append("types="+ category);
//	        sb.append("&key=" + API_KEY);
//
//	        Log.e("url", ""+sb.toString());
//
//	        URL url = new URL(sb.toString());
//
//	        conn = (HttpURLConnection) url.openConnection();
//
//	        InputStreamReader in = new InputStreamReader(conn.getInputStream());
//
//	        // Load the results into a StringBuilder
//	        int read;
//	        char[] buff = new char[1024];
//	        while ((read = in.read(buff)) != -1) {
//	            jsonResults.append(buff, 0, read);
//	        }
//	    } catch (MalformedURLException e) {
//	        Log.e(LOG_TAG, "Error processing Places API URL", e);
//	        return resultList;
//	    } catch (IOException e) {
//	        Log.e(LOG_TAG, "Error connecting to Places API", e);
//	        return resultList;
//	    } finally {
//	        if (conn != null) {
//	            conn.disconnect();
//	        }
//	    }
//
//	    try{
//	        // Create a JSON object hierarchy from the results
//	        JSONObject jsonObj = new JSONObject(jsonResults.toString());
//	        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
//	        HashMap<String, String> hm = new HashMap<String, String>();
//
//	        // Extract the Place descriptions from the results
//	        resultList = new ArrayList<String>(predsJsonArray.length());//ArrayList<HashMap<String, String>>(); //ArrayList<String>(predsJsonArray.length());
//
//	        for (int i = 0; i < predsJsonArray.length(); i++) {
////	        	hm = new HashMap<String, String>();
////	            hm.put("place_id", predsJsonArray.getJSONObject(i).getString("place_id"));
////	            hm.put("place_name", predsJsonArray.getJSONObject(i).getString("description"));
////	            resultList.add(hm);
//	           resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
//	        }
//	        //Log.e("url", ""+resultList.toString());
//	    } catch (JSONException e) {
//	        Log.e(LOG_TAG, "Cannot process JSON results", e);
//	    }
//
//	    return resultList;
//	}


    public static ArrayList<PlaceAutoCompleteModel> autocomplete(String input, String category, UserLocationModel model) {

        //ArrayList<HashMap<String, String>> resultList = null;
        ArrayList<PlaceAutoCompleteModel> resultList = null;
        HttpURLConnection conn = null;

        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_NEAYBYPLACE + OUT_JSON);
            sb.append("?keyword=halal|" + URLEncoder.encode(input, "UTF-8"));
            sb.append("&radius=10000");


            if(category!=null)
                sb.append("&types="+URLEncoder.encode(category, "UTF-8"));

            if(model!=null)
                sb.append("&location=" + model.getLatitude()+","+model.getLongitude()); // This must be specified as latitude,longitude.
//            else
//                sb.append("&location=0,0"); // This must be specified as latitude,longitude.

            sb.append("&key=" + API_KEY);

            Log.e("RequestURl", "" + sb.toString());

            URL url = new URL(sb.toString());

            conn = (HttpURLConnection) url.openConnection();

            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
	    /* response
	    {
  "html_attributions": [],
  "results": [
    {
      "geometry": {
        "location": {
          "lat": -33.870775,
          "lng": 151.199025
        }
      },
      "icon": "http://maps.gstatic.com/mapfiles/place_api/icons/travel_agent-71.png",
      "id": "21a0b251c9b8392186142c798263e289fe45b4aa",
      "name": "Rhythmboat Cruises",
      "opening_hours": {
        "open_now": true
      },
      "photos": [
        {
          "height": 270,
          "html_attributions": [],
          "photo_reference": "CnRnAAAAF-LjFR1ZV93eawe1cU_3QNMCNmaGkowY7CnOf-kcNmPhNnPEG9W979jOuJJ1sGr75rhD5hqKzjD8vbMbSsRnq_Ni3ZIGfY6hKWmsOf3qHKJInkm4h55lzvLAXJVc-Rr4kI9O1tmIblblUpg2oqoq8RIQRMQJhFsTr5s9haxQ07EQHxoUO0ICubVFGYfJiMUPor1GnIWb5i8",
          "width": 519
        }
      ],
      "place_id": "ChIJyWEHuEmuEmsRm9hTkapTCrk",
      "scope": "GOOGLE",
      "alt_ids": [
        {
          "place_id": "D9iJyWEHuEmuEmsRm9hTkapTCrk",
          "scope": "APP"
        }
      ],
      "reference": "CoQBdQAAAFSiijw5-cAV68xdf2O18pKIZ0seJh03u9h9wk_lEdG-cP1dWvp_QGS4SNCBMk_fB06YRsfMrNkINtPez22p5lRIlj5ty_HmcNwcl6GZXbD2RdXsVfLYlQwnZQcnu7ihkjZp_2gk1-fWXql3GQ8-1BEGwgCxG-eaSnIJIBPuIpihEhAY1WYdxPvOWsPnb2-nGb6QGhTipN0lgaLpQTnkcMeAIEvCsSa0Ww",
      "types": [
        "travel_agency",
        "restaurant",
        "food",
        "establishment"
      ],
      "vicinity": "Pyrmont Bay Wharf Darling Dr, Sydney"
    }


  ],
  "status": "OK"
}
	     */
        try{
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());

            if(jsonObj.getString("status").equalsIgnoreCase("ok")) {

                Log.d("PlaceAPIResult:", jsonResults.toString());

                JSONArray JSONArray = jsonObj.getJSONArray("results");

                Gson gson = new Gson();

                Type listType = new TypeToken<ArrayList<PlaceAutoCompleteModel>>(){}.getType();

                resultList = gson.fromJson(JSONArray.toString(), listType);

                // Extract the Place descriptions from the results
//                resultList = new ArrayList<PlaceAutoCompleteModel>(predsJsonArray.length());//ArrayList<HashMap<String, String>>(); //ArrayList<String>(predsJsonArray.length());
//
//                for (int i = 0; i < predsJsonArray.length(); i++) {
//
//                    CharSequence a = predsJsonArray.getJSONObject(i).getString("place_id");
//                    CharSequence b = predsJsonArray.getJSONObject(i).getString("description");
//                    PlaceAutoCompleteModel p = new PlaceAutoCompleteModel(a, b);
//                    resultList.add(p);
//                    //  resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
//                }
                Log.e("url", "" + JSONArray.toString());

                return resultList;
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }


    public static ArrayList<PlaceAutoCompleteModel> autocomplete(String input, UserLocationModel model) {

        //ArrayList<HashMap<String, String>> resultList = null;
        ArrayList<PlaceAutoCompleteModel> resultList = null;
        HttpURLConnection conn = null;

        StringBuilder jsonResults = new StringBuilder();

        try {
            //String placeUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";
            // input=shaheen&radius=10000&location=31.562061,74.3331049&key=AIzaSyBDI_nkm_I3UuQRAXjQWQx550zD87Rfc4M
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?input=" + URLEncoder.encode(input, "UTF-8"));
            sb.append("&radius=50000");
            //sb.append("&types="+ category);

            if(model!=null)
                sb.append("&location=" + model.getLatitude()+","+model.getLongitude()); // This must be specified as latitude,longitude.
//            else
//                sb.append("&location=0,0"); // This must be specified as latitude,longitude.

            sb.append("&key=" + API_KEY);

            Log.e("RequestURl", "" + sb.toString());

            URL url = new URL(sb.toString());

            conn = (HttpURLConnection) url.openConnection();

            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
	    /* response
	    {
  "html_attributions": [],
  "results": [
    {
      "geometry": {
        "location": {
          "lat": -33.870775,
          "lng": 151.199025
        }
      },
      "icon": "http://maps.gstatic.com/mapfiles/place_api/icons/travel_agent-71.png",
      "id": "21a0b251c9b8392186142c798263e289fe45b4aa",
      "name": "Rhythmboat Cruises",
      "opening_hours": {
        "open_now": true
      },
      "photos": [
        {
          "height": 270,
          "html_attributions": [],
          "photo_reference": "CnRnAAAAF-LjFR1ZV93eawe1cU_3QNMCNmaGkowY7CnOf-kcNmPhNnPEG9W979jOuJJ1sGr75rhD5hqKzjD8vbMbSsRnq_Ni3ZIGfY6hKWmsOf3qHKJInkm4h55lzvLAXJVc-Rr4kI9O1tmIblblUpg2oqoq8RIQRMQJhFsTr5s9haxQ07EQHxoUO0ICubVFGYfJiMUPor1GnIWb5i8",
          "width": 519
        }
      ],
      "place_id": "ChIJyWEHuEmuEmsRm9hTkapTCrk",
      "scope": "GOOGLE",
      "alt_ids": [
        {
          "place_id": "D9iJyWEHuEmuEmsRm9hTkapTCrk",
          "scope": "APP"
        }
      ],
      "reference": "CoQBdQAAAFSiijw5-cAV68xdf2O18pKIZ0seJh03u9h9wk_lEdG-cP1dWvp_QGS4SNCBMk_fB06YRsfMrNkINtPez22p5lRIlj5ty_HmcNwcl6GZXbD2RdXsVfLYlQwnZQcnu7ihkjZp_2gk1-fWXql3GQ8-1BEGwgCxG-eaSnIJIBPuIpihEhAY1WYdxPvOWsPnb2-nGb6QGhTipN0lgaLpQTnkcMeAIEvCsSa0Ww",
      "types": [
        "travel_agency",
        "restaurant",
        "food",
        "establishment"
      ],
      "vicinity": "Pyrmont Bay Wharf Darling Dr, Sydney"
    }


  ],
  "status": "OK"
}
	     */
        try{
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());

            if(jsonObj.getString("status").equalsIgnoreCase("ok")) {

                Log.d("PlaceAPIResult:", jsonResults.toString());

                String stringJson = jsonObj.getString("predictions");

                Gson gson = new Gson();

                Type listType = new TypeToken<ArrayList<PlaceAutoCompleteModel>>(){}.getType();

                resultList = gson.fromJson(stringJson, listType);

                // Extract the Place descriptions from the results
//                resultList = new ArrayList<PlaceAutoCompleteModel>(predsJsonArray.length());//ArrayList<HashMap<String, String>>(); //ArrayList<String>(predsJsonArray.length());
//
//                for (int i = 0; i < predsJsonArray.length(); i++) {
//
//                    CharSequence a = predsJsonArray.getJSONObject(i).getString("place_id");
//                    CharSequence b = predsJsonArray.getJSONObject(i).getString("description");
//                    PlaceAutoCompleteModel p = new PlaceAutoCompleteModel(a, b);
//                    resultList.add(p);
//                    //  resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
//                }
                Log.e("url", "" + resultList.toString());

                return resultList;
            }
            else  if(jsonObj.getString("status").equalsIgnoreCase("ZERO_RESULTS")){

                resultList = new ArrayList<PlaceAutoCompleteModel>();
                return resultList;
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

}