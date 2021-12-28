package com.health.my_heart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String getDate(long timeMillis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date(timeMillis);
        return formatter.format(date);
    }

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String getFullDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String getFullDate(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        Date date = new Date(millis);
        return formatter.format(date);
    }

    public String getTime(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date(millis);
        return formatter.format(date);
    }

    public String getDayMonth(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM", Locale.getDefault());
        Date date = new Date(millis);
        String res = formatter.format(date);
        return res.substring(0, res.length() - 1);
    }

    public String getYear(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
        Date date = new Date(millis);
        return formatter.format(date);
    }
}
