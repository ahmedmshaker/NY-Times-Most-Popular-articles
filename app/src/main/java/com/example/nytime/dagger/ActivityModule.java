package com.example.nytime.dagger;


import com.example.nytime.dagger.scopes.PerActivity;
import com.example.nytime.presentation.master.ArticlesFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    @Provides
    @PerActivity
    public ArticlesFragment provideArticleFragment() {
        return new ArticlesFragment();
    }
}
