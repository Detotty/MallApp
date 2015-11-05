package com.mallapp.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mallapp.Application.MallApplication;
import com.mallapp.Model.FacebookProfileModel;
import com.mallapp.Model.ThirdPartyRegistrationModel;
import com.mallapp.Model.VolleyErrorHelper;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;
import com.mallapp.View.RegistrationProfileActivity;
import com.mallapp.globel.GlobelServices;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sharjeel on 11/5/2015.
 */
public class RegistrationController {

    private Context context;

    private String TAG = RegistrationController.class.getSimpleName();

    // Tag used to cancel the request
    private String tag_json_obj = "json_obj_req";
    private ProgressDialog progressDialog;

    public RegistrationController(Context context){
        this.context = context;
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


}
