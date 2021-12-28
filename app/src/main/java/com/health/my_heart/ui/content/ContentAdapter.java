package com.health.my_heart.ui.content;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private final ArrayList<ContentCard> data;
    private final OnCardClickListener listener;

    public ContentAdapter(OnCardClickListener listener) {
        data = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CardViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ContentCard> cards) {
        data.clear();
        data.addAll(cards);
        notifyDataSetChanged();
    }
}
