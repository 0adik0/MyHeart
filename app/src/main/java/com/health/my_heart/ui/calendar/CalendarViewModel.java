package com.health.my_heart.ui.calendar;

import androidx.lifecycle.MutableLiveData;

import com.health.my_heart.domain.model.AlarmType;
import com.health.my_heart.domain.model.Reminder;
import com.health.my_heart.domain.model.Result;
import com.health.my_heart.domain.repository.Repository;
import com.health.my_heart.ui.base.BaseViewModel;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CalendarViewModel extends BaseViewModel {
    public final MutableLiveData<Result<List<Reminder>>> remindersResult;
    public final MutableLiveData<Result<Void>> saveResult;
    public final MutableLiveData<Reminder> newReminder;
    public final MutableLiveData<Reminder> reminderRequest;
    private final Repository repository;

    @Inject
    public CalendarViewModel(Repository repository) {
        this.repository = repository;
        remindersResult = new MutableLiveData<>();
        saveResult = new MutableLiveData<>();
        newReminder = new MutableLiveData<>();
        reminderRequest = new MutableLiveData<>();
    }

    public void getReminderById(int id) {
        disposable.add(repository.getReminderById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reminderRequest::postValue));
    }

    public void getReminders() {
        disposable.add(repository.getReminders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> remindersResult.postValue(Result.success(list)), t -> remindersResult.postValue(Result.error(t))));
    }

    public void getRemindersByDate(int day, int month, int year) {
        disposable.add(repository.getRemindersByDate(day, month, year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> remindersResult.postValue(Result.success(list)), t -> remindersResult.postValue(Result.error(t))));
    }

    public void saveReminder(Reminder reminder) {
        if (reminder.getAlarmTypeOrdinals().size() > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MINUTE, 0);
            int currHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currDay = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.setTimeInMillis(reminder.getTimeMillis());
            if (reminder.getAlarmTypeOrdinals().get(0) == AlarmType.ALL_DAY.ordinal() && reminder.getDayOfAWeek() == currDay) {
                //check current time and schedule nearest time
                if (currHour >=7 && currHour < 12) {
                    calendar.set(Calendar.HOUR_OF_DAY, 12);
                } else if (currHour >= 12 && currHour < 18)
                    calendar.set(Calendar.HOUR_OF_DAY, 18);
                else {
                    calendar.set(Calendar.HOUR_OF_DAY, 7);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                }
            } else if (reminder.getAlarmTypeOrdinals().size() > 1) {
                if (currDay == reminder.getDayOfAWeek()) {
                    //check current time and schedule nearest time
                    switch (AlarmType.values()[reminder.getAlarmTypeOrdinals().get(0)]) {
                        case MORNING: {
                            if (currHour >= 7 && currHour < 12) {
                                if (AlarmType.values()[reminder.getAlarmTypeOrdinals().get(1)] == AlarmType.AFTERNOON) {
                                    calendar.set(Calendar.HOUR_OF_DAY, 12);
                                    calendar.set(Calendar.MINUTE, 0);
                                }
                                if (AlarmType.values()[reminder.getAlarmTypeOrdinals().get(1)] == AlarmType.EVENING) {
                                    calendar.set(Calendar.HOUR_OF_DAY, 18);
                                    calendar.set(Calendar.MINUTE, 0);
                                }
                            } else {
                                calendar.set(Calendar.HOUR_OF_DAY, 7);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.add(Calendar.DAY_OF_MONTH, 7);
                            }
                            break;
                        }
                        case AFTERNOON: {
                            if (currHour >= 12 && currHour < 18) {
                                calendar.set(Calendar.HOUR_OF_DAY, 18);
                            } else {
                                calendar.set(Calendar.HOUR_OF_DAY, 12);
                                calendar.add(Calendar.DAY_OF_MONTH, 7);
                            }
                            calendar.set(Calendar.MINUTE, 0);
                            break;
                        }
                    }
                } else {
                    switch (AlarmType.values()[reminder.getAlarmTypeOrdinals().get(0)]) {
                        case MORNING: {
                            calendar.set(Calendar.HOUR_OF_DAY, 7);
                            break;
                        }
                        case AFTERNOON: {
                            calendar.set(Calendar.HOUR_OF_DAY, 12);
                            break;
                        }
                        case EVENING: {
                            calendar.set(Calendar.HOUR_OF_DAY, 18);
                            break;
                        }
                    }
                }
            }
            reminder.setTimeMillis(calendar.getTimeInMillis());
        }

        if (reminder.getId() == 0) {
            reminder.setId((int) (System.currentTimeMillis() % Integer.MAX_VALUE));
            disposable.add(repository.saveReminder(reminder)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> saveResult.postValue(Result.success(null))));
        } else
            disposable.add(repository.updateReminder(reminder)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> saveResult.postValue(Result.success(null))));

        newReminder.postValue(reminder);
    }
}
