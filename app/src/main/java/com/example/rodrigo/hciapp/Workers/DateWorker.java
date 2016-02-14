package com.example.rodrigo.hciapp.Workers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rodrigo.hciapp.R;
import com.example.rodrigo.hciapp.Utils.DateUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import static android.R.layout.simple_spinner_item;

/**
 * Created by rodrigo on 16/01/16.
 */
public class DateWorker implements Worker {
    private DialogFragment dateFragment;
    private Activity activity;
    private int codeTask;
    private int year,monthOfYear,dayOfMonth;
    private String date;
    private TextView textViewDate;
    private Spinner inputDaysAfter;
    private ArrayList<Integer> optionsDaysAfter;
    private DateObservable dateObservable;

    public DateWorker(Activity activity,int code){
        dateFragment = new DatePickerFragment();
        this.activity = activity;
        this.codeTask = code;
        inputDaysAfter = (Spinner)activity.findViewById(R.id.inputDaysAfter);
        textViewDate = (TextView) this.activity.findViewById(R.id.viewInputDate);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        year = gregorianCalendar.get(Calendar.YEAR);
        monthOfYear = gregorianCalendar.get(Calendar.MONTH);
        dayOfMonth = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        date = DateUtils.convertDateToString(gregorianCalendar);
        textViewDate.setText(date);
        dateObservable = new DateObservable();
        dateObservable.dateChanged(year,monthOfYear,dayOfMonth);
    }

    public void setObserver(Observer observer){
        this.dateObservable.addObserver(observer);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void launchTask() {
        dateFragment.show(this.activity.getFragmentManager(), "DatePickerFragment");
    }

    @Override
    public void resolveTask(Bundle result) {
        this.year = result.getInt("Year");
        this.monthOfYear = result.getInt("MonthOfYear") ;
        this.dayOfMonth = result.getInt("DayOfMonth");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);
        date = DateUtils.convertDateToString(gregorianCalendar);
        textViewDate.setText(date);
        int days = (int) DateUtils.getDifferenceDays(gregorianCalendar,true);
        setOptionsDaysAfter(days);
        dateObservable.dateChanged(year, monthOfYear, dayOfMonth);
    }

    public void setOptionsDaysAfter(int days){
        String [] arraySpinner;
        if( days > 0) {
            if(days > 5){
                days = 5;
            }
            arraySpinner = new String[days + 1];
            arraySpinner[0] = "Mismo día";
            for(int i = 1 ; i <= days ; i++){
                arraySpinner[i] = Integer.toString(i) + " días antes";
            }
        }else{
            arraySpinner = new String [1];
            arraySpinner[0] = "Mismo día";
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.activity, android.R.layout.simple_spinner_item, arraySpinner);
        inputDaysAfter.setAdapter(spinnerAdapter);
    }

    @Override
    public int getCodeTask() {
        return this.codeTask;
    }

    private class DateObservable extends Observable{
        Bundle data ;

        private DateObservable(){
            this.setChanged();
        }

        public void dateChanged(int year,int month,int day){
            data = new Bundle();
            data.putInt("Year",year);
            data.putInt("Month",month);
            data.putInt("Day",day);
            this.setChanged();
            this.notifyObservers(data);
        }
    }

    private class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar newDate = Calendar.getInstance();
            int year,monthOfYear,dayOfMoth;
            year = newDate.get(Calendar.YEAR);
            monthOfYear = newDate.get(Calendar.MONTH);
            dayOfMoth = newDate.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(activity,this,year,monthOfYear,dayOfMoth);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Bundle results = new Bundle();
            results.putInt("Year",year);
            results.putInt("MonthOfYear",monthOfYear);
            results.putInt("DayOfMonth", dayOfMonth);
            resolveTask(results);
        }
    }

}
