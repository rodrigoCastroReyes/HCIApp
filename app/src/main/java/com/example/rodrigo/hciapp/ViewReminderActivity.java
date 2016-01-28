package com.example.rodrigo.hciapp;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rodrigo.hciapp.Model.Reminder;
import com.example.rodrigo.hciapp.Utils.DateUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.graphics.Color.rgb;

public class ViewReminderActivity extends AppCompatActivity {
    private Reminder reminder;
    private TextView viewTitle,viewNotes,viewDate,viewHour,viewAfterDay,viewHourRange;
    private ImageView viewPhoto;
    private LinearLayout wrapperTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);
        Bundle data = getIntent().getExtras();
        reminder = (Reminder) data.get("Reminder");
        viewTitle = (TextView)findViewById(R.id.viewTitle);
        viewNotes = (TextView)findViewById(R.id.viewNotes);
        viewPhoto = (ImageView)findViewById(R.id.viewPhoto);
        viewDate = (TextView) findViewById(R.id.viewDate);
        viewHour = (TextView) findViewById(R.id.viewHour);
        viewAfterDay = (TextView) findViewById(R.id.viewDaysAfter);
        viewHourRange = (TextView) findViewById(R.id.viewHourRange);
        this.setToolbar();
        this.setDataReminder(reminder);
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
        viewAfterDay.setText(reminder.getDaysAfter() + " dia antes");
        viewHourRange.setText("cada " + reminder.getHourRange()+ " horas");
        if(reminder.getPhotoPath()==null){
            ((ViewGroup) viewPhoto.getParent()).removeView(viewPhoto);
            wrapperTitle = (LinearLayout)findViewById(R.id.wrapperTitulo);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wrapperTitle.getLayoutParams();
            params.weight = (float) 0.15;
            wrapperTitle.setLayoutParams(params);
        }else {
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
