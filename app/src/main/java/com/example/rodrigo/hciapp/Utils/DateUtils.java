package com.example.rodrigo.hciapp.Utils;

import com.example.rodrigo.hciapp.Model.Reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by rodrigo on 23/01/16.
 */
public class DateUtils {

    public static String setDateFormat(String date){
        /*String str = removeTimeZone(date);

        String strData = null;
        TimeZone tzUTC = TimeZone.getTimeZone("UTC");
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
        formatoEntrada.setTimeZone(tzUTC);
        SimpleDateFormat formatoSaida = new SimpleDateFormat("EEE, dd/MM/yy, HH:mm");

        try {
            strData = formatoSaida.format(formatoEntrada.parse(str));
        } catch (ParseException e) {
            Log.e("Erro parser data", Log.getStackTraceString(e));
        }
        return strData;*/
        return date;
    }

    public static GregorianCalendar parserStringDate(String date)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        GregorianCalendar gregorianCalendar = null;
        try {
            cal.setTime(dateFormat.parse(date));
            gregorianCalendar = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gregorianCalendar;
    }

    public static String convertMonthToString(int month){
        switch (month){
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            case 11:
                return "Diciembre";
        }
        return "";
    }

    public static String convertAM_PM(int codeAM_PM){
        if(codeAM_PM == 1){
            return "PM";
        }else {
            return "AM";
        }
    }

    public static String convertDateToString(GregorianCalendar calendar){
        String date;
        date = "" + calendar.get(Calendar.DAY_OF_MONTH) + " de " + convertMonthToString(calendar.get(Calendar.MONTH))
                + " " + calendar.get(Calendar.YEAR) ;
        return date;
    }

    public static String convertTimeToString(GregorianCalendar calendar){
        String time;
        time = "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " " + convertAM_PM(calendar.get(Calendar.AM_PM));
        return time;
    }

    public static String removeTimeZone(String data){
        // busca na string e remove o padrão " (+ ou -)dddd" Ex: " +3580"
        return data.replaceFirst("(\\s[+|-]\\d{4})", "");
    }
}
