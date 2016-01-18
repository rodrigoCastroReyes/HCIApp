package com.example.rodrigo.hciapp.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.rodrigo.hciapp.Helpers.NotificationHelper;
import com.example.rodrigo.hciapp.ViewReminderActivity;


public class AlarmService extends Service {
    private Thread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new ManagerThread(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ManagerThread extends Thread{
        private NotificationThread notificationThread;

        public ManagerThread(Context context){
            notificationThread = new NotificationThread(context,"Notification Thread");
            notificationThread.start();
            notificationThread.prepareHandler();
        }

        public void run(){
            Bundle bundle = new Bundle();
            bundle.putString("Title","Hello from a thread");
            bundle.putString("Message","hola mundo");
            bundle.putLong("Delay", 2 * 60 * 1000);
            notificationThread.sheduleTask(bundle);
        }

    }

    private class NotificationThread extends HandlerThread {

        private Handler mWorkerHandler;
        private Context context;

        public NotificationThread(Context context,String name) {
            super(name);
            this.context = context;
        }

        public void prepareHandler(){
            mWorkerHandler = new Handler(getLooper());
        }

        public void sheduleTask(Bundle data){
            NotificationTask task = new NotificationTask(context,data);
            mWorkerHandler.post(task);
        }
    }

    private class NotificationTask implements Runnable{
        private String title,msg;
        private long delay;
        private Context context;

        public NotificationTask(Context context,Bundle data){
            this.context = context;
            this.title = data.getString("Title");
            this.msg =  data.getString("Message");
            this.delay = data.getLong("Delay");
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NotificationHelper notificationHelper = new NotificationHelper(context, ViewReminderActivity.class,title,msg);
            notificationHelper.launch();
        }
    }


}