package com.mallapp.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;

import com.mallapp.View.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Created by Sharjeel on 24/02/2016.
 */
public class StaticLiterls {
    public static ProgressDialog mCustomProgressDialog;

    public static Bitmap announceR_IMG;
    public static Bitmap announcement_bitmap;

    public static String reward;
    public static boolean bookmark_fvrt = true;
    public static boolean fvrt = true;
    public static boolean profile = false;
    public static String announcementID;
    public static String announcementID_edit;
    public static String loc_gps;
    public static String top_cat = "";
    public static String claim_status;
    public static String profileId = "";
    public static String handover_location = "";
    public static String conv_id = "";
    public static String type_id = "";
    public static String type_id_edit = "";
    public static String link = "";
    public static String version = "";
    public static String response;
    public static String response_book;
    public static String response_comments;
    public static String response_category;
    public static String status;
    public static String claimProId;
    public static String claimDesc;
    public static String claimID;
    public static String time_claim;
    public static String claimer_name;
    public static String status_claim;
    public static String reject_text;
    public static String ann_type;
    public static String ann_title;
    public static String ann_Desc;
    public static String ann_location;
    public static String ann_time;
    public static String crated_time;
    public static String announcement_count = "20";
    public static String current_location = "";
    public static String current_location_gps = "";
    public static String current_owner = "";
    public static String web_title = "";
    public static String deliver_name = "";
    public static String deliver_id = "";
    public static String deliver_pic = "";
    public static String announcement_location = "";
    public static String announcement_time = "";
    public static String announcement_name = "";
    public static String announcer_pic_url="";

    public static String ImImageUrl="http://108.163.184.34/qchat/upload_file.php";

    public static ArrayList<Bitmap> allAnnImages;
    public static ArrayList<String> allAnnImages_edit;
    public static ArrayList<Bitmap> allAnnImages_claim;
    public static ArrayList<String> claimPicArrId;
    public static ArrayList<String> allimages_Id;

    public static void showProgressDialog(Context con) {
        mCustomProgressDialog = new ProgressDialog(con);
        mCustomProgressDialog.setTitle(con.getString(R.string.loading_data_message));
        mCustomProgressDialog.setMessage(con.getString(R.string.wait));
        mCustomProgressDialog.show();
    }

    public static void DismissesDialog() {

        mCustomProgressDialog.dismiss();
    }

    public static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            if (target.length() <= 6 || target.length() >= 13) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
        }
    }

    public static Bitmap getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath, Bitmap bitmap) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    public static Bitmap getCameraPhotoOrientationString(String imagePath, Bitmap bitmap) {
        int rotate = 0;
        try {
//            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    public static boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }



    public static void sendSms(Context context, String msg) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("sms_body", msg);

        try {
            context.startActivity(smsIntent);
            android.util.Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }

    public static void sendEmail(Context context, String msg) {
        Intent gmailIntent = new Intent();
        gmailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        gmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
        gmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
        try {
            context.startActivity(gmailIntent);
        } catch (ActivityNotFoundException ex) {
        }
    }

    public static void postToTwitter(Context context, String msg) {
        String tweetUrl = "https://twitter.com/intent/tweet?text=" + msg;
        Uri uri = Uri.parse(tweetUrl);
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public static String changeDateFormat(String encode_date) {
        if (!encode_date.equals("null")) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat sdf_1 = new SimpleDateFormat("dd-MM-yyyy kk:mm", Locale.getDefault());

            Date date = null;
            try {
                date = sdf.parse(encode_date);
                encode_date = sdf_1.format(date);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            encode_date = "";
        }
        return encode_date;
    }


    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = 650;
            height = (int) (width / bitmapRatio);
        } else {
            height = 650;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static String reolveUnicode(String string) {
        if (string.contains("\u0027")) {
            string.replace("\u0027", " ");
        }
        return string;
    }
}
