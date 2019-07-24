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
    private JDBC jdbc=new JDBC();
    private boolean itemBottomVisibility;
    public ShowNews(String column, LinearLayout container,boolean itemBottomVisibility){
        this.column=column;
        this.container=container;
        this.newsList=jdbc.findNewsByColumnForCover(column);
        this.itemBottomVisibility=itemBottomVisibility;
        if (itemBottomVisibility==true){
            this.newsList=searchNewsInfoForTopNews();
        }
        if(newsList.size()!=0){
            showNewsInfo();
        }
    }
    public ShowNews(List<News> list, LinearLayout container,boolean itemBottomVisibility){
        this.newsList=list;
        this.container=container;
        this.itemBottomVisibility=itemBottomVisibility;
        if(newsList.size()!=0){
            showNewsInfo();
        }
    }
    public ShowNews( LinearLayout container,boolean itemBottomVisibility,List<News> newsList) {
        this.container = container;
        this.newsList = newsList;
        this.itemBottomVisibility=itemBottomVisibility;
        if (newsList.size()!=0){
            showRelateNewsInfo();
        }
    }
    private void showRelateNewsInfo() {
        for (News news : newsList) {
            String title = news.getTitle();
            int id = news.getNews_id();
            List<Picture> pictureList = new ArrayList<Picture>();
            pictureList = jdbc.findPictureByNewsId(id);
            String column = news.getColumn();
            if(pictureList.size()!=0){
                BindItemSmall bindItemSmall = new BindItemSmall(R.layout.item_picture_small, container, title, pictureList, column, itemBottomVisibility, id);
            }

        }
    }
    private void showNewsInfo(){
        for( News news :newsList){
            int layoutType=news.getLayoutType();
            String title=news.getTitle();
            int id=news.getNews_id();
            List<Picture> pictureList =new ArrayList<Picture>();
            pictureList=jdbc.findPictureByNewsId(id);
            String column=news.getColumn();
            if(pictureList.size()!=0){
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
    }
    public List searchNewsInfoForTopNews(){
        List<News> templeList=new ArrayList<News>();
        List<News> list=new ArrayList<News>();
        int i=0;
        int j=0;
        templeList=jdbc.findAll("News");
        boolean[]  bool = new boolean[20];
        for(i=0;i<4;i++){
            News news=new News();
            Random rand = new Random();
            if(templeList.size()!=0){
                do{j=rand.nextInt(templeList.size());}   //防止出现重复的随机数
                while(bool[j]);
                bool[j] = true;
            news=templeList.get(j);
            list.add(news);}
        }
        return list;
    }
}
