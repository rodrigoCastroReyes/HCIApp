package com.example.rodrigo.hciapp.Helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
    private String title, msg;
    private int mNotificationId;
    private Class resultActivityClass;

    public NotificationHelper(Context context,Class actionClass, String title,String msg){
        context = context;
        mNotifyMgr = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        this.title = title;
        this.msg = msg;
        this.resultActivityClass = actionClass;
    }

    public Notification create(){
        Intent resultIntent = new Intent(context,resultActivityClass);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,(int) System.currentTimeMillis(), resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(title)
                .setContentText(msg);
        mBuilder.setContentIntent(resultPendingIntent);
        return mBuilder.build();
    }

    public void launch(){
        //launchNotificatio
        mNotificationId = (int) System.currentTimeMillis();//save id of current notification
        mNotifyMgr.notify(mNotificationId,this.create());
    }

}
