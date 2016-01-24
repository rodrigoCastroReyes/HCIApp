package com.example.rodrigo.hciapp.Workers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rodrigo.hciapp.R;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by rodrigo on 16/01/16.
 */
public class DateWorker implements Worker {
    private DialogFragment dateFragment;
    private Activity activity;
    private int codeTask;
    private int year,monthOfYear,dayOfMonth;

    public DateWorker(Activity activity,int code){
        dateFragment = new DatePickerFragment();
        this.activity = activity;
        this.codeTask = code;
    }

    @Override
    public void launchTask() {
        dateFragment.show(this.activity.getFragmentManager(),"DatePickerFragment");
    }

    @Override
    public void resolveTask(Bundle result) {
        this.year = result.getInt("Year");
        this.monthOfYear = result.getInt("MonthOfYear");
        this.dayOfMonth = result.getInt("DayOfMonth");
        TextView v = (TextView) this.activity.findViewById(R.id.viewInputDate);
        v.setText("" + year + " " + monthOfYear + " " + dayOfMonth);
    }

    @Override
    public int getCodeTask() {
        return this.codeTask;
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
            results.putInt("DayOfMonth",dayOfMonth);
            resolveTask(results);
        }
    }
}
