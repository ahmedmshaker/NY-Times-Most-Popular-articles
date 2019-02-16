package com.example.nytime.dagger.component;

import android.app.Application;

import com.example.nytime.ArticlesApplication;
import com.example.nytime.dagger.ActivityBuilder;
import com.example.nytime.dagger.modules.ContextModule;
import com.example.nytime.dagger.modules.NetworkModule;
import com.example.nytime.dagger.modules.PicassoModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by ashaker on 12/19/2017.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, NetworkModule.class, ActivityBuilder.class, PicassoModule.class, ContextModule.class})
public interface AppComponent {
    void inject(ArticlesApplication app);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder url(@Named("url") String url);


        AppComponent build();
    }

}
