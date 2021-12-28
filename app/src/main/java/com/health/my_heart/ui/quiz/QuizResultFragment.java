package com.health.my_heart.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentQuizResultBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QuizResultFragment extends Fragment {
    private QuizSharedViewModel viewModel;
    private FragmentQuizResultBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizSharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuizResultBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        observeViewModel();
        viewModel.getResult();
    }

    private void initListeners() {
        binding.quizResultContinueBtn.setOnClickListener(v -> requireActivity().finish());
    }

    private void observeViewModel() {
        viewModel.quizResult.observe(requireActivity(), result -> {
            switch (result) {
                case RED: {
                    setTitle(R.string.diagnosis_red_zone);
                    setTitleBackground(R.drawable.bg_red);
                    setDescription(R.string.red_zone_description);
                    break;
                }
                case YELLOW: {
                    setTitle(R.string.diagnosis_yellow_zone);
                    setTitleBackground(R.drawable.bg_yellow);
                    setDescription(R.string.yellow_zone_description);
                    break;
                }
                case GREEN: {
                    setTitle(R.string.diagnosis_green_zone);
                    setTitleBackground(R.drawable.bg_green);
                    setDescription(R.string.green_zone_description);
                    break;
                }
            }
        });
    }

    private void setTitle(@StringRes int diagnosis) {
        binding.quizResultDiagnosisTv.setText(diagnosis);
    }

    private void setTitleBackground(@DrawableRes int bg) {
        binding.quizResultDiagnosisTv.setBackgroundResource(bg);
    }

    private void setDescription(@StringRes int description) {
        binding.quizResultDescriptionTv.setText(description);
    }
}
