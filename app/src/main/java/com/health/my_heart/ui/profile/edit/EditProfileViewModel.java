package com.health.my_heart.ui.profile.edit;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.health.my_heart.domain.model.BodyParameters;
import com.health.my_heart.domain.model.User;
import com.health.my_heart.domain.repository.Repository;

import javax.inject.Inject;

public class EditProfileViewModel extends ViewModel {
    public final MutableLiveData<User> userInfo = new MutableLiveData<>();
    public final MutableLiveData<BodyParameters> bodyInfo = new MutableLiveData<>();
    private final Repository repository;

    @Inject
    public EditProfileViewModel(Repository repository) {
        this.repository = repository;
    }


    public void saveChanges(User user, BodyParameters parameters) {
        repository.editProfile(user, parameters);
    }

    public void getUserInfo() {
        repository.getUserInfo().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userInfo.postValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getBodyParameters() {
        repository.getBodyParameters().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BodyParameters bodyParameters = snapshot.getValue(BodyParameters.class);
                bodyInfo.postValue(bodyParameters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
