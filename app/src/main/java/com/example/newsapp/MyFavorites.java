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

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MyFavorites extends AppCompatActivity {
    private Toolbar toolbar;
    private List<Collect> list;
    private LinearLayout container;
    private boolean itemBottomVisibility;
    private List<News> newsList=new ArrayList<News>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list=DataSupport.where("user_id=?",GlobalData.getUserId()+"").find(Collect.class);
        Log.d("collecList",list+"");
        container=(LinearLayout)findViewById(R.id.myFavorites);
        container.removeAllViews();
        if(list.size()!=0){
            for(Collect collect:list){
                News news=DataSupport.where("id=?",collect.getNews_id()+"").findFirst(News.class);
                newsList.add(news);
                itemBottomVisibility=true;
            }
            ShowNews showNews=new ShowNews(container,itemBottomVisibility,newsList);
        }
    }
}
