package com.health.my_heart.ui.register.registration;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.health.my_heart.domain.model.BodyParameters;
import com.health.my_heart.domain.model.Diseases;
import com.health.my_heart.domain.model.User;
import com.health.my_heart.domain.repository.Repository;

import javax.inject.Inject;

public class RegistrationViewModel extends ViewModel {
    private final Repository repository;

    @Inject
    public RegistrationViewModel(Repository repository) {
        this.repository = repository;
    }

    public Task<AuthResult> createUser(String email, String password) {
        return repository.createUser(email, password);
    }

    public void saveUser(User user) {
        repository.saveUser(user);
    }

    public void saveBodyParameters(BodyParameters bodyParameters) {
        repository.saveBodyParameters(bodyParameters);
    }

    public void saveDiseases(Diseases diseases) {
        repository.saveDiseases(diseases);
    }

    public void forgetUser() {
        repository.forgetUser();
    }
}
