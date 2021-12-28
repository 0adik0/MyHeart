package com.health.my_heart.ui.profile.indicators;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.health.my_heart.domain.model.BodyParameters;
import com.health.my_heart.domain.model.HealthIndicator;
import com.health.my_heart.domain.repository.Repository;
import com.health.my_heart.ui.base.BaseViewModel;

import javax.inject.Inject;

public class IndicatorsViewModel extends BaseViewModel {
    public MutableLiveData<DataSnapshot> snapshotAllIndicators;
    public MutableLiveData<DataSnapshot> snapshotSpecificIndicators;
    public MutableLiveData<BodyParameters> bodyParameters;
    private final Repository repository;

    @Inject
    public IndicatorsViewModel(Repository repository) {
        this.repository = repository;
        this.snapshotAllIndicators = new MutableLiveData<>();
        this.snapshotSpecificIndicators = new MutableLiveData<>();
        this.bodyParameters = new MutableLiveData<>();
    }

    public void getBodyParameters() {
        repository.getBodyParameters().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BodyParameters value = snapshot.getValue(BodyParameters.class);
                bodyParameters.postValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void saveIndicator(String name, HealthIndicator indicator) {
        repository.saveIndicator(name, indicator);
    }

    public void getIndicatorsByName(String name) {
        repository.getIndicatorsByName(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null)
                    snapshotSpecificIndicators.postValue(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getAllIndicators() {
        repository.getAllIndicators().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    repository.initUserIndicators().addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                            getAllIndicators();
                    });
                } else {
                    snapshotAllIndicators.postValue(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void updateImt(String imt) {
        repository.updateImt(imt);
    }
}
