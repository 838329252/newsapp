package com.example.newsapp.BindItem;

import android.util.Log;
import android.widget.LinearLayout;

import com.example.newsapp.R;
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.News;
import com.example.newsapp.db.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShowNews {
    private String column;
    private LinearLayout container;
    private List<News> newsList;
    private JDBC jdbc;
    private boolean itemBottomVisibility;
    public ShowNews(String column, LinearLayout container,boolean itemBottomVisibility){
        this.column=column;
        this.container=container;
        jdbc=new JDBC();
        this.newsList=jdbc.findNewsByColumnForCover(column);
        this.itemBottomVisibility=itemBottomVisibility;
        if (itemBottomVisibility==true){
            this.newsList=searchNewsInfoForTopNews();
        }
        showNewsInfo();
    }
    public ShowNews( LinearLayout container,boolean itemBottomVisibility,List<News> newsList) {
        this.container = container;
        this.newsList = newsList;
        this.itemBottomVisibility=itemBottomVisibility;
        showRelateNewsInfo();
    }
    private void showRelateNewsInfo() {
        for (News news : newsList) {
            String title = news.getTitle();
            int id = news.getId();
            Log.d("HIHIshow", id + "");
            List<Picture> pictureList = new ArrayList<Picture>();
           /* pictureList = jdbc.findPictureByNewsId(id);*/ //这里的bug是怎么肥事呢！！！
            Log.d("HIHIpicture2", pictureList + "");
            String column = news.getColumn();
            BindItemSmall bindItemSmall = new BindItemSmall(R.layout.item_picture_small, container, title, pictureList, column, itemBottomVisibility, id);
        }
    }
    private void showNewsInfo(){
        for( News news :newsList){
            int layoutType=news.getLayoutType();
            String title=news.getTitle();
            int id=news.getId();
            List<Picture> pictureList =new ArrayList<Picture>();
            pictureList=jdbc.findPictureByNewsId(id);
            Log.d("HIHIpicture",pictureList+"");
            String column=news.getColumn();
            switch(layoutType){
                case R.layout.item_picture_large:
                    BindItemLarge bindItemLarge=new BindItemLarge(layoutType,container,title,pictureList,column,itemBottomVisibility,id);
                    break;
                case R.layout.item_picture_small:
                    BindItemSmall bindItemSmall=new BindItemSmall(layoutType,container,title,pictureList,column,itemBottomVisibility,id);
                    break;
                case R.layout.item_picture_three:
                    BindItemThree bindItemThree=new BindItemThree(layoutType,container,title,pictureList,column,itemBottomVisibility,id);
                    break;
                case R.layout.item_picture_two:
                    BindItemTwo bindItemTwo=new BindItemTwo(layoutType,container,title,pictureList,column,itemBottomVisibility,id);
                    break;

            }
        }
    }
    private List searchNewsInfoForTopNews(){
        List<News> templeList=new ArrayList<News>();
        List<News> list=new ArrayList<News>();
        int i=0;
        templeList=jdbc.findAll("News");
        Log.d("Peggy",templeList.size()+"");
        for(i=0;i<5;i++){
            News news=new News();
            Random rand = new Random();
            if(templeList.size()!=0){
            news=templeList.get(rand.nextInt(templeList.size()));
            list.add(news);}
        }
        return list;
    }
}
