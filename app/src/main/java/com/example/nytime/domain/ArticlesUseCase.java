package com.example.nytime.domain;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.nytime.common.Constants;
import com.example.nytime.common.EspressoIdlingResource;
import com.example.nytime.data.entities.Article;
import com.example.nytime.data.entities.ArticlesResponse;
import com.example.nytime.data.services.ArticlesService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArticlesUseCase {

    private ArticlesService articlesService;
    private CompositeDisposable compositeDisposable;

    @Inject
    public ArticlesUseCase(ArticlesService articlesService , CompositeDisposable compositeDisposable) {
        this.articlesService = articlesService;
        this.compositeDisposable = compositeDisposable;
    }

    public LiveData<List<Article>> getArticles() {
        final MutableLiveData<List<Article>> data = new MutableLiveData<>();

        EspressoIdlingResource.increment();

        Observable<ArticlesResponse> articlesResponseObservable = articlesService.getArticles(Constants.API_KEY);


        articlesResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticlesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ArticlesResponse articlesResponse) {
                        data.postValue(articlesResponse.getArticles());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error", e.getMessage());
                        EspressoIdlingResource.decrement();


                    }

                    @Override
                    public void onComplete() {

                        EspressoIdlingResource.decrement();

                    }
                });

        return data;
    }
}
