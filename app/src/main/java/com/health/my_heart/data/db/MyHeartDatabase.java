package com.health.my_heart.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.health.my_heart.data.db.converter.Converters;
import com.health.my_heart.data.db.dao.HealthIndicatorsDao;
import com.health.my_heart.data.db.dao.RemindersDao;
import com.health.my_heart.data.db.entity.HealthIndicatorEntity;
import com.health.my_heart.data.db.entity.ReminderEntity;

@Database(entities = {HealthIndicatorEntity.class, ReminderEntity.class}, version = 5)
@TypeConverters(value = {Converters.class})
public abstract class MyHeartDatabase extends RoomDatabase {
    private static final String DB_NAME = "my_heart.db";

    public abstract HealthIndicatorsDao getHealthIndicatorsDao();
    public abstract RemindersDao getRemindersDao();

    public synchronized static MyHeartDatabase create(Context context) {
        return Room.databaseBuilder(context, MyHeartDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
}
