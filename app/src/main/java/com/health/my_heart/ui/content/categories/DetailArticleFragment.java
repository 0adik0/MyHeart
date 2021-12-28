package com.health.my_heart.ui.content.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentDetailArticleBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailArticleFragment extends Fragment {
    @Inject
    ArticlesViewModel viewModel;
    private FragmentDetailArticleBinding binding;
    private String articleTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleTitle = DetailArticleFragmentArgs.fromBundle(getArguments()).getArticleTitle();
        String id = DetailArticleFragmentArgs.fromBundle(getArguments()).getRefId();
        viewModel.loadArticle(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailArticleBinding.inflate(getLayoutInflater(), container, false);
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
        initUi();
        observeViewModel();
    }

    private void initUi() {
        binding.detailArticleTitleTv.setText(articleTitle);
        binding.detailArticleWebView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_100));
    }

    private void observeViewModel() {
        viewModel.article.observe(getViewLifecycleOwner(), text -> binding.detailArticleWebView.loadDataWithBaseURL(null, text, "text/html", "utf-8", null));
    }
}
