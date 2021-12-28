package com.health.my_heart.ui.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.health.my_heart.databinding.FragmentDetailFoodRecommendationsBinding;

public class FragmentDetailFoodRecommendations extends Fragment {
    private FragmentDetailFoodRecommendationsBinding binding;
    private int imageRes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageRes = FragmentDetailFoodRecommendationsArgs.fromBundle(getArguments()).getImageRes();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailFoodRecommendationsBinding.inflate(inflater, container, false);
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
        binding.detailFoodIv.setImageDrawable(ContextCompat.getDrawable(requireContext(), imageRes));
    }
}
