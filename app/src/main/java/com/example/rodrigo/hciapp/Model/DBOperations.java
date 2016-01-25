package com.example.rodrigo.hciapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.rodrigo.hciapp.Helpers.DBHelper;
import com.example.rodrigo.hciapp.Utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by rodrigo on 24/01/16.
 */
public class DBOperations {

    private DBHelper dbHelper;

    public DBOperations(Context context){
        dbHelper = new DBHelper(context);
    }

    public DBHelper getDbHelper(){
        return dbHelper;
    }

    public long insertReminder(Reminder reminder){
        SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
        return dataBase.insert(Reminder.ReminderEntry.TABLE_NAME, null, generateValuesReminder(reminder));
    }

    public ContentValues generateValuesReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(Reminder.ReminderEntry.COLUMN_TITLE, reminder.getTitle());
        values.put(Reminder.ReminderEntry.COLUMN_NOTES,reminder.getNotes());
        values.put(Reminder.ReminderEntry.COLUMN_DATE,reminder.getDateToString());
        values.put(Reminder.ReminderEntry.COLUMN_DAYS_AFTER,reminder.getDaysAfter());
        values.put(Reminder.ReminderEntry.COLUMN_HOUR_RANGE,reminder.getHourRange());
        values.put(Reminder.ReminderEntry.COLUMN_ESTADO, reminder.getState().toString());
        return values;
    }

    public ArrayList<Reminder> getActiveReminders(){
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();
        try{
            SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
            Cursor cursor = dataBase.query(Reminder.ReminderEntry.TABLE_NAME, null, null, null, null, null, null);

            if(cursor.moveToFirst()){
                while (cursor.isAfterLast() == false) {
                    Reminder reminder = new Reminder();
                    reminder.setIdReminder(cursor.getInt(Reminder.ReminderEntry.INDEX_ENTRY_ID));
                    reminder.setTitle(cursor.getString(Reminder.ReminderEntry.INDEX_TITLE));
                    reminder.setNotes(cursor.getString(Reminder.ReminderEntry.INDEX_NOTES));
                    String date = cursor.getString(Reminder.ReminderEntry.INDEX_DATE);
                    GregorianCalendar gregorianCalendar = DateUtils.parserStringDate(date);
                    reminder.setDate(gregorianCalendar);
                    reminder.setDaysAfter(cursor.getInt(Reminder.ReminderEntry.INDEX_DAYS_AFTER));
                    reminder.setHourRange(cursor.getInt(Reminder.ReminderEntry.INDEX_HOUR_RANGE));
                    reminders.add(reminder);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return reminders;
    }

}
