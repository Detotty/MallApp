package com.mallapp.Controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mallapp.Application.MallApplication;
import com.mallapp.Model.VolleyErrorHelper;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.listeners.VolleyDataReceivedListener;
import com.mallapp.listeners.VolleyErrorListener;
import com.mallapp.utils.SharedInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sharjeel on 11/6/2015.
 */
public class InitialDataDownload /*implements VolleyDataReceivedListener, VolleyErrorListener*/ {

    /*private Context context;

    private String tag_json_obj = "json_obj_req";

    private String TAG = InitialDataDownload.class.getSimpleName();

    private String requestType ;

    private final String CategoryRequest = "GetCategory";
    private final String ProviderRequest = "GetProvider";


    public InitialDataDownload(Context context){
        this.context = context;
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG, "Response:" + response.toString());

        if(requestType == CategoryRequest){
            try {
                boolean success = response.getBoolean("Success");
                if (success) {

                   *//* Gson gson = new Gson();

                    String jsonString = response.getJSONArray("CategoryList").toString();

                    Type listType = new TypeToken<List<CategoryListingModel>>(){}.getType();

                    List<CategoryListingModel> model = gson.fromJson(jsonString, listType);

                    Log.d(TAG, "CategoryListingModel Size:" + model.size());

                    if( model!= null )
                        SharedInstance.getInstance().setCategoryHashMap(context,model);

                    getProviderList();*//*


                }
                else if (response.has("Message")){

                    String message = response.getString("Message");

                    if(message!=null && message!= "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }

            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        else if(requestType == ProviderRequest){
            try {
                boolean success = response.getBoolean("Success");
                if (success) {

                   *//* Gson gson = new Gson();

                    String jsonString = response.getJSONArray("ProviderList").toString();

                    Type listType = new TypeToken<List<ProviderListModel>>(){}.getType();

                    List<ProviderListModel> model = gson.fromJson(jsonString.toString(), listType);

                    Log.d(TAG, "Provider List Size:" + model.size());

                    // if( model!= null )
                    SharedInstance.getInstance().setProviderHashMap(context, model);*//*



                }
                else if (response.has("Message")){

                    String message = response.getString("Message");

                    if(message!=null && message!= "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

        String message = VolleyErrorHelper.getMessage(volleyError, context);



        com.mallapp.utils.Log.e("", " error message ..." + message);
    }

    public void getCategory(){

        String url = GlobelAPIURLs.GET_CATEGORY_TREE_URL_KEY;

        url += "/null/User/"+ SharedPreferenceUserProfile.getUserId(context);

        Log.d("userId","Category Url:"+ url);

        requestType = CategoryRequest;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,this,this)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String token = SharedPreferenceUserProfile.getUserToken(context);
                com.mallapp.utils.Log.e("", " token" + token);
                headers.put("Content-Type", "application/json");
                headers.put("Auth-Token", token);

                return headers;
            }
        };

        MallApplication.getInstance().addToRequestQueue(request, tag_json_obj);

    }


    public void getProviderList(){

        String url  = GlobelAPIURLs.GET_PROVIDER_LIST_URL_KEY;

        requestType = ProviderRequest;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,this,this)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String token = SharedPreferenceUserProfile.getUserToken(context);
                com.mallapp.utils.Log.e("", " token" + token);
                headers.put("Content-Type", "application/json");
                headers.put("Auth-Token", token);

                return headers;
            }
        };


        MallApplication.getInstance().addToRequestQueue(request, tag_json_obj);
    }*/
}
