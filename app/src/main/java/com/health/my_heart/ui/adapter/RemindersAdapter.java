package com.health.my_heart.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.domain.model.Reminder;
import com.health.my_heart.ui.calendar.AlarmViewHolder;
import com.health.my_heart.ui.calendar.OnReminderEditClickListener;
import com.health.my_heart.ui.home.ReminderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_REMINDER = 0;
    private final int TYPE_EDITABLE_REMINDER = 1;
    private final List<Reminder> data;
    private final boolean isEditable;
    private final OnReminderEditClickListener onReminderEditClickListener;

    public RemindersAdapter(boolean isEditable, OnReminderEditClickListener onReminderEditClickListener) {
        this.isEditable = isEditable;
        this.onReminderEditClickListener = onReminderEditClickListener;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_REMINDER)
            return ReminderViewHolder.create(parent);
        else if (viewType == TYPE_EDITABLE_REMINDER)
            return AlarmViewHolder.create(parent);
        else
            throw new IllegalArgumentException("Unknown view type");
    }

    @Override
    public int getItemViewType(int position) {
        if (isEditable)
            return TYPE_EDITABLE_REMINDER;
        else
            return TYPE_REMINDER;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        if (type == TYPE_REMINDER)
            ((ReminderViewHolder)holder).bind(data.get(position));
        else
            ((AlarmViewHolder)holder).bind(data.get(position), onReminderEditClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Reminder> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addItem(Reminder reminder) {
        data.add(reminder);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }
}

