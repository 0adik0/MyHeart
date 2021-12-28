package com.health.my_heart.domain.model;

import java.util.List;

public class Reminder {
    private int id;
    private final String time;
    private final String date;
    private final String year;
    public long timeMillis;
    private final int dayOfAWeek;
    private final List<Integer> alarmTypeOrdinals;
    private final List<String> drugs;

    public Reminder(int id, String time, String date, String year, long timeMillis, int dayOfAWeek, List<Integer> alarmTypes, List<String> drugs) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.year = year;
        this.timeMillis = timeMillis;
        this.dayOfAWeek = dayOfAWeek;
        this.alarmTypeOrdinals = alarmTypes;
        this.drugs = drugs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public int getId() {
        return id;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getYear() {
        return year;
    }

    public List<String> getDrugs() {
        return drugs;
    }

    public List<Integer> getAlarmTypeOrdinals() {
        return alarmTypeOrdinals;
    }

    public int getDayOfAWeek() {
        return dayOfAWeek;
    }


}
