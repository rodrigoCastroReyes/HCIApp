package com.example.rodrigo.hciapp.Model;

import android.os.Bundle;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by rodrigo on 17/01/16.
 */
public class Reminder {
    private int idReminder;
    private String title,notes;
    private Date date;
    private Time time;
    private int daysAfter,hourRange;
    private State state;

    public boolean isForToday(){
        return true;
    }

    public Bundle getData(){
        Bundle data = new Bundle();
        data.putString("Title",this.title);
        data.putString("Message",this.notes);
        data.putLong("Delay", 2 * 60 * 1000);
        return data;
    }

}