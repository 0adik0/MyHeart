package com.health.my_heart.worker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.health.my_heart.R;
import com.health.my_heart.ui.LaunchActivity;

public class NotifyWork extends Worker {
    public static final String NOTIFICATION_SURVEY_ID = "NOTIFICATION_SURVEY_ID";
    public static final String NOTIFICATION_IS_SURVEY_KEY = "NOTIFICATION_IS_SURVEY_KEY";
    public static final String NOTIFICATION_CONTENT_TEXT_KEY = "NOTIFICATION_CONTENT_TEXT_KEY";
    public static final String NOTIFICATION_DETAIL_TEXT_KEY = "NOTIFICATION_DETAIL_TEXT_KEY";

    public NotifyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        boolean isSurveyNotify = getInputData().getBoolean(NOTIFICATION_IS_SURVEY_KEY, false);
        if (isSurveyNotify) {
            sendNotification("Опрос", "Пожалуйста, пройдите регулярный опрос");
        } else {
            String contentText = getInputData().getString(NOTIFICATION_CONTENT_TEXT_KEY);
            String detailText = getInputData().getString(NOTIFICATION_DETAIL_TEXT_KEY);
            sendNotification(contentText, detailText);
        }
        return Result.success();
    }

    private void sendNotification(String contentText, String detailText) {
        Intent intent = new Intent(getApplicationContext(), LaunchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(getApplicationContext().getString(R.string.default_notification_channel_id), "Rewards Notifications", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setVibrationPattern(new long[]{0, 500, 200, 500, 200, 500, 200});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), getApplicationContext().getString(R.string.default_notification_channel_id))
                .setContentTitle(contentText)
                .setContentText(detailText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(detailText))
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_alarm)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_MAX);

        Notification notification = notificationBuilder.build();
        //unique notification id
        int notificationId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        notificationManager.notify(notificationId, notification);
    }
}
