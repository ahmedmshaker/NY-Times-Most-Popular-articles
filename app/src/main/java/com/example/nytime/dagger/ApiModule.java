package com.example.nytime.dagger;


import com.example.nytime.dagger.scopes.PerActivity;
import com.example.nytime.data.services.ArticlesService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    @PerActivity
    public ArticlesService getService(Retrofit retrofit) {
        return retrofit.create(ArticlesService.class);
    }


}
