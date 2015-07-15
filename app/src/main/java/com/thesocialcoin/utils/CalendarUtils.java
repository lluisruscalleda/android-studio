package com.thesocialcoin.utils;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 14/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class CalendarUtils {

    public static java.util.Calendar getCalendarFromStringDate(String dateString){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("ETC/UTC"));


        try {
            Date date = format.parse(dateString);
            // System.out.println("Date ->" + date);
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDayOfTheWeek(int dayOfTheWeek){
        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] dayNames = symbols.getShortWeekdays();
        return dayNames[dayOfTheWeek];
    }

    public static String getTimeOfDayFromString(String dateString){
        java.util.Calendar c = getCalendarFromStringDate(dateString);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(c.getTime());
    }

    public static String getFormattedDate(String date){
        java.util.Calendar calendar = getCalendarFromStringDate(date);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy',' h:mm");

        return format.format(calendar.getTime());
    }

    public static String getScheduleFormattedDate(String date){
        java.util.Calendar calendar = getCalendarFromStringDate(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(calendar.getTime());
    }

    public static String getDayAndMonthFromDateString(String date){
        java.util.Calendar calendar = getCalendarFromStringDate(date);
        System.out.println("Date ->" + date);
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("es", "ES"));
        String[] monthNames = symbols.getMonths();

        return calendar.get(java.util.Calendar.DAY_OF_MONTH) + " DE "+ monthNames[calendar.get(java.util.Calendar.MONTH)];
    }

}
