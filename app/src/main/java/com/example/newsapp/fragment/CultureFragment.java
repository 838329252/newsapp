package com.example.newsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.R;
import com.example.newsapp.SearchActivity;
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.News;
import com.example.newsapp.util.HttpUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class CultureFragment extends Fragment {
    private JDBC jdbc;
    private LinearLayout cultureLayout;
    private ShowNews showNews;
    private List pictureList;
    private List relatedList;
    private boolean itemBottomVisibility;
    private TextView fakeSearchView;
    private RefreshLayout refreshLayout;
    private View view;
    private int countNews;
    private int limitA=0;
    private int limitB=3;
    private HttpUtil httpUtil;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            limitA=0;
            limitB=3;
            DataSupport.deleteAll(News.class,"column=?","CULTURE");
            httpUtil=new HttpUtil();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    countNews=httpUtil.sendOkHttpForNewsOfDifferentColumn("CULTURE",limitA,limitB);
                    Message message=new Message();
                    message.what=2;
                    handler.sendMessage(message);
                }
            }).start();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_culture, container, false);
        cultureLayout=view.findViewById(R.id.cultureLinearLayout);
        cultureLayout.removeAllViews();
        fakeSearchView=(TextView)view.findViewById(R.id.fake_searchEdit) ;
        itemBottomVisibility=false;
        fakeSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchActivity.class);
                getContext().startActivity(intent);
            }
        });
        setPullRefresher();
        return view;
    }

    private void setPullRefresher(){
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                cultureLayout.removeAllViews();
                limitA=0;
                limitB=3;
                DataSupport.deleteAll(News.class,"column=?","CULTURE");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpUtil.sendOkHttpForNewsOfDifferentColumn("CULTURE",limitA,limitB);
                        Message message=new Message();
                        message.what=0;
                        handler.sendMessage(message);
                    }
                }).start();

                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                limitA=limitA+3;
                Log.d("CultureFragment","limitA="+limitA);
                limitB=3;
                if(limitA<countNews){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            httpUtil.sendOkHttpForNewsOfDifferentColumn("CULTURE",limitA,limitB);
                            Message message=new Message();
                            message.what=1;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:
                    showNews=new ShowNews("CULTURE",cultureLayout,itemBottomVisibility);break;
                case 1:
                    List<News> list=DataSupport.where("column=?","CULTURE").limit(limitB).offset(limitA).find(News.class);
                    showNews=new ShowNews(list,cultureLayout,itemBottomVisibility);break;
                case 2:
                    showNews=new ShowNews("CULTURE",cultureLayout,itemBottomVisibility);
                default:break;
            }
        }
    };
}
