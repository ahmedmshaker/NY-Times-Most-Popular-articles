package com.example.nytime.presentation.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.nytime.data.entities.Article;
import com.example.nytime.domain.ArticlesUseCase;

import java.util.List;

public class ArticlesViewModel extends ViewModel {

    private ArticlesUseCase articlesUseCase;


     ArticlesViewModel(ArticlesUseCase articlesUseCase) {
        this.articlesUseCase = articlesUseCase;
    }

    public LiveData<List<Article>> loadArticles() {
        return articlesUseCase.getArticles();
    }

}
