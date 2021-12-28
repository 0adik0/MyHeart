package com.health.my_heart.ui.register.registration;

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
import com.health.my_heart.databinding.FragmentStep2Binding;
import com.health.my_heart.domain.model.BodyParameters;
import com.health.my_heart.utils.EditTextDateMask;
import com.health.my_heart.utils.HelpFunctions;
import com.health.my_heart.utils.StringDecoder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment2 extends Fragment {
    @Inject
    RegistrationViewModel viewModel;

    private FragmentStep2Binding binding;
    private String mail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStep2Binding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mail = RegisterFragment2Args.fromBundle(getArguments()).getEmailAddress();

        InputFilter[] filters = {new InputFilter.LengthFilter(3)};
        binding.edRost.setFilters(filters);
        binding.edVes.setFilters(filters);
        binding.edDate.addTextChangedListener(new EditTextDateMask(binding.edDate));

        binding.button3.setOnClickListener(v -> {
            if (fieldsAreNotEmpty()) {
                String date = binding.edDate.getText().toString();
                String growth = binding.edRost.getText().toString();
                String weight = binding.edVes.getText().toString();
                BodyParameters bodyParameters = new BodyParameters(date, growth, weight);

                viewModel.saveBodyParameters(bodyParameters);
                openRegister3(v);
            } else {
                HelpFunctions.showToast(requireContext(), R.string.error_empty_fields);
            }
        });
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }

    private void openRegister3(View view) {
        RegisterFragment2Directions.ActionOpenRegisterFragment3 action = RegisterFragment2Directions.actionOpenRegisterFragment3(StringDecoder.encodeEMail(mail));
        Navigation.findNavController(view).navigate(action);
    }

    private boolean fieldsAreNotEmpty() {
        return !binding.edDate.getText().toString().isEmpty() &&
                !binding.edRost.getText().toString().isEmpty() &&
                !binding.edVes.getText().toString().isEmpty();
    }
}
