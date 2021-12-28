package com.health.my_heart.ui.register.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentForgotPasswordBinding;
import com.health.my_heart.utils.HelpFunctions;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgotPasswordFragment extends Fragment {
    @Inject
    public ForgotPasswordViewModel viewModel;
    private FragmentForgotPasswordBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(getLayoutInflater(), container, false);
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
    }

    private void initListeners() {
        binding.forgotPasswordSendBtn.setOnClickListener(v -> {
            if (binding.forgotPasswordMailEt.getText().toString().trim().isEmpty())
                HelpFunctions.showToast(requireContext(), getString(R.string.error_empty_fields));
            else
                viewModel.resetPassword(binding.forgotPasswordMailEt.getText().toString());
        });
    }

    private void observeViewModel() {
        viewModel.sendResult.observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                HelpFunctions.showToast(requireContext(), R.string.success_send_reset);
                Navigation.findNavController(binding.getRoot()).navigateUp();
            } else
                HelpFunctions.showToast(requireContext(), R.string.error_try_again);
        });
    }
}
