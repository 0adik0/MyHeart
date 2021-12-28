package com.health.my_heart.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.databinding.ItemReminderBinding;
import com.health.my_heart.domain.model.AlarmType;
import com.health.my_heart.domain.model.Reminder;

public class ReminderViewHolder extends RecyclerView.ViewHolder {
    private final ItemReminderBinding binding;
    private boolean isExpanded = false;

    public static ReminderViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemReminderBinding binding = ItemReminderBinding.inflate(inflater, parent, false);
        return new ReminderViewHolder(binding);
    }

    public ReminderViewHolder(@NonNull ItemReminderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Reminder reminder) {
        @SuppressLint({"NewApi", "LocalSuppress"})
        String drugs = String.join("\n", reminder.getDrugs());

        binding.remDate.setText(reminder.getDate());
        binding.remCount.setText(reminder.getDrugs().size() + " таблетки");
        binding.remTime.setText(reminder.getTime());
        if (reminder.getAlarmTypeOrdinals().size() > 0)
            binding.remType.setText(AlarmType.values()[reminder.getAlarmTypeOrdinals().get(0)].getName());
        binding.remYear.setText(reminder.getYear());
        if (reminder.getAlarmTypeOrdinals().size() > 0) {
            if (AlarmType.values()[reminder.getAlarmTypeOrdinals().get(0)] == AlarmType.ALL_DAY) {
                binding.remDrugs1.setVisibility(View.VISIBLE);
                binding.remMorning.setVisibility(View.VISIBLE);
                binding.remDrugs2.setVisibility(View.VISIBLE);
                binding.remAfternoon.setVisibility(View.VISIBLE);
                binding.remDrugs3.setVisibility(View.VISIBLE);
                binding.remEvening.setVisibility(View.VISIBLE);
                binding.remDrugs1.setText(drugs);
                binding.remDrugs2.setText(drugs);
                binding.remDrugs3.setText(drugs);
            } else {
                for (int i : reminder.getAlarmTypeOrdinals()) {
                    AlarmType type = AlarmType.values()[i];
                    switch (type) {
                        case MORNING: {
                            binding.remDrugs1.setVisibility(View.VISIBLE);
                            binding.remMorning.setVisibility(View.VISIBLE);
                            binding.remDrugs1.setText(drugs);
                            break;
                        }
                        case AFTERNOON: {
                            binding.remDrugs2.setVisibility(View.VISIBLE);
                            binding.remAfternoon.setVisibility(View.VISIBLE);
                            binding.remDrugs2.setText(drugs);
                            break;
                        }
                        case EVENING: {
                            binding.remDrugs3.setVisibility(View.VISIBLE);
                            binding.remEvening.setVisibility(View.VISIBLE);
                            binding.remDrugs3.setText(drugs);
                            break;
                        }

                    }
                }
            }
        } else {
            binding.remDrugs1.setVisibility(View.VISIBLE);
            binding.remDrugs1.setText(drugs);
        }
        binding.getRoot().setOnClickListener(v -> {
            if (isExpanded) {
                rotateArrowImage(90f, 0f);
                binding.remExpandable.collapse();
            } else {
                rotateArrowImage(0f, 90f);
                binding.remExpandable.expand(true);
            }
            isExpanded = !isExpanded;
        });
    }

    private void rotateArrowImage(float fromRotation, float toRotation) {
        final RotateAnimation rotateAnim = new RotateAnimation(
                fromRotation, toRotation, binding.remExpand.getWidth() / 2f, binding.remExpand.getHeight() / 2f);

        rotateAnim.setDuration(300);
        rotateAnim.setFillAfter(true); // Must be true or the animation will reset

        binding.remExpand.startAnimation(rotateAnim);
    }
}
