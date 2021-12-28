package com.health.my_heart.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.health.my_heart.domain.model.DayOfAWeek;

import java.util.List;

@Entity(tableName = "table_reminders")
public class ReminderEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "day")
    public int day;
    @ColumnInfo(name = "month")
    public int month;
    @ColumnInfo(name = "year")
    public int year;
    @ColumnInfo(name = "day_of_a_week")
    public DayOfAWeek dayOfAWeek;
    @ColumnInfo(name = "alarm_type")
    public List<Integer> alarmTypes;
    @ColumnInfo(name = "time")
    public long time;
    @ColumnInfo(name = "drugs")
    public List<String> drugs;

    public ReminderEntity(int id, int day, int month, int year, DayOfAWeek dayOfAWeek, List<Integer> alarmTypes, long time, List<String> drugs) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayOfAWeek = dayOfAWeek;
        this.alarmTypes = alarmTypes;
        this.time = time;
        this.drugs = drugs;
    }
}
