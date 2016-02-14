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
        long dias = DateUtils.getDifferenceDays(date);
        
        if(dias >= 0 && dias < 1 ){
            return Color.rgb(244,11,11);//color rojo
        }
        else if(dias >=1 && dias <=3){
            return Color.rgb(247,198,2);//color naranja
        }
        return Color.rgb(39, 174, 97);
    }


}
