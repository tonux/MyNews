package com.darcos.julie.mynews.Models;

/**
 * getter and setteur of varaible of articles
 */
public class Article {

    // "section" or "section>subsection"
    private String title;
    // title of article
    private String resume;
    //dd/mm/yyyy published date
    private String date;
    //url of article
    private String url;
    //image url
    private String image;


    public Article() {
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public String getResume() {
        return resume;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }
}
