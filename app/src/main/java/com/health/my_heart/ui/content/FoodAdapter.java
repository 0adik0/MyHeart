package com.health.my_heart.ui.content;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.health.my_heart.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private final List<Integer> data = new ArrayList<>();
    private final OnFoodItemClickListener onFoodItemClickListener;

    FoodAdapter(OnFoodItemClickListener onFoodItemClickListener) {
        this.onFoodItemClickListener = onFoodItemClickListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_img, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.bind(data.get(position), onFoodItemClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Integer> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.foodItem_iv);
        }

        public void bind(int image, OnFoodItemClickListener onFoodItemClickListener) {
            Glide.with(iv.getContext())
                    .load(image)
                    .into(iv);
            iv.setOnClickListener(v -> onFoodItemClickListener.onFoodItemClickListener(image));
        }
    }
}
