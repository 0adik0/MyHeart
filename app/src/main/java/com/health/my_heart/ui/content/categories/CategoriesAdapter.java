package com.health.my_heart.ui.content.categories;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.my_heart.domain.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private final ArrayList<Category> data;
    private final OnCategoryClickListener listener;

    public CategoriesAdapter(OnCategoryClickListener listener) {
        data = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CategoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Category> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void add(Category category) {
        data.add(category);
        notifyDataSetChanged();
    }
}
