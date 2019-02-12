package com.example.nytime.presentation;

import android.os.Bundle;
import android.support.test.espresso.IdlingRegistry;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.nytime.R;
import com.example.nytime.common.EspressoIdlingResource;
import com.example.nytime.presentation.master.ArticlesFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends DaggerAppCompatActivity {


    private static final String CURRENT_FRAGMENT_TAG = "current_fragment";

    @Inject
    ArticlesFragment articlesFragment;

    @Inject
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            initView();
        else
            handleOnConfigurationChanged();


    }


    void initView() {
        replaceCurrentFragment(articlesFragment , false);
    }

    void handleOnConfigurationChanged() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_FRAGMENT_TAG);
        replaceCurrentFragment(fragment , false);
    }


    public void replaceCurrentFragment(Bundle data, Fragment fragment) {
        fragment.setArguments(data);
        replaceCurrentFragment(fragment , true);
    }

    public void replaceCurrentFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, CURRENT_FRAGMENT_TAG);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(articlesFragment.getClass().getName());
        fragmentTransaction.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!compositeDisposable.isDisposed())
            compositeDisposable.isDisposed();
    }
}
