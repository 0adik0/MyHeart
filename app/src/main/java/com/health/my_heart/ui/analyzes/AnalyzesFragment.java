package com.health.my_heart.ui.analyzes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentAnalyzesBinding;
import com.health.my_heart.utils.HelpFunctions;

public class AnalyzesFragment extends Fragment {
    private FragmentAnalyzesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAnalyzesBinding.inflate(getLayoutInflater(), container, false);
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
    }

    private void initListeners() {
//        binding.analyzesSubmitBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_open_analyze));
        HelpFunctions.showToast(requireContext(), R.string.razrabotka);
    }
}
