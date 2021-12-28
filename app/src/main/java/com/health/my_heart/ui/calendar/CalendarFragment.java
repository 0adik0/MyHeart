package com.health.my_heart.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentCalendarBinding;
import com.health.my_heart.ui.adapter.RemindersAdapter;
import com.health.my_heart.utils.HelpFunctions;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CalendarFragment extends Fragment implements OnReminderEditClickListener {
    private static final int REQUEST_CODE = 1;
    @Inject
    CalendarViewModel viewModel;
    private FragmentCalendarBinding binding;
    private RemindersAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(getLayoutInflater(), container, false);
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
        initRecyclerView();
        observeViewModel();
        getReminders();
        initListeners();
    }

    private void getReminders() {
        Calendar calendar = Calendar.getInstance();
        viewModel.getRemindersByDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            Calendar calendar = Calendar.getInstance();
            new Handler(Looper.getMainLooper()).postDelayed(() -> viewModel.getRemindersByDate(
                    calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)),
                    100);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onReminderEditClickListener(int reminderId) {
        showDialogAddAlarm(reminderId);
    }

    private void initRecyclerView() {
        adapter = new RemindersAdapter(true, this);
        binding.calendarRv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.calendarRv.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.remindersResult.observe(getViewLifecycleOwner(), result -> {
            switch (result.getEvent()) {
                case SUCCESS: {
                    adapter.setData(result.getData());
                    break;
                }
                case ERROR: {
                    result.getThrowable().printStackTrace();
                    HelpFunctions.showToast(requireContext(), result.getThrowable().getMessage());
                    break;
                }
            }
        });
        viewModel.saveResult.observe(getViewLifecycleOwner(), result -> {
            switch (result.getEvent()) {
                case SUCCESS: {
                    HelpFunctions.showToast(requireContext(), R.string.reminder_successful_save);
                    viewModel.getReminders();
                    break;
                }
                case ERROR: {
                    HelpFunctions.showToast(requireContext(), result.getThrowable().getMessage());
                    break;
                }
            }
        });
    }

    private void initListeners() {
        binding.calendarAddBtn.setOnClickListener(v -> showDialogAddAlarm(0));
        binding.calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> viewModel.getRemindersByDate(dayOfMonth, month, year));
    }

    private void showDialogAddAlarm(int id) {
        AddAlarmDialogFragment addAlarmDialogFragment = new AddAlarmDialogFragment(id);
        addAlarmDialogFragment.setTargetFragment(this, REQUEST_CODE);
        addAlarmDialogFragment.show(getParentFragmentManager(), AddAlarmDialogFragment.class.getSimpleName());
    }
}
