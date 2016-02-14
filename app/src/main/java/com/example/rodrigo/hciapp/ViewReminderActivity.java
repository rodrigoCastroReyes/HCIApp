
package com.example.rodrigo.hciapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rodrigo.hciapp.Adapters.RemindersAdapter;
import com.example.rodrigo.hciapp.Helpers.LocationHelper;
import com.example.rodrigo.hciapp.Model.DBOperations;
import com.example.rodrigo.hciapp.Model.Reminder;
import com.example.rodrigo.hciapp.Utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.graphics.Color.rgb;

public class ViewReminderActivity extends AppCompatActivity {
    private Reminder reminder;
    private TextView viewTitle,viewNotes,viewDate,viewHour,viewAfterDay,viewHourRange;
    private ImageView viewPhoto;
    private LinearLayout wrapperTitle;
    private LocationHelper locationHelper;
    private ArrayList<Integer> optionsDaysAfter,optionsHourRange;
    private DBOperations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);
        dbOperations = new DBOperations(this);
        Bundle data = getIntent().getExtras();
        reminder = (Reminder) data.get("Reminder");
        viewTitle = (TextView)findViewById(R.id.viewTitle);
        viewNotes = (TextView)findViewById(R.id.viewNotes);
        viewPhoto = (ImageView)findViewById(R.id.viewPhoto);
        viewDate = (TextView) findViewById(R.id.viewDate);
        viewHour = (TextView) findViewById(R.id.viewHour);
        viewAfterDay = (TextView) findViewById(R.id.viewDaysAfter);
        viewHourRange = (TextView) findViewById(R.id.viewHourRange);
        locationHelper = new LocationHelper(this);
        optionsHourRange = new ArrayList<>();
        optionsDaysAfter = new ArrayList<>();
        setNotificationInputs();
        this.setToolbar();
        this.setDataReminder(reminder);
    }

    public void setNotificationInputs(){
        int maxHourRange = 6, maxDaysAfter = 6;
        for(int i = 0 ; i <= maxHourRange ; i++){
            optionsHourRange.add(i*2);
        }
        for(int i = 0 ; i < maxDaysAfter ; i++){
            optionsDaysAfter.add(i);
        }
    }

    public void launchEditReminderActivity(View v) {
        Intent intent = new Intent(this, EditReminderActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("Reminder", reminder);
        intent.putExtras(data);
        startActivity(intent);
    }

    public void inactiveReminder(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        long id = reminder.getIdReminder();
        ContentValues values = new ContentValues();
        values.put(Reminder.ReminderEntry.COLUMN_ESTADO, "RESOLVED");
        dbOperations.updateReminder(values, id);
    }

    public void setDataReminder(Reminder reminder){
        String date,time;
        GregorianCalendar calendar;
        calendar = reminder.getDate();
        date = DateUtils.convertDateToString(calendar);
        time = DateUtils.convertTimeToString(calendar);
        viewTitle.setText(reminder.getTitle());
        viewNotes.setText(reminder.getNotes());
        viewDate.setText(date);
        viewHour.setText(time);
        if(reminder.getDaysAfter()==0){
            viewAfterDay.setText("Mismo día");
        }else {
            viewAfterDay.setText(reminder.getDaysAfter() + " dia antes");
        }
        if(reminder.getHourRange()==0){
            viewHourRange.setText("Misma hora");
        }else{
            viewHourRange.setText("cada " + reminder.getHourRange() + " horas");

        }



        if(reminder.getPhotoPath()==null){
            ((ViewGroup) viewPhoto.getParent()).removeView(viewPhoto);
            wrapperTitle = (LinearLayout)findViewById(R.id.wrapperTitulo);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wrapperTitle.getLayoutParams();
            params.weight = (float) 0.15;
            wrapperTitle.setLayoutParams(params);
        } else {
            viewPhoto.setImageBitmap(BitmapFactory.decodeFile(reminder.getPhotoPath()));
        }
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarViewReminder);
        toolbar.setTitleTextColor(rgb(255, 255, 255));
        toolbar.setTitle("Recordatorio");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_reminder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.adviceReminder:
                new LoaderAdvice(this).execute();
                return true;
            case R.id.action_eliminate:
                dbOperations.removeReminder(reminder.getIdReminder());
                launchAlertMessage("Recordatorio Eliminado con Exito");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void launchAlertMessage(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        final Intent intent = new Intent(this,MainActivity.class);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private class LoaderAdvice extends AsyncTask<Void, Void,Bundle> {
        private Context context;

        public LoaderAdvice(Context context){
            this.context = context;
        }

        @Override
        protected Bundle doInBackground(Void... params) {
            Bundle data = null;
            Location userLocation = locationHelper.getRecentLocation();
            if(userLocation!=null) {
                data = new Bundle();
                data.putParcelable("UserLocation", userLocation);
            }
            return data;
        }

        protected void onPostExecute(Bundle data){
            if(data!=null) {
                Intent intent = new Intent(context, AdviceActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
            //mandar mensaje para activar la geolocalizacion
        }

    }


}
