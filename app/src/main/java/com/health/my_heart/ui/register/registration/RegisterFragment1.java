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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentStep1Binding;
import com.health.my_heart.domain.model.User;
import com.health.my_heart.utils.HelpFunctions;
import com.health.my_heart.utils.StringDecoder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment1 extends Fragment {
    @Inject
    RegistrationViewModel viewModel;
    private FragmentStep1Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStep1Binding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InputFilter[] innFilters = { new InputFilter.LengthFilter(12) };
        binding.edInn.setFilters(innFilters);

        InputFilter[] length40Filter = { new InputFilter.LengthFilter(40) };
        binding.edName.setFilters(length40Filter);
        binding.edFam.setFilters(length40Filter);
        binding.edMail.setFilters(length40Filter);
        binding.edPas.setFilters(length40Filter);
        binding.edPas2.setFilters(length40Filter);

        binding.btnReg.setOnClickListener(v -> {
            if (fieldsAreNotEmpty()) {
                if (samePasswords()) {
                    insertDataReg();
                } else {
                    HelpFunctions.showToast(requireContext(), R.string.error_passwords_are_not_same);
                }
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

    private void insertDataReg() {
        String inn = binding.edInn.getText().toString();
        String name = binding.edName.getText().toString();
        String surname = binding.edFam.getText().toString();
        String email = binding.edMail.getText().toString();
        String password = binding.edPas.getText().toString();

        viewModel.createUser(email, password)
                .addOnCompleteListener(requireActivity(), (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        User userData = new User(inn, name, surname, email, password);
                        viewModel.saveUser(userData);
                        openRegister2();
                    } else {
                        HelpFunctions.showToast(requireContext(), R.string.unknown_error);
                    }
                });
    }

    private void openRegister2() {
        String mail = binding.edMail.getText().toString();
        RegisterFragment1Directions.ActionOpenRegisterFragment2 action = RegisterFragment1Directions.actionOpenRegisterFragment2(StringDecoder.encodeEMail(mail));
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    private boolean samePasswords() {
        return binding.edPas.getText().toString().equals(binding.edPas2.getText().toString());
    }

    private boolean fieldsAreNotEmpty() {
        return !binding.edInn.getText().toString().isEmpty() &&
                !binding.edName.getText().toString().isEmpty() &&
                !binding.edFam.getText().toString().isEmpty() &&
                !binding.edMail.getText().toString().isEmpty() &&
                !binding.edPas.getText().toString().isEmpty() &&
                !binding.edPas2.getText().toString().isEmpty();
    }
}
