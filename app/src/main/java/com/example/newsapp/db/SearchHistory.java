package com.example.newsapp.db;

import org.litepal.crud.DataSupport;

public class SearchHistory extends DataSupport {
    private int id;
    private int user_id;
    private String search;
    private User user;
    private int search_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSearch_id() {
        return search_id;
    }

    public void setSearch_id(int search_id) {
        this.search_id = search_id;
    }
}
