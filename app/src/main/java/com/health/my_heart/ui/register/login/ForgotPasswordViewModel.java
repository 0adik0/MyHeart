package com.health.my_heart.ui.register.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.health.my_heart.domain.repository.Repository;

import javax.inject.Inject;

public class ForgotPasswordViewModel extends ViewModel {
    public MutableLiveData<Boolean> sendResult = new MutableLiveData<>();
    private final Repository repository;

    @Inject
    public ForgotPasswordViewModel(Repository repository) {
        this.repository = repository;
    }

    public void resetPassword(String email) {
        repository.resetPassword(email).addOnCompleteListener(task -> {
            sendResult.setValue(task.isSuccessful());
        });
    }
}
