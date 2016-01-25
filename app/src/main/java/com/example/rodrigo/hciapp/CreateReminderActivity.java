package com.example.rodrigo.hciapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodrigo.hciapp.Helpers.DBHelper;
import com.example.rodrigo.hciapp.Model.DBOperations;
import com.example.rodrigo.hciapp.Model.Reminder;
import com.example.rodrigo.hciapp.Workers.CameraWorker;
import com.example.rodrigo.hciapp.Workers.DateWorker;
import com.example.rodrigo.hciapp.Workers.RecorderVoiceWorker;
import com.example.rodrigo.hciapp.Workers.TimeWorker;
import com.example.rodrigo.hciapp.Workers.Worker;

import java.util.GregorianCalendar;

import static android.graphics.Color.rgb;

public class CreateReminderActivity extends AppCompatActivity {
    private Worker cameraWorker,recorderWorker,timeWorker,dateWorker;
    private DBOperations dbOperations;
    private EditText inputTitle,inputNotes;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_RECORD_VOICENOTE = 2;
    static final int REQUEST_GET_TIME = 3;
    static final int REQUEST_GET_DATE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        this.setToolbar();

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputNotes = (EditText) findViewById(R.id.inputNotes);

        cameraWorker = new CameraWorker(this,REQUEST_IMAGE_CAPTURE);
        recorderWorker = new RecorderVoiceWorker(this,REQUEST_RECORD_VOICENOTE);
        timeWorker = new TimeWorker(this,REQUEST_GET_TIME);
        dateWorker = new DateWorker(this,REQUEST_GET_DATE);

        dbOperations = new DBOperations(this);

    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCreateReminder);
        toolbar.setTitleTextColor(rgb(255, 255, 255));
        toolbar.setTitle("Nuevo Recordatorio");
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
        getMenuInflater().inflate(R.menu.menu_create_reminder, menu);
        return true;
    }

    public void takePhotoHandle(View v){//al hacer click en tomar foto se llama a este metodo
        cameraWorker.launchTask();
    }

    public void recordVoiceNoteHandle(View v){//al hacer click en grabar nota de voz se llama a este metodo
        recorderWorker.launchTask();
    }

    public void setCalendar(View v){
        dateWorker.launchTask();
    }

    public void setTime(View v){
        timeWorker.launchTask();
    }

    public void saveNewReminder(View v){
        int year,month,day,hour,minute;
        int daysAfter,hourRange;
        String title = inputTitle.getText().toString();
        String notes = inputNotes.getText().toString();
        year = ((DateWorker) dateWorker).getYear();
        month = ((DateWorker) dateWorker).getMonthOfYear();
        day = ((DateWorker) dateWorker).getDayOfMonth();
        hour = ((TimeWorker)timeWorker).getHourOfDay();
        minute = ((TimeWorker)timeWorker).getMinute();
        daysAfter = 1;
        hourRange = 12;
        Reminder reminder = new Reminder(title,notes,new GregorianCalendar(year,month,day,hour,minute),daysAfter,hourRange);
        long id = dbOperations.insertReminder(reminder);
        if(id!=-1){
            reminder.setIdReminder(id);
        }
        Toast.makeText(this,"Recordatorio Creado",Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        Log.d("MainActivity", "onActivityResult");
        Bundle results = new Bundle();
        results.putInt("ResultCode", resultCode);
        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE :
                cameraWorker.resolveTask(results);
            case REQUEST_RECORD_VOICENOTE:
                recorderWorker.resolveTask(results);
        }
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
