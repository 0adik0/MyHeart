package com.health.my_heart.ui.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.health.my_heart.R;
import com.health.my_heart.databinding.FragmentHomeBinding;
import com.health.my_heart.domain.model.QuizResult;
import com.health.my_heart.domain.model.Relative;
import com.health.my_heart.ui.adapter.RemindersAdapter;
import com.health.my_heart.ui.quiz.QuizActivity;
import com.health.my_heart.utils.HelpFunctions;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.health.my_heart.ui.main.MainActivity.RC_CALL_PHONE;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    public static final int REQUEST_CODE = 1;
    @Inject
    HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private RemindersAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
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
        initRecyclerView();
        observeVm();
        initListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        initUi();
        adapter.clearData();
        viewModel.getTodayReminders();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == ConnectRelativesDialogFragment.REQUEST_CODE) {
            Relative relative1 = new Relative(data.getStringExtra(ConnectRelativesDialogFragment.FIO1_KEY), data.getStringExtra(ConnectRelativesDialogFragment.PHONE1_KEY));
            Relative relative2 = new Relative(data.getStringExtra(ConnectRelativesDialogFragment.FIO2_KEY), data.getStringExtra(ConnectRelativesDialogFragment.PHONE2_KEY));
            viewModel.connectRelatives(relative1, relative2);
            HelpFunctions.showToast(requireContext(), R.string.successful_save);
            binding.homeLinkRelativesNumberBtn.setVisibility(View.GONE);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void initUi() {
        setDiagnosis();
        if (viewModel.skippedSurvey())
            binding.homeQuizTv.setVisibility(View.VISIBLE);
        else
            binding.homeQuizTv.setVisibility(View.GONE);

        if (!viewModel.linkedRelativesNumber())
            binding.homeLinkRelativesNumberBtn.setVisibility(View.VISIBLE);
        else
            binding.homeLinkRelativesNumberBtn.setVisibility(View.GONE);
    }

    private void setDiagnosis() {
        QuizResult result = viewModel.getCurrentUserZone();
        if (result != null) {
            switch (result) {
                case GREEN: {
                    setTitle(result.getName());
                    setSubtitle(R.string.green_zone_subtitle);
                    setTitleBackground(R.drawable.bg_green);
                    setInfo(R.string.info_desc1_green, false);
                    break;
                }
                case YELLOW: {
                    setTitle(result.getName());
                    setSubtitle(R.string.yellow_zone_subtitle);
                    setTitleBackground(R.drawable.bg_yellow);
                    setInfo(R.string.info_desc1_yellow, false);
                    break;
                }
                case RED: {
                    setTitle(result.getName());
                    setSubtitle(R.string.red_zone_subtitle);
                    setTitleBackground(R.drawable.bg_red);
                    setInfo(R.string.info_desc1_red, true);
                    break;
                }
            }
        } else {
            setTitle(QuizResult.GREEN.getName());
            setSubtitle(R.string.green_zone_subtitle);
            setTitleBackground(R.drawable.bg_green);
            setInfo(R.string.info_desc1_green, false);
        }
    }

    private void setInfo(@StringRes int infoDesc, boolean isVisibleInfo) {
        binding.info1.setText(infoDesc);
        if (!isVisibleInfo) {
            binding.info2Title.setVisibility(View.GONE);
            binding.infoPic.setVisibility(View.GONE);
            binding.info2.setVisibility(View.GONE);
        } else {
            binding.info2Title.setVisibility(View.VISIBLE);
            binding.infoPic.setVisibility(View.VISIBLE);
            binding.info2.setVisibility(View.VISIBLE);
        }
    }

    private void setTitle(String diagnosis) {
        binding.homeUserZoneTitleTv.setText(diagnosis);
    }

    private void setSubtitle(@StringRes int subtitle) {
        binding.homeUserZoneSubTitleTv.setText(subtitle);
    }

    private void setTitleBackground(@DrawableRes int bg) {
        binding.homeUserZoneLl.setBackgroundResource(bg);
    }

    private void initRecyclerView() {
        adapter = new RemindersAdapter(false, null);
        binding.homeRemindersRv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.homeRemindersRv.setAdapter(adapter);
    }

    private void observeVm() {
        viewModel.isShowInfo.observe(getViewLifecycleOwner(), show -> {
            if (show)
                binding.homeContentInfo.setVisibility(View.VISIBLE);
            else
                binding.homeContentInfo.setVisibility(View.GONE);
        });
        viewModel.remindersResult.observe(getViewLifecycleOwner(), result -> {
            switch (result.getEvent()) {
                case SUCCESS: {
                    adapter.addItem(result.getData());
                    break;
                }
                case ERROR: {
                    HelpFunctions.showToast(requireContext(), result.getThrowable().getMessage());
                    break;
                }
            }
        });
    }

    private void initListeners() {
        binding.homeSosBtn.setOnClickListener(v -> openSosDialog());
        binding.homeLinkRelativesNumberBtn.setOnClickListener(v -> showConnectRelativesDialog());
        binding.homeMoreInfoIv.setOnClickListener(v -> showUnshowInfo());
        binding.homeQuizTv.setOnClickListener(v -> openQuiz());
    }

    @AfterPermissionGranted(RC_CALL_PHONE)
    private void openSosDialog() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(requireContext(), perms)) {
            List<Relative> relatives = viewModel.getRelatives();
            new SosDialogFragment(relatives).show(getParentFragmentManager(), SosDialogFragment.class.getSimpleName());
        } else
            EasyPermissions.requestPermissions(requireActivity(),
                    getString(R.string.rationale_for_call_permission), RC_CALL_PHONE, perms);
    }

    private void showConnectRelativesDialog() {
        ConnectRelativesDialogFragment dialogFragment = new ConnectRelativesDialogFragment();
        dialogFragment.setTargetFragment(this, REQUEST_CODE);
        dialogFragment.show(getParentFragmentManager(), ConnectRelativesDialogFragment.class.getSimpleName());
    }

    private void showUnshowInfo() {
        viewModel.isShowInfo.postValue(!viewModel.isShowInfo.getValue());
    }

    private void openQuiz() {
        Intent intent = new Intent(requireActivity(), QuizActivity.class);
        startActivity(intent);
    }
}
