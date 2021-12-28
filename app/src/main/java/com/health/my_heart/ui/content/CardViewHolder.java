package com.health.my_heart.ui.content;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.health.my_heart.databinding.ItemArticleCardBinding;

public class CardViewHolder extends RecyclerView.ViewHolder {
    private ItemArticleCardBinding binding;

    public CardViewHolder(@NonNull ItemArticleCardBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public static CardViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemArticleCardBinding binding = ItemArticleCardBinding.inflate(inflater, parent, false);
        return new CardViewHolder(binding);
    }

    public void bind(ContentCard card, OnCardClickListener listener) {
        binding.itemCardTitleTv.setText(card.getTitle());
        Glide.with(binding.getRoot())
                .asBitmap()
                .load(card.getBgResId())
                .centerCrop()
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        binding.articleCardBg.setBackground(new BitmapDrawable(resource));
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        binding.getRoot().setOnClickListener(v -> listener.onCardClickListener(card));
    }
}