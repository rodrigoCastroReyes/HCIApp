package com.example.rodrigo.hciapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rodrigo.hciapp.Services.AdviceService;
import com.example.rodrigo.hciapp.Services.AlarmService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createNewReminder(View v){
        //Intent intent = new Intent(this,CreateReminderActivity.class);
        //startActivity(intent);
        Intent intent = new Intent(this, AlarmService.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchNotification(String title,String msg){
        Intent resultIntent = new Intent(this, ViewReminderActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,(int) System.currentTimeMillis(), resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(title)
                .setContentText(msg);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotifyMgr = (NotificationManager)getSystemService(this.NOTIFICATION_SERVICE);

        int mNotificationId = (int) System.currentTimeMillis();//save id of current notification

        mNotifyMgr.notify(mNotificationId,mBuilder.build());
    }

}
