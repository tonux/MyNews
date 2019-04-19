package com.darcos.julie.mynews.Models;


import com.darcos.julie.mynews.Models.MostPopular.MostPopular;
import com.darcos.julie.mynews.Models.MostPopular.ResultMostPopular;
import com.darcos.julie.mynews.Models.Search.Doc;
import com.darcos.julie.mynews.Models.Search.Search;
import com.darcos.julie.mynews.Models.TopStories.Result;
import com.darcos.julie.mynews.Models.TopStories.TopStories;

import java.util.List;

public class ArticleList {

    /**
     * added to a list of items and initializes their parameter
     *
     * @param listArticle empty list
     * @param topStories top Stories api
     */
    public static void listTopStories(List<Article> listArticle, TopStories topStories) {
        for (Result result : topStories.getResults()) {
            Article article = new Article();

            //if no image displays a default image else app crash
            if (result.getMultimedia().size() != 0) {
                article.setImage(result.getMultimedia().get(0).getUrl());
            } else {
                article.setImage(null);
            }

            article.setUrl(result.getUrl());

            article.setResume(result.getTitle());

            //if not subsection show only section else "section>subsection"
            if (result.getSubsection().equals("")) {
                article.setTitle(result.getSection());
            } else {
                article.setTitle(result.getSection() + " > " + result.getSubsection());
            }

            article.setDate(date(result.getCreatedDate()));

            listArticle.add(article);
        }

    }

    /**
     * added to a list of items and initializes their parameter
     *
     * @param listArticle empty list
     * @param search Search api
     */
    public static void listSearchArticle(List<Article> listArticle, Search search) {
        for (Doc result : search.getResponse().getDocs()) {
            Article article = new Article();

            //if no image displays a default image else app crash
            if (result.getMultimedia().size() != 0) {
                article.setImage("https://static01.nyt.com/" + result.getMultimedia().get(0).getUrl());
            } else {
                article.setImage(null);
            }

            article.setUrl(result.getWebUrl());

            article.setResume(result.getHeadline().getMain());

            article.setTitle(result.getSectionName());

            article.setDate(date(result.getPubDate()));

            listArticle.add(article);
        }
    }

    /**
     * added to a list of items and initializes their parameter
     *
     * @param listArticle empty list
     * @param mostPopular MostPopular api
     */
    public static void listMostPopular(List<Article> listArticle, MostPopular mostPopular) {
        for (ResultMostPopular result : mostPopular.getResults()) {
            Article article = new Article();

            //if no image displays a default image else app crash
            if (result.getMedia().get(0).getMediaMetadata().size() != 0) {
                article.setImage(result.getMedia().get(0).getMediaMetadata().get(0).getUrl());
            } else {
                article.setImage(null);
            }

            article.setUrl(result.getUrl());

            article.setResume(result.getTitle());

            article.setTitle(result.getSection());

            article.setDate(date(result.getPublishedDate()));

            listArticle.add(article);
        }

    }

    /**
     * formating date of api
     *
     * @param date date of api
     * @return formating date dd/mm/yyyy
     */
    public static String date(String date) {
        String year = date.substring(2, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);

        date = day + "/" + month + "/" + year;
        return date;
    }
}
