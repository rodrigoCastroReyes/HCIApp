package com.example.rodrigo.hciapp.Utils;

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

    public static String removeTimeZone(String data){
        // busca na string e remove o padrão " (+ ou -)dddd" Ex: " +3580"
        return data.replaceFirst("(\\s[+|-]\\d{4})", "");
    }
}
