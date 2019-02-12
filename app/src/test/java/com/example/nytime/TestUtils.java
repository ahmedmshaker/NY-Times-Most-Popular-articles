package com.example.nytime;

import com.example.nytime.common.Constants;
import com.example.nytime.data.entities.ArticlesResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestUtils {

    private String getJson(String path) {
        URL uri = this.getClass().getClassLoader().getResource(path);
        File file = new File(uri.getPath());
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

     ArticlesResponse getArticles() {

        return provideJson().fromJson(getJson("articles.json") ,ArticlesResponse.class );
    }

    private Gson provideJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return  gsonBuilder.create();
    }

    private OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(provideJson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.URL)
                .client(okHttpClient())
                .build();
    }

}
