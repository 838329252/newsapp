package com.example.newsapp.db;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class News extends DataSupport {
    private int id;
    private String title;
    private String author;
    private String content;
    private String time;
    private int layoutType;
    private String column;
    private List<Picture> pictureList=new ArrayList<Picture>();
    private List<Relate> relatedList =new ArrayList<Relate>();
    private List<Comment> commentList = new ArrayList<Comment>();
    private List<User> userList=new ArrayList<User>();
    private List<LikeNews> likeNewsList=new ArrayList<LikeNews>();
    private List<DislikeNews> dislikeNewsList=new ArrayList<DislikeNews>();
    private List<Collect> collectList=new ArrayList<Collect>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;

    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public List<Relate> getRelatedList() {
        return relatedList;
    }

    public void setRelatedList(List<Relate> relatedList) {
        this.relatedList = relatedList;
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
}
