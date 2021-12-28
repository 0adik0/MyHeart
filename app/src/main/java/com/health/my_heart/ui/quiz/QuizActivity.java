package com.health.my_heart.ui.quiz;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.health.my_heart.databinding.ActivityQuizBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QuizActivity extends AppCompatActivity {
    private ActivityQuizBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
