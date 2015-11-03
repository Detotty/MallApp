package com.mallapp.utils;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Log class to show logging enabled/disabled by a single flag 
 * <br />
 * <b> Should be TURNED-OFF while sending debugging build <b />
 * @author naeem.arshad
 *
 */
public class Log {

    public static final boolean MODE_DEBUG = true;

    /**
     * Debug
     * @param TAG
     * @param message
     */
    public static void d(String TAG, String message) {
	if (Log.MODE_DEBUG)
	    android.util.Log.d(TAG, message);
    }

    /**
     * Error - Send an ERROR log message.
     * @param TAG
     * @param message
     */
    public static void e(String TAG, String message) {
	if (Log.MODE_DEBUG)
	    android.util.Log.e(TAG, message);
    }

    /**
     * Information
     * @param TAG
     * @param message
     */
    public static void i(String TAG, String message) {
	if (Log.MODE_DEBUG)
	    android.util.Log.i(TAG, message);
    }

    /**
     * Verbose
     * @param TAG
     * @param message
     */
    public static void v(String TAG, String message) {
	if (Log.MODE_DEBUG)
	    android.util.Log.v(TAG, message);
    }

    /**
     * Warnings
     * @param TAG
     * @param message
     */
    public static void w(String TAG, String message) {
	if (Log.MODE_DEBUG)
	    android.util.Log.w(TAG, message);
    }

    /**
     * Shows a toast
     * @param context
     * @param string
     */
    public static void showToast(Context context, String string) {
	if (Log.MODE_DEBUG)
	    Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * Logs all attributes of a bundle
     * @param bundle
     */
    public static void logBundle(Bundle bundle) {
	if (Log.MODE_DEBUG)
	    for (String key : bundle.keySet()) {
		Log.d("Bundle Debug", key + " = \"" + bundle.get(key) + "\"");
	    }
    }
}
