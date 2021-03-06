package com.example.rodrigo.hciapp.Utils;

import com.example.rodrigo.hciapp.Model.Reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by rodrigo on 23/01/16.
 */
public class DateUtils {

    public static GregorianCalendar parserStringDate(String date)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        GregorianCalendar gregorianCalendar = null;
        try {
            cal.setTime(dateFormat.parse(date));
            gregorianCalendar = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
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
        if(codeAM_PM == Calendar.PM){
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

    public static String showDate(GregorianCalendar calendar){
        int dias = (int) Math.floor(getDifferenceDays(calendar));
        if(dias == 0){
            return "Hoy";
        }
        else if(dias>0){
            if(dias == 1){
                return "Queda " + dias + " dia";
            }
            return "Quedan " + dias + " dias";
        }
        return convertDateToString(calendar);
    }

    public static String convertTimeToString(GregorianCalendar calendar){
        String time;
        time = "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " " + convertAM_PM(calendar.get(Calendar.AM_PM));
        return time;
    }

    public static long getDifference(GregorianCalendar date){
        GregorianCalendar currentDate = new GregorianCalendar();
        //currentDate = new GregorianCalendar(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH),currentDate.get(Calendar.DAY_OF_MONTH),0,0);
        Date today = currentDate.getTime();
        Date anotherDay =   date.getTime();
        long diferencia = Math.abs(today.getTime() - anotherDay.getTime());
        return diferencia;
    }

    public static long getDifference(GregorianCalendar date,boolean sameDay){
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate = new GregorianCalendar(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH),currentDate.get(Calendar.DAY_OF_MONTH),0,0);
        Date today = currentDate.getTime();
        Date anotherDay =   date.getTime();
        long diferencia = Math.abs(today.getTime() - anotherDay.getTime());
        return diferencia;
    }

    public static long getDifferenceDays(GregorianCalendar date){
        long diferencia = getDifference(date);
        long dias = diferencia / (1000 * 60 * 60 * 24);
        return dias;
    }

    public static long getDifferenceDays(GregorianCalendar date,boolean sameDay){
        long diferencia = getDifference(date,sameDay);
        long dias = diferencia / (1000 * 60 * 60 * 24);
        return dias;
    }

    public static long getDifferenceHours(GregorianCalendar date){
        long diferencia = getDifference(date);
        double div = diferencia / (1000 * 60 * 60);
        long hours = (long) div;
        return hours;
    }

    public static long getDifferenceMinutes(GregorianCalendar date){
        long diferencia = getDifference(date);
        long minutes = diferencia / (1000 * 60 );
        return minutes;
    }

    public static long getDifferenceSeconds(GregorianCalendar date){
        long diferencia = getDifference(date);
        long seconds = diferencia / (1000 );
        return seconds;
    }

}
