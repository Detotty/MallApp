package com.mallapp.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mallapp.Application.MallApplication;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.FacebookProfileModel;
import com.mallapp.Model.ThirdPartyRegistrationModel;
import com.mallapp.Model.UserProfile;
import com.mallapp.Model.VolleyErrorHelper;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;
import com.mallapp.View.RegistrationProfileActivity;
import com.mallapp.globel.GlobelServices;
import com.mallapp.imagecapture.Image_Scaling;
import com.mallapp.listeners.RegistrationUserListener;
import com.mallapp.utils.SharedInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sharjeel on 11/5/2015.
 */
public class RegistrationController {

    private Context context;
    private RegistrationUserListener listener;


    private String TAG = RegistrationController.class.getSimpleName();

    // Tag used to cancel the request
    private String tag_json_obj = "json_obj_req";
    private ProgressDialog progressDialog;

    public RegistrationController(Context context){
        this.context = context;
    }


    public void  updateUserProfile( final UserProfile user_profile , RegistrationUserListener registrationUserListener) {

        progressDialog =  ProgressDialog.show(context,"",context.getResources().getString(R.string.updating_profile_data_message) );

        this.listener  = registrationUserListener;


        //JSONObject jsonObj = null ;
        try {

            if(user_profile.getImageBitmap()!= null) {
                user_profile.setImageBase64String(Image_Scaling.encodeTobase64(user_profile.getImageBitmap()));
                user_profile.setImageBitmap(null);
            }

            String userId = SharedPreferenceUserProfile.getUserId(context);

            user_profile.setUserId(userId);

            Gson gson = new Gson();

            final String jsonString = gson.toJson( user_profile );
            //System.out.println(jsonString);
            final JSONObject jsonObject = new JSONObject( jsonString );

            android.util.Log.d("", "json send usper profile ...." + jsonObject.toString() );

            String url		= GlobelServices.PROFILE_UPDATE_URL_KEY;

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if( progressDialog!= null )
                            progressDialog.dismiss();

                        boolean success = jsonObject.getBoolean("Success");
                        Log.e(" update ", "Update user response: " + jsonObject.toString());

                        if (success) {

                            //Log.e(" update ", "JSONObject  update user = " + jsonObject.toString());
//
//                            Gson gson = new Gson();
//                            String jsonString = jsonObject.getJSONObject("Profile").toString();
//
//                            UserProfile userProfile = gson.fromJson(jsonString, UserProfile.class);

                            listener.onDataReceived(user_profile);

                        } else if (jsonObject.has("Message")){

                            String message = jsonObject.getString("Message");

                            if(message!=null && message!= "")
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();

                    String message = VolleyErrorHelper.getMessage(volleyError,context);



                    Log.e("", " error message ..." + message);

                    if(message!=null && message!= "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    String token = SharedPreferenceUserProfile.getUserToken(context);
                    Log.e("", " token:" + token);
                    //headers.put("Content-Type", "application/json");
                    headers.put("Auth-Token", token);

                    return headers;
                }
            };

            MallApplication.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void registerWithFacebook(final FacebookProfileModel facebookProfileModel){

        progressDialog =  ProgressDialog.show(context,"",context.getResources().getString(R.string.registration_with_fb_dialog_message));

        try{

            ThirdPartyRegistrationModel thirdPartyRegistrationModel = new ThirdPartyRegistrationModel();
            thirdPartyRegistrationModel.setAccount("facebook");
            thirdPartyRegistrationModel.setId(facebookProfileModel.getId());

            String[] name = facebookProfileModel.getName().split(" ");

            String firstName = name[0];

            String lastName = "";

            for(int i=1;i< name.length;i++){
                lastName += name[1];
            }

            thirdPartyRegistrationModel.setFirstName(firstName);

            if(lastName!= "")
                thirdPartyRegistrationModel.setLastName(lastName);

            String parameters = "" ;

            parameters += "account="+thirdPartyRegistrationModel.getAccount()
                    + "&id="+ thirdPartyRegistrationModel.getId()
                    + "&firstName="+ thirdPartyRegistrationModel.getFirstName()
                    + "&lastName="+ thirdPartyRegistrationModel.getLastName();

            Gson gson = new Gson();

            JSONObject jsonObject = new JSONObject( gson.toJson(thirdPartyRegistrationModel) );

            Log.d(TAG, jsonObject.toString());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, GlobelServices.FB_REG_URL_KEY+thirdPartyRegistrationModel.getId(),
                    null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonObj) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();

                    Log.d(TAG, jsonObj.toString());

                    try {
                        boolean success = jsonObj.getBoolean("Success");



                             /** {"Success":true,"User_Id":"3def09ea-9850-46dc-9831-a8d6b1f44b00",
                             * "Auth_Token":"d1bbadcb-1118-4c4a-84e4-38221f7dc37b",
                             * "Message":"","httpStatusCode":200}*/


                        if(success) {
                            String profileID = jsonObj.getString("User_Id");
                            String security_token = jsonObj.getString("Auth_Token");


                            SharedPreferenceUserProfile.saveUserToken(security_token, profileID, context);

//                            downloadCategoryList(true);

                            //Toast.makeText(getApplicationContext(), "Registration Successfully!", Toast.LENGTH_LONG).show();


                            Intent profileScreen = new Intent(context, RegistrationProfileActivity.class);
                            context.startActivity(profileScreen);

                        }else{

                            if( jsonObj.has("Message")) {
                                String message = jsonObj.getString("Message");
                                if(message != "")
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                    catch(JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if(progressDialog!=null)
                        progressDialog.dismiss();


                    String message = VolleyErrorHelper.getMessage(volleyError, context);



                    Log.e("", " error message ..." + message);

                    if(message!=null && message!= "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }

            }
            );



            // Adding request to request queue
            MallApplication.getInstance().addToRequestQueue(request, tag_json_obj);



        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //Download CatagoryList
    /*public void downloadCategoryList(boolean facebookRegistration){

        if(!facebookRegistration)
            progressDialog =  ProgressDialog.show(context,"",context.getResources().getString(R.string.login_in_message));

        InitialDataDownload initialDataDownload = new InitialDataDownload(context);
        initialDataDownload.getCategory();

        if( !facebookRegistration )
            getUserProfile(null);

    }

    public void getUserProfile(  RegistrationUserListener registrationUserListener) {

        if(registrationUserListener != null)
            this.listener  = registrationUserListener;

        String userId = SharedPreferenceUserProfile.getUserId(context);

        String url = GlobelAPIURLs.GET_USER_PROFILE_URL_KEY+"/"+userId;

        Log.e("Registration Controller:", " url ..." + url);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.d("","ProfileModel:"+obj.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();

                try {

                    boolean success = obj.getBoolean("Success");

                    if (success) {
                        JSONObject jsonObj = obj.getJSONObject("Profile");
                        Log.e("", " user not exist ..." + obj.toString());

                        UserProfile user_profile = null;

                        Gson gson = new Gson();

                        user_profile  = gson.fromJson(jsonObj.toString(),UserProfile.class);
                        if(user_profile== null)
                            user_profile = new UserProfile();

//                            if(listener!= null)
//                                 listener.onDataReceived(user_profile);
//                            else
                        SharedInstance.getInstance().getSharedHashMap().put(AppConstants.PROFILE_DATA,user_profile);
                        ((Code_Verifiction)context).startProfileActivity();


				*//*
                 * {"httpStatusCode":200,"Success":true,
				 * "Profile":{"Street":null,"Phone":"923361466623",
				 * "Password":null,"LastUpdateUnixDatetime":1300124700,
				 * "CurrentLoctionName":null,"DeviceType":"android","LastName":"Attiq",
				 * "Dob":"2010-03-05T19:00:00Z","LastLoginUTCDateTime":null,
				 * "Zipcode":null,"IsActive":true,"Image":null,"LastLoginUnixDateTime":0,
				 * "FirstName":"Attiq","MobilePhone":"923361466623","LastUpdateUTCDateTime":"2011-03-14T17:45:00",
				 * "ImageBase64String":null,"DefaultLocationName":"Lahore, Punjab, Pakistan",
				 * "CreatedUnixDatetime":1.438680902327E9,"UserId":"3def09ea-9850-46dc-9831-a8d6b1f44b00",
				 * "LanguageId":null,"DefaultLocationLatLong":"0.00.0","Country":"Pakistan","Title":"Mr.",
				 * "City":null,"Summary":null,"UnixDob":1267815600,"CurrentLocationLatLong":null,"Email":"atr@quantumcph.com",
				 * "PersonIdentificationNumber":null,"FileName":"http:\/\/52.28.59.218:9010\/\/profiles\/images\/3def09ea-9850-46dc-9831-a8d6b1f44b00.png",
				 * "Username":null,"Gender":"Female","CreatedUTCDateTime":"2015-08-04T09:35:02.327","IsDeleted":false},"Message":""}
				 *//*
                    }
                    else if (obj.has("Message")){

                        String message = obj.getString("Message");

                        if(message!=null && message!= "")
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("", " user not exist ..." + volleyError.toString());

                if(progressDialog!=null)
                    progressDialog.dismiss();

                String message = VolleyErrorHelper.getMessage(volleyError,context);



                Log.e("", " error message ..." + message);

                if(message!=null && message!= "")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                else {
                    String serverError = context.getResources().getString(R.string.request_error_message);
                    Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                }


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<String, String>();
                String token = SharedPreferenceUserProfile.getUserToken(context);
                Log.e("", " token" + token);
                headers.put("Content-Type", "application/json");
                headers.put("Auth-Token", token);

                return headers;

            }

        };

        MallApplication.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);

    }*/


}
