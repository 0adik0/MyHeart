package com.health.my_heart.ui.content.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentCategoriesBinding;
import com.health.my_heart.domain.model.Category;
import com.health.my_heart.domain.model.ContentType;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesFragment extends Fragment implements OnCategoryClickListener {
    @Inject
    ArticlesViewModel viewModel;
    private FragmentCategoriesBinding binding;
    private CategoriesAdapter adapter;
    private ContentType contentType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setTitle();
        initRecyclerView();
//        observeViewModel();
    }

    @Override
    public void onCategoryClickListener(Category category) {
        CategoriesFragmentDirections.ActionOpenDetailArticle actionOpenDetailArticle = CategoriesFragmentDirections.actionOpenDetailArticle(category.getName(), category.getId());
        Navigation.findNavController(binding.getRoot()).navigate(actionOpenDetailArticle);
    }

//    private void setTitle() {
//        contentType = CategoriesFragmentArgs.fromBundle(getArguments()).getContentType();
//        viewModel.loadCategories(contentType.getName());
//        binding.cateforiesTitleTv.setText(getString(R.string.categories_screen_title) + " " + contentType.getTitlePostfix());
//    }

    private void initRecyclerView() {
        adapter = new CategoriesAdapter(this);
        binding.categoriesRv.setAdapter(adapter);
    }
}

