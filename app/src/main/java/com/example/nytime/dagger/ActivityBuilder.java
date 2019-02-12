package com.example.nytime.dagger;


import com.example.nytime.dagger.scopes.PerActivity;
import com.example.nytime.presentation.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBuilder {


    @PerActivity
    @ContributesAndroidInjector(modules = {ApiModule.class, ActivityModule.class, FragmentBuilder.class})
    MainActivity getMainActivity();
}



