package com.example.rodrigo.hciapp.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.rodrigo.hciapp.Model.Reminder;

import java.util.ArrayList;

/**
 * Created by rodrigo on 24/01/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "recordatoriosDB.db";
    private static final int SCHEME_VERSION = 1;
    private SQLiteDatabase database;
    private Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, SCHEME_VERSION);
        database = this.getWritableDatabase();
        context = context;
        //database.execSQL("drop table Reminder ");
    }

    public void onCreate(SQLiteDatabase db) {
        database = db;
    }

    public void createTables(){
        ArrayList<String>sentences = this.getCreateTableSentences();
        for(String sentence : sentences){
            database.execSQL(sentence);//create tables
        }
    }

    public ArrayList<String>getCreateTableSentences(){
        ArrayList<String >sentences = new ArrayList<>();
        sentences.add(Reminder.ReminderEntry.DATABASE_CREATE);
        //otras tablas
        return sentences;
    }

    public ArrayList<String >getDeleteTableSentences(){
        ArrayList<String >sentences = new ArrayList<>();
        sentences.add(Reminder.ReminderEntry.DATABASE_DELETE);
        //otras tablas
        return sentences;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArrayList<String>sentences = this.getDeleteTableSentences();
        for(String sentence : sentences){
            db.execSQL(sentence);//create tables
        }
        onCreate(db);
    }
}
