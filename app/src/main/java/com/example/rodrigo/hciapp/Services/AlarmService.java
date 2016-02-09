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
import com.example.rodrigo.hciapp.Model.DBOperations;
import com.example.rodrigo.hciapp.Model.Reminder;
import com.example.rodrigo.hciapp.Utils.DateUtils;
import com.example.rodrigo.hciapp.ViewReminderActivity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Queue;


public class AlarmService extends Service {
    private ManagerThread thread;
    private ArrayList<Reminder> activeReminders ;
    @Override
    public void onCreate() {
        super.onCreate();
        thread = new ManagerThread(this);
        activeReminders = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle data = intent.getExtras();
        if(data!=null){
            activeReminders = (ArrayList < Reminder >) data.get("Reminders");
            thread.setActiveReminders(activeReminders);
            if (thread.getState() == Thread.State.NEW ){
                thread.start();
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ManagerThread extends Thread {
        private NotificationThread notificationThread;
        private ArrayList <Reminder> activeReminders ;
        private DBOperations dbOperations;
        private final long hourMax= 1;

        public ManagerThread(Context context){
            dbOperations = new DBOperations(context);
            notificationThread = new NotificationThread(context,"Notification Thread");
            notificationThread.start();
            notificationThread.prepareHandler();
        }

        public void setActiveReminders(ArrayList <Reminder> activeReminders){
            this.activeReminders = activeReminders;
        }

        public void run() {
            Reminder reminder;
            long delay = 1*60*60*1000;
            try {
                while (true) {
                    if(!activeReminders.isEmpty()) {
                        scheduleReminders(activeReminders);//agendar notificaciones de los recordatorios
                        Thread.sleep(delay);
                    }
                    activeReminders = dbOperations.getActiveReminders();//obtener recordatorios desde la base de datos
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void scheduleReminders(ArrayList<Reminder>reminders){
            //agenda las notificaciones por cada recordatorio, un recordatorio puede generar una o mas notificaciones
            long difHours;
            GregorianCalendar currenDate ;
            ArrayList<GregorianCalendar>datesOfNotifications;
            for(Reminder reminder: reminders){
                datesOfNotifications = reminder.getDatesNotifications();
                for(GregorianCalendar date: datesOfNotifications){
                    currenDate = new GregorianCalendar();//fecha actual
                    if(date.compareTo(currenDate)==1) {//comprobar si la fecha del recordatorio es mayor a la fecha actual
                        difHours = DateUtils.getDifferenceHours(date);
                        if(difHours >= 0 && difHours <= hourMax){
                            launchNotification(date,reminder);//lanzar una notificion por cada 'date'
                        }
                    }
                }
            }
        }

        public void launchNotification(GregorianCalendar date, Reminder reminder){
            //long difMinutes=2;
            long difMinutes;
            difMinutes = DateUtils.getDifferenceMinutes(date);
            Bundle data = new Bundle();
            data.putString("Title", reminder.getTitle());
            data.putString("Message", reminder.getNotes());
            data.putLong("Delay", difMinutes * 60 * 1000);
            data.putSerializable("Reminder",reminder);
            notificationThread.sheduleTask(data);
        }
    }

    private class NotificationThread extends HandlerThread {
        //https://blog.nikitaog.me/2014/10/11/android-looper-handler-handlerthread-i/

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
        private long delay;
        private Context context;
        private Intent intent;

        public NotificationTask(Context context,Bundle data){
            this.context = context;
            this.intent = new Intent(context,ViewReminderActivity.class);
            this.intent.putExtras(data);
            this.delay = data.getLong("Delay");
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.delay);//espera delay segundos antes de enviar la notificacion
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NotificationHelper notificationHelper = new NotificationHelper(context,intent);
            notificationHelper.launch();
        }
    }


}