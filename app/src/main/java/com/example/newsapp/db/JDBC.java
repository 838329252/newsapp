package com.example.newsapp.db;

import android.util.Log;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class JDBC extends DataSupport{
    public void getDatabase(){
        LitePal.getDatabase();
    }
    public void InsertDataToNews(String title,String author,String content,
                                 String time,int layoutType,
                                 String column){
        News news=new News();
        news.setTitle(title);
        news.setAuthor(author);
        news.setContent(content);
        news.setTime(time);
        news.setLayoutType(layoutType);
        news.setColumn(column);
        news.save();
    }
    public void InsertDataToComment(String content,int news_id,int user_id,String time){
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setNews_id(news_id);
        comment.setUser_id(user_id);
        comment.setTime(time);
        comment.save();
    }
    public void InsertDataToUser(String username,String userAccount,String password,String headPicture){
        User user=new User();
        user.setUsername(username);
        user.setUserAccount(userAccount);
        user.setPassword(password);
        user.setHeadPicture(headPicture);
        user.save();
    }
    public void InsertDataToLikeNews(int news_id,int user_id){
        LikeNews likeNews=new LikeNews();
        likeNews.setNews_id(news_id);
        likeNews.setUser_id(user_id);
        likeNews.save();
    }
    public void InsertDataToDislikeNews(int news_id,int user_id){
        DislikeNews dislikeNews=new DislikeNews();
        dislikeNews.setNews_id(news_id);
        dislikeNews.setUser_id(user_id);
        dislikeNews.save();
    }
    public void InsertDataToCollect(int news_id,int user_id){
        Collect collect=new Collect();
        collect.setNews_id(news_id);
        collect.setUser_id(user_id);
        collect.save();
    }
    public void InsertDataToPicture(int news_id,int picture_id){

    }
    public List findNewsByColumnForCover (String column){
        List list=DataSupport.
                select("news_id","title","layoutType","column").
                where("column=?",column).
                find(News.class);
        return list;
    }
    public List findNewsById (int id){
        List list=DataSupport.
                select("news_id","title","column").
                where("news_id=?",id+"").
                find(News.class);
        return list;
    }
    public List findPictureByNewsId(int id){
        List list=DataSupport.
                where("news_id=?",id+"").find(Picture.class);
        return list;
    }
    public List findRelateByNewsId(int id){
        List list=DataSupport.
                where("news_id=?",id+"").find(Relate.class);
        return list;
    }
    public List findAll(String className){
        List list=new ArrayList<>();
        switch (className){
            case "News":
                list= DataSupport.findAll(News.class);break;
            case "Comment":
                list=DataSupport.findAll(Comment.class);break;
            case "User":
                list=DataSupport.findAll(User.class);break;
            case "Relate":
                list=DataSupport.findAll(Relate.class);break;
            case "Picture":
                list=DataSupport.findAll(Picture.class);break;
        }
        return list;
    }
    public static void deleteAll(){

        DataSupport.deleteAll(News.class);
        DataSupport.deleteAll(Comment.class);
        DataSupport.deleteAll(LikeNews.class);
        DataSupport.deleteAll(DislikeNews.class);
        DataSupport.deleteAll(LikeNews.class);
        DataSupport.deleteAll(Relate.class);
        DataSupport.deleteAll(Picture.class);
        DataSupport.deleteAll(Collect.class);
        DataSupport.deleteAll(User.class);
    }
    public List findLatestComment(int news_id){
        List list=DataSupport.where("news_id=? ",news_id+"")
                        .order("id desc")
                        .limit(3)
                        .find(Comment.class);
        return list;
    }
    public List findCommentBynewsId(int news_id){
        List list=DataSupport.where("news_id=?",news_id+"")
                .order("id desc")
                .find(Comment.class);
        return list;
    }
}
