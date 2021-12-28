package com.health.my_heart.ui.profile.indicators;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.domain.model.CommonIndicator;

import java.util.ArrayList;
import java.util.List;

public class IndicatorsAdapter extends RecyclerView.Adapter<HealthIndicatorViewHolder> {
    private ArrayList<CommonIndicator> data;
    private OnIndicatorGetMoreClickListener listener;

    public IndicatorsAdapter(OnIndicatorGetMoreClickListener listener) {
        this.listener = listener;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public HealthIndicatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HealthIndicatorViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthIndicatorViewHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<CommonIndicator> indicators) {
        data.clear();
        data.addAll(indicators);
        notifyDataSetChanged();
    }

    public void add(CommonIndicator commonIndicator) {
        data.add(commonIndicator);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }
}
