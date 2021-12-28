package com.health.my_heart.ui.analyzes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentAnalyzeBinding;
import com.health.my_heart.utils.HelpFunctions;

public class AnalyzeFragment extends Fragment {
    private FragmentAnalyzeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HelpFunctions.showToast(requireContext(), R.string.razrabotka);
        return null;

    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
