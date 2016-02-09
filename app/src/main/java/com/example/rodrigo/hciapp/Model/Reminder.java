package com.example.rodrigo.hciapp.Model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.design.widget.TabLayout;
import android.text.format.Time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by rodrigo on 17/01/16.
 */
public class Reminder implements Serializable,Comparable {
    private long idReminder;
    private String title,notes;
    private GregorianCalendar date;
    private int daysAfter,hourRange;
    private String photoPath,voiceNotePath;
    private State state;
    private ArrayList<GregorianCalendar>datesNotifications;

    public Reminder(){
        this.state =  State.PENDING;
    }

    public Reminder(String title, String notes, GregorianCalendar date,int daysAfter,int hourRange){
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.daysAfter = daysAfter;
        this.hourRange = hourRange;
        this.state =  State.PENDING;
        photoPath = "";
        voiceNotePath = "";
    }

    public void setIdReminder(long idReminder) {
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

    public String getVoiceNotePath() {
        return voiceNotePath;
    }

    public ArrayList<GregorianCalendar> getDatesNotifications() {
        return datesNotifications;
    }

    public Reminder setDatesNotifications(ArrayList<GregorianCalendar> datesNotifications) {
        this.datesNotifications = datesNotifications;
        return this;
    }

    public Reminder setVoiceNotePath(String voiceNotePath) {
        this.voiceNotePath = voiceNotePath;
        return this;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Reminder setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
        return this;
    }

    public String getDateToString(){
        int month = date.get(Calendar.MONTH) + 1;
        return ""+ date.get(Calendar.YEAR) + "/" + month + "/" +date.get(Calendar.DAY_OF_MONTH)  + " " +
                date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + ":00";
    }

    public Bundle getData(){
        Bundle data = new Bundle();
        data.putString("Title", this.title);
        data.putString("Message", this.notes);
        data.putLong("Delay", 2 * 60 * 1000);
        return data;
    }

    @Override
    public int compareTo(Object another) {
        Reminder reminderTwo = (Reminder)another;
        return this.getDate().compareTo(reminderTwo.getDate());
    }

    public void defineDatesOfNotifications(){
        datesNotifications = new ArrayList<>();
        datesNotifications.add(this.date);
        //int minutes= hourRange;
        int num_notifications ;
        if(hourRange >0){
            if(daysAfter>0){
                num_notifications = (24/hourRange)*daysAfter;
            }else{
                num_notifications = (24/hourRange);
            }
            GregorianCalendar currentDate = (GregorianCalendar) this.date.clone();
            while(num_notifications>0){
                currentDate.add(Calendar.HOUR,-hourRange);
                //currentDate.add(Calendar.MINUTE,-minutes);
                datesNotifications.add((GregorianCalendar) currentDate.clone());
                num_notifications--;
            }
        }else{
            return;
        }
    }

    public static abstract class ReminderEntry implements BaseColumns {
        public static final String TABLE_NAME = "Reminder";
        public static final String COLUMN_ENTRY_ID = "reminderid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_NOTES= "notes";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DAYS_AFTER = "daysAfter";
        public static final String COLUMN_HOUR_RANGE = "hourRange";
        public static final String COLUMN_ESTADO = "estado";
        public static final String COLUMN_PHOTO_PATH = "photoPath";
        public static final String COLUMN_VOICE_NOTE_PATH ="voiceNotePath";

        public static final int INDEX_ENTRY_ID = 0;
        public static final int INDEX_TITLE = 1;
        public static final int INDEX_NOTES= 2;
        public static final int INDEX_DATE = 3;
        public static final int INDEX_DAYS_AFTER = 4;
        public static final int INDEX_HOUR_RANGE = 5;
        public static final int INDEX_ESTADO = 6;
        public static final int INDEX_PHOTO_PATH = 7;
        public static final int INDEX_VOICE_NOTE_PATH = 8;

        public static final String DATABASE_CREATE = "create table if not exists "
                + TABLE_NAME + " (" + COLUMN_ENTRY_ID + " integer primary key autoincrement, "
                + COLUMN_TITLE + " text, " + COLUMN_NOTES + " text, " + COLUMN_DATE + " text," +
                COLUMN_DAYS_AFTER  + " integer, " + COLUMN_HOUR_RANGE  + " integer, " +
                COLUMN_ESTADO + " text, " + COLUMN_PHOTO_PATH + " text, " + COLUMN_VOICE_NOTE_PATH  +" text)";
        public static final String DATABASE_DELETE = "drop table if exists " + TABLE_NAME;
    }
}