package com.health.my_heart.data.db.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.health.my_heart.domain.model.DayOfAWeek;
import com.health.my_heart.domain.model.IndicatorType;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public IndicatorType toIndicatorType(int ordinal) {
        return IndicatorType.values()[ordinal];
    }

    @TypeConverter
    public int fromIndicatorType(IndicatorType indicatorType) {
        return indicatorType.ordinal();
    }

    @TypeConverter
    public DayOfAWeek toDayOfAWeek(int ordinal) {
        return DayOfAWeek.values()[ordinal];
    }

    @TypeConverter
    public int fromDayOfAWeek(DayOfAWeek dayOfAWeek) {
        return dayOfAWeek.ordinal();
    }

    @TypeConverter
    public List<String> toListDrugs(String json) {
        if (json == null)
            return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public String fromListDrugs(List<String> drugs) {
        if (drugs == null)
            return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.toJson(drugs, type);
    }

    @TypeConverter
    public List<Integer> toAlarmTypes(String json) {
        if (json == null)
            return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public String fromAlarmTypes(List<Integer> alarmTypes) {
        if (alarmTypes == null)
            return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return gson.toJson(alarmTypes, type);
    }
}
