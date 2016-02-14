package com.example.rodrigo.hciapp;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.rodrigo.hciapp.Helpers.LocationHelper;
import com.example.rodrigo.hciapp.Model.DBOperations;
import com.example.rodrigo.hciapp.Model.Reminder;
import com.example.rodrigo.hciapp.Utils.DateUtils;
import com.example.rodrigo.hciapp.Workers.CameraWorker;
import com.example.rodrigo.hciapp.Workers.DateWorker;
import com.example.rodrigo.hciapp.Workers.TimeWorker;
import com.example.rodrigo.hciapp.Workers.Worker;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static android.graphics.Color.rgb;
/**
 * Created by kl on 09/02/16.
 */
public class EditReminderActivity extends AppCompatActivity {
    private Button bt_start;
    private Worker cameraWorker, timeWorker,dateWorker;
    private Reminder reminder;
    private EditText viewTitle,viewNotes;
    private TextView viewDate,viewHour;
    private ImageView viewPhoto;
    private LinearLayout wrapperTitle;
    private LocationHelper locationHelper;
    private Spinner viewAfterDay,viewHourRange;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_GET_TIME = 3;
    static final int REQUEST_GET_DATE = 4;
    private ArrayList<Integer> optionsDaysAfter,optionsHourRange;
    private DBOperations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        dbOperations = new DBOperations(this);
        Bundle data = getIntent().getExtras();
        timeWorker = new TimeWorker(this,REQUEST_GET_TIME);
        dateWorker = new DateWorker(this,REQUEST_GET_DATE);
        cameraWorker = new CameraWorker(this,REQUEST_IMAGE_CAPTURE);
        reminder = (Reminder) data.getSerializable("Reminder");
        viewTitle = (EditText)findViewById(R.id.inputTitle);
        viewNotes = (EditText)findViewById(R.id.inputNotes);
        viewPhoto = (ImageView)findViewById(R.id.viewPhoto);
        viewDate = (TextView) findViewById(R.id.viewInputDate);
        viewHour = (TextView) findViewById(R.id.viewInputHour);
        viewAfterDay = (Spinner) findViewById(R.id.inputDaysAfter);
        viewHourRange = (Spinner) findViewById(R.id.inputHourRange);
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

    public void setCalendar(View v){
        dateWorker.launchTask();
    }

    public void setTime(View v){
        timeWorker.launchTask();
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEditReminder);
        toolbar.setTitleTextColor(rgb(255, 255, 255));
        toolbar.setTitle("Editar Recordatorio");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
    }

    public void takePhotoHandle(View v){//al hacer click en tomar foto se llama a este metodo
        cameraWorker.launchTask();
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
        //viewAfterDay.setText(reminder.getDaysAfter() + " dia antes");
        //viewHourRange.setText("cada " + reminder.getHourRange() + " horas");

    }

    public void saveNewReminder(View v){
        int year,month,day,hour,minute;
        int daysAfter,hourRange;
        long id = reminder.getIdReminder();
        String title = viewTitle.getText().toString();
        String notes = viewNotes.getText().toString();
        year = ((DateWorker) dateWorker).getYear();
        month = ((DateWorker) dateWorker).getMonthOfYear();
        day = ((DateWorker) dateWorker).getDayOfMonth();
        hour = ((TimeWorker)timeWorker).getHourOfDay();
        minute = ((TimeWorker)timeWorker).getMinute();

        daysAfter = optionsDaysAfter.get(viewAfterDay.getSelectedItemPosition());
        hourRange = optionsHourRange.get(viewHourRange.getSelectedItemPosition());

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, day, hour, minute);
        Reminder rem = new Reminder(title, notes,gregorianCalendar, daysAfter, hourRange);

        ContentValues values = new ContentValues();
        values.put(Reminder.ReminderEntry.COLUMN_TITLE, rem.getTitle());
        values.put(Reminder.ReminderEntry.COLUMN_NOTES,rem.getNotes());
        values.put(Reminder.ReminderEntry.COLUMN_DATE,rem.getDateToString());
        values.put(Reminder.ReminderEntry.COLUMN_DAYS_AFTER,rem.getDaysAfter());
        values.put(Reminder.ReminderEntry.COLUMN_HOUR_RANGE,rem.getHourRange());
        values.put(Reminder.ReminderEntry.COLUMN_ESTADO, rem.getState().toString());

        dbOperations.updateReminder(values,id);
        launchAlertMessage("Recordatorio Editado");
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


}
