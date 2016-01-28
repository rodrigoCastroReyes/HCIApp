package com.example.rodrigo.hciapp.Utils;

import android.graphics.Color;

import com.example.rodrigo.hciapp.Model.State;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by rodrigo on 23/01/16.
 */
public class ReminderUtils {


    public static int getColorByDate(GregorianCalendar date){
        //implementar bien esta funcion
        GregorianCalendar currentDate = new GregorianCalendar();
        int k = date.get(Calendar.MONTH);
       /*
        int today = currentDate.get(Calendar.DAY_OF_MONTH);
        int anotherDay = date.get(Calendar.DAY_OF_MONTH);
        int dif = Math.abs(today - anotherDay);*/
        Date today = currentDate.getTime();
        Date anotherDay =   date.getTime();

        int m1 = today.getMonth();
        int m2 = anotherDay.getMonth();


        long diferencia = Math.abs(today.getTime() - anotherDay.getTime());
        long dias = diferencia / (1000 * 60 * 60 * 24);

        if(dias >= 0 && dias < 1 ){
            return Color.rgb(244,11,11);//color rojo
        }
        else if(dias >=1 && dias <=3){
            return Color.rgb(247,97,2);//color naranja
        }
        return Color.rgb(39, 174, 97);
    }
}
