package com.example.newsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newsapp.BindItem.BindItemLarge;
import com.example.newsapp.BindItem.BindItemThree;
import com.example.newsapp.BindItem.BindItemTwo;
import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.R;
import com.example.newsapp.SearchActivity;
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.News;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class BusinessFragment extends Fragment {
    private JDBC jdbc;
    private List businessNewsList;
    private List pictureList;
    private List relatedList;
    private LinearLayout businessLayout;
    private ShowNews showNews;
    private boolean itemBottomVisibility;
    private TextView fakeSearchView;
    private RefreshLayout refreshLayout;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_business, container, false);
        fakeSearchView=(TextView)view.findViewById(R.id.fake_searchEdit) ;
        businessLayout=view.findViewById(R.id.businessLinearLayout);
        businessLayout.removeAllViews();
        itemBottomVisibility=false;
        showNews=new ShowNews("BUSINESS",businessLayout,itemBottomVisibility);
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


    //设置下拉刷新和上拉加载
    private void setPullRefresher(){
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }
}

