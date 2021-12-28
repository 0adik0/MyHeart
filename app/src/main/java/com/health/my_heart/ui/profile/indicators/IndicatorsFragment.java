package com.health.my_heart.ui.profile.indicators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.health.my_heart.databinding.FragmentAllIndicatorsBinding;
import com.health.my_heart.domain.model.CommonIndicator;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IndicatorsFragment extends Fragment implements OnIndicatorGetMoreClickListener {
    @Inject
    IndicatorsViewModel viewModel;
    private FragmentAllIndicatorsBinding binding;
    private IndicatorsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAllIndicatorsBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        observeViewModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clearData();
        viewModel.getAllIndicators();
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }

    @Override
    public void onIndicatorGetMoreClickListener(CommonIndicator indicator) {
        IndicatorsFragmentDirections.ActionOpenMore actionOpenMore = IndicatorsFragmentDirections.actionOpenMore(indicator.getName());
        Navigation.findNavController(binding.getRoot()).navigate(actionOpenMore);
    }

    private void initRecyclerView() {
        adapter = new IndicatorsAdapter(this);
        binding.indicatorsItemsRv.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.snapshotAllIndicators.observe(getViewLifecycleOwner(), dataSnapshot -> {
            if (adapter.getItemCount() == 0)
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String currentValue = ds.child("currentValue").getValue(String.class);
                    adapter.add(new CommonIndicator(name, currentValue, ""));
                }
        });
    }
}
