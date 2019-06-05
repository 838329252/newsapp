package com.example.newsapp.db;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class JDBC extends DataSupport{
    public void getDatabase(){
        LitePal.getDatabase();
    }
    public void InsertDataToNews(String title,String author,String content,
                                 String time,List pictureList,int layoutType,
                                 String column,int like,int dislike,List relatedList){
        News news=new News();
        news.setTitle(title);
        news.setAuthor(author);
        news.setContent(content);
        news.setTime(time);
        news.setPictureList(pictureList);
        news.setLayoutType(layoutType);
        news.setColumn(column);
        news.setLike(like);
        news.setDislike(dislike);
        news.setRelatedList(relatedList);
        news.save();
    }
    public void InsertDataToComment(String content,int like,int news_id,int user_id){
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setLike(like);
        comment.setNews_id(news_id);
        comment.setUser_id(user_id);
        comment.save();
    }
    public void InsertDataToUser(String username,String userAccount,String password,int headPicture){
        User user=new User();
        user.setUsername(username);
        user.setUserAccount(userAccount);
        user.setPassword(password);
        user.setHeadPicture(headPicture);
        user.save();
    }
    public List findNewsByColumnForCover (String column){
        List list=DataSupport.
                select("title","pictureList","layoutType","column").
                where("column=?",column).
                order("id desc").
                find(News.class);
        return list;
    }
    public void deleteAll(String className){
        switch (className){
            case "News":
                DataSupport.deleteAll(News.class);break;
            case "Comment":
                DataSupport.deleteAll(Comment.class);break;
            case "User":
                DataSupport.deleteAll(User.class);break;
        }
    }

}
