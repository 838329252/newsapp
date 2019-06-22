package com.example.newsapp.db;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Relate extends DataSupport {
    int id ;
    int news_id;
    private News news;
    private int relate_newsId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public int getRelate_newsId() {
        return relate_newsId;
    }

    public void setRelate_newsId(int relate_newsId) {
        this.relate_newsId = relate_newsId;
    }
}
