package com.mallapp.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.mallapp.View.R;

import org.json.JSONObject;

/**
 * Created by Sharjeel on 11/5/2015.
 */
public class VolleyErrorHelper {
    /**
     * Returns appropriate message which is to be displayed to the user
     * against the specified error object.
     *
     * @param error
     * @param context
     * @return
     */
    public static String getMessage(VolleyError error, Context context) {
        if (error instanceof TimeoutError) {
            return context.getResources().getString(R.string.request_error_message);
        } else if (isServerProblem(error)) {
            return handleServerError(error, context);
        } else if (isNetworkProblem(error)) {
            return context.getResources().getString(R.string.network_error);
        }
        return context.getResources().getString(R.string.request_error_message);

    }

    /**
     * Determines whether the error is related to network
     *
     * @param error
     * @return
     */
    private static boolean isNetworkProblem(VolleyError error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    /**
     * Determines whether the error is related to server
     *
     * @param error
     * @return
     */
    private static boolean isServerProblem(VolleyError error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to
     * show a message retrieved from the server.
     *
     * @param err
     * @param context
     * @return
     */
    private static String handleServerError(VolleyError err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 400:
                case 500:
                case 422:
                case 401:
                    try {
                        // server might return error like this { "error": "Some error occured" }
                        // Use "Gson" to parse the result
                        String data = new String(response.data);
                        //Log.d("testerror", "" + response.data);
                        Log.d("data", "" + data);
                        JSONObject json = new JSONObject(data);

                        // JSONObject obj = new JSONObject(data);
//                        trimmedString = obj.getString(key);
//
//                        HashMap<String, String> result = new Gson().fromJson(json.toString(),
//                                new TypeToken<Map<String, String>>() {
//                                }.getType());


                        if (json != null && json.has("Message")) {
                            return json.getString("Message");
                        } else if (json != null && json.has("ErrorMessages")) {
//                            String[] array = result.get("ErrorMessages");
                            return json.getString("ErrorMessages");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // invalid request
                    return error.getMessage();

                default:
                    return context.getResources().getString(R.string.request_error_message);
            }
        }
        return context.getResources().getString(R.string.request_error_message);
    }
}
