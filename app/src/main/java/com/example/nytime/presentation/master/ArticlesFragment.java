package com.example.nytime.presentation.master;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nytime.presentation.MainActivity;
import com.example.nytime.R;
import com.example.nytime.common.Constants;
import com.example.nytime.data.entities.Article;
import com.example.nytime.presentation.ItemClickListener;
import com.example.nytime.presentation.adapters.ArticlesAdapter;
import com.example.nytime.presentation.details.ArticlesDetailsFragment;
import com.example.nytime.presentation.viewmodel.ArticlesViewModel;
import com.example.nytime.presentation.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class ArticlesFragment extends Fragment implements ItemClickListener<Article> {

    private ArticlesViewModel mViewModel;
    @BindView(R.id.articles_recycler)
    RecyclerView articlesRecyclerView;

    @Inject
    ArticlesAdapter adapter;

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    public static ArticlesFragment newInstance() {
        return new ArticlesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.articles_fragment, container, false);
        ButterKnife.bind(this, view);

        initViews();
        setupToolbar();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity() , viewModelFactory).get(ArticlesViewModel.class);
    }

    void initViews() {
        adapter.setItemClickListenr(this);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        articlesRecyclerView.setAdapter(adapter);
    }

    private void setupToolbar(){
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    void loadData() {
        mViewModel.loadArticles().observe(this, new Observer<List<Article>>() {

            @Override
            public void onChanged(@Nullable List<Article> articles) {
                adapter.setArticles(articles);
            }
        });
    }

    @Override
    public void onItemClick(int position, Article model) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.ARTICLE , model);
        ((MainActivity) getActivity()).replaceCurrentFragment(bundle , new ArticlesDetailsFragment());
    }
}
