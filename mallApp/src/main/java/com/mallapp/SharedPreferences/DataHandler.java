package com.mallapp.SharedPreferences;



import android.content.Context;
import android.content.SharedPreferences;
import static com.mallapp.Constants.AppConstants.FILE_NAME_SHARED_PREF;
import com.google.gson.Gson;
import com.mallapp.utils.Utils;

/**
 * Created by Sharjeel on 1/12/2016.
 */
public class DataHandler {


    private static Context context;

    public DataHandler(Context context) {
        DataHandler.context = context;
    }

    public static void setContext(Context context) {
        DataHandler.context = context;
    }

    /* This method is for update values of String variables if existed else create new variable in Shared Preferences */
    public static void updatePreferences(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /* This method is for update values of Boolean variables if existed else create new variable in Shared Preferences */
    public static void updatePreferences(String key, Boolean value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    /* This method is for update values of Integer variables if existed else create new variable in Shared Preferences */
    public static void updatePreferences(String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public static void updatePreferences(String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.commit();

    }

    public static void updatePreferences(String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    /* This method is for update value of any Object if exisit or create new object variable in Shared Preference */
    public static void updatePreferences(String key, Object value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.commit();

    }
    /*
    public static void updatePreferences(String key, Object value,boolean parable) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putParcelable
        editor.commit();

    }*/
	/* This method is for get values of String variables if existed otherwise return null */
    public static String getStringPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF,  Context.MODE_PRIVATE);
        return settings.getString(key, "");

    }
    public static float getFloatPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getFloat(key, -1);

    }
    /* This method is for get values of Boolean variables if existed otherwise return null */
    public static Boolean getBooleanPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getBoolean(key, false);

    }

    /* This method is for get values of Integer variables if existed otherwise return null */
    public static int getIntPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getInt(key, -1);
    }

    public static Long getLongPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getLong(key, -1);
    }

    public static Long getLongPreferences(String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getLong(key, defaultValue);
    }

    public static <T> Object getObjectPreferences(String key, Class<T> objectClass) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF,  Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = settings.getString(key, "");
        if(!Utils.isEmpty(json)){
            return gson.fromJson(json, objectClass);
        }
        return null;
    }

    /* This method is for update values of String variables if existed else create new variable in Shared Preferences */
    public static void deletePreference(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();

    }

}
