package com.csga.sourceload_server.Utils.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date){
        return df.format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return df.parse(date);
    }


}
