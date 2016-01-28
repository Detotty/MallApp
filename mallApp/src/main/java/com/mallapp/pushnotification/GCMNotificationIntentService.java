package com.mallapp.pushnotification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mallapp.Application.MallApplication;
import com.mallapp.View.DashboardTabFragmentActivity;
import com.mallapp.View.R;
import com.mallapp.utils.BadgeUtils;
import com.mallapp.utils.WakeLocker;

import java.util.HashMap;
import java.util.Map;

public class GCMNotificationIntentService extends IntentService {
    // Sets an ID for the notification, so it can be updated
    public static int notifyID = 0;
    NotificationCompat.Builder builder;

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        Log.e("push Notifications", "" + extras);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                sendNotification(extras.toString());
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        Intent resultIntent = new Intent(this, DashboardTabFragmentActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);
        int icon = R.drawable.icon;
        String notification_msg = "";
        WakeLocker.acquire(getApplicationContext());
        Log.e("push Notifications", "" + msg);
        String response = msg.substring(msg.indexOf("{") + 1, msg.indexOf("}"));
        Map<String, String> responseMap = splitToMap(response, ", ", "=");
        String type = modifyNotification(responseMap.get("Type"));
        if (type.equals("SimpleChat")) {
            type = "Chat";

            notification_msg = "Chat:\nYou got new message from:\n" + responseMap.get("Notification");
            Intent intent = new Intent("Badge-Recieved");
            intent.putExtra("message", "chat");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } else if (type.equals("Comment")) {
            type = "Comment";
            notification_msg = "Announcement:" + responseMap.get("AnnouncementName") + "\n" + responseMap.get("Notification");
        } else if (type.equals("Claim")) {

            notification_msg = "Activities:\nAnnouncement:" + responseMap.get("AnnouncementName") + "\n" + responseMap.get("Notification");
        } else if (type.equals("AnnouncementConversation")) {
            notification_msg = "Chat:\n" + responseMap.get("Notification");
        }
        int smallIcon = R.drawable.ic_launcher;
        Notification notification = null;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getApplicationContext());
        if (WakeLocker.isAppIsInBackground(MallApplication.appContext)) {

            notification = mBuilder.setSmallIcon(smallIcon).setTicker("CrowdEyes: " + type).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle("CrowdEyes")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(modifyNotification(notification_msg)))
                    .setContentIntent(resultPendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(new long[]{100, 250})
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), icon))
                    .setContentText(modifyNotification(notification_msg))
                    .build();
        } else {
            notification = mBuilder.setSmallIcon(smallIcon).setTicker("CrowdEyes: " + type).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle("CrowdEyes")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(modifyNotification(notification_msg)))
                    .setContentIntent(resultPendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), icon))
                    .setContentText(modifyNotification(notification_msg))
                    .build();
        }
        notifyID = notifyID + 1;
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyID, notification);
        BadgeUtils.setBadge(MallApplication.appContext, notifyID);
        WakeLocker.release();
    }

    public static Map<String, String> splitToMap(String source, String entriesSeparator, String keyValueSeparator) {
        Map<String, String> map = new HashMap<String, String>();
        String[] entries = source.split(entriesSeparator);
        for (String entry : entries) {
            if (!TextUtils.isEmpty(entry) && entry.contains(keyValueSeparator)) {
                String[] keyValue = entry.split(keyValueSeparator);
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }

    public String modifyNotification(String notification) {
        String clean_notification = notification.replace(",", "");
        return clean_notification;
    }
}
