package com.health.my_heart.ui.profile.indicators.indicator;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.domain.model.HealthIndicator;

import java.util.ArrayList;
import java.util.List;

public class IndicatorAdapter extends RecyclerView.Adapter<IndicatorViewHolder> {
    private ArrayList<HealthIndicator> data;

    public IndicatorAdapter() {
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public IndicatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return IndicatorViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull IndicatorViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<HealthIndicator> indicators) {
        data.clear();
        data.addAll(indicators);
        notifyDataSetChanged();
    }

    public void add(HealthIndicator value) {
        data.add(value);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }
}
