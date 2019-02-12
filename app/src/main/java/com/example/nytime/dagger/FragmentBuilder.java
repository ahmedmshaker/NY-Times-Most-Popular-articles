package com.example.nytime.dagger;

import com.example.nytime.presentation.master.ArticlesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentBuilder {

    @ContributesAndroidInjector
    ArticlesFragment getArticlesFragment();
}
