package com.health.my_heart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.health.my_heart.R;
import com.health.my_heart.data.SharedPrefsStorage;
import com.health.my_heart.ui.main.MainActivity;
import com.health.my_heart.ui.register.login.LoginActivity;
import com.health.my_heart.ui.register.registration.RegistrationActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LaunchActivity extends AppCompatActivity {
    @Inject
    SharedPrefsStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!storage.getCurrentUserUid().isEmpty())
            openMainScreen();
        setContentView(R.layout.activity_launch);
    }

    private void openMainScreen() {
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void btnReg(View view) {
        Intent intent = new Intent(LaunchActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void btnLog(View view) {
        Intent mySuperIntent = new Intent(LaunchActivity.this, LoginActivity.class);
        startActivity(mySuperIntent);
    }

}