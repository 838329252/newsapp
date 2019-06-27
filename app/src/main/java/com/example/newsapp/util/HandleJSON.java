package com.example.newsapp.util;

import com.example.newsapp.db.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HandleJSON {
    private String responseData;

    public static void handleNews(String jsonData){
        try{
            JSONArray jsonArray=new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                News news=new News();
                news.setId(jsonObject.getInt("id"));
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
}
