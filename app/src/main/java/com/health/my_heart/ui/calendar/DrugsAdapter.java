package com.health.my_heart.ui.calendar;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DrugsAdapter extends RecyclerView.Adapter<DrugViewHolder> {
    private final List<String> data;
    private final OnTextChangedListener textChangedListener;

    public DrugsAdapter(OnTextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public DrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DrugViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugViewHolder holder, int position) {
        holder.bind(data.get(position), position, textChangedListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(String s) {
        data.add(s);
        notifyDataSetChanged();
    }

    public void updateItem(int position, String s) {
        data.set(position, s);
    }

    public List<String> getDrugs() {
        return data;
    }

    public boolean hasEmptyFields() {
        for (String s : data) {
            if (s.trim().isEmpty())
                return true;
        }
        return false;
    }
}


