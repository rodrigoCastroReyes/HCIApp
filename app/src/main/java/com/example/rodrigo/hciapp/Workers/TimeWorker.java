package com.example.rodrigo.hciapp.Workers;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.rodrigo.hciapp.R;

import java.util.Calendar;

/**
 * Created by rodrigo on 16/01/16.
 */
public class TimeWorker implements Worker{
    private DialogFragment timeFragment;
    private Activity activity;
    private int codeTask;
    private int hourOfDay,minute;
    private String time;

    public TimeWorker(Activity activity,int code){
        timeFragment = new TimePickerFragment();
        this.activity = activity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    @Override
    public void launchTask() {
        timeFragment.show(this.activity.getFragmentManager(),"TimePickerFragment");
    }

    @Override
    public void resolveTask(Bundle result) {
        this.hourOfDay = result.getInt("HourOfDay");
        this.minute = result.getInt("Minute");
        TextView v = (TextView) this.activity.findViewById(R.id.viewInputHour);
        time = "" + hourOfDay + ":" + minute + ":00";
        v.setText(time);
    }

    @Override
    public int getCodeTask() {
       return this.codeTask;
    }

    private class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
        /*TimePickerDialog : A dialog that prompts the user for the time of day using a TimePicker*/
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Bundle results = new Bundle();
            results.putInt("HourOfDay",hourOfDay);
            results.putInt("Minute",minute);
            resolveTask(results);
        }
    }
}