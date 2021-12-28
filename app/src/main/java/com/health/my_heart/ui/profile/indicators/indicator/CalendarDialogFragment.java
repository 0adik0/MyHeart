package com.health.my_heart.ui.profile.indicators.indicator;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.health.my_heart.R;
import com.health.my_heart.databinding.DialogCalendarBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarDialogFragment extends DialogFragment {
    private static final int REQUEST_CODE = 1;
    public static final String DATE_KEY = "DATE_KEY";
    private DialogCalendarBinding binding;
    private Calendar calendar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen);
        calendar = Calendar.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogCalendarBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        setInitialDateTime();
    }

    private void initListeners() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        };

        binding.calendarDialogTimeTv.setOnClickListener(v -> new TimePickerDialog(requireContext(), onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true)
                .show());

        binding.calendar.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        });

        binding.calendarDialogSubmitBtn.setOnClickListener(v -> passData());
    }

    private void setInitialDateTime() {
        binding.calendarDialogTimeTv.setText(DateUtils.formatDateTime(requireContext(),
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }

    private void passData() {
        Intent intent = new Intent();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        Date date = new Date(calendar.getTimeInMillis());
        String formattedDate = formatter.format(date);
        intent.putExtra(DATE_KEY, formattedDate);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(), REQUEST_CODE, intent);
        dismiss();
    }
}
