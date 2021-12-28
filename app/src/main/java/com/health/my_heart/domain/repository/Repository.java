package com.health.my_heart.domain.repository;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.health.my_heart.domain.model.BodyParameters;
import com.health.my_heart.domain.model.Diseases;
import com.health.my_heart.domain.model.HealthIndicator;
import com.health.my_heart.domain.model.QuizResult;
import com.health.my_heart.domain.model.Relative;
import com.health.my_heart.domain.model.Reminder;
import com.health.my_heart.domain.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface Repository {
    Task<AuthResult> createUser(String email, String password);

    void saveUser(User user);

    void saveBodyParameters(BodyParameters bodyParameters);

    void saveDiseases(Diseases diseases);

    Task<AuthResult> login(String email, String password);

    Task<Void> resetPassword(String email);

    String getCurrentUserUid();

    void updateCurrentUserUid();

    void forgetUser();

    List<String> getQuestions();

    void saveResult(QuizResult result);

    boolean needForSurvey();

    QuizResult getCurrentZone();

    void skipSurvey();

    boolean skippedSurvey();

    boolean hasLinkedRelativePhone();

    void connectRelatives(Relative relative1, Relative relative2);

    List<Relative> getRelatives();

    void updateUserName(String username);

    String getUserName();

    DatabaseReference getUserInfo();

    void editProfile(User user, BodyParameters parameters);

    DatabaseReference getArticle(String id);

    DatabaseReference getCategories(String name);

    Completable saveReminder(Reminder reminder);

    Observable<List<Reminder>> getReminders();

    Single<Reminder> getReminderById(int id);

    Single<List<Reminder>> getRemindersByDate(int day, int month, int year);

    Completable updateReminder(Reminder reminder);

    Completable deleteOldReminders();

    Task<Void> initUserIndicators();

    DatabaseReference getAllIndicators();

    void updateImt(String imt);

    DatabaseReference getIndicatorsByName(String name);

    void saveIndicator(String name, HealthIndicator healthIndicator);

    DatabaseReference getBodyParameters();

    void updateUserPassword(String newPassword);
}
