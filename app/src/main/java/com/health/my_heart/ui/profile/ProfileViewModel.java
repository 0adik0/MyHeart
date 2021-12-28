package com.health.my_heart.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.health.my_heart.domain.model.User;
import com.health.my_heart.domain.repository.Repository;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    public MutableLiveData<String> userName;
    private final Repository repository;

    @Inject
    public ProfileViewModel(Repository repository) {
        this.repository = repository;
        userName = new MutableLiveData<>();
        getUserInfo();
    }

    void getUserInfo() {
        String userNamePrefs = repository.getUserName();
        if (userNamePrefs != null && !userNamePrefs.isEmpty())
            userName.postValue(userNamePrefs);
        else
            repository.getUserInfo().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userInfo = snapshot.getValue(User.class);
                    if (userInfo != null) {
                        String username = userInfo.getName() + " " + userInfo.getSurname();
                        userName.postValue(username);
                        repository.updateUserName(username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
    }
}
