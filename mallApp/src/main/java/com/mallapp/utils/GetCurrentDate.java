package com.mallapp.utils;

import android.util.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Sharjeel on 11/6/2015.
 */
public  class GetCurrentDate {


    public static String getDateTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        String current_date = simpleDateFormat.format(cal.getTime());
        return current_date;
    }

    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        String current_date = simpleDateFormat.format(cal.getTime());
        return current_date;
    }

    public static String unixDateConversion(Date date){
        long unixTime = (long)date.getTime()/1000;
        System.out.println(unixTime); //<- prints 1352504418
        String date_as_encoded = ""+unixTime;
        return date_as_encoded;
    }

    public static String unixDateToSimpaleDateConversion(String date_str){
        long unixSeconds = Long.parseLong(date_str);//1372339860;
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd , yyyy", Locale.ENGLISH); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }



    public static Date StringToDate(String format, String s_date) {
        DateFormat d_format = new SimpleDateFormat(format,  Locale.ENGLISH);
        Date date 			= null;
        try {
            date = d_format.parse(s_date);
            d_format.setTimeZone(TimeZone.getTimeZone("UTC"));
            if(date !=null)
                return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getLocalDate(String dateString, String dateFormat){

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try
        {
            if(dateString!=null) {
                // (2) give the formatter a String that matches the SimpleDateFormat pattern
                dateString = dateString.replace("T"," ");
//                formatter.setTimeZone(TimeZone.getDefault());
                //formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = formatter.parse(dateString);


                Date fromGmt = new Date(date.getTime() + TimeZone.getDefault().getOffset(new Date().getTime()));

                // (3) prints out "Tue Sep 22 00:00:00 EDT 2009"
                com.mallapp.utils.Log.d("", "dateString:"+dateString+":: Converted date:" + date +" : fromGmt:"+ fromGmt);

                return fromGmt;
            }
        }
        catch (ParseException e)
        {
            // execution will come here if the String that is given
            // does not match the expected format.
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateString(Date date, String format){

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try
        {

            String dateString = formatter.format(date);
            com.mallapp.utils.Log.d("", "dateString date:" + dateString);

            return dateString;
        }
        catch (Exception e)
        {
            // execution will come here if the String that is given
            // does not match the expected format.
            e.printStackTrace();
        }
        return null;
    }


    public static String convertUnixDate(long unixDate, String format) {

        try {

            long unixSeconds = unixDate;
            Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat(format); // the format of your date
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
            String formattedDate = sdf.format(date);
            com.mallapp.utils.Log.d("unix date", "unix date:" + formattedDate);

            return formattedDate;

        }catch (Exception e)
        {
            // execution will come here if the String that is given
            // does not match the expected format.
            e.printStackTrace();
        }
        return null;
    }


    public static String DateToString(String format, Date date){
        DateFormat df = new SimpleDateFormat(format,  Locale.US);
        String date_s= df.format(date);
        if(date_s !=null)
            return date_s;

        return null;
    }

    public static  String changeBirthdayDateFormat(String encode_date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat sdf_1 = new SimpleDateFormat("MMM dd , yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf.parse(encode_date);
            encode_date= sdf_1.format(date);
            android.util.Log.e("", "date of encodes string after parsing" + date);
            android.util.Log.e("", "date of encodes string after formating" + encode_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }return encode_date;
    }


}