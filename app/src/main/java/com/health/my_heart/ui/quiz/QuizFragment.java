package com.health.my_heart.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentQuizBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QuizFragment extends Fragment {
    private QuizSharedViewModel viewModel;
    private FragmentQuizBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizSharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(getLayoutInflater(), container, false);
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
        observeViewModel();
        initListeners();
    }

    private void observeViewModel() {
        viewModel.currentQuestionId.observe(requireActivity(), index -> {
            if (index <= QuizSharedViewModel.LAST_INDEX) {
                String q = viewModel.getQuestion(index);
                binding.question.setText(q);
            } else {
                openResults();
            }
        });
    }

    private void openResults() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_open_result);
    }

    private void initListeners() {
        binding.skip.setOnClickListener(v -> skipSurvey());
        binding.yes.setOnClickListener(v -> viewModel.setAnswer(true));
        binding.no.setOnClickListener(v -> viewModel.setAnswer(false));
    }

    private void skipSurvey() {
        viewModel.skipSurvey();
        requireActivity().finish();
    }
}