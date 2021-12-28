package com.health.my_heart.ui.content.categories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.health.my_heart.domain.repository.Repository;

import javax.inject.Inject;

public class ArticlesViewModel extends ViewModel {
    MutableLiveData<String> article;
    MutableLiveData<DataSnapshot> dataSnapshotCategories;
    private final Repository repository;


    @Inject
    public ArticlesViewModel(Repository repository) {
        this.repository = repository;
        article = new MutableLiveData<>();
        dataSnapshotCategories = new MutableLiveData<>();
    }

    public void loadArticle(String id) {
        repository.getArticle(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                article.postValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void loadCategories(String name) {
        repository.getCategories(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataSnapshotCategories.postValue(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}