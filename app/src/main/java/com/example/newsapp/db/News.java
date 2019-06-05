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
    private List<Integer> pictureList=new ArrayList<Integer>();
    private int layoutType;
    private String column;
    private int like;
    private int dislike;
    private List<Integer> relatedList =new ArrayList<Integer>();
    private List<Comment> commentList = new ArrayList<Comment>();
    private List<User> userList=new ArrayList<User>();

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

    public List<Integer> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Integer> pictureList) {
        this.pictureList = pictureList;
    }

    public List<Integer> getRelatedList() {
        return relatedList;
    }

    public void setRelatedList(List<Integer> relatedList) {
        this.relatedList = relatedList;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
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
}
