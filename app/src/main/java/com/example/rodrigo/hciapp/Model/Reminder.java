package com.example.rodrigo.hciapp.Model;

import android.os.Bundle;
import android.text.format.Time;

import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by rodrigo on 17/01/16.
 */
public class Reminder {
    private int idReminder;
    private String title,notes;
    private GregorianCalendar date;
    private int daysAfter,hourRange;
    private State state;

    public Reminder(String title, String notes, GregorianCalendar date,int daysAfter,int hourRange){
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.daysAfter = daysAfter;
        this.hourRange = hourRange;
        this.state =  State.PENDING;
    }

    public int getIdReminder() {
        return idReminder;
    }

    public void setIdReminder(int idReminder) {
        this.idReminder = idReminder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public int getDaysAfter() {
        return daysAfter;
    }

    public void setDaysAfter(int daysAfter) {
        this.daysAfter = daysAfter;
    }

    public int getHourRange() {
        return hourRange;
    }

    public void setHourRange(int hourRange) {
        this.hourRange = hourRange;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

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