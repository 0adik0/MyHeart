package com.health.my_heart.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.health.my_heart.data.db.entity.ReminderEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface RemindersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertReminder(ReminderEntity reminderEntity);

    @Query("SELECT * FROM table_reminders")
    Observable<List<ReminderEntity>> getReminders();

    @Query("SELECT * FROM table_reminders WHERE id =:id")
    Single<ReminderEntity> getReminderById(int id);

    @Query("SELECT * FROM table_reminders WHERE day =:day AND month =:month AND year =:year")
    Single<List<ReminderEntity>> getRemindersByDate(int day, int month, int year);

    @Update
    Completable updateReminder(ReminderEntity reminderEntity);

    @Query("DELETE FROM table_reminders WHERE time < :timeMillis")
    Completable deleteRemindersOlderThan(long timeMillis);
}
