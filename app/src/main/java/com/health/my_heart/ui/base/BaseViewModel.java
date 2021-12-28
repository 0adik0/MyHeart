package com.health.my_heart.ui.base;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {
    protected final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }


}
