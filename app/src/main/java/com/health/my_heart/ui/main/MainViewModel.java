package com.health.my_heart.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.health.my_heart.domain.model.AlarmType;
import com.health.my_heart.domain.model.Reminder;
import com.health.my_heart.domain.repository.Repository;
import com.health.my_heart.ui.base.BaseViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {
    private static final String TAG = "MainViewModel";
    public MutableLiveData<Reminder> newReminder;
    private final Repository repository;

    @Inject
    public MainViewModel(Repository repository) {
        this.repository = repository;
        newReminder = new MutableLiveData<>();
    }

    public boolean needForQuiz() {
        return repository.needForSurvey();
    }

    public void deleteOldReminders() {
        long currentTimeMillis = System.currentTimeMillis();
        disposable.add(repository.getReminders()
                .subscribeOn(Schedulers.io())
                .flatMap((Function<List<Reminder>, ObservableSource<Reminder>>) Observable::fromIterable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reminder -> {
                    long time = reminder.getTimeMillis();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(reminder.getTimeMillis());
                    int currHour = calendar.get(Calendar.HOUR_OF_DAY);
                    //check alarm types
                    //if size == 0 just + 7 days
                    //else find nearest time and schedule
                    if (reminder.getTimeMillis() < currentTimeMillis) {
                        if (reminder.getAlarmTypeOrdinals().size() == 0) {
                            reminder.setTimeMillis(time + TimeUnit.DAYS.toMillis(7));
                            updateReminder(reminder);
                        } else if (reminder.getAlarmTypeOrdinals().size() == 1) {
                            if (AlarmType.values()[reminder.getAlarmTypeOrdinals().get(0)] != AlarmType.ALL_DAY) {
                                reminder.setTimeMillis(time + TimeUnit.DAYS.toMillis(7));
                            } else {
                                if (currHour >= 7 && currHour < 12) {
                                    calendar.set(Calendar.HOUR_OF_DAY, 12);
                                    calendar.set(Calendar.MINUTE, 0);
                                } else if (currHour >= 12 && currHour < 18 ) {
                                    calendar.set(Calendar.HOUR_OF_DAY, 18);
                                    calendar.set(Calendar.MINUTE, 0);
                                } else {
                                    calendar.set(Calendar.HOUR_OF_DAY, 7);
                                    calendar.set(Calendar.MINUTE, 0);
                                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                                }
                                reminder.setTimeMillis(calendar.getTimeInMillis());
                            }
                            updateReminder(reminder);
                        } else if (reminder.getAlarmTypeOrdinals().size() > 1) {
                            switch(AlarmType.values()[reminder.getAlarmTypeOrdinals().get(0)]) {
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
                            reminder.setTimeMillis(calendar.getTimeInMillis());
                            updateReminder(reminder);
                        }
                    }
                }, Throwable::printStackTrace));
    }

    private void updateReminder(Reminder reminder) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reminder.getTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        Date date = new Date(reminder.timeMillis);

        Log.d(TAG, "updateReminder: " + formatter.format(date));
        newReminder.postValue(reminder);
        disposable.add(repository.updateReminder(reminder).subscribeOn(Schedulers.io()).subscribe(()->{}, Throwable::printStackTrace));
    }
}
