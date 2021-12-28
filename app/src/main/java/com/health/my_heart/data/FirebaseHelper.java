package com.health.my_heart.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    public static final String USERS = "Users";
    public static final String BODY_PARAMETERS = "BodyParameters";
    public static final String DISEASES = "Diseases";
    public static final String QUIZ = "Quiz";
    public static final String RELATIVES = "Relatives";
    public static final String CONTENT = "Content";
    public static final String CATEGORIES = "Categories";
    public static final String INDICATORS = "Indicators";

    public FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public DatabaseReference getDbReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getUsersReference() {
        return getDbReference().child(USERS);
    }

    public DatabaseReference getBodyParametersReference() {
        return getDbReference().child(BODY_PARAMETERS);
    }

    public DatabaseReference getDiseasesReference() {
        return getDbReference().child(DISEASES);
    }

    public DatabaseReference getQuizReference() {
        return getDbReference().child(QUIZ);
    }

    public DatabaseReference getRelativesReference() {
        return getDbReference().child(RELATIVES);
    }

    public DatabaseReference getContentReference() {
        return getDbReference().child(CONTENT);
    }

    public DatabaseReference getCategoriesReference() {
        return getDbReference().child(CATEGORIES);
    }

    public DatabaseReference getIndicatorsReference() {
        return getDbReference().child(INDICATORS);
    }
}
