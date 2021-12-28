package com.health.my_heart.ui.calendar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.databinding.ItemDrugBinding;

public class DrugViewHolder extends RecyclerView.ViewHolder {
    private final ItemDrugBinding binding;

    public DrugViewHolder(@NonNull ItemDrugBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public static DrugViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDrugBinding binding = ItemDrugBinding.inflate(inflater, parent, false);
        return new DrugViewHolder(binding);
    }

    public void bind(String drug, int position, OnTextChangedListener textChangedListener) {
        if (!drug.isEmpty())
            binding.itemDrugEt.setText(drug);
        binding.itemDrugTitle.setText("Название препарата " + (position + 1));
        binding.itemDrugEt.setHint("Диксоцилин, 100 мл");
        binding.itemDrugEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textChangedListener.onTextChangedListener(position, s.toString());
            }
        });
    }
}
