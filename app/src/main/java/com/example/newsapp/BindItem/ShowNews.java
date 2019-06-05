package com.example.newsapp.BindItem;

import android.widget.LinearLayout;

import com.example.newsapp.R;
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.News;

import java.util.List;

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
        showNewsInfo();
    }
    private void showNewsInfo(){
        for( News news :newsList){
            int layoutType=news.getLayoutType();
            String title=news.getTitle();
            List pictureList=news.getPictureList();
            String column="BUSINESS";
            switch(layoutType){
                case R.layout.item_picture_large:
                    BindItemLarge bindItemLarge=new BindItemLarge(layoutType,container,title,pictureList,column,itemBottomVisibility);
                    break;
                case R.layout.item_picture_small:
                    BindItemSmall bindItemSmall=new BindItemSmall(layoutType,container,title,pictureList,column,itemBottomVisibility);
                    break;
                case R.layout.item_picture_three:
                    BindItemThree bindItemThree=new BindItemThree(layoutType,container,title,pictureList,column,itemBottomVisibility);
                    break;
                case R.layout.item_picture_two:
                    BindItemTwo bindItemTwo=new BindItemTwo(layoutType,container,title,pictureList,column,itemBottomVisibility);
                    break;

            }
        }
    }
}
