package com.health.my_heart.ui.calendar;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.databinding.ItemAlarmBinding;
import com.health.my_heart.domain.model.AlarmType;
import com.health.my_heart.domain.model.Reminder;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private final ItemAlarmBinding binding;

    public static AlarmViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAlarmBinding binding = ItemAlarmBinding.inflate(inflater, parent, false);
        return new AlarmViewHolder(binding);
    }

    public AlarmViewHolder(@NonNull ItemAlarmBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Reminder reminder, OnReminderEditClickListener onReminderEditClickListener) {
        binding.date.setText(reminder.getDate());
        binding.count.setText(reminder.getDrugs().size() + " таблетки");
        binding.time.setText(reminder.getTime());
        if (reminder.getAlarmTypeOrdinals().size() > 0)
            binding.type.setText(AlarmType.values()[reminder.getAlarmTypeOrdinals().get(0)].getName());
        binding.year.setText(reminder.getYear());
        binding.alarmEditBtn.setOnClickListener(v -> onReminderEditClickListener.onReminderEditClickListener(reminder.getId()));
    }
}
