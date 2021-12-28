package com.health.my_heart.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.health.my_heart.R;
import com.health.my_heart.domain.model.Relative;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefsStorage {
    public static final String USER_NAME = "USER_NAME";
    public static final String UID_KEY = "UID_KEY";
    public static final String LAST_QUIZ_DATE_KEY = "LAST_QUIZ_KEY";
    public static final String CURRENT_ZONE = "CURRENT_ZONE";
    public static final String SKIP_SURVEY_KEY = "SKIP_SURVEY_KEY";
    public static final String SKIP_SURVEY_DATE_KEY = "SKIP_SURVEY_DATE_KEY";
    public static final String LINKED_PHONE_KEY = "LINKED_PHONE_KEY";
    public static final String RELATIVE_1 = "RELATIVE_1";
    public static final String RELATIVE_2 = "RELATIVE_2";
    private static final String TRUE = "true";
    private final SharedPreferences prefs;

    public SharedPrefsStorage(Context context) {
        prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void updateCurrentUserName(String username) {
        prefs.edit().putString(USER_NAME, username).apply();
    }

    public String getCurrentUserName() {
        return prefs.getString(USER_NAME, "");
    }

    public void updateCurrentUserUid(String uid) {
        prefs.edit().putString(UID_KEY, uid).apply();
    }

    public String getCurrentUserUid() {
        return prefs.getString(UID_KEY, "");
    }

    public void forgetUser() {
        prefs.edit().putString(UID_KEY, "").apply();
    }

    public String getLastQuizDate() {
        return prefs.getString(LAST_QUIZ_DATE_KEY, "");
    }

    public void updateLastQuizDate(String date) {
        prefs.edit().putString(LAST_QUIZ_DATE_KEY, date).apply();
    }

    public String getSkippedDate() {
        return prefs.getString(SKIP_SURVEY_DATE_KEY, "");
    }

    public void setSkippedDate(String date) {
        prefs.edit().putString(SKIP_SURVEY_DATE_KEY, date).apply();
    }

    public boolean skippedSurvey() {
        return prefs.getString(SKIP_SURVEY_KEY, "").equals("1");
    }

    public void setSkippedSurvey(boolean skipped) {
        if (skipped)
            prefs.edit().putString(SKIP_SURVEY_KEY, "1").apply();
        else
            prefs.edit().putString(SKIP_SURVEY_KEY, "0").apply();
    }

    public String getCurrentZone() {
        return prefs.getString(CURRENT_ZONE, "");
    }

    public void updateUserZone(String zone) {
        prefs.edit().putString(CURRENT_ZONE, zone).apply();
    }

    public void setHasRelativePhone() {
        prefs.edit().putString(LINKED_PHONE_KEY, TRUE).apply();
    }

    public boolean hasLinkedRelativePhone() {
        return prefs.getString(LINKED_PHONE_KEY, "").equals(SharedPrefsStorage.TRUE);
    }

    public void saveRelatives(Relative relative1, Relative relative2) {
        Gson gson = new Gson();
        if (!relative1.isEmpty()) {
            String json = gson.toJson(relative1);
            prefs.edit().putString(RELATIVE_1, json).apply();
        }
        if (!relative2.isEmpty()) {
            String json = gson.toJson(relative2);
            prefs.edit().putString(RELATIVE_2, json).apply();
        }
    }

    public List<Relative> getRelatives() {
        Gson gson = new Gson();
        List<Relative> relatives = new ArrayList<>();

        String json1 = prefs.getString(RELATIVE_1, "");
        if (!json1.isEmpty()) {
            Relative relative = gson.fromJson(json1, Relative.class);
            relatives.add(relative);
        }

        String json2 = prefs.getString(RELATIVE_2, "");
        if (!json2.isEmpty()) {
            Relative relative = gson.fromJson(json2, Relative.class);
            relatives.add(relative);
        }
        return relatives;
    }
}
