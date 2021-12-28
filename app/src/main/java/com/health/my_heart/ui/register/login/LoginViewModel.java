package com.health.my_heart.ui.register.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.health.my_heart.domain.model.Result;
import com.health.my_heart.domain.model.User;
import com.health.my_heart.domain.repository.Repository;

import javax.inject.Inject;

public class LoginViewModel {
    MutableLiveData<Result<User>> userLogin;
    private final Repository repository;

    @Inject
    public LoginViewModel(Repository repository) {
        this.repository = repository;
        userLogin = new MutableLiveData<>();
    }

    public void login(String email, String password) {
        userLogin.postValue(Result.loading());
        repository.login(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        repository.getUserInfo().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                if (user != null) {
                                    if (!user.getPassword().equals(password))
                                        repository.updateUserPassword(password);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        userLogin.postValue(Result.success(null));
                    } else
                        userLogin.postValue(Result.error(null));
                });
    }

    public void saveUser() {
        repository.updateCurrentUserUid();
    }
}
