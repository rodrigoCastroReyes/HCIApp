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
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;

import static java.util.Collections.*;

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

    public long insertMarket(Market market){
        SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
        return dataBase.insert(Market.MarketEntry.TABLE_NAME, null, generateValuesMarket(market));
    }

    public void updateReminder(ContentValues values,long id){
        int update = dbHelper.getDatabase().update(Reminder.ReminderEntry.TABLE_NAME, values, Reminder.ReminderEntry.COLUMN_ENTRY_ID + "=" + id, null);
    }

    public void insertMarkets(){
        insertMarket( new Market("Supermaxi", "-2.17099786", "-79.9223557", "Av. Rodrigo Chávez Gonzáles Etapa III Manzana 270 Solar 1"));
        insertMarket( new Market("Supermaxi", "-2.163201", "-79.92429", "CC La Piazza Ceibos Avenue Abdon Calderon Munoz"));
        insertMarket( new Market("Supermaxi", "-2.180182", "-79.9044952", "CC Albán Borja Avenida Carlos Julio Arosemena Tola"));
        insertMarket( new Market("Supermaxi", "-2.17085338", "-79.90021", "Policentro Av San Jorge"));
        insertMarket( new Market("Supermaxi", "-2.13192", "-79.93332", "CC Parque California Via Daule"));
        insertMarket( new Market("Supermaxi", "-2.1434145", "-79.89248", "CC Garzocentro Av. Agustín Freire"));
        insertMarket( new Market("Tia", "-2.15784717", "-79.9196243", "Av. Manuel Rendon y Tercer Callejon 12 N-O"));
        insertMarket( new Market("Tia", "-2.18021417", "-79.9104843", "BELLAVISTA Av. J.M. Velasco Ibarra Calle Primera B"));
        insertMarket( new Market("Tia", "-2.152473", "-79.93312", "GYE EL TREBOL Km 5 1/2 Via A Daule Av 8 y Calle O Mapasingue"));
        insertMarket( new Market("Tia", "-2.14464974", "-79.92366", "Cdla. Martha de Roldos Calle Publica 18, entre Peatonal 38 y Peatonal s/n"));
        insertMarket( new Market("Tia", "-2.1988163", "-79.92576", "29 y Portete"));
        insertMarket( new Market("Tia", "-2.190025", "-79.9430161", "José Rodríguez Bonín Urbanización Renacer y Jardín del Salado"));
        insertMarket( new Market("Tia", "-2.19791555", "-79.93291", "38 y Portete"));
        insertMarket( new Market("Tia", "-2.20119", "-79.91599", "17 Y PORTETE"));
        insertMarket( new Market("Tia", "-2.203792", "-79.9092", "Portete y la Octava"));
        insertMarket( new Market("Tia", "-2.20748138", "-79.92792", "Calle Assab Bucaram y Rosendo Aviles"));
        insertMarket( new Market("Tia", "-2.20097184", "-79.89955", "Calle Carchi 2305 y Gomez Rendon Esquina"));
        insertMarket( new Market("Tia", "-2.13982582", "-79.8986359", "ALBORADA Av. Rodolfo Baquerizo Nazur y Av. Jose Maria Egas esquina"));
        insertMarket( new Market("Tia", "-2.144191", "-79.89343", "AGUSTIN FREIRE Y ROLANDO PAREJA GARZOCENTRO 2000"));
        insertMarket( new Market("Tia", "-2.191007", "-79.8882446", "Calle Velez entre Pedro Moncayo y 6 de Marzo Edificio Forum frente a la Plaza Centenario"));
        insertMarket( new Market("Tia", "-2.132232", "-79.90794", "Urb. Los Alamos av. Francisco de Orellana y calle tiwinza intersección mz 025 solar 02-02"));
        insertMarket( new Market("Tia", "-2.210262", "-79.90726", "Calle 4 de Noviembre y Calle Gallegos Lara"));
        insertMarket( new Market("Tia", "-2.19507241", "-79.88768", "Mercado Lorenzo de Garaicoa No. 1500 y Sucre"));
        insertMarket( new Market("Tia", "-2.1956", "-79.887764", "Colón No. 806 y Lorenzo de Garaicoa"));
        insertMarket( new Market("Tia", "-2.192611", "-79.8829956", "Chimborazo 217 y Luque"));
        insertMarket( new Market("Tia", "-2.217469", "-79.92672", "Calle 25 y la Ch."));
        insertMarket( new Market("Tia", "-2.19853616", "-79.8846054", "Calle .Chimborazo y Avda. Olmedo"));
        insertMarket( new Market("Tia", "-2.218776", "-79.90976", "CRISTO DEL CONSUELO Calle Josefina Barba La A y Guerrero Martinez"));
        insertMarket( new Market("Tia", "-2.12586784", "-79.89651", "SAUCES VI Av. Gabriel Roldos Garces entre Peatonal y 2do Pasaje 3 -A Noreste"));
        insertMarket( new Market("Tia", "-2.225146", "-79.9293", "25 y la LL"));
        insertMarket( new Market("Tia", "-2.223049", "-79.90148", "ACACIAS Calle Garcia Moreno y Av. 3 Suroeste entre Tercer paseo 43 Suroeste y Jose Feraud Gúzman El Conquistador"));
        insertMarket( new Market("Tia", "-2.12165856", "-79.89509", "GUAYACANES Av. Presidente Jose Luis Tamayo Teran y 12 Pasaje 3A"));
        insertMarket( new Market("Tia", "-2.12273455", "-79.89016", "SAUCES V  Av. Rodrigo Izcaza Cornejo y Enrique Gil Gilbert Esquina"));
        insertMarket( new Market("Tia", "-2.11927557", "-79.9495544", "CASUARINA Cdla. El Fortín calle 22, entrada de la línea 8 y calle s/n Mz. 061"));
        insertMarket( new Market("Tia", "-2.22966", "-79.9062042", "MALVINAS  Alban Mosquera y Peatonal 8 Suroeste"));
        insertMarket( new Market("Tia", "-2.23077083", "-79.9399643", "23 y la Q"));
        insertMarket( new Market("Megamaxi", "-2.175962", "-79.94385", "CC Riocentro Los Ceibos Av. Del Bombero s/n Km 6 1/2 y Av. Leopoldo Carrera"));
        insertMarket( new Market("Megamaxi", "-2.14085", "-79.9096", "CC CityMall Av. Felipe Pezo s/n y Av Benjamín Carrión "));
        insertMarket( new Market("Megamaxi", "-2.15546465", "-79.89213", "Av. Joaquín J. Orrantia G. s/n y Juan Tanca Marengo (Mall del Sol)"));
        insertMarket( new Market("Megamaxi", "-2.22962236", "-79.89813", "Av. José de la Cuadra s/n y Ernesto Albán (Mall del Sur)"));
        insertMarket( new Market("Megamaxi", "-2.1409502", "-79.86426", "CC Village Plaza Km 1 1/2 Vía a Samborondón entre Ríos Lote 101"));
        insertMarket( new Market("Gran Aki", "-2.155601", "-79.9301147", "MARTHA BUCARANDE ROLDOS (VÍA DAULE KILÓMETRO NÚMERO 4.8) Y CALLE PRIMERA)"));
        insertMarket( new Market("Aki", "-2.177549", "-79.88896", "AV. DEMOCRACIA SOLAR 2 Y SUFRAGIO LIBRE"));
        insertMarket( new Market("Gran Aki", "-2.207019", "-79.88637", "LETAMENDI 303 ENTRE CHILE Y CHIMBORAZO"));
        insertMarket( new Market("Gran Aki", "-2.14296079", "-79.87942", "AV. BENJAMIN ROSALES S/N Y AV. DE LAS AMERICAS"));
        insertMarket( new Market("Super Aki", "-2.118903", "-79.94825", "Vía Perimetral Km 26 1/2 y Dr. Honorato Vázquez"));
        insertMarket( new Market("Gran Aki", "-2.242521", "-79.89139", "AV. DOMINGO COMIN SOLAR UNO CALLE 50SE"));
        insertMarket( new Market("Aki", "-2.25074458", "-79.8971939", "AV. 25 DE JULIO S/N CALLE 51 C"));
        insertMarket( new Market("Super Aki", "-2.081944", "-79.91589", "AV. FRANCISCO DE ORELLANA S/N E ISIDRO AYORA ESQ."));
        insertMarket( new Market("Coral Hipermercados", "-2.17995477", "-79.90466", "Avenue Carlos Julio Arosemena Tola"));
        insertMarket( new Market("Cossfa", "-2.13192", "-79.93332", "Vía Daule"));
        insertMarket( new Market("Santa Maria", "-2.14717031", "-79.89365", "La Garzota Guillermo Pareja Rolando"));
        insertMarket( new Market("Santa Maria", "-2.10940337", "-79.9490356", "Vía Perimetral"));
    }

    public ContentValues generateValuesReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(Reminder.ReminderEntry.COLUMN_TITLE, reminder.getTitle());
        values.put(Reminder.ReminderEntry.COLUMN_NOTES,reminder.getNotes());
        values.put(Reminder.ReminderEntry.COLUMN_DATE,reminder.getDateToString());
        values.put(Reminder.ReminderEntry.COLUMN_DAYS_AFTER,reminder.getDaysAfter());
        values.put(Reminder.ReminderEntry.COLUMN_HOUR_RANGE,reminder.getHourRange());
        values.put(Reminder.ReminderEntry.COLUMN_ESTADO, reminder.getState().toString());
        if(reminder.getPhotoPath()!="") {
            values.put(Reminder.ReminderEntry.COLUMN_PHOTO_PATH, reminder.getPhotoPath());
        }else{
            values.putNull(Reminder.ReminderEntry.COLUMN_PHOTO_PATH);
        }
        if(reminder.getVoiceNotePath()!=""){
            values.put(Reminder.ReminderEntry.COLUMN_VOICE_NOTE_PATH, reminder.getVoiceNotePath());
        }else{
            values.putNull(Reminder.ReminderEntry.COLUMN_VOICE_NOTE_PATH);
        }
        return values;
    }

    public ContentValues generateValuesMarket(Market market) {
        ContentValues values = new ContentValues();
        values.put(Market.MarketEntry.COLUMN_NAME,market.getName());
        values.put(Market.MarketEntry.COLUMN_LATITUDE,market.getLatitude());
        values.put(Market.MarketEntry.COLUMN_LONGITUDE,market.getLongitude());
        values.put(Market.MarketEntry.COLUMN_ADDRESS, market.getAddress());
        return values;
    }

    public void removeReminder(long id){
        final int delete = dbHelper.getDatabase().delete(Reminder.ReminderEntry.TABLE_NAME, Reminder.ReminderEntry.COLUMN_ENTRY_ID + "=" + id,null);
    }

    public ArrayList<Reminder> getActiveReminders(){
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();
        try{
            SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
            Cursor cursor = dataBase.query(Reminder.ReminderEntry.TABLE_NAME, null, null, null, null, null, null);
            if(cursor.moveToFirst()){
                while (cursor.isAfterLast() == false) {
                    Reminder reminder = new Reminder();
                    String date = cursor.getString(Reminder.ReminderEntry.INDEX_DATE);
                    GregorianCalendar gregorianCalendar = DateUtils.parserStringDate(date);
                    reminder.setDate(gregorianCalendar);
                    GregorianCalendar currentDate = new GregorianCalendar();
                    String state = cursor.getString(Reminder.ReminderEntry.INDEX_ESTADO);
                    /*if(state.compareTo("RESOLVED") == 0 ||state.compareTo("resolved") == 0){
                        continue;
                    }*/
                    if(gregorianCalendar.compareTo(currentDate)==1  ){
                        reminder.setIdReminder(cursor.getInt(Reminder.ReminderEntry.INDEX_ENTRY_ID));
                        reminder.setTitle(cursor.getString(Reminder.ReminderEntry.INDEX_TITLE));
                        reminder.setNotes(cursor.getString(Reminder.ReminderEntry.INDEX_NOTES));
                        reminder.setDaysAfter(cursor.getInt(Reminder.ReminderEntry.INDEX_DAYS_AFTER));
                        reminder.setHourRange(cursor.getInt(Reminder.ReminderEntry.INDEX_HOUR_RANGE));
                        String photoPath = cursor.getString(Reminder.ReminderEntry.INDEX_PHOTO_PATH);
                        String voiceNotePath = cursor.getString(Reminder.ReminderEntry.INDEX_VOICE_NOTE_PATH);
                        reminder.setPhotoPath(photoPath);
                        reminder.setVoiceNotePath(voiceNotePath);
                        reminder.defineDatesOfNotifications();
                        reminders.add(reminder);
                    }
                    cursor.moveToNext();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(reminders);
        //Collections.reverse(reminders);
        return reminders;
    }

    public ArrayList<Reminder> getInactiveReminders(){
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();
        try{
            SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
            Cursor cursor = dataBase.query(Reminder.ReminderEntry.TABLE_NAME, null, null, null, null, null, null);
            if(cursor.moveToFirst()){
                while (cursor.isAfterLast() == false) {
                    Reminder reminder = new Reminder();
                    String date = cursor.getString(Reminder.ReminderEntry.INDEX_DATE);
                    GregorianCalendar gregorianCalendar = DateUtils.parserStringDate(date);
                    reminder.setDate(gregorianCalendar);
                    GregorianCalendar currentDate = new GregorianCalendar();
                    if(gregorianCalendar.compareTo(currentDate)==-1){
                        reminder.setIdReminder(cursor.getInt(Reminder.ReminderEntry.INDEX_ENTRY_ID));
                        reminder.setTitle(cursor.getString(Reminder.ReminderEntry.INDEX_TITLE));
                        reminder.setNotes(cursor.getString(Reminder.ReminderEntry.INDEX_NOTES));
                        reminder.setDaysAfter(cursor.getInt(Reminder.ReminderEntry.INDEX_DAYS_AFTER));
                        reminder.setHourRange(cursor.getInt(Reminder.ReminderEntry.INDEX_HOUR_RANGE));
                        String photoPath = cursor.getString(Reminder.ReminderEntry.INDEX_PHOTO_PATH);
                        String voiceNotePath = cursor.getString(Reminder.ReminderEntry.INDEX_VOICE_NOTE_PATH);
                        reminder.setPhotoPath(photoPath);
                        reminder.setVoiceNotePath(voiceNotePath);
                        reminder.defineDatesOfNotifications();
                        reminders.add(reminder);
                    }
                    cursor.moveToNext();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(reminders);
        //Collections.reverse(reminders);
        return reminders;
    }

    public ArrayList<Market>getMarkets(){
        ArrayList<Market> markets = new ArrayList<Market>();
        SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
        Cursor cursor = dataBase.query(Market.MarketEntry.TABLE_NAME, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast() == false) {
                Market market = new Market();
                market.setIdMarket(cursor.getInt(Market.MarketEntry.INDEX_ENTRY_ID));
                market.setName(cursor.getString(Market.MarketEntry.INDEX_NAME));
                market.setLatitude(Double.valueOf(cursor.getString(Market.MarketEntry.INDEX_LATITUDE)));
                market.setLongitude(Double.valueOf(cursor.getString(Market.MarketEntry.INDEX_LONGITUDE)));
                market.setAddress(cursor.getString(Market.MarketEntry.INDEX_ADDRESS));
                markets.add(market);
                cursor.moveToNext();
            }
        }
        return markets;
    }

}
