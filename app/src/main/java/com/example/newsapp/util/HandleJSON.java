package com.example.newsapp.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.newsapp.db.Collect;
import com.example.newsapp.db.Comment;
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.News;
import com.example.newsapp.db.Picture;
import com.example.newsapp.db.Relate;
import com.example.newsapp.db.User;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static org.litepal.LitePalApplication.getContext;


public class HandleJSON {
    private String responseData;
    private static JDBC jdbc=new JDBC();
    public  static void handleNews(JSONArray jsonArray){
        try{
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                News news=new News();
                news.setNews_id(jsonObject.getInt("id"));
                news.setTitle(jsonObject.getString("title"));
                news.setColumn(jsonObject.getString("column"));
                news.setAuthor(jsonObject.getString("author"));
                news.setContent(jsonObject.getString("content"));
                news.setTime(jsonObject.getString("time"));
                news.setLayoutType(jsonObject.getInt("layoutType"));
                news.save();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public static void handlePicture(JSONArray jsonArray){
        try{
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Picture picture=new Picture();
                picture.setNews_id(jsonObject.getInt("news_id"));
                picture.setPictureUrl(jsonObject.getInt("pictureUrl"));
                picture.save();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public static void handleRelate(JSONArray jsonArray){
        try{
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Relate relate=new Relate();
                relate.setNews_id(jsonObject.getInt("news_id"));
                relate.setRelate_newsId(jsonObject.getInt("relate_newsId"));
                relate.save();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public static int handleLogin(String response){
        int userCount=0;
        try{
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("user");
            String count=jsonObject.getString("number");
            JSONArray jsonArray1=new JSONArray(count);
            JSONObject jsonObject1=jsonArray1.getJSONObject(0);
            userCount=jsonObject1.getInt("COUNT(*)");
            handleUserInfo(jsonArray);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return userCount;
    }
    public static void handleUserInfo(JSONArray jsonArray){
        try{
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                User user=new User();
                user.setUsername(jsonObject.getString("username"));
                user.setUserAccount(jsonObject.getString("userAccount"));
                user.setPassword(jsonObject.getString("password"));
                user.setHeadPicture(jsonObject.getString("headPicture"));
                user.setUser_id(jsonObject.getInt("id"));
                user.save();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public static void handleNewsPictureRelate(String jsonData){
        try{
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONObject jsonObject1=jsonObject.getJSONObject("newsAndpicture");
            JSONArray jsonArray=jsonObject.getJSONArray("relate");
            JSONObject jsonObject2=jsonObject1.getJSONObject("news2");
            JSONArray jsonArray1=jsonObject1.getJSONArray("picture");
            JSONArray jsonArray2=jsonObject2.getJSONArray("news");
            handleNews(jsonArray2);
            handlePicture(jsonArray1);
            handleRelate(jsonArray);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public static void handleNewsDetail(String jsonData){
        try{
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONObject jsonObject1=jsonObject.getJSONObject("caulaudaudl");
            String collect=jsonObject.getString("collect");
            JSONArray jsonArray=new JSONArray(collect);
            JSONObject jsonObject2=jsonArray.getJSONObject(0);
            int collectCount=jsonObject2.getInt("COUNT(*)");

            JSONObject jsonObject3=jsonObject1.getJSONObject("caulaudanl");
            String newsdislike=jsonObject1.getString("newsdislike");
            JSONArray jsonArray1=new JSONArray(newsdislike);
            JSONObject jsonObject4=jsonArray1.getJSONObject(0);
            int newsdislikeCount=jsonObject4.getInt("COUNT(*)");

            JSONObject jsonObject5=jsonObject3.getJSONObject("caulaud");
            String newslike=jsonObject3.getString("newslike");
            JSONArray jsonArray2=new JSONArray(newslike);
            JSONObject jsonObject6=jsonArray2.getJSONObject(0);
            int newslikeCount=jsonObject6.getInt("COUNT(*)");

            JSONObject jsonObject7=jsonObject5.getJSONObject("caul");
            String userdislike=jsonObject5.getString("userdislike");
            JSONArray jsonArray3=new JSONArray(userdislike);
            JSONObject jsonObject8=jsonArray3.getJSONObject(0);
            int userdislikeCount=jsonObject8.getInt("COUNT(*)");


            JSONArray jsonArray4=jsonObject7.getJSONArray("comment");
            if (jsonArray4.length()!=0){
                for(int i=0;i<jsonArray4.length();i++){
                    JSONObject jsonObject10=jsonArray4.getJSONObject(i);
                    Comment comment=new Comment();
                    comment.setUser_id(jsonObject10.getInt("user_id"));
                    comment.setNews_id(jsonObject10.getInt("news_id"));
                    comment.setTime(jsonObject10.getString("time"));
                    comment.setContent(jsonObject10.getString("content"));
                    comment.save();
                    User user=new User();
                    user.setUsername(jsonObject10.getString("username"));
                    user.setUserAccount(jsonObject10.getString("userAccount"));
                    user.setPassword(jsonObject10.getString("password"));
                    user.setHeadPicture(jsonObject10.getString("headPicture"));
                    user.setUser_id(jsonObject10.getInt("user_id"));
                    user.save();
                }
            }
            String userlike=jsonObject7.getString("userlike");
            JSONArray jsonArray5=new JSONArray(userlike);
            JSONObject jsonObject9=jsonArray5.getJSONObject(0);
            int userlikeCount=jsonObject9.getInt("COUNT(*)");

            SharedPreferences sp3 =  PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor  editor3 = sp3.edit();
            editor3.putInt("collectCount",collectCount);
            editor3.putInt("newsdislikeCount",newsdislikeCount);
            editor3.putInt("newslikeCount",newslikeCount);
            editor3.putInt("userdislikeCount",userdislikeCount);
            editor3.putInt("userlikeCount",userlikeCount);
            editor3.apply();

        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public static int handleCount(String response){
        int count=0;
        try{
            JSONObject jsonObject=new JSONObject(response);
            String colletcount=jsonObject.getString("collectCount");
            JSONArray jsonArray=new JSONArray(colletcount);
            JSONObject jsonObject1=jsonArray.getJSONObject(0);
            count =jsonObject1.getInt("COUNT(*)");

        }catch(JSONException e){
            e.printStackTrace();
        }
        return count;
    }
    public static void  handleComment(String response){
        try{
            JSONArray jsonArray=new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Comment comment=new Comment();
                comment.setUser_id(jsonObject.getInt("user_id"));
                comment.setNews_id(jsonObject.getInt("news_id"));
                comment.setTime(jsonObject.getString("time"));
                comment.setContent(jsonObject.getString("content"));
                comment.save();

            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public static void  handleCollect(String response){
        try{
            JSONArray jsonArray=new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Collect collect=new Collect();
                collect.setUser_id(jsonObject.getInt("user_id"));
                collect.setNews_id(jsonObject.getInt("news_id"));
                collect.save();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

}
