package com.health.my_heart.ui.calendar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.health.my_heart.R;
import com.health.my_heart.databinding.DialogAddAlarmBinding;
import com.health.my_heart.domain.model.AlarmType;
import com.health.my_heart.domain.model.DayOfAWeek;
import com.health.my_heart.domain.model.Reminder;
import com.health.my_heart.ui.main.MainActivity;
import com.health.my_heart.utils.HelpFunctions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddAlarmDialogFragment extends DialogFragment implements OnTextChangedListener {
    public static final int REQUEST_CODE = 2;

    @Inject
    CalendarViewModel viewModel;
    private DialogAddAlarmBinding binding;
    private Calendar time;
    private ArrayList<DayOfAWeek> selectedDays;
    private List<Integer> checkedSwitches;
    private DrugsAdapter adapter;
    private final int id;

    public AddAlarmDialogFragment(int id) {
        this.id = id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen);
        time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, 7);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        selectedDays = new ArrayList<>();
        checkedSwitches = new ArrayList<>();
        selectedDays.add(DayOfAWeek.MONDAY);
        checkedSwitches.add(AlarmType.ALL_DAY.ordinal());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogAddAlarmBinding.inflate(getLayoutInflater(), container, false);
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
        viewModel.saveResult.getValue();
        initRecyclerView();
        initListeners();
        observeViewModel();
        requestReminder();
        initTimePicker();
        initWeekPicker();
        initSwitches();
    }

    @Override
    public void onTextChangedListener(int position, String s) {
        adapter.updateItem(position, s);
    }

    private void requestReminder() {
        if (id != 0)
            viewModel.getReminderById(id);
    }

    private void observeViewModel() {
        viewModel.reminderRequest.observe(getViewLifecycleOwner(), reminder -> {
            binding.dialogPickMondayTv.performClick();
            time.setTimeInMillis(reminder.getTimeMillis());
            setInitialDateTime();
            for (String drug : reminder.getDrugs())
                adapter.add(drug);
            switch (DayOfAWeek.values()[reminder.getDayOfAWeek()]) {
                case MONDAY: {
                    binding.dialogPickMondayTv.performClick();
                    break;
                }
                case TUESDAY: {
                    binding.dialogPickTuesdayTv.performClick();
                    break;
                }
                case WEDNESDAY: {
                    binding.dialogPickWednesdayTv.performClick();
                    break;
                }
                case THURSDAY: {
                    binding.dialogPickThursdayTv.performClick();
                    break;
                }
                case FRIDAY: {
                    binding.dialogPickFridayTv.performClick();
                    break;
                }
                case SATURDAY: {
                    binding.dialogPickSaturdayTv.performClick();
                    break;
                }
                case SUNDAY: {
                    binding.dialogPickSundayTv.performClick();
                    break;
                }
            }
            for (int typeOrdinal : reminder.getAlarmTypeOrdinals()) {
                switch (AlarmType.values()[typeOrdinal]) {
                    case ALL_DAY: {
                        binding.dialogAllDaySwitch.setChecked(true);
                        break;
                    }
                    case MORNING: {
                        binding.dialogMorningSwitch.setChecked(true);
                        break;
                    }
                    case AFTERNOON: {
                        binding.dialogAfternoonSwitch.setChecked(true);
                        break;
                    }
                    case EVENING: {
                        binding.dialogEveningSwitch.setChecked(true);
                        break;
                    }
                }
            }
        });

        viewModel.newReminder.observe(getViewLifecycleOwner(), reminder -> {
            String notificationContent = getString(R.string.dont_forget_to_take_pills);
            String notificationText = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationText = String.join("\n", adapter.getDrugs());
            }
            ((MainActivity) requireActivity()).scheduleNotification(String.valueOf(reminder.getId()), notificationContent,
                    notificationText, reminder.getTimeMillis());
        });
    }

    private void initRecyclerView() {
        adapter = new DrugsAdapter(this);
        binding.indicatorsItemsRv.setAdapter(adapter);
        if (id == 0)
            adapter.add("");
    }

    private void initListeners() {
        binding.submit.setOnClickListener(v -> saveReminder());
        binding.addIndicator.setOnClickListener(v -> adapter.add(""));
    }

    private void saveReminder() {
        if (!adapter.hasEmptyFields()) {
            Collections.sort(checkedSwitches);
            List<String> drugs = adapter.getDrugs();
            int todayDayOfWeek = time.get(Calendar.DAY_OF_WEEK);
            time.set(Calendar.SECOND, 0);
            long selectedTimeMillis = time.getTimeInMillis();
            for (DayOfAWeek dayOfAWeek : selectedDays) {
                if (dayOfAWeek.getKey() == todayDayOfWeek) {
                    if (time.getTimeInMillis() < System.currentTimeMillis()) {
                        time.add(Calendar.DAY_OF_MONTH, 7);
                        viewModel.saveReminder(new Reminder(id, "", "", "", time.getTimeInMillis(), dayOfAWeek.ordinal(), checkedSwitches, drugs));
                        time.add(Calendar.DAY_OF_MONTH, -7);
                    }
                    viewModel.saveReminder(new Reminder(id, "", "", "", time.getTimeInMillis(), dayOfAWeek.ordinal(), checkedSwitches, drugs));
                } else {
                    time.set(Calendar.DAY_OF_WEEK, dayOfAWeek.getKey());
                    if (time.getTimeInMillis() < System.currentTimeMillis()) {
                        time.add(Calendar.DAY_OF_MONTH, 7);
                    }
                    viewModel.saveReminder(new Reminder(id, "", "", "", time.getTimeInMillis(), dayOfAWeek.ordinal(), checkedSwitches, drugs));
                    time.setTimeInMillis(selectedTimeMillis);
                }
            }
            Intent intent = new Intent();
            getTargetFragment().onActivityResult(
                    getTargetRequestCode(), REQUEST_CODE, intent);
            dismiss();
        } else {
            HelpFunctions.showToast(requireContext(), R.string.error_empty_fields);
        }
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (view, hourOfDay, minute) -> {
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            time.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        };

        binding.timeP.setOnClickListener(v -> new TimePickerDialog(requireContext(), onTimeSetListener,
                time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE), true)
                .show());
    }

    private void setInitialDateTime() {
        binding.timeP.setText(DateUtils.formatDateTime(requireContext(),
                time.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
        binding.dialogAllDaySwitch.setChecked(false);
        binding.dialogMorningSwitch.setChecked(false);
        binding.dialogAfternoonSwitch.setChecked(false);
        binding.dialogEveningSwitch.setChecked(false);
    }

    private void initWeekPicker() {
        View.OnClickListener onPickerClickListener = v -> {
            int bgRes = 0;
            switch (v.getId()) {
                case R.id.dialog_pickMonday_tv: {
                    bgRes = getBgResId(DayOfAWeek.MONDAY);
                    break;
                }
                case R.id.dialog_pickTuesday_tv: {
                    bgRes = getBgResId(DayOfAWeek.TUESDAY);
                    break;
                }
                case R.id.dialog_pickWednesday_tv: {
                    bgRes = getBgResId(DayOfAWeek.WEDNESDAY);
                    break;
                }
                case R.id.dialog_pickThursday_tv: {
                    bgRes = getBgResId(DayOfAWeek.THURSDAY);
                    break;
                }
                case R.id.dialog_pickFriday_tv: {
                    bgRes = getBgResId(DayOfAWeek.FRIDAY);
                    break;
                }
                case R.id.dialog_pickSaturday_tv: {
                    bgRes = getBgResId(DayOfAWeek.SATURDAY);
                    break;
                }
                case R.id.dialog_pickSunday_tv: {
                    bgRes = getBgResId(DayOfAWeek.SUNDAY);
                    break;
                }
            }
            v.setBackgroundResource(bgRes);
        };

        binding.dialogPickMondayTv.setOnClickListener(onPickerClickListener);
        binding.dialogPickTuesdayTv.setOnClickListener(onPickerClickListener);
        binding.dialogPickWednesdayTv.setOnClickListener(onPickerClickListener);
        binding.dialogPickThursdayTv.setOnClickListener(onPickerClickListener);
        binding.dialogPickFridayTv.setOnClickListener(onPickerClickListener);
        binding.dialogPickSaturdayTv.setOnClickListener(onPickerClickListener);
        binding.dialogPickSundayTv.setOnClickListener(onPickerClickListener);
    }

    private int getBgResId(DayOfAWeek day) {
        int CHECKED_COLOR = R.color.purple_150;
        int UNCHECKED_COLOR = R.color.transparent;

        if (!selectedDays.contains(day)) {
            selectedDays.add(day);
            return CHECKED_COLOR;
        } else {
            selectedDays.remove(day);
            return UNCHECKED_COLOR;
        }
    }

    private void initSwitches() {
        if (id == 0)
            binding.dialogAllDaySwitch.setChecked(true);
        CompoundButton.OnCheckedChangeListener onSwitchChangeListener = (buttonView, isChecked) -> {
            if (binding.dialogMorningSwitch.isChecked() && binding.dialogAfternoonSwitch.isChecked() && binding.dialogEveningSwitch.isChecked()) {
                binding.dialogAllDaySwitch.setChecked(true);
                binding.dialogMorningSwitch.setChecked(false);
                binding.dialogAfternoonSwitch.setChecked(false);
                binding.dialogEveningSwitch.setChecked(false);
                checkedSwitches.add(AlarmType.ALL_DAY.ordinal());
                time.set(Calendar.HOUR_OF_DAY, 7);
                time.set(Calendar.MINUTE, 0);
            } else {
                switch (buttonView.getId()) {
                    case R.id.dialog_allDay_switch: {
                        if (isChecked) {
                            binding.dialogMorningSwitch.setChecked(false);
                            binding.dialogAfternoonSwitch.setChecked(false);
                            binding.dialogEveningSwitch.setChecked(false);
                            checkedSwitches.clear();
                            checkedSwitches.add(AlarmType.ALL_DAY.ordinal());
                            time.set(Calendar.HOUR_OF_DAY, 7);
                            time.set(Calendar.MINUTE, 0);
                        } else {
                            checkedSwitches.remove(Integer.valueOf(AlarmType.ALL_DAY.ordinal()));
                        }
                        break;
                    }
                    case R.id.dialog_morning_switch: {
                        if (isChecked) {
                            checkedSwitches.add(AlarmType.MORNING.ordinal());
                            binding.dialogAllDaySwitch.setChecked(false);
                            checkedSwitches.remove(Integer.valueOf(AlarmType.ALL_DAY.ordinal()));
                            time.set(Calendar.HOUR_OF_DAY, 7);
                            time.set(Calendar.MINUTE, 0);
                        } else {
                            checkedSwitches.remove(Integer.valueOf(AlarmType.MORNING.ordinal()));
                        }
                        break;
                    }
                    case R.id.dialog_afternoon_switch: {
                        if (isChecked) {
                            checkedSwitches.add(AlarmType.AFTERNOON.ordinal());
                            binding.dialogAllDaySwitch.setChecked(false);
                            checkedSwitches.remove(Integer.valueOf(AlarmType.ALL_DAY.ordinal()));
                            time.set(Calendar.HOUR_OF_DAY, 12);
                            time.set(Calendar.MINUTE, 0);
                        } else {
                            checkedSwitches.remove(Integer.valueOf(AlarmType.AFTERNOON.ordinal()));
                        }
                        break;
                    }
                    case R.id.dialog_evening_switch: {
                        if (isChecked) {
                            checkedSwitches.add(AlarmType.EVENING.ordinal());
                            binding.dialogAllDaySwitch.setChecked(false);
                            checkedSwitches.remove(Integer.valueOf(AlarmType.ALL_DAY.ordinal()));
                            time.set(Calendar.HOUR_OF_DAY, 18);
                            time.set(Calendar.MINUTE, 0);
                        } else {
                            checkedSwitches.remove(Integer.valueOf(AlarmType.EVENING.ordinal()));
                        }
                        break;
                    }
                }
            }
            binding.timeP.setText(DateUtils.formatDateTime(requireContext(),
                    time.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));
        };
        binding.dialogAllDaySwitch.setOnCheckedChangeListener(onSwitchChangeListener);
        binding.dialogMorningSwitch.setOnCheckedChangeListener(onSwitchChangeListener);
        binding.dialogAfternoonSwitch.setOnCheckedChangeListener(onSwitchChangeListener);
        binding.dialogEveningSwitch.setOnCheckedChangeListener(onSwitchChangeListener);
    }
}