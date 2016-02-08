package com.example.rodrigo.hciapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rodrigo.hciapp.Adapters.RemindersAdapter;
import com.example.rodrigo.hciapp.Model.DBOperations;
import com.example.rodrigo.hciapp.Model.Reminder;
import com.example.rodrigo.hciapp.Services.AdviceService;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Reminder> reminders;
    private DBOperations dbOperations;
    private boolean isLoadedDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolBar();
        //savePreferences("isLoad", false);
        isLoadedDB = loadPreferences();
        dbOperations = new DBOperations(this);
        if (!isLoadedDB) {
            dbOperations.getDbHelper().createTables();
            dbOperations.insertMarkets();
            //Toast.makeText(this,"supermercados creados",Toast.LENGTH_SHORT).show();
            savePreferences("isLoad", true);
        }

    }

    @Override
    protected void onStart(){
        super.onStart();
        loadActiveReminders();
    }

    public void loadActiveReminders() {
       new LoaderReminders(this).execute();
    }

    public void launchAdviceService() {
        Intent intent = new Intent(this, AdviceService.class);
        startService(intent);
    }

    public void setToolBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(rgb(255, 255, 255));
        setSupportActionBar(myToolbar);
    }

    private boolean loadPreferences() {
        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return preferences.getBoolean("isLoad", false);
    }

    private void savePreferences(String key, boolean value) {
        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void launchNewReminderActivity(View v) {
        Intent intent = new Intent(this, CreateReminderActivity.class);
        startActivity(intent);
    }

    public ArrayList<Reminder> getActiveReminders() {
        ArrayList<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder("Falta azucar", "Comprar al volver de casa", new GregorianCalendar(2016, 01, 23, 13, 30), 1, 6));
        reminders.add(new Reminder("Comprar leche", "Comprar al volver de casa", new GregorianCalendar(2016, 01, 26, 18, 10), 1, 6));
        reminders.add(new Reminder("Comprar carne", "Comprar al volver de casa", new GregorianCalendar(2016, 01, 29, 16, 30), 1, 6));
        reminders.add(new Reminder("Comprar articulos de limpieza", "Comprar al volver de casa", new GregorianCalendar(2016, 01, 29, 18, 30), 1, 6));
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

    private class LoaderReminders extends AsyncTask<Void, Void, ArrayList<Reminder>> {
        private Context context;

        public LoaderReminders(Context context){
            this.context = context;
        }

        @Override
        protected ArrayList<Reminder> doInBackground(Void... params) {
            ArrayList<Reminder> reminders = dbOperations.getActiveReminders();
            return reminders;
        }

        protected void onPostExecute(final ArrayList<Reminder> reminders){
            if (!reminders.isEmpty()) {
                final ListView elements = (ListView) findViewById(R.id.listViewReminder);
                final RemindersAdapter adapter = new RemindersAdapter(context, -1, reminders);
                elements.setAdapter(adapter);
                elements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent intent = new Intent(context, ViewReminderActivity.class);
                        Bundle data = new Bundle();
                        Reminder reminder = adapter.getReminders().get(position);
                        data.putSerializable("Reminder", reminder);
                        intent.putExtras(data);
                        startActivity(intent);
                    }
                });
                Intent intent = new Intent(context, AdviceService.class);
                intent.putExtra("Reminders",reminders);
                startService(intent);
            }
        }
    }

}