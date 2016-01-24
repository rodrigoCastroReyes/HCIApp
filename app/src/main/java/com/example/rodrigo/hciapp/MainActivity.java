package com.example.rodrigo.hciapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.rodrigo.hciapp.Adapters.RemindersAdapter;
import com.example.rodrigo.hciapp.Model.Reminder;
import com.example.rodrigo.hciapp.Services.AdviceService;
import com.example.rodrigo.hciapp.Services.AlarmService;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Reminder>reminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(this, AdviceService.class);
        //startService(intent);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(rgb(255, 255, 255));

        setSupportActionBar(myToolbar);

        ListView elements = (ListView) this.findViewById(R.id.listViewReminder);
        reminders = getActiveReminders();
        RemindersAdapter adapter = new RemindersAdapter(this,-1,reminders);
        elements.setAdapter(adapter);
    }

    public void launchNewReminderActivity(View v){
        Intent intent = new Intent(this,CreateReminderActivity.class);
        startActivity(intent);
    }

    public ArrayList<Reminder> getActiveReminders(){
        ArrayList<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder("Falta azucar","Comprar al volver de casa",new GregorianCalendar(2016,01,23,13,30),1,6));
        reminders.add(new Reminder("Comprar leche","Comprar al volver de casa",new GregorianCalendar(2016,01,26,18,10),1,6));
        reminders.add(new Reminder("Comprar carne","Comprar al volver de casa",new GregorianCalendar(2016,01,29,16,30),1,6));
        reminders.add(new Reminder("Comprar articulos de limpieza","Comprar al volver de casa",new GregorianCalendar(2016,01,29,18,30),1,6));
        return reminders;
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
}
