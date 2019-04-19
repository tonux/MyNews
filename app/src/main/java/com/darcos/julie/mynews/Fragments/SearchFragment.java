package com.darcos.julie.mynews.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.darcos.julie.mynews.Activities.SearchActivity;
import com.darcos.julie.mynews.Activities.WebViewActivity;
import com.darcos.julie.mynews.Models.Article;
import com.darcos.julie.mynews.Models.ArticleList;
import com.darcos.julie.mynews.Models.Search.Search;
import com.darcos.julie.mynews.R;
import com.darcos.julie.mynews.Utils.ItemClickSupport;
import com.darcos.julie.mynews.Utils.TimesStreams;
import com.darcos.julie.mynews.Views.TimesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    //FOR DATA
    private Disposable disposable;
    // 2 - Declare list of users (GithubUser) & Adapter
    private List<Article> list;
    private TimesAdapter adapter;
    private String beginDate;
    private String endDate;
    private String querySearch;
    private String newsDesk;
    @BindView(R.id.fragment_main_swipe_container_search)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fragment_main_recycler_view_search)
    RecyclerView recyclerView;

    private callback mCallback;

    // interface callback to retrieve value entered by the user
    public interface callback {
        public String beginDate();

        public String endDate();

        public String querySearch();

        public String newsDesk();
    }


    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        //initializes with callback
        this.beginDate = mCallback.beginDate();
        this.endDate = mCallback.endDate();
        this.querySearch = mCallback.querySearch();
        this.newsDesk = mCallback.newsDesk();

        //Call during UI creation
        this.configureRecyclerView();
        //Execute stream after UI creation
        this.executeHttpRequestWithRetrofit();
        // Configure the SwipeRefreshLayout
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }


    // Create callback to parent activity
    private void createCallbackToParentActivity() {
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (callback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnButtonClickedListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // Configure the SwipeRefreshLayout
    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // when user click on article open on a webView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_article_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Intent webView = new Intent(SearchFragment.this.getContext(), WebViewActivity.class);
                        webView.putExtra("url", adapter.getUrl(position));
                        webView.putExtra("title", adapter.getResume(position));
                        startActivity(webView);

                        Log.e("TAG", "Position : " + position);
                    }
                });
    }

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        //Reset list
        this.list = new ArrayList<>();
        //Create adapter passing the list of users
        this.adapter = new TimesAdapter(this.list, Glide.with(this));
        //Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        //Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit() {

        this.disposable = TimesStreams.streamSearch(querySearch, newsDesk, beginDate, endDate).subscribeWith(new DisposableObserver<Search>() {
            @Override
            public void onNext(Search articles) {
                //number of article return by api
                int i = articles.getResponse().getMeta().getHits();

                //if 0 article
                if (i == 0) {
                    noArticle();

                } else {
                    updateUI(articles);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "Error SearchFragment " + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //updateUI with  list of article
    private void updateUI(Search articles) {
        swipeRefreshLayout.setRefreshing(false);
        this.list.clear();
        ArticleList.listSearchArticle(this.list, articles);
        adapter.notifyDataSetChanged();
    }

    //if no article show popup
    private void noArticle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("No articles found");
        builder.setMessage("Try to change the parameters of your search");
        builder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SearchFragment.this.getContext(), SearchActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

