package com.health.my_heart.ui.register.registration;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.health.my_heart.databinding.ActivityRegisterBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegistrationActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
