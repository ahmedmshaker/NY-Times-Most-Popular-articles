package com.example.nytime.dagger.modules;

/**
 * Created by ashaker on 12/4/2017.
 */

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


@Singleton
@Module
public class NetworkModule {

    //String mBaseUrl;
//
//    @Inject
//    @Named("url") // replaced by @Inject
//    String mBaseUrl;
//


    @Provides
    @Singleton
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    @Provides
    @Singleton
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @Singleton
    public Interceptor provideNetworkIntercepter(@Named("hasNetwork") boolean hasNetwork) {
        return chain -> {
            Request request = chain.request();


            if (hasNetwork)
                /*
                 *  If there is Internet, get the cache that was stored 5 seconds ago.
                 *  If the cache is older than 5 seconds, then discard it,
                 *  and indicate an error in fetching the response.
                 *  The 'max-age' attribute is responsible for this behavior.
                 */
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
            return chain.proceed(request);
        };
    }


    @Provides
    @Singleton
    @Named("hasNetwork")
    public boolean hasNetwork(Context context) {
        boolean isConnected = false;// Initial Value
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
            isConnected = true;
        return isConnected;
    }

    @Provides
    @Singleton
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); //10MB Cache
    }

    @Provides
    @Singleton
    public File cacheFile(Application context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }


    @Provides
    @Singleton
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache, Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient, RxJava2CallAdapterFactory rxJava2CallAdapterFactory, @Named("url") String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .baseUrl(url)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }
}