package com.health.my_heart.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.health.my_heart.R;
import com.health.my_heart.databinding.DialogConnectRelativesBinding;
import com.health.my_heart.utils.HelpFunctions;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class ConnectRelativesDialogFragment extends DialogFragment {
    public static final String PHONE1_KEY = "PHONE1_KEY";
    public static final String PHONE2_KEY = "PHONE2_KEY";
    public static final String FIO1_KEY = "FIO1_KEY";
    public static final String FIO2_KEY = "FIO2_KEY";
    public static final int REQUEST_CODE = 2;
    private DialogConnectRelativesBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogConnectRelativesBinding.inflate(getLayoutInflater(), container, false);
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
        initPhoneFormatting();
        initListeners();
    }

    private void initPhoneFormatting() {
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher watcher1 = new MaskFormatWatcher(mask);
        FormatWatcher watcher2 = new MaskFormatWatcher(mask);
        watcher1.installOn(binding.dialogConnectPhone1Et);
        watcher2.installOn(binding.dialogConnectPhone2Et);
    }

    private void initListeners() {
        binding.dialogConnectSubmitBtn.setOnClickListener(v -> {
            if (fieldsAreNotEmpty()) {
                Intent intent = new Intent();
                intent.putExtra(PHONE1_KEY, binding.dialogConnectPhone1Et.getText().toString());
                intent.putExtra(PHONE2_KEY, binding.dialogConnectPhone2Et.getText().toString());
                intent.putExtra(FIO1_KEY, binding.dialogConnectFio1Et.getText().toString());
                intent.putExtra(FIO2_KEY, binding.dialogConnectFio2Et.getText().toString());
                getTargetFragment().onActivityResult(
                        getTargetRequestCode(), REQUEST_CODE, intent);
                dismiss();
            } else {
                HelpFunctions.showToast(requireContext(), R.string.error_no_relatives_data);
            }
        });
    }

    private boolean fieldsAreNotEmpty() {
        return (binding.dialogConnectFio1Et.getText().toString().isEmpty() && binding.dialogConnectPhone1Et.getText().toString().isEmpty()) ||
                (binding.dialogConnectFio2Et.getText().toString().isEmpty() && binding.dialogConnectPhone2Et.getText().toString().isEmpty());
    }
}
