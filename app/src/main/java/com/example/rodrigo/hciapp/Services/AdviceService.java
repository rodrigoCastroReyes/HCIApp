package com.example.rodrigo.hciapp.Services;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.rodrigo.hciapp.R;
import com.example.rodrigo.hciapp.ViewReminderActivity;

import java.util.ArrayList;

/**
 * Created by rodrigo on 16/01/16.
 */
public class AdviceService extends Service {
    private Activity activity;
    private ArrayList<Integer>notificationsId = new ArrayList<Integer>();
    private Thread thread;

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // If we get killed, after returning from here, restart
        thread = new Thread(new GeoLocationThread());
        thread.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class GeoLocationThread implements Runnable{
        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            long endTime = 2*60*1000;
            //long initTime = 0;
            //long currentTime = System.currentTimeMillis();
            try {
                Thread.sleep(endTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}

