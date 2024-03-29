package com.example.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.db.Collect;
import com.example.newsapp.db.GlobalData;
import com.example.newsapp.db.News;
import com.example.newsapp.util.HttpUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MyFavorites extends AppCompatActivity {
    private Toolbar toolbar;
    private List<Collect> list;
    private LinearLayout container;
    private boolean itemBottomVisibility;
    private List<News> newsList=new ArrayList<News>();
    private HttpUtil httpUtil=new HttpUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);
        DataSupport.deleteAll(Collect.class);
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        container=(LinearLayout)findViewById(R.id.myFavorites);
        container.removeAllViews();
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpUtil.sendOkHttpForMy(GlobalData.getUserId(),"collect");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list=DataSupport.where("user_id=?",GlobalData.getUserId()+"").find(Collect.class);
                        if(list.size()!=0){
                            for(Collect collect:list){
                                News news=DataSupport.where("news_id=?",collect.getNews_id()+"").findFirst(News.class);
                                newsList.add(news);
                                itemBottomVisibility=true;
                            }
                            ShowNews showNews=new ShowNews(container,itemBottomVisibility,newsList);
                        }
                    }
                });
            }
        }).start();


    }
}
