package com.health.my_heart.ui.profile.indicators;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.databinding.ItemHealthIndicatorBinding;
import com.health.my_heart.domain.model.CommonIndicator;

public class HealthIndicatorViewHolder extends RecyclerView.ViewHolder {
    private ItemHealthIndicatorBinding binding;

    public HealthIndicatorViewHolder(@NonNull ItemHealthIndicatorBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public static HealthIndicatorViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHealthIndicatorBinding binding = ItemHealthIndicatorBinding.inflate(inflater, parent, false);
        return new HealthIndicatorViewHolder(binding);
    }

    public void bind(CommonIndicator indicator, OnIndicatorGetMoreClickListener listener) {
        binding.nameIndicator.setText(indicator.getName());
        binding.currStats.setText(binding.currStats.getText().toString() + indicator.getCurrentValue());
        binding.getRoot().setOnClickListener(v -> listener.onIndicatorGetMoreClickListener(indicator));
    }
}
