package com.mallapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

import com.Location.Gps.Lattitude_Logitude;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.UserLocationModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.View.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }


    public static void getDefaultLocation(Context context) {

        Lattitude_Logitude gps = new Lattitude_Logitude(context);

        //String countryZipCode = null;

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude ;
            double longitude;
            String countryName;
            String countryID;



            try {

                String address = gps.onLocationCountry();

                if(address!= null) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    countryName = gps.getCountryName();
                    countryID = gps.getCountryCode();
                    String cityName = gps.getCityName();
                    android.util.Log.e(" condition..", "address ..." + address + ": City Name:" + cityName);


                    UserLocationModel model = new UserLocationModel();
                    model.setCountryName(countryName);
                    model.setLatitude(latitude);
                    model.setLongitude(longitude);
                    model.setCountryCode(countryID);
                    model.setCityName(cityName);

//                    SharedInstance.getInstance().getSharedHashMap().put(AppConstants.USER_LOCATION, model);

                    DataHandler.updatePreferences(AppConstants.USER_LOCATION, model);

//                if(countryID!= null && countryID.length()>0 &&  !countryID.equals("null")){
//
//                    String[] rl = context.getResources().getStringArray(R.array.CountryCodes);
//
//                    for(int i = 0; i<rl.length; i++ ){
//
//                        String[] g = rl[i].split(",");
//                        if(g[1].trim().equals(countryID.trim())){
//                            countryZipCode  = g[0];
//                            break;
//                        }
//
//                    }
//                }

                    android.util.Log.d(context.getClass().getSimpleName(), "GPS:" + gps.toString());
                }


            }catch (Exception e) {
                e.printStackTrace();
                //countryEditText.setText("");
                Toast.makeText(context, R.string.location_msg3, Toast.LENGTH_LONG).show();
            }




//            mobile_no.requestFocus();
//            Handler mHandler= new Handler();
//            mHandler.post(new Runnable() {
//            	public void run() {
//            		InputMethodManager inputMethodManager =  (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//            		inputMethodManager.toggleSoftInputFromWindow(mobile_no.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
//            		mobile_no.requestFocus();
//            	}
//            });
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        }else{
            android.util.Log.e(" else condition..", "call pop up for location ..");
            open_setting(context);
        }
    }

    public static void open_setting(final Context context){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(R.string.location_msg1);
        // set dialog message
        alertDialogBuilder	.setMessage(R.string.location_msg2)
                .setCancelable(false)
                .setPositiveButton(R.string.yes_,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        ((Activity)context).startActivityForResult(intent, AppConstants.OPEN_LOCATION_SETTING_RESULT_CODE);
                    }})
                .setNegativeButton(R.string.no_,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        alertDialogBuilder.show();
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static long convertToUnixDate(String dateString, String format) {
        try {
            Date date = null; // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat(format); // the format of your date
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
            //String formattedDate = sdf.format(date);
            date = sdf.parse(dateString);
            System.out.println(date);
            long unixDate = date.getTime()/1000;
            System.out.println(unixDate);
            return unixDate;
        }catch (Exception e)
        {
            // execution will come here if the String that is given
            // does not match the expected format.
            e.printStackTrace();
        }
        return 0;
    }

    public static String convertUnixDate(long unixDate, String format) {

        try {

            long unixSeconds = unixDate;
            Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat(format); // the format of your date
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
            String formattedDate = sdf.format(date);
            Log.d("unix date", "unix date:" + formattedDate);

            return formattedDate;

        }catch (Exception e)
        {
            // execution will come here if the String that is given
            // does not match the expected format.
            e.printStackTrace();
        }
        return null;
    }

    /*
	 * Checks whether the Value String is Empty or Not
	 */
    public static boolean isEmpty(String value) {
        if (value == null || "".equals(value.trim())) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the Internet connection is available.
     *
     * @return Returns true if the Internet connection is available. False otherwise.
     * *
     */
    public static boolean isInternetAvailable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // if network is NOT available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }

}