package com.example.newsapp.db;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class User extends DataSupport {
    private int id;
    private String username;
    private String userAccount;
    private String password;
    private int headPicture;
    private List<News> newsList=new ArrayList<News>();  //comment 和user是多对一的关系，以后记得改

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(int headPicture) {
        this.headPicture = headPicture;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

}
