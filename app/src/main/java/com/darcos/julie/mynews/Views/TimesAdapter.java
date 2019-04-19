package com.darcos.julie.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.darcos.julie.mynews.Models.Article;
import com.darcos.julie.mynews.R;


import java.util.List;

public class TimesAdapter extends RecyclerView.Adapter<TimesViewHolder> {

    private List<Article> list;
    private RequestManager glide;

    // CONSTRUCTOR
    public TimesAdapter(List<Article> list, RequestManager glide) {
        this.list = list;
        this.glide = glide;
    }


    @Override
    public TimesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_article_item, parent, false);

        return new TimesViewHolder(view);
    }

    // UPDATE VIEW
    @Override
    public void onBindViewHolder(TimesViewHolder viewHolder, int position) {
        viewHolder.updateWithTimesUser(this.list.get(position), this.glide);
    }

    public String getUrl(int position) {
        return this.list.get(position).getUrl();
    }


    public String getResume(int position) {
        return this.list.get(position).getTitle();
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
