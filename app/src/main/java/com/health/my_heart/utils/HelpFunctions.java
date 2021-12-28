package com.health.my_heart.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class HelpFunctions {
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context,@StringRes int stringResource) {
        Toast.makeText(context, stringResource, Toast.LENGTH_SHORT).show();
    }
}
