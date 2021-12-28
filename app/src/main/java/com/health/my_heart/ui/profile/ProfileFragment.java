package com.health.my_heart.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentProfileBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    @Inject
    ProfileViewModel viewModel;
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewModel();
        initListeners();
    }

    private void observeViewModel() {
        viewModel.userName.observe(getViewLifecycleOwner(), username -> {
            binding.profileUserNameTv.setText(username);
            binding.profileUserIndicatorsBtn.setText(username);
        });
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }

    private void initListeners() {
        binding.profileEditBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_edit_profile));
        binding.profileUserIndicatorsBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_open_indicators));
    }
}
