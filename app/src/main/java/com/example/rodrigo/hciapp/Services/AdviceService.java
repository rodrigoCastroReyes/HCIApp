package com.example.rodrigo.hciapp.Services;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.rodrigo.hciapp.AdviceActivity;
import com.example.rodrigo.hciapp.Helpers.LocationHelper;
import com.example.rodrigo.hciapp.Helpers.MarketsHelper;
import com.example.rodrigo.hciapp.Helpers.NotificationHelper;
import com.example.rodrigo.hciapp.Model.Market;
import com.example.rodrigo.hciapp.Model.Reminder;
import com.example.rodrigo.hciapp.R;
import com.example.rodrigo.hciapp.ViewReminderActivity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by rodrigo on 16/01/16.
 */
public class AdviceService extends Service {
    private Activity activity;
    private ArrayList<Integer> notificationsId = new ArrayList<Integer>();
    private Thread thread;

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        thread = new AdviceThread(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // If we get killed, after returning from here, restart
        thread.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class AdviceThread extends Thread {
        private List<Reminder> activeReminders;
        private MarketsHelper marketsHelper;
        private LocationHelper locationHelper;
        private Context context;

        public AdviceThread(Context context) {
            this.marketsHelper = new MarketsHelper();
            this.locationHelper = new LocationHelper(context);
            this.context = context;
        }

        public List getActiveReminder() {
            return null;
        }

        public void run() {
            Location userLocation;
            boolean isThereReminders, isThereMarketsCloser;
            float ratio = 1000;
            ArrayList<Market> marketsCloser;
            /*while (true) {
                activeReminders = this.getActiveReminder();
                isThereReminders = activeReminders.isEmpty();
                userLocation = locationHelper.getRecentLocation();
                marketsCloser = (ArrayList<Market>)marketsHelper.findMarketsCloser(userLocation,ratio);
                isThereMarketsCloser = marketsCloser.isEmpty();
                if(isThereReminders && isThereMarketsCloser){
                    notifyTo(userLocation);
                }
            }*/
            userLocation = locationHelper.getRecentLocation();
            marketsCloser = (ArrayList<Market>)marketsHelper.findMarketsCloser(userLocation,ratio);
            notifyTo(userLocation,marketsCloser);
        }

        public void notifyTo(Location userLocation,ArrayList<Market> marketsCloser){
            Bundle data = new Bundle();
            data.putString("Title","There is markets closer");
            data.putString("Message","Remind!!");
            data.putParcelable("UserLocation",userLocation);
            data.putParcelableArrayList("MarketCloser",marketsCloser);
            Intent intent = new Intent(context,AdviceActivity.class);
            intent.putExtras(data);
            NotificationHelper notificationHelper = new NotificationHelper(context,intent);
            notificationHelper.launch();
        }
    }

}


