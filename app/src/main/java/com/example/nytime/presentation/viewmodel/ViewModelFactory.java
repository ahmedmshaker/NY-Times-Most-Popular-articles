package com.example.nytime.presentation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.nytime.domain.ArticlesUseCase;

import javax.inject.Inject;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private ArticlesUseCase articlesUseCase;

    @Inject
    public ViewModelFactory(ArticlesUseCase articlesUseCase) {
        this.articlesUseCase = articlesUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ArticlesViewModel(articlesUseCase);
    }
}
