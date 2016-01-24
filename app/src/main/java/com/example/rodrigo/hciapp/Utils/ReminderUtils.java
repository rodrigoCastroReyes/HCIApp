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
        int today = currentDate.get(Calendar.DAY_OF_MONTH);
        int anotherDay = date.get(Calendar.DAY_OF_MONTH);
        int dif = Math.abs(today - anotherDay);
        switch (dif){
            case 0://el mismo dia
                return Color.rgb(244,11,11);//color rojo
            case 3: //3 dias
                return Color.rgb(247,97,2);//color naranja
            case 7://una semana
                return Color.rgb(39, 174, 97);//color verde
        }
        return Color.rgb(39, 174, 97);
    }
}
