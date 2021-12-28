package com.health.my_heart.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.health.my_heart.R;
import com.health.my_heart.domain.model.Relative;

import java.util.List;

public class SosDialogFragment extends DialogFragment {

    private final List<Relative> relatives;

    public SosDialogFragment(List<Relative> relatives) {
        this.relatives = relatives;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] items = new String[relatives.size() + 1];
        items[0] = "Скорая";
        int i = 1;
        for (Relative relative : relatives) {
            items[i] = relative.getFio();
            i++;
        }
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.sos)
                .setItems(items, (dialog, which) -> {
                    if (which == 0)
                        call("103");
                    else
                        call(relatives.get(--which).getPhone());
                })
                .create();
    }

    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }
}
