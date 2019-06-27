package com.example.newsapp.db;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class User extends DataSupport {
    private int id;
    private String username;
    private String userAccount;
    private String password;
    private String headPicture;
    private List<News> newsList=new ArrayList<News>();
    private List<Comment> commentList=new ArrayList<Comment>();
    private List<LikeNews> likeNewsList=new ArrayList<LikeNews>();
    private List<DislikeNews> dislikeNewsList=new ArrayList<DislikeNews>();
    private List<Collect> collectList=new ArrayList<Collect>();

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

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<LikeNews> getLikeNewsList() {
        return likeNewsList;
    }

    public void setLikeNewsList(List<LikeNews> likeNewsList) {
        this.likeNewsList = likeNewsList;
    }

    public List<DislikeNews> getDislikeNewsList() {
        return dislikeNewsList;
    }

    public void setDislikeNewsList(List<DislikeNews> dislikeNewsList) {
        this.dislikeNewsList = dislikeNewsList;
    }

    public List<Collect> getCollectList() {
        return collectList;
    }

    public void setCollectList(List<Collect> collectList) {
        this.collectList = collectList;
    }
}
