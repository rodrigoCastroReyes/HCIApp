package com.example.rodrigo.hciapp.Workers;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.rodrigo.hciapp.R;
import com.example.rodrigo.hciapp.Utils.DateUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by rodrigo on 16/01/16.
 */
public class TimeWorker implements Worker,Observer{
    private DialogFragment timeFragment;
    private Activity activity;
    private int codeTask,year,month,day;
    private int hourOfDay,minute;
    private String time;
    private TextView textViewTime;
    private Spinner inputHourRange;

    public TimeWorker(Activity activity,int code){
        timeFragment = new TimePickerFragment();
        this.activity = activity;
        textViewTime = (TextView) this.activity.findViewById(R.id.viewInputHour);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        year = gregorianCalendar.get(Calendar.YEAR);
        month = gregorianCalendar.get(Calendar.MONTH);
        day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        hourOfDay = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
        minute = gregorianCalendar.get(Calendar.MINUTE);
        time = "" + hourOfDay + ":" + minute + ":00";
        textViewTime.setText(time);
        inputHourRange = (Spinner)activity.findViewById(R.id.inputHourRange);
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
        timeFragment.show(this.activity.getFragmentManager(), "TimePickerFragment");
    }

    @Override
    public void resolveTask(Bundle result) {
        this.hourOfDay = result.getInt("HourOfDay");
        this.minute = result.getInt("Minute");
        time = "" + hourOfDay + ":" + minute + ":00";
        textViewTime.setText(time);
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year,month,day,hourOfDay,minute);
        int hours = (int) DateUtils.getDifferenceHours(gregorianCalendar);
        setOptionsHourRange(hours);
    }

    public void setOptionsHourRange(int hours){
        String [] arraySpinner;
        int hoursInputs[] = {2,4,6,8,12};
        int maxRange ;
        if( hours > 0) {
            if(hours > 24){
                maxRange = 5;
            }else{
                maxRange = findMaxHourRange(hours,hoursInputs);
            }
            arraySpinner = new String[maxRange + 1];
            arraySpinner[0] = "Misma hora";
            for(int i = 0 ; i < maxRange ; i++){
                arraySpinner[i+1] = "cada " + Integer.toString(hoursInputs[i]) + " horas";
            }
        }else{
            arraySpinner = new String [1];
            arraySpinner[0] = "Mismo hora";
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.activity, android.R.layout.simple_spinner_item, arraySpinner);
        inputHourRange.setAdapter(spinnerAdapter);
    }

    public int findMaxHourRange(int hours,int hoursInputs[]){
        int rate;
        int max = 0;
        for(int i = 0 ; i <hoursInputs.length;i++){
            rate = hours/hoursInputs[i];
            if(rate>1){
                max++;
            }
        }
        return max;
    }

    @Override
    public int getCodeTask() {
       return this.codeTask;
    }

    @Override
    public void update(Observable observable, Object date) {
        Bundle data = (Bundle)date;
        year = data.getInt("Year");
        month = data.getInt("Month");
        day = data.getInt("Day");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year,month,day,hourOfDay,minute);
        int hours = (int) DateUtils.getDifferenceHours(gregorianCalendar);
        setOptionsHourRange(hours);
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