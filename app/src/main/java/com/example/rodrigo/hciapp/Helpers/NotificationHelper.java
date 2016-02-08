package com.example.rodrigo.hciapp.Helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import com.example.rodrigo.hciapp.R;
import com.example.rodrigo.hciapp.ViewReminderActivity;

import android.app.Notification;
/**
 * Created by rodrigo on 17/01/16.
 */
public class NotificationHelper {
    private Context context;
    private NotificationManager mNotifyMgr;
    private int mNotificationId;
    private Intent resultIntent;

    public NotificationHelper(Context context,Intent resultIntent){
        mNotifyMgr = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        this.context = context;
        this.resultIntent = resultIntent;
    }

    public Notification create(){
        Bundle data = resultIntent.getExtras();
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_shopping_cart)
                .setContentTitle(data.getString("Title"))
                .setContentText(data.getString("Message"));
        mBuilder.setContentIntent(resultPendingIntent);
        return mBuilder.build();
    }

    public void launch(){//launch Notification
        mNotificationId = (int) System.currentTimeMillis();//save id of current notification
        mNotifyMgr.notify(mNotificationId,this.create());
    }

}
