package com.health.my_heart.ui.register.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentLoginBinding;
import com.health.my_heart.ui.main.MainActivity;
import com.health.my_heart.utils.HelpFunctions;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    @Inject
    LoginViewModel viewModel;
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        observeViewModel();
    }

    @Override
    public void onDestroy() {
        binding.loginSignInBtn.dispose();
        binding = null;
        super.onDestroy();
    }

    private void initListeners() {
        binding.loginForgotPasswordText.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_open_forgot_password));
        binding.loginSignInBtn.setOnClickListener(v -> {
            if (fieldsAreNotEmpty()) {
                String email = binding.loginMailEt.getText().toString();
                String password = binding.loginPasswordEt.getText().toString();
                viewModel.login(email, password);
            } else
                HelpFunctions.showToast(requireContext(), R.string.error_empty_fields);
        });
    }

    private boolean fieldsAreNotEmpty() {
        return !binding.loginMailEt.getText().toString().isEmpty() &&
                !binding.loginPasswordEt.getText().toString().isEmpty();
    }

    private void observeViewModel() {
        viewModel.userLogin.observe(getViewLifecycleOwner(), (result -> {
            switch (result.getEvent()) {
                case LOADING: {
                    showProgressBar();
                    break;
                }
                case SUCCESS: {
                    saveUser();
                    openMainMenu();
                    break;
                }
                case ERROR: {
                    showError();
                    break;
                }
            }
        }));
    }

    private void saveUser() {
        viewModel.saveUser();
    }

    private void showProgressBar() {
        binding.loginSignInBtn.startAnimation();
    }

    private void openMainMenu() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.loginSignInBtn.revertAnimation();
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }, 500);
    }

    private void showError() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.loginSignInBtn.revertAnimation();
            binding.loginSignInBtn.setDrawableBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_btn));
            HelpFunctions.showToast(requireContext(), R.string.error_wrong_login_or_password);
        }, 500);
    }
}