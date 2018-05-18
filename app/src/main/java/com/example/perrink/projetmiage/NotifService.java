package com.example.perrink.projetmiage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Pault on 18/05/2018.
 */

public class NotifService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.wtf("Log !", "HELLO");
        final NotificationManager mNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(context, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1, launchNotifiactionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        String title = intent.getExtras().getString("title");
        String desc = intent.getExtras().getString("desc");
        Long when = intent.getExtras().getLong("when");

        Notification.Builder builder = new Notification.Builder(context)
                .setWhen(when)
                .setTicker("testNotification")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent);

        mNotification.notify(1, builder.build());

        Log.wtf("Log !", "HELLO");
    }
}
