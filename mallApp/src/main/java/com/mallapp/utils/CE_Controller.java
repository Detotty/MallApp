package com.mallapp.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.*;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chatdbserver.xmpp.IMManager;
import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.mallapp.Application.MallApplication;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.GlobelWebURLs;
import com.mallapp.Model.ContactModel;
import com.mallapp.Model.ContryInfo;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sharjeel on 24/02/2016.
 */
public class CE_Controller implements Runnable {

    private static String TAG = CE_Controller.class.getCanonicalName();


    public static CE_Controller instance = null;

    private Thread mThread = null;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private String countryCode = "";
    private String countryISOName = "";
    private TelephonyManager telephoneManager;
    private List<ContryInfo> countryList;
    private Map<String, ContryInfo> countryCodeMap;
    private Map<String, ContryInfo> countryIsoMap;
    UserProfileModel user_profile;

    public CE_Controller() {

        try {

            mThread = new Thread(this);
            mThread.setName("CE_Controller");
            mThread.start();


            telephoneManager = (TelephonyManager) MallApplication.appContext.getSystemService(Context.TELEPHONY_SERVICE);
            countryISOName = telephoneManager.getSimCountryIso();
            readCsvFile();
            ContryInfo contryInfo = countryIsoMap.get(countryISOName.toUpperCase());
            if (contryInfo != null)
                countryCode = contryInfo.getDialCode().replace("+", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static CE_Controller getInstance() {
        if (instance == null) {
            instance = new CE_Controller();
        }

        return instance;
    }


    @Override
    public void run() {

        try {
            android.os.Process
                    .setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        } catch (Exception e) {
            android.util.Log.e(TAG,
                    "Error while running CE_Controller run  and error messassge "
                            + e.getMessage());
        }
    }


    public void getAllUserContactAndUpdateOnServer() {

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                readContactBookAndPreparJsonObject();
            }
        });
    }


    /***
     *
     */
    private void readContactBookAndPreparJsonObject() {

        Cursor phones = null;
        try {

            ContentResolver cr = MallApplication.appContext.getContentResolver();
            String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
            phones = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, sortOrder);

            List<String> numberList = new ArrayList<String>();
            while (phones.moveToNext()) {
                String name = phones
                        .getString(phones
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
                String number = "";
                if (id.equalsIgnoreCase("8693")) {
                    System.out.println("");
                }
                if (Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while (pCur.moveToNext()) {
                        int phNumber = pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        number = pCur.getString(phNumber);


                    }

                    if (pCur != null)
                        pCur.close();

                } else {
                    continue;
                }

                if (id.equalsIgnoreCase("8693")) {
                    System.out.println("");
                }
//                String cleanNumber="";
//                if(number.substring(0,2).equals("00")) {
//                    cleanNumber=number.substring(2);
//                }
//                else if(number.substring(0,1).equals("0"))
//                {
//                    cleanNumber=number.substring(1);
//                }
//                else {
//                    cleanNumber=number;
//                }
                numberList.add(number.replace(" ", ""));


            }
            user_profile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("SecurityToken", "h7IoVKTbHiDuE4cLTOIsKCw2NY4Nnxgj"/*SharedPreferenceUserProfile.getUserToken(MallApplication.appContext)*/);
            jsonObject.put("ProfileID", "12"/*user_profile.getUserId()*/);
            JSONArray jsonArray = new JSONArray();
            JSONObject[] jo = new JSONObject[numberList.size()];


            for (int i = 0; i < numberList.size(); i++) {
                jsonArray.put(numberList.get(i));
                jo[i] =  new JSONObject();
                jo[i].put("Mobile",numberList.get(i));

            }
            jsonObject.put("PhoneNumbers", jsonArray);
            JSONArray ja = new JSONArray(jo);
            JSONObject json = new JSONObject();
            json.put("Contact",ja);

// populate the array

            android.util.Log.e("Done", "" + json.toString());
            if (ConnectionDetector.isInternetAvailable(MallApplication.appContext)) {
                postData(json);
            }


        } catch (Exception e) {
            android.util.Log.e(TAG, " Error while saving conntact information");
        } finally {
            if (phones != null)
                phones.close();
        }
    }

    public void postData(JSONObject jsonStr) {
//        JSONObject jsonArrObj = null;
//
//            String url = GlobelWebURLs.GET_CE_USERS;
//            String result = MySqlConnection.executeHttpPost(url, jsonStr);
//            jsonArrObj = new JSONObject(result);
//            Log.e("Response", "CEContacts = " + result);

        RequestQueue mRequestQueue = Volley.newRequestQueue(MallApplication.appContext);
        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST,
                ApiConstants.GET_MALL_CONTACTS, jsonStr, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    android.util.Log.e("Response ce_contacts", response.toString());
//                    JSONArray jsonArray = response.getJSONArray("data");
                    contactBookResponse(response);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String token = SharedPreferenceUserProfile.getUserToken(MallApplication.appContext);
                Log.e("", " token:" + token);
//                    headers.put("Content-Type", "application/json");
                headers.put("Auth-Token", token);

                return headers;
            }
        };
        // Adding request to request queue
        mRequestQueue.add(jsonObjReq);
    }


    private void contactBookResponse(JSONArray jsonArray) {
        String profile_id = "";
        String profile_pic_url = "";
        String name = "";
        String phoneNumber = "";


        ArrayList<PhoneBookContacts> ConatctList = new ArrayList<>();
        ContactModel comObj = null;
        PhoneBookContacts phoneBookContacts = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            comObj = new ContactModel();
            phoneBookContacts = new PhoneBookContacts();
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                phoneNumber = object.getString("MobilePhone");
                comObj.setPhone_number(phoneNumber);
                phoneBookContacts.setMobilePhone(phoneNumber);
                if (true) {

//                    JSONObject jsonObject_profileInfo = object.getJSONObject("LightProfileInfo");
                    profile_id = object.getString("UserId");
                    name = object.getString("FullName");
                    android.util.Log.e("CEUser", "name:" + name);
                    comObj.setProfile_id(profile_id);
                    comObj.setName(name);
                    phoneBookContacts.setFirstName(name);
                    phoneBookContacts.setUserId(profile_id);

                    profile_pic_url = object.getString("ImageURL");

                    if (profile_pic_url == "null") {
                        comObj.setProfile_pic_url("null");
                        phoneBookContacts.setFileName("null");
                    } else {
                        comObj.setProfile_pic_url(profile_pic_url);
                        phoneBookContacts.setFileName(profile_pic_url);

                    }
                    phoneBookContacts.setAppUser(true);
                    phoneBookContacts.setIsContact(true);
                    IMManager.getIMManager(MallApplication.appContext).saveContactById(phoneBookContacts);
                    if (name.length() > 1) {
                        ConatctList.add(phoneBookContacts);
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        CrowdEyesGeneral.setCEContactList(ConatctList);

    }


    private void readCsvFile() {

        countryCodeMap = new HashMap<String, ContryInfo>();
        countryIsoMap = new HashMap<String, ContryInfo>();
        countryList = new ArrayList<ContryInfo>();

        InputStream is = MallApplication.appContext.getResources().openRawResource(R.raw.countrycode);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] RowData = line.split(",");

                ContryInfo country = new ContryInfo();
                String countryName = RowData[0];
                country.setCountryName(countryName);
                String countryIso = RowData[1];
                country.setCountryiso(countryIso);
                country.setDialCode(RowData[2]);

                countryList.add(country);
                countryCodeMap.put(countryName, country);
                countryIsoMap.put(countryIso, country);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

