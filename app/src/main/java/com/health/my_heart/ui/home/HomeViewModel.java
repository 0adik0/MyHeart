package com.health.my_heart.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.health.my_heart.domain.model.QuizResult;
import com.health.my_heart.domain.model.Relative;
import com.health.my_heart.domain.model.Reminder;
import com.health.my_heart.domain.model.Result;
import com.health.my_heart.domain.repository.Repository;
import com.health.my_heart.ui.base.BaseViewModel;
import com.health.my_heart.utils.DateUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {
    public MutableLiveData<Boolean> isShowInfo;
    public final MutableLiveData<Result<Reminder>> remindersResult;
    private final Repository repository;

    @Inject
    public HomeViewModel(Repository repository) {
        this.repository = repository;
        isShowInfo = new MutableLiveData<>(false);
        remindersResult = new MutableLiveData<>();
    }

    public boolean skippedSurvey() { return repository.skippedSurvey(); }

    public boolean linkedRelativesNumber() { return repository.hasLinkedRelativePhone(); }

    public QuizResult getCurrentUserZone() {
        return repository.getCurrentZone();
    }

    public void connectRelatives(Relative relative1, Relative relative2) {
        repository.connectRelatives(relative1, relative2);
    }

    public void getTodayReminders() {
        String currentDate = DateUtils.getCurrentDate();
        disposable.add(repository.getReminders()
                .subscribeOn(Schedulers.io())
                .flatMapIterable((Function<List<Reminder>, List<Reminder>>) list -> list)
                .filter(reminder -> {
                    String date = DateUtils.getDate(reminder.getTimeMillis());
                    return currentDate.equals(date);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        reminder -> remindersResult.setValue(Result.success(reminder)),
                        t -> remindersResult.setValue(Result.error(t))));
    }

    public List<Relative> getRelatives() {
        return repository.getRelatives();
    }
}
