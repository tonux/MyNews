package com.darcos.julie.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import com.darcos.julie.mynews.Models.Article;
import com.darcos.julie.mynews.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class TimesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_item_title)
    TextView textView;

    @BindView(R.id.fragment_main_item_website)
    TextView texViewWebsite;
    @BindView(R.id.fragment_main_item_image)
    ImageView imageView;
    @BindView(R.id.fragment_main_item_date)
    TextView dateView;

    public TimesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //update view
    public void updateWithTimesUser(Article article, RequestManager glide) {

        this.textView.setText(article.getTitle());

        this.texViewWebsite.setText(article.getResume());

        this.dateView.setText(article.getDate());

        glide.load(article.getImage()).into(imageView);


        //if no image displays a default image else app crash
        if (article.getImage() == null) {
            glide.load(R.drawable.news).into(imageView);
        } else {
            glide.load(article.getImage()).into(imageView);
        }
    }
}
