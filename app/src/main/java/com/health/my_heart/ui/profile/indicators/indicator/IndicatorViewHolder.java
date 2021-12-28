package com.health.my_heart.ui.profile.indicators.indicator;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.databinding.ItemDetailHealthIndicatorBinding;
import com.health.my_heart.domain.model.HealthIndicator;

public class IndicatorViewHolder extends RecyclerView.ViewHolder {
    private ItemDetailHealthIndicatorBinding binding;

    public IndicatorViewHolder(@NonNull ItemDetailHealthIndicatorBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public static IndicatorViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDetailHealthIndicatorBinding binding = ItemDetailHealthIndicatorBinding.inflate(inflater, parent, false);
        return new IndicatorViewHolder(binding);
    }

    public void bind(HealthIndicator healthIndicator) {
        binding.indicatorValue.setText(healthIndicator.getValue());
        binding.indicatorDateTv.setText(healthIndicator.getDate());
    }
}
