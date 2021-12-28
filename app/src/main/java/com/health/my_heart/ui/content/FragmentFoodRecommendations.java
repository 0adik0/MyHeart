package com.health.my_heart.ui.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentFoodRecommendationsBinding;

import java.util.ArrayList;

public class FragmentFoodRecommendations extends Fragment {
    private FragmentFoodRecommendationsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFoodRecommendationsBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        FoodAdapter adapter = new FoodAdapter(this);
//        binding.foodRv.setAdapter(adapter);
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(R.drawable.food1);
//        list.add(R.drawable.food2);
//        list.add(R.drawable.food3);
//        list.add(R.drawable.food4);
//        list.add(R.drawable.food5);
//        list.add(R.drawable.food6);
//        list.add(R.drawable.food7);
//        adapter.setData(list);
//    }
//
//    @Override
//    public void onFoodItemClickListener(int imgRes) {
//        FragmentFoodRecommendationsDirections.ActionOpenDetailFood actionOpenDetailFood = FragmentFoodRecommendationsDirections.actionOpenDetailFood(imgRes);
//        Navigation.findNavController(binding.getRoot()).navigate(actionOpenDetailFood);
//    }
}
