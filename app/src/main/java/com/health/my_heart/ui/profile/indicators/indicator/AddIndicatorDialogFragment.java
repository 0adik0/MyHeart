package com.health.my_heart.ui.profile.indicators.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.health.my_heart.R;
import com.health.my_heart.databinding.DialogAddIndicatorBinding;
import com.health.my_heart.domain.model.HealthIndicator;
import com.health.my_heart.domain.model.IndicatorType;
import com.health.my_heart.ui.profile.indicators.IndicatorsViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddIndicatorDialogFragment extends DialogFragment {
    private static final int REQUEST_CODE = 0;
    @Inject
    IndicatorsViewModel viewModel;
    private DialogAddIndicatorBinding binding;
    private final String indicatorName;

    public AddIndicatorDialogFragment(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogAddIndicatorBinding.inflate(getLayoutInflater(), container, false);
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
        initUi();
        initListeners();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            String date = data.getStringExtra(CalendarDialogFragment.DATE_KEY);
            binding.dialogIndicatorDateTv.setText(date);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initUi() {
        if (indicatorName.equals(IndicatorType.PULSE.getName()))
            binding.dialogIndicatorIndicator1Et.setHint("уд/мин");

        if (indicatorName.equals(IndicatorType.WEIGHT.getName()))
            binding.dialogIndicatorIndicator1Et.setHint("кг");

        if (indicatorName.equals(IndicatorType.SUGAR_URINE.getName()) ||
                indicatorName.equals(IndicatorType.CHOLESTEROL.getName()) ||
                indicatorName.equals(IndicatorType.SUGAR_BLOOD.getName()))
            binding.dialogIndicatorIndicator1Et.setHint("ммоль/л");

        if (indicatorName.equals(IndicatorType.XE_LEVEL.getName()))
            binding.dialogIndicatorIndicator1Et.setHint("хе");

        if (indicatorName.equals(IndicatorType.GROWTH.getName()))
            binding.dialogIndicatorIndicator1Et.setHint("см");

        if (indicatorName.equals(IndicatorType.TEMPERATURE.getName()))
            binding.dialogIndicatorIndicator1Et.setHint("температура");

        if (!indicatorName.equals(IndicatorType.PRESSURE.getName())) {
            binding.dialogIndicatorIndicator1Tv.setText(indicatorName);
            binding.dialogIndicatorIndicator2Tv.setVisibility(View.GONE);
            binding.dialogIndicatorIndicator2Et.setVisibility(View.GONE);
        } else {
            binding.dialogIndicatorIndicator1Tv.setText("АД верх.");
            binding.dialogIndicatorIndicator2Tv.setText("АД низжн.");
        }
    }

    private void initListeners() {
        binding.dialogIndicatorDateTv.setOnClickListener(v -> openCalendarDialog());
        binding.dialogIndicatorSubmitBtn.setOnClickListener(v -> {
            String value = binding.dialogIndicatorIndicator1Et.getText().toString();
            if (binding.dialogIndicatorIndicator2Et.getVisibility() == View.VISIBLE)
                value += "/" + binding.dialogIndicatorIndicator2Et.getText().toString();
            viewModel.saveIndicator(indicatorName, new HealthIndicator(value, binding.dialogIndicatorDateTv.getText().toString()));
            Intent intent = new Intent();
            getTargetFragment().onActivityResult(
                    getTargetRequestCode(), REQUEST_CODE, intent);
            dismiss();
        });
    }

    private void openCalendarDialog() {
        CalendarDialogFragment calendarDialogFragment = new CalendarDialogFragment();
        calendarDialogFragment.setTargetFragment(this, REQUEST_CODE);
        calendarDialogFragment.show(getParentFragmentManager(), CalendarDialogFragment.class.getSimpleName());
    }
}
