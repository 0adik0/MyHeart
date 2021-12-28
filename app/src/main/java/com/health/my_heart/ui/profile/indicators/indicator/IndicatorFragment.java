package com.health.my_heart.ui.profile.indicators.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.health.my_heart.databinding.FragmentIndicatorBinding;
import com.health.my_heart.domain.model.HealthIndicator;
import com.health.my_heart.domain.model.IndicatorType;
import com.health.my_heart.ui.profile.indicators.IndicatorsViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IndicatorFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    @Inject
    IndicatorsViewModel viewModel;
    private FragmentIndicatorBinding binding;
    private IndicatorAdapter adapter;
    private String indicatorName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIndicatorBinding.inflate(getLayoutInflater(), container, false);
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
        getSafeArgs();
        initUi();
        initRecyclerView();
        observeViewModel();
        initListeners();
        viewModel.getIndicatorsByName(indicatorName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            adapter.clearData();
            viewModel.getIndicatorsByName(indicatorName);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initUi() {
        if (indicatorName.equals(IndicatorType.IMT.getName())) {
            binding.indicatorAddBtn.setVisibility(View.GONE);
            viewModel.getBodyParameters();
        }
    }

    private void getSafeArgs() {
        if (getArguments() != null) {
            indicatorName = IndicatorFragmentArgs.fromBundle(getArguments()).getIndicatorName();
            setTitle();
        }
    }

    private void setTitle() {
        binding.indicatorTitleTv.setText(indicatorName);
    }

    private void initRecyclerView() {
        adapter = new IndicatorAdapter();
        binding.indicatorItemsRv.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.bodyParameters.observe(getViewLifecycleOwner(), bodyParameters -> {
            float weight = Float.parseFloat(bodyParameters.getWeight());
            float growth = Float.parseFloat(bodyParameters.getGrowth()) / 100;
            float imt = weight / (growth * growth);
            float roundedImt = (float) (Math.round(imt * 10.0) / 10.0);
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            Date date = new Date(System.currentTimeMillis());
            String formattedDate = formatter.format(date);
            adapter.add(new HealthIndicator(String.valueOf(roundedImt), formattedDate));
            viewModel.updateImt(String.valueOf(roundedImt));
        });
        viewModel.snapshotSpecificIndicators.observe(getViewLifecycleOwner(), dataSnapshot -> {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                String value = ds.child("value").getValue(String.class);
                String date = ds.child("date").getValue(String.class);
                adapter.add(new HealthIndicator(value, date));
            }
        });
    }

    private void initListeners() {
        binding.indicatorAddBtn.setOnClickListener(v -> openDialogAddIndicator());
    }

    private void openDialogAddIndicator() {
        AddIndicatorDialogFragment addIndicatorDialogFragment = new AddIndicatorDialogFragment(indicatorName);
        addIndicatorDialogFragment.setTargetFragment(this, REQUEST_CODE);
        addIndicatorDialogFragment.show(getParentFragmentManager(), AddIndicatorDialogFragment.class.getSimpleName());
    }
}