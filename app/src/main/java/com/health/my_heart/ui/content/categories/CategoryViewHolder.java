package com.health.my_heart.ui.content.categories;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.databinding.ItemCategoryBinding;
import com.health.my_heart.domain.model.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private ItemCategoryBinding binding;
    public CategoryViewHolder(@NonNull ItemCategoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public static CategoryViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater, parent, false);
        return new CategoryViewHolder(binding);
    }

    public void bind(Category category, OnCategoryClickListener listener) {
        binding.itemCategoryTitleTv.setText(category.getName());
        binding.getRoot().setOnClickListener(v -> listener.onCategoryClickListener(category));
    }
}
