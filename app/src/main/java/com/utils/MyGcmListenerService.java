package com.utils;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.gcm.GcmListenerService;
import com.travelx.R;
import com.travelx.SplashActivity;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;


    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        System.out.println("Prinitng message " + message);

        String title = data.getString("title");
        System.out.println("Prinitng title " + title);
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(title,message);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
//     * @param message GCM message received.
     */
    private void sendNotification(String title,String msg) {
        System.out.println("Message received");
        if(msg!=null){
            mNotificationManager = (NotificationManager)
                    this.getSystemService(Context.NOTIFICATION_SERVICE);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, SplashActivity.class), 0);
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this).setLargeIcon(icon)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle(title)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(msg))
                            .setAutoCancel(true)
                            .setContentText(msg);
            mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }
}
