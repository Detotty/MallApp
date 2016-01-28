package com.mallapp.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.mallapp.Constants.SocialSharingConstants;
import com.mallapp.View.R;

/**
 * Created by Sharjeel on 1/26/2016.
 */
public class SocialUtils {



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
}
