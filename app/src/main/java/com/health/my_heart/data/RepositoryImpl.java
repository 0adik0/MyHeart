package com.health.my_heart.data;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.health.my_heart.data.db.dao.RemindersDao;
import com.health.my_heart.domain.mapper.ReminderMapper;
import com.health.my_heart.domain.model.BodyParameters;
import com.health.my_heart.domain.model.CommonIndicator;
import com.health.my_heart.domain.model.Diseases;
import com.health.my_heart.domain.model.HealthIndicator;
import com.health.my_heart.domain.model.IndicatorType;
import com.health.my_heart.domain.model.QuizResult;
import com.health.my_heart.domain.model.Relative;
import com.health.my_heart.domain.model.Reminder;
import com.health.my_heart.domain.model.User;
import com.health.my_heart.domain.repository.Repository;
import com.health.my_heart.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class RepositoryImpl implements Repository {
    public static final String RESULT = "result";
    public static final String FIRST = "0";
    public static final String SECOND = "1";
    private final FirebaseHelper firebase;
    private final SharedPrefsStorage storage;
    private final RemindersDao remindersDao;
    private final ReminderMapper reminderMapper;

    @Inject
    public RepositoryImpl(FirebaseHelper firebase, SharedPrefsStorage storage, RemindersDao remindersDao, ReminderMapper reminderMapper) {
        this.firebase = firebase;
        this.storage = storage;
        this.remindersDao = remindersDao;
        this.reminderMapper = reminderMapper;
    }

    @Override
    public Task<AuthResult> createUser(String email, String password) {
        return firebase.getAuth().createUserWithEmailAndPassword(email, password);
    }

    @Override
    public void saveUser(User user) {
        String uid = firebase.getAuth().getCurrentUser().getUid();
        storage.updateCurrentUserUid(uid);
        storage.updateCurrentUserName(user.getName() + " " + user.getSurname());
        firebase.getUsersReference().child(uid).setValue(user);
    }

    @Override
    public void saveBodyParameters(BodyParameters bodyParameters) {
        firebase.getBodyParametersReference().child(storage.getCurrentUserUid()).setValue(bodyParameters);
    }

    @Override
    public void saveDiseases(Diseases diseases) {
        firebase.getDiseasesReference().child(storage.getCurrentUserUid()).setValue(diseases);
    }

    @Override
    public Task<AuthResult> login(String email, String password) {
        return firebase.getAuth().signInWithEmailAndPassword(email, password);
    }

    @Override
    public Task<Void> resetPassword(String email) {
        return firebase.getAuth().sendPasswordResetEmail(email);
    }

    @Override
    public String getCurrentUserUid() {
        return storage.getCurrentUserUid();
    }

    @Override
    public void updateCurrentUserUid() {
        storage.updateCurrentUserUid(firebase.getAuth().getCurrentUser().getUid());
    }

    @Override
    public void forgetUser() {
        firebase.getAuth().signOut();
        storage.forgetUser();
    }

    @Override
    public List<String> getQuestions() {
        List<String> questions = new ArrayList<>();
        questions.add("Чувствуете боли в груди?");
        questions.add("Имеются головные боли?");
        questions.add("Имеется ли отдышка?");
        questions.add("Артериальное давление в норме?");
        questions.add("Отеки имеются?");
        questions.add("Принимали лекарства?");
        questions.add("Физическая нагрузка была?");
        questions.add("Имеются вредные привычки?");
        questions.add("Была ли диета?");
        questions.add("Вы на правильном питании?");
        questions.add("У вас хороший сон?");
        questions.add("Бывали ли у вас обмороки?");
        questions.add("У вас учащенное сердцебиение?");
        return questions;
    }

    @Override
    public void saveResult(QuizResult result) {
        Map<String, String> answer = new HashMap<>();
        answer.put(RESULT, result.getName());
        String date = DateUtils.getFullDate();
        firebase.getQuizReference().child(storage.getCurrentUserUid()).child(date).setValue(answer);
        storage.updateLastQuizDate(date);
        storage.updateUserZone(result.getName());
        storage.setSkippedSurvey(false);
    }

    @Override
    public boolean needForSurvey() {
        if (storage.skippedSurvey()) {
            String skipDate = storage.getSkippedDate();
            int hours = getDifferenceFromDateInHours(skipDate);
            return hours >= 24;
        }

        String lastQuizDate = storage.getLastQuizDate();
        int hours = getDifferenceFromDateInHours(lastQuizDate);
        return hours >= 24;
    }

    public int getDifferenceFromDateInHours(String dateS) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = 0;
        if (date != null) {
            millis = date.getTime();
        }
        long difference = System.currentTimeMillis() - millis;
        return (int) (difference / 1000L / 60L / 60L);
    }

    @Override
    public QuizResult getCurrentZone() {
        String zoneName = storage.getCurrentZone();
        QuizResult[] values = QuizResult.values();
        for (QuizResult q : values) {
            if (q.getName().equals(zoneName))
                return q;
        }
        return null;
    }

    @Override
    public void skipSurvey() {
        storage.setSkippedDate(DateUtils.getFullDate());
        storage.setSkippedSurvey(true);
    }

    @Override
    public boolean skippedSurvey() {
        return storage.skippedSurvey();
    }

    @Override
    public boolean hasLinkedRelativePhone() {
        return storage.hasLinkedRelativePhone();
    }

    @Override
    public void connectRelatives(Relative relative1, Relative relative2) {
        Map<String, Relative> result = new HashMap<>();
        result.put(FIRST, relative1);
        result.put(SECOND, relative2);
        firebase.getRelativesReference().child(storage.getCurrentUserUid()).setValue(result);
        storage.setHasRelativePhone();
        storage.saveRelatives(relative1, relative2);
    }

    @Override
    public List<Relative> getRelatives() {
        return storage.getRelatives();
    }

    @Override
    public void updateUserName(String username) {
        storage.updateCurrentUserName(username);
    }

    @Override
    public String getUserName() {
        return storage.getCurrentUserName();
    }

    @Override
    public DatabaseReference getUserInfo() {
        return firebase.getUsersReference().child(storage.getCurrentUserUid());
    }

    @Override
    public void editProfile(User userInfo, BodyParameters parameters) {
        firebase.getUsersReference().child(storage.getCurrentUserUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String password = snapshot.getValue(User.class).getPassword();
                        userInfo.setPassword(password);
                        FirebaseUser user = firebase.getAuth().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
                        user.reauthenticate(credential)
                                .addOnCompleteListener(task -> {
                                    FirebaseUser firebaseUser = firebase.getAuth().getCurrentUser();
                                    firebaseUser.updateEmail(userInfo.getMail());
                                });
                        String key = storage.getCurrentUserUid();
                        firebase.getUsersReference().child(key).setValue(userInfo);
                        firebase.getBodyParametersReference().child(key).setValue(parameters);
                        storage.updateCurrentUserName(userInfo.getName() + " " + userInfo.getSurname());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    @Override
    public DatabaseReference getArticle(String id) {
        return firebase.getContentReference().child(id);
    }

    @Override
    public DatabaseReference getCategories(String name) {
        return firebase.getCategoriesReference().child(name);
    }

    @Override
    public Completable saveReminder(Reminder reminder) {
        return remindersDao.insertReminder(reminderMapper.toEntity(reminder));
    }

    @SuppressLint("NewApi")
    @Override
    public Observable<List<Reminder>> getReminders() {
        return remindersDao.getReminders()
                .map( reminderEntities -> reminderEntities.stream().map(reminderMapper::toDomain).collect(Collectors.toList()));
    }

    @Override
    public Single<Reminder> getReminderById(int id) {
        return remindersDao.getReminderById(id).map(reminderMapper::toDomain);
    }

    @SuppressLint("NewApi")
    @Override
    public Single<List<Reminder>> getRemindersByDate(int day, int month, int year) {
        return remindersDao.getRemindersByDate(day, month, year).map( reminderEntities -> reminderEntities.stream().map(reminderMapper::toDomain).collect(Collectors.toList()));
    }

    @Override
    public Completable updateReminder(Reminder reminder) {
        return remindersDao.updateReminder(reminderMapper.toEntity(reminder));
    }

    @Override
    public Completable deleteOldReminders() {
        long timeMillis = System.currentTimeMillis();
        return remindersDao.deleteRemindersOlderThan(timeMillis);
    }

    @Override
    public Task<Void> initUserIndicators() {
        Map<String, CommonIndicator> hashMap = new HashMap<>();
        for (IndicatorType indicatorType : IndicatorType.values()) {
            hashMap.put(String.valueOf(indicatorType.getName()), new CommonIndicator(indicatorType.getName(), "-", ""));
        }
        return firebase.getIndicatorsReference().child(getCurrentUserUid()).setValue(hashMap);
    }

    @Override
    public DatabaseReference getAllIndicators() {
        return firebase.getIndicatorsReference().child(getCurrentUserUid());
    }

    @Override
    public void updateImt(String imt) {
        firebase.getIndicatorsReference().child(getCurrentUserUid()).child(IndicatorType.IMT.getName()).child("currentValue").setValue(imt);
    }

    @Override
    public DatabaseReference getIndicatorsByName(String name) {
        return firebase.getIndicatorsReference().child(getCurrentUserUid()).child(name).child("allValues");
    }

    @Override
    public void saveIndicator(String name, HealthIndicator healthIndicator) {
        if (name.equals(IndicatorType.GROWTH.getName()))
            firebase.getBodyParametersReference().child(getCurrentUserUid()).child("growth").setValue(healthIndicator.getValue());
        if (name.equals(IndicatorType.WEIGHT.getName()))
            firebase.getBodyParametersReference().child(getCurrentUserUid()).child("weight").setValue(healthIndicator.getValue());
        firebase.getIndicatorsReference().child(getCurrentUserUid()).child(name).child("allValues").push().setValue(healthIndicator);
        firebase.getIndicatorsReference().child(getCurrentUserUid()).child(name).child("currentValue").setValue(healthIndicator.getValue());
    }

    @Override
    public DatabaseReference getBodyParameters() {
        return firebase.getBodyParametersReference().child(getCurrentUserUid());
    }

    @Override
    public void updateUserPassword(String newPassword) {
        firebase.getUsersReference().child(getCurrentUserUid()).child("password").setValue(newPassword);
    }
}
