package com.health.my_heart.ui.profile.edit;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentEditProfileBinding;
import com.health.my_heart.domain.model.BodyParameters;
import com.health.my_heart.domain.model.User;
import com.health.my_heart.utils.EditTextDateMask;
import com.health.my_heart.utils.HelpFunctions;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditProfileFragment extends Fragment {
    @Inject
    EditProfileViewModel viewModel;
    private FragmentEditProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getUserInfo();
        viewModel.getBodyParameters();
        observeViewModel();
        initListeners();
    }

    private void observeViewModel() {
        viewModel.userInfo.observe(getViewLifecycleOwner(), user -> {
            binding.editProfileInnEt.setText(user.getInn());
            binding.editProfileNameEt.setText(user.getName());
            binding.editProfileSurnameEt.setText(user.getSurname());
            binding.editProfileEmailEt.setText(user.getMail());
        });

        viewModel.bodyInfo.observe(getViewLifecycleOwner(), bodyParameters -> {
            binding.editProfileBirthdayEt.setText(bodyParameters.getDate());
            binding.editProfileWeightEt.setText(bodyParameters.getWeight());
            binding.editProfileGrowthEt.setText(bodyParameters.getGrowth());
        });
    }

    private void initListeners() {
        InputFilter[] innFilters = {new InputFilter.LengthFilter(12)};
        binding.editProfileInnEt.setFilters(innFilters);

        InputFilter[] length20Filter = {new InputFilter.LengthFilter(20)};
        binding.editProfileNameEt.setFilters(length20Filter);
        binding.editProfileSurnameEt.setFilters(length20Filter);
        binding.editProfileEmailEt.setFilters(length20Filter);

        InputFilter[] filters = {new InputFilter.LengthFilter(3)};
        binding.editProfileGrowthEt.setFilters(filters);
        binding.editProfileWeightEt.setFilters(filters);

        binding.editProfileBirthdayEt.addTextChangedListener(new EditTextDateMask(binding.editProfileBirthdayEt));
        binding.editProfileBackBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.editProfileSaveBtn.setOnClickListener(v -> {
            if (fieldsAreNotEmpty()) {
                User user = new User();
                user.setInn(binding.editProfileInnEt.getText().toString());
                user.setName(binding.editProfileNameEt.getText().toString());
                user.setSurname(binding.editProfileSurnameEt.getText().toString());
                user.setMail(binding.editProfileEmailEt.getText().toString());

                BodyParameters parameters = new BodyParameters();
                parameters.setDate(binding.editProfileBirthdayEt.getText().toString());
                parameters.setGrowth(binding.editProfileGrowthEt.getText().toString());
                parameters.setWeight(binding.editProfileWeightEt.getText().toString());

                viewModel.saveChanges(user, parameters);
                HelpFunctions.showToast(requireContext(), R.string.successful_save);
                Navigation.findNavController(v).navigateUp();
            } else
                HelpFunctions.showToast(requireContext(), R.string.error_empty_fields);
        });
    }

    private boolean fieldsAreNotEmpty() {
        return !binding.editProfileInnEt.getText().toString().isEmpty() &&
                !binding.editProfileNameEt.getText().toString().isEmpty() &&
                !binding.editProfileSurnameEt.getText().toString().isEmpty() &&
                !binding.editProfileEmailEt.getText().toString().isEmpty() &&
                !binding.editProfileBirthdayEt.getText().toString().isEmpty() &&
                !binding.editProfileGrowthEt.getText().toString().isEmpty() &&
                !binding.editProfileWeightEt.getText().toString().isEmpty();
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
