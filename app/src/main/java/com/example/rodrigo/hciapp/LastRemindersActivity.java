package com.example.rodrigo.hciapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rodrigo.hciapp.Adapters.RemindersAdapter;
import com.example.rodrigo.hciapp.Model.DBOperations;
import com.example.rodrigo.hciapp.Model.Reminder;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class LastRemindersActivity extends AppCompatActivity {
    private DBOperations dbOperations;
    private ListView elements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_reminders);
        dbOperations = new DBOperations(this);
        elements = (ListView) findViewById(R.id.listViewLastReminder);
        elements.setEmptyView(findViewById(android.R.id.empty));
        this.setToolbar();
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadInactiveReminders();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_last_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadInactiveReminders() {
        new LoaderReminders(this).execute();
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLastReminders);
        toolbar.setTitleTextColor(rgb(255, 255, 255));
        toolbar.setTitle("Recordatorios Pasados");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
    }


    private class LoaderReminders extends AsyncTask<Void, Void, ArrayList<Reminder>> {
        private Context context;

        public LoaderReminders(Context context){
            this.context = context;
        }

        @Override
        protected ArrayList<Reminder> doInBackground(Void... params) {
            ArrayList<Reminder> reminders = dbOperations.getInactiveReminders();
            return reminders;
        }

        protected void onPostExecute(final ArrayList<Reminder> reminders){
            if (!reminders.isEmpty()) {
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
                        //startActivity(intent);
                    }
                });
            }
        }
    }

}
