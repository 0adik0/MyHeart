package com.health.my_heart.ui.register.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentStep3Binding;
import com.health.my_heart.domain.model.Diseases;
import com.health.my_heart.utils.HelpFunctions;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment3 extends Fragment {
    @Inject
    RegistrationViewModel viewModel;

    private FragmentStep3Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStep3Binding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        String mail = RegisterFragment3Args.fromBundle(getArguments()).getEmailAddress();

        binding.btnReg3.setOnClickListener(v -> {
            Diseases diseases = getSelectedDiseases();
            viewModel.saveDiseases(diseases);
            viewModel.forgetUser();
            openLogin();
        });
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }

    private void openLogin() {
        HelpFunctions.showToast(requireContext(), R.string.successful_registration);
        requireActivity().finish();
    }

    private Diseases getSelectedDiseases() {
        Diseases diseases = new Diseases(
                binding.checkBox1.isChecked(),
                binding.checkBox2.isChecked(),
                binding.checkBox3.isChecked(),
                binding.checkBox4.isChecked(),
                binding.checkBox5.isChecked(),
                binding.checkBox6.isChecked()
        );
        if (binding.checkBox7.isChecked()) {
            diseases.setOtherFormsChronicIschemicHeartDisease(true);
            diseases.setOther(binding.editTextTextMultiLine.getText().toString());
        }
        return diseases;
    }
}
