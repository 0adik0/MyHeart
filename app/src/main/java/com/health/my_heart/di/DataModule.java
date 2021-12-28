package com.health.my_heart.di;

import android.content.Context;

import com.health.my_heart.data.FirebaseHelper;
import com.health.my_heart.data.RepositoryImpl;
import com.health.my_heart.data.SharedPrefsStorage;
import com.health.my_heart.data.db.MyHeartDatabase;
import com.health.my_heart.data.db.dao.RemindersDao;
import com.health.my_heart.domain.mapper.ReminderMapper;
import com.health.my_heart.domain.repository.Repository;
import com.health.my_heart.utils.DateUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {
    @Singleton
    @Provides
    public static Repository provideRepository(FirebaseHelper firebase, SharedPrefsStorage storage, RemindersDao remindersDao, ReminderMapper reminderMapper) {
        return new RepositoryImpl(firebase, storage, remindersDao, reminderMapper);
    }

    @Singleton
    @Provides
    public static SharedPrefsStorage provideStorage(@ApplicationContext Context appContext) {
        return new SharedPrefsStorage(appContext);
    }

    @Singleton
    @Provides
    public static MyHeartDatabase provideDb(@ApplicationContext Context appContext) {
        return MyHeartDatabase.create(appContext);
    }

    @Provides
    public static RemindersDao provideReminderDao(MyHeartDatabase db) {
        return db.getRemindersDao();
    }

    @Provides
    public static ReminderMapper provideReminderMapper(DateUtils dateUtils) {
        return new ReminderMapper(dateUtils);
    }

    @Provides
    public static DateUtils provideDateUtils() {
        return new DateUtils();
    }

    @Provides
    public static FirebaseHelper provideFirebase() {
        return new FirebaseHelper();
    }
}
