package com.example.nytime.dagger.modules;

import android.app.Application;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by ashaker on 12/10/2017.
 */

@Module
public class PicassoModule {

    @Provides
    @Singleton
    public Picasso picasso(Application context, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .build();
    }

    @Provides
    @Singleton
    public OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }
//    @Provides
//    @Singleton
//    Application provideApplication(Application application) {
//        return application;
//    }

}