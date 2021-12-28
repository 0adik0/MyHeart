package com.health.my_heart.domain.mapper;

import com.health.my_heart.data.db.entity.ReminderEntity;
import com.health.my_heart.domain.model.DayOfAWeek;
import com.health.my_heart.domain.model.Reminder;
import com.health.my_heart.utils.DateUtils;
import com.health.my_heart.utils.Mapper;

import java.util.Calendar;

import javax.inject.Inject;

public class ReminderMapper implements Mapper<Reminder, ReminderEntity> {
    private final DateUtils dateUtils;

    @Inject
    public ReminderMapper(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    @Override
    public Reminder toDomain(ReminderEntity reminderEntity) {
        long millis = reminderEntity.time;
        String time = dateUtils.getTime(millis);
        String date = dateUtils.getDayMonth(millis);
        String year = dateUtils.getYear(millis);
        return new Reminder(reminderEntity.id, time, date, year, millis, reminderEntity.dayOfAWeek.ordinal(), reminderEntity.alarmTypes, reminderEntity.drugs);
    }

    @Override
    public ReminderEntity toEntity(Reminder reminder) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reminder.getTimeMillis());
        ReminderEntity reminderEntity = new ReminderEntity(reminder.getId(), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), DayOfAWeek.values()[reminder.getDayOfAWeek()], reminder.getAlarmTypeOrdinals(), reminder.getTimeMillis(), reminder.getDrugs());
        if (reminder.getId() != 0)
            reminderEntity.id = reminder.getId();
        return reminderEntity;
    }
}
