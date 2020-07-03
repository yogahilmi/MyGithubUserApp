package com.tasanah.mygithubuserapp.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.view.MainActivity;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class UserReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent intentNotif = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 27, intentNotif, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("aSwitch", "aSwitch");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "27")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notif)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite))
                .setContentTitle(context.getResources().getString(R.string.ContentTittleNotif))
                .setContentText(context.getResources().getString(R.string.SubTextNotif))
                .setSubText(context.getResources().getString(R.string.ContentTextNotif))
                .setAutoCancel(true);

        Notification notification = builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("27", "AlarmManager Channel", NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId("27");

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        if (notificationManager != null) {
            notificationManager.notify(2, notification);
        }
    }
}
