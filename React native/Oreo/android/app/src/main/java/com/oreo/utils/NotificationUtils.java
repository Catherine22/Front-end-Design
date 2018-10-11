package com.oreo.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.oreo.R;

import com.oreo.MainActivity;

public class NotificationUtils extends ContextWrapper {
    private ChannelInfo channelInfo;
    private NotificationManager notificationManager;

    public NotificationUtils(Context context) {
        super(context);
    }


    @RequiresApi(Build.VERSION_CODES.O)
    public NotificationUtils(Context context, ChannelInfo channelInfo) {
        super(context);
        createChannels(channelInfo);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannels(ChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(channelInfo.CHANNEL_ID,
                channelInfo.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true);
        // Sets the notification light color for notifications posted to this channel
        androidChannel.setLightColor(Color.GREEN);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getNotificationManager().createNotificationChannel(androidChannel);
    }

    private NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    /**
     * Push a notification on whatever version devices
     *
     * @param title          comes from Firebase cloud messing
     * @param body           comes from Firebase cloud messing
     * @param notificationId user defined
     */
    public void sendChannelNotification(String title, String body, int notificationId) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent
                .FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                    channelInfo.CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setTicker("Firebase Cloud Messaging")
                    .setSmallIcon(R.mipmap.ic_stat_ic_notification)
                    .setAutoCancel(true)
                    // for notification click action, also required on Gingerbread and below
                    .setContentIntent(pi);
            getNotificationManager().notify(notificationId, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setTicker("Firebase Cloud Messaging")
                    .setSmallIcon(R.mipmap.ic_stat_ic_notification)
                    .setAutoCancel(true)
                    // for notification click action, also required on Gingerbread and below
                    .setContentIntent(pi);
            getNotificationManager().notify(notificationId, builder.build());
        }
    }

    /**
     * Create a notification for foreground services
     *
     * @return Notification
     */
    @RequiresApi(Build.VERSION_CODES.O)
    public Notification getServiceNotification() {
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent
                .FLAG_UPDATE_CURRENT);

        return new Notification.Builder(getApplicationContext(), channelInfo.CHANNEL_ID)
                .setContentTitle("Foreground Service Title")
                .setContentText("Foreground Service content")
                .setTicker("Foreground Service Ticker")
                .setSmallIcon(R.mipmap.ic_stat_ic_notification)
                .setAutoCancel(true)
                // for notification click action, also required on Gingerbread and below
                .setContentIntent(pi)
                .build();
    }
}