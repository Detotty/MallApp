package com.mallapp.SharedPreferences;

import com.mallapp.Constants.AppConstants;
import com.mallapp.utils.Log;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * The Class SharedPreferenceManager provides interface to use {@link SharedPreferences}.
 */
public class SharedPreferenceManager {

	/** The Constant TAG. */
	private static final String TAG = "SharedPreferenceManager";

	/** The usr preference. */
	private static SharedPreferences usrPrefrence = null;

	/**
	 * Inits the preference.
	 * 
	 * @param mContext
	 *            The context
	 */
	private static void initPrefrence(Context mContext) {

		try {
			SharedPreferenceManager.usrPrefrence = mContext.getSharedPreferences(AppConstants.PREF_KEY, Context.MODE_PRIVATE);
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
	}

	/**
	 * Save int.
	 * 
	 * @param context
	 *            the context
	 * @param value
	 *            the value
	 * @param key
	 *            the key
	 */
	public static void saveInt(Context context, int value, String key) {

		try {
			if (SharedPreferenceManager.usrPrefrence == null) {
				SharedPreferenceManager.initPrefrence(context);
			}
			if (SharedPreferenceManager.usrPrefrence != null) {
				final Editor editing = SharedPreferenceManager.usrPrefrence.edit();
				try {
					editing.remove(key);
				} catch (final Exception exception) {
					Log.e(SharedPreferenceManager.TAG, "" + exception);
				}
				editing.putInt(key, value);
				editing.commit();
			}
		} catch (final Exception exception) {
			Log.e(SharedPreferenceManager.TAG, "" + exception);
		}
	}

	/**
	 * Gets the int.
	 * 
	 * @param context
	 *            the context
	 * @param key
	 *            the key
	 * @return the int
	 */
	public static int getInt(Context context, String key) {

		try {
			if (SharedPreferenceManager.usrPrefrence == null) {
				SharedPreferenceManager.initPrefrence(context);
			}
			return SharedPreferenceManager.usrPrefrence.getInt(key, -1);
		} catch (final Exception exception) {
			Log.e(SharedPreferenceManager.TAG, "" + exception);
		}
		return -1;
	}

	/**
	 * Save value.
	 * 
	 * @param context
	 *            the context
	 * @param value
	 *            the value
	 * @param key
	 *            the key
	 */
	public static void saveString(Context context, String key, String value) {

		try {
			if (SharedPreferenceManager.usrPrefrence == null) {
				SharedPreferenceManager.initPrefrence(context);
			}
			if (SharedPreferenceManager.usrPrefrence != null) {
				final Editor editing = SharedPreferenceManager.usrPrefrence.edit();
				try {
					editing.remove(key);
				} catch (final Exception exception) {
					Log.e(SharedPreferenceManager.TAG, "" + exception);
				}
				editing.putString(key, value);
				editing.commit();
			}
		} catch (final Exception exception) {
			Log.e(SharedPreferenceManager.TAG, "" + exception);
		}
	}

	/**
	 * Gets the value.
	 * 
	 * @param context
	 *            the context
	 * @param key
	 *            the key
	 * @return the value
	 */
	public static String getString(Context context, String key) {
		try {
			if (SharedPreferenceManager.usrPrefrence == null) {
				SharedPreferenceManager.initPrefrence(context);
			}
			return SharedPreferenceManager.usrPrefrence.getString(key, null);
		} catch (final Exception exception) {
			Log.e(SharedPreferenceManager.TAG, "" + exception);
		}
		return null;
	}

	/**
	 * Save data.
	 * 
	 * @param context
	 *            the context
	 * @param values
	 *            the values
	 * @param key
	 *            the key
	 */
	public static void saveFlag(Context context, boolean values, String key) {

		try {
			if (SharedPreferenceManager.usrPrefrence == null) {
				SharedPreferenceManager.initPrefrence(context);
			}
			if (SharedPreferenceManager.usrPrefrence != null) {
				final Editor editing = SharedPreferenceManager.usrPrefrence.edit();
				try {
					editing.remove(key);
				} catch (final Exception exception) {
					Log.e(SharedPreferenceManager.TAG, "" + exception);
				}
				editing.putBoolean(key, values);
				editing.commit();
			}
		} catch (final Exception exception) {
			Log.e(SharedPreferenceManager.TAG, "" + exception);
		}
	}

	/**
	 * Gets the saved data.
	 * 
	 * @param context
	 *            the context
	 * @param key
	 *            the key
	 * @return the saved data
	 */
	public static boolean getFlag(Context context, String key) {

		boolean flag = false;
		try {
			if (SharedPreferenceManager.usrPrefrence == null) {
				SharedPreferenceManager.initPrefrence(context);
			}
			if (SharedPreferenceManager.usrPrefrence != null) {
				flag = SharedPreferenceManager.usrPrefrence.getBoolean(key, false);
			}
		} catch (final Exception exception) {
			Log.e(TAG, "" + exception);
		}
		return flag;
	}

}
