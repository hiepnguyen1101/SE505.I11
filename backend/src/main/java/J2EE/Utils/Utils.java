package com.SE325.J2EE.Utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils
{
    public static Date convertToDateSQL(String string) throws ParseException
    {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        java.util.Date date = DATE_FORMAT.parse(string);
        return new java.sql.Date(date.getTime());
    }

    public static String convertToNormalDate(String string) throws ParseException
    {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        java.util.Date date = inputFormat.parse(string);
        return outputFormat.format(date);
    }
}
