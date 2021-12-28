package com.health.my_heart.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.health.my_heart.R;
import com.health.my_heart.databinding.ActivityMainBinding;
import com.health.my_heart.ui.quiz.QuizActivity;
import com.health.my_heart.worker.NotifyWork;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.EasyPermissions;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    MainViewModel viewModel;
    public static final int RC_CALL_PHONE = 0;
    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observeViewModel();
        viewModel.deleteOldReminders();
        if (needForQuiz())
            openQuiz();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.destination_profile,
                R.id.destination_calendar,
                R.id.destination_home,
                R.id.destination_articles,
                R.id.destination_analyzes,
                R.id.destination_edit_profile,
                R.id.destination_indicators,
                R.id.destination_indicator,
                R.id.destination_categories,
                R.id.destination_detail_article,
                R.id.destination_analyze,
                R.id.destination_food,
                R.id.destination_food_detail)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_main_fragment);
        setupToolbar();
        setupBottomNav();
        addListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void observeViewModel() {
        viewModel.newReminder.observe(this, reminder -> {
            String alarmText = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                alarmText = String.join("\n", reminder.getDrugs());
            }
            scheduleNotification(String.valueOf(reminder.getId()), getString(R.string.dont_forget_to_take_pills), alarmText, reminder.timeMillis);
        });
    }

    public void scheduleNotification(String id, String alarmContent, String alarmText, long timeMillis) {
        long delay = timeMillis - System.currentTimeMillis();
        Data data = new Data.Builder()
                .putString(NotifyWork.NOTIFICATION_CONTENT_TEXT_KEY, alarmContent)
                .putString(NotifyWork.NOTIFICATION_DETAIL_TEXT_KEY, alarmText)
                .build();
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(NotifyWork.class).setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build();
        WorkManager.getInstance(this).beginUniqueWork(id, ExistingWorkPolicy.REPLACE, work).enqueue();
    }

    private boolean needForQuiz() {
        return viewModel.needForQuiz();
    }

    private void notifyAboutSurvey(long timeInMillis) {
        long delay = timeInMillis - System.currentTimeMillis();
        Data data = new Data.Builder()
                .putBoolean(NotifyWork.NOTIFICATION_IS_SURVEY_KEY, true)
                .build();
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(NotifyWork.class).setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build();
        WorkManager.getInstance(this).beginUniqueWork(NotifyWork.NOTIFICATION_SURVEY_ID, ExistingWorkPolicy.REPLACE, work).enqueue();
    }

    private void openQuiz() {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void setupBottomNav() {
        NavigationUI.setupWithNavController(binding.mainBottomNav, navController);
    }

    private void addListeners() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.destination_home: {
//                    MenuItem item = binding.mainBottomNav.getMenu().findItem(R.id.action_home);
//                    item.setChecked(true);
                    break;
                }
                case R.id.destination_analyzes:
                case R.id.destination_articles:
                case R.id.destination_calendar:
                case R.id.destination_profile: {
                    break;
                }
                default: {

                    /*View itemView = binding.mainBottomNav.getChildAt(2);
                    ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                    layoutParams.height = dpToPx(32);
                    layoutParams.width = dpToPx(32);
                    itemView.setLayoutParams(layoutParams);*/
                }
            }
        });
    }

    public int dpToPx(int dp) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}
